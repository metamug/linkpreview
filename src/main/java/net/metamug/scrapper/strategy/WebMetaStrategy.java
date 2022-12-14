package net.metamug.scrapper.strategy;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.metamug.scrapper.entity.MetaData;
import net.metamug.scrapper.entity.WebMetaData;
import net.metamug.scrapper.util.DownloadManager;
import net.metamug.scrapper.util.ImageUtil;
import static net.metamug.scrapper.util.ScrapperUtil.getHost;
import static net.metamug.scrapper.util.ScrapperUtil.makeAbsoluteURL;
import static net.metamug.scrapper.util.StrategyHelper.getFirstAttributeValue;
import static net.metamug.scrapper.util.StrategyHelper.getFirstLongText;
import static net.metamug.scrapper.util.StrategyHelper.getFirstText;
import static net.metamug.scrapper.util.StrategyHelper.getMetaTagContent;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

/**
 *
 * @author bhatt
 */
public class WebMetaStrategy implements MetaStrategy {

    public static byte[] getBytes(String path) {
        byte[] buffer = null;
        try {
            //since these domains are static .. specific headers are not needed.
            buffer = Jsoup.connect(path)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                    .referrer("http://www.google.com")
                    .ignoreContentType(true)
                    .timeout(12000)
                    .followRedirects(true)
                    .execute().bodyAsBytes();
        } catch (IOException ex) {
            Logger.getLogger(DownloadManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return buffer;
    }

    public static Response getResponse(String path) {
        Response res = null;
        try {
            //since these domains are static .. specific headers are not needed.
            res = Jsoup.connect(path)
                    .userAgent(USER_AGENT)
                    .referrer("http://www.google.com")
                    .ignoreContentType(true)
                    .header("Accept-Encoding", "gzip,deflate,sdch")
                    .header("Accept-Language", "en")
                    .timeout(12000)
                    .maxBodySize(0)
                    //.ignoreHttpErrors(true)
                    .followRedirects(true)
                    .execute();
        } catch (IOException ex) {
            Logger.getLogger(DownloadManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return res;
    }
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";

    Document doc;
    String url;

    public WebMetaStrategy(Document doc, String url) {
        this.doc = doc;
        this.url = url;
    }

    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }

    @Override
    public WebMetaData getMeta() {
        WebMetaData meta = new WebMetaData();
        meta.setTitle(StringEscapeUtils.unescapeHtml4(getTitle()));
        meta.setDomain(getHost(url));
        meta.setType(MetaData.HTML);
        meta.setPicture(getThumbnailURL());
        meta.setDescription(StringEscapeUtils.unescapeHtml4(getDescription()));

        return meta;
    }

    public String getTitle() {
        String title;

        if ((title = getMetaTagContent(doc, "meta[name=og:title]")) != null) {
        } else if ((title = getMetaTagContent(doc, "meta[property=og:title]")) != null) {
        } else if ((title = getMetaTagContent(doc, "meta[name=twitter:title]")) != null) {
        } else if ((title = getMetaTagContent(doc, "meta[itemprop=name]")) != null) { //video objects on yahoo are better represented with schema.org item title
        } else if (StringUtils.isNotBlank(title = doc.title())) {
        } else if ((title = getFirstLongText(doc, "h1", 2)) != null) {
        }
        //remove new lines
        title = title.replace("\n", "").replace("\r", "").replace("\t", "");
        return (title);
    }

    public String getFaviconURL(String URL) {
        String faviconURL;
        if ((faviconURL = getFaviconURLFromLinkTag(URL)) != null && !faviconURL.isEmpty()) {
        } else if ((faviconURL = getFaviconURLFromRoot(URL)) != null && !faviconURL.isEmpty()) {
        } else {
//        faviconURL = "assets/img/cup.png";
            faviconURL = "http://www.google.com/s2/favicons?domain=" + URL; //"img/cup.png";
        }
        faviconURL = faviconURL.split("\\?")[0];
        return faviconURL;
    }

    public String getFaviconURLFromLinkTag(String url) {
        String faviconURL = null;

        Elements links = doc.select("link[rel$=icon]");
        for (Element link : links) {
            faviconURL = link.absUrl("href");
        }
        if (faviconURL == null) {
            Elements elements = doc.head().select("meta[itemprop=image]");
            for (Element element : elements) {
                faviconURL = element.absUrl("content");
            }
        }

//        return makeAbsoluteURL(faviconURL, url);
        return faviconURL;
    }

    private String getFaviconURLFromRoot(String URL) {

        try {
            //final Pattern pattern = Pattern.compile("(^((?!:).)*$)://(.*?)/.*");
            //final Matcher matcher = pattern.matcher(URL);
            //matcher.find();
            //return matcher.replaceAll("$1") + "://" + matcher.replaceAll("$2") + "/favicon.ico";
            URL url = new URL(URL);
            return url.getProtocol() + "://" + url.getHost() + "/favicon.ico";
        } catch (MalformedURLException ex) {
            Logger.getLogger(WebMetaStrategy.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public String getDescription() {
        String desc;
        if (StringUtils.isNotBlank(desc = getMetaTagContent(doc, "meta[itemprop=description]"))) { //video objects
        } else if (StringUtils.isNotBlank(desc = getMetaTagContent(doc, "meta[name=og:description]"))) {
        } else if (StringUtils.isNotBlank(desc = getMetaTagContent(doc, "meta[property=og:description]"))) {
        } else if (StringUtils.isNotBlank(desc = getMetaTagContent(doc, "meta[name=twitter:description]"))) {
        } else if (StringUtils.isNotBlank(desc = getMetaTagContent(doc, "meta[name=description]"))) {
        } else if (StringUtils.isNotBlank(desc = getFirstText(doc, "div[itemprop*=description]"))) { //since description itemprop is multivalued
        }

        //check if first paragraph has more data.
        if (desc == null || desc.length() < MetaData.MIN_DESC_LENGTH) { //take p tags
            String pTagText = getFirstLongText(doc, "p", MetaData.MIN_DESC_LENGTH);
            desc = (pTagText != null) ? pTagText : desc;
        }

        // Create short description
        if (desc != null) {
            if (desc.length() >= WebMetaData.MAX_DESC_LENGTH) {
                desc = desc.substring(0, WebMetaData.MAX_DESC_LENGTH) + "...";
            }
            return (desc);
        } else {
            return null;
        }
    }

    public String getThumbnailURL() {
        String thumbURL;

        //<meta itemprop="image" content=""> on yahoo
        if ((thumbURL = getMetaTagContent(doc, "meta[itemprop=image]")) != null) {
        } else if ((thumbURL = getMetaTagContent(doc, "meta[itemprop=thumbnailUrl]")) != null) {
        } else if ((thumbURL = getMetaTagContent(doc, "meta[property=og:image]")) != null) {
        } else if ((thumbURL = getMetaTagContent(doc, "meta[name=og:image]")) != null) {
        } else if ((thumbURL = getMetaTagContent(doc, "meta[name=twitter:image]")) != null) {
        } else if ((thumbURL = getMetaTagContent(doc, "meta[itemprop=image_url]")) != null) {
        } else if ((thumbURL = getMetaTagContent(doc, "link[itemprop=image]")) != null) { //<link itemprop="image" href="//cdn.sstatic.net/stackoverflow/img/apple-touch-icon.png">
        } else if ((thumbURL = getMetaTagContent(doc, "link[rel=image_src]")) != null) {
        } else if ((thumbURL = getFirstAttributeValue(doc, "img[class*=wp-image]", "abs:src")) != null) {
            //} else if ((thumbURL = getFirstAttributeValue("img[alt*=wp-image]","abs:src")) != null) {
        } else if ((thumbURL = getMetaTagContent(doc, "link[rel=apple-touch-icon]")) != null) {
        } else if ((thumbURL = getMetaTagContent(doc, "meta[name=msapplication-TileImage]")) != null) { //<meta name="msapplication-TileImage" content="https://a2.sndcdn.com/assets/images/sc-icons/win8-6e938b6a.png">
        } else {
            Elements img = doc.getElementsByTag("img");
            List<String> list = new ArrayList<>();
            img.stream().forEach((el) -> {
                list.add(el.attr("abs:src"));
            });
            thumbURL = ImageUtil.getLargestImage(list);
        }

        String fullPath = makeAbsoluteURL(thumbURL, url);

        //Check if its possible to return path without query string
        if (StringUtils.isNotBlank(fullPath)) {
            String withoutQueryStringPath = fullPath.split("\\?")[0];
            Response r = DownloadManager.getResponse(withoutQueryStringPath);
            
            if (r.statusCode() != 200) {
                return withoutQueryStringPath;
            } else {
                return fullPath;
            }
            
        } else {
            return null;
        }

    }

    private List<String> getBreadCrumbs() {
        List<String> breadCrumbs = new ArrayList();
        Elements elements = doc.select("matchesOwn(>)");
        for (Element e : elements) {
            Node siblingNode = e.nextSibling();
            if (siblingNode.nodeName().equals("a")) {

            }
        }
        return null;
    }

    private String getType() {
        try {
            if (doc == null) {
                doc = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
            }
            Elements type = doc.select("meta[property=og:type]");
            if (!type.isEmpty()) {
                return type.attr("content");
            }
        } catch (Exception ex) {
            //Logger.getLogger(HTMLParser.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return "html";
    }

    String[] ignoreKeysArr = {"com", "www", "asp", "php", "co", "in", "html", "net", "", "html", "and", "through", "my", "not",
        "is", "was", "are", "were", "a", "an", "i", "am", "will", "to", "of", "you", "u", "still", "do", "how", "what", "why", "when",
        "the", "with", "without", "within", "for", "if", "else", "then", "yo", "cfm"};

    private List<String> getKeys(String URL) {
        final boolean echo = true;

        List<String> ignoreKeys = Arrays.asList(ignoreKeysArr);
        List<String> keys = new ArrayList<String>();
        URL = URL.substring(URL.indexOf("//") >= 0 ? URL.indexOf("//") : -2 + 2);
        String keyStore = URL + " " + getTitle();
        String[] keyArr = keyStore.split("[0-9\\./_ -]");
        for (int i = keyArr.length - 1; i >= 0; i--) {
            if (!keys.contains(keyArr[i]) && !ignoreKeys.contains(keyArr[i])) {
                keys.add(keyArr[i].toLowerCase());
            }
        }
        if (echo) {
            System.out.println(keys);
        }
        return keys;
    }

    private List<String> getKeys(Elements imgElements, String attr) {
        List<String> keys = new ArrayList<String>();
        List<String> ignoreKeys = Arrays.asList(ignoreKeysArr);
        final boolean echo = true;

        for (Element e : imgElements) {
            String keyArr[] = e.attr(attr).split("[0-9\\./_ -]");
            for (int i = 0; i < keyArr.length; i++) {
                if (!keys.contains(keyArr[i]) && !ignoreKeys.contains(keyArr[i])) {
                    keys.add(keyArr[i].toLowerCase());
                }
            }
        }
        if (echo) {
            System.out.println(keys);
        }

        return keys;
    }

}
//    <div itemprop="video" itemscope="" itemtype="http://schema.org/VideoObject" id="yui_3_9_1_1_1393142546040_2000">
//                                    <meta itemprop="name" content="Jaguar Attacks Caiman Crocodile - CLOSE UP FOOTAGE">
//                <meta itemprop="description" content="A jaguar ambushes a stunned caiman - by exploding from a river like a crocodile.&nbsp;The 20-stone cat then sinks its teeth into the eight-foot reptile before dragging it back across the water and into the jungle.&nbsp;The unbelievable kill sequence was captured by videographer Sally Eagle in the Pantanal Wetlands of Brazil.">
//                <meta itemprop="thumbnailUrl" content="http://l3.yimg.com/bt/api/res/1.2/XIW7DQG29uZ6kQ0sr_k_oQ--/YXBwaWQ9eW5ld3M7Zmk9ZmlsbDtoPTY5OTtxPTc1O3c9MTA0OQ--/http://media.zenfs.com/en-GB/video/video.barcroft.com/jaguar_caiman.jpg">
//                <meta itemprop="duration" content="PT1M3S">
//                    <div id="mediavideoplayercollectionca" class="yom-mod mod-video-player" style="background-color:#000000;min-height:351px;">
//                        <div class="bd" id="yui_3_9_1_1_1393142546040_1999">
//                            <div id="player-cont-mediavideoplayercollectionca" class="player-container" style="height:351px;"><div class="yui3-videoplayer-content medium desktop" id="yui_3_9_1_1_1393142546040_668"><div class="yui3-videoplayer-main" id="yui_3_9_1_1_1393142546040_667" style="height: 100%;"><div class="accessible-controls" id="yui_3_9_1_1_1393142546040_589" style="display: block;"><button class="play">Play</button><button class="seek-back">Seek Back 5 Seconds</button><button class="seek-forward">Seek Forward 5 Seconds</button><button class="volume-up">Volume Up</button><button class="volume-down">Volume Down</button><button class="mute">Mute</button><button class="next-video">Next Video</button><button class="hotkey-menu">Open Hotkey Menu</button></div><div class="yui3-videoplayer-controls" id="yui_3_9_1_1_1393142546040_486"><div class="bar disable" id="yui_3_9_1_1_1393142546040_487"><div class="main"><button class="item play-pause yui3-videoplayer-transparent pause"><span class=""></span></button></div><div class="seek" id="yui_3_9_1_1_1393142546040_2007"><div class="slider" id="yui_3_9_1_1_1393142546040_2006"><div class="buffer" style="width: 50.152023320690844%;"></div><div class="progress" style="width: 35.634579820079615%;"></div><div class="handle" id="yui_3_9_1_1_1393142546040_2014" style="left: 35.634579820079615%;"></div></div><div class="time show" style="width: 48px; margin-left: -24px; left: 35.634579820079615%;">0:41</div><div class="vseek" style="display: none;"><div class="vseek-thumb"></div><div class="vseek-time"></div></div></div><div class="aux"><div class="item settings yui3-videoplayer-transparent"><span class=""></span></div></div></div><div class="settings-panel-box embed-hidden yui3-videoplayer-transparent" id="yui_3_9_1_1_1393142546040_573" style="display: none;"><div class="settings-panel"><table class=""><tbody class=""><tr class="share-embeds" style=""><td class="share-embeds-hdr">share</td><td class="share-embeds-buttons"><a class="button tumblr-icon" target="_blank" href="https://www.tumblr.com/share/video?embed=http%3A%2F%2Fin.news.yahoo.com%2Fvideo%2Fplaylist%2Fbarcroft-media-videos%2Fjaguar-attacks-caiman-crocodile-close-163000211.html&amp;caption=A%20jaguar%20ambushes%20a%20stunned%20caiman%20-%20by%20exploding%20from%20a%20river%20like%20a%20crocodile.%C2%A0The%2020-stone%20cat%20then%20sinks%20its%20teeth%20into%20the%20eight-foot%20reptile%20before%20dragging%20it%20back%20across%20the%20water%20and%20into%20the%20jungle.%C2%A0The%20unbelievable%20kill%20sequence%20was%20captured%20by%20videographer%20Sally%20Eagle%20in%20the%20Pantanal%20Wetlands%20of%20Brazil."></a><a class="button fb-icon" target="_blank" href="http://www.facebook.com/sharer.php?t=Jaguar%20Attacks%20Caiman%20Crocodile%20-%20CLOSE%20UP%20FOOTAGE&amp;u=http%3A%2F%2Fin.news.yahoo.com%2Fvideo%2Fplaylist%2Fbarcroft-media-videos%2Fjaguar-attacks-caiman-crocodile-close-163000211.html"></a><a class="button twitter-icon" target="_blank" href="https://twitter.com/intent/tweet?text=Jaguar%20Attacks%20Caiman%20Crocodile%20-%20CLOSE%20UP%20FOOTAGE&amp;url=http%3A%2F%2Fin.news.yahoo.com%2Fvideo%2Fplaylist%2Fbarcroft-media-videos%2Fjaguar-attacks-caiman-crocodile-close-163000211.html&amp;via=Yahoo"></a><a class="button mail-icon" href="mailto:?subject=Jaguar Attacks Caiman Crocodile - CLOSE UP FOOTAGE&amp;body=http%3A%2F%2Fin.news.yahoo.com%2Fvideo%2Fplaylist%2Fbarcroft-media-videos%2Fjaguar-attacks-caiman-crocodile-close-163000211.html%0D%0DA jaguar ambushes a stunned caiman - by exploding from a river like a crocodile.??The 20-stone cat then sinks its teeth into the eight-foot reptile before dragging it back across the water and into the jungle.??The unbelievable kill sequence was captured by videographer Sally Eagle in the Pantanal Wetlands of Brazil."></a><button class="button embed-button">Embed</button></td></tr><tr class="embed-link"><th class="">link</th><td class=""><div class="text"><input class="text embed-link-text" readonly="readonly"><span class="text embed-link-button"><button class="button embed-chainlink-button"></button></span></div></td></tr><tr class="embed-code"><th class="">embed</th><td class=""><div class="text embed-valid-code" style="display: none;"><input class="text embed-code-text"><span class="text embed-code-button"><button class="button embed-chainlink-button"></button></span></div><div class="text embed-error"><input class="text embed-error-text" readonly="readonly"></div></td></tr><tr class="captions"><th class="caption-hdr">captions</th><td class="caption-buttons" id="yui_3_9_1_1_1393142546040_445"><button class="text cc-on">on</button><button class="text cc-off on">off</button><button class="text cc-feedback">feedback</button></td></tr><tr class="size"><th class="">size</th><td class=""><button class="small on"></button><button class="full"></button></td></tr><tr class="volume"><th class="">volume</th><td class=""><button class="mute"><span class=""></span></button><div class="bars" id="yui_3_9_1_1_1393142546040_1814"><div class="bar-1 on"></div><div class="bar-2 on"></div><div class="bar-3 on"></div><div class="bar-4 on"></div><div class="bar-5 on"></div><div class="bar-6"></div></div><a class="button y-logo" target="_blank" href="http://in.news.yahoo.com/video/playlist/barcroft-media-videos/jaguar-attacks-caiman-crocodile-close-163000211.html" style="display: none;"></a></td></tr></tbody></table></div></div></div><div class="yui3-videoplayer-loading" style="display: none;"></div><div class="yui3-videoplayer-slug" style="display: none;"></div><div class="yui3-videoplayer-video" id="yui_3_9_1_1_1393142546040_1998" style="opacity: 1;"><object id="yuiswfyui_3_9_1_1_1393142546040_678" type="application/x-shockwave-flash" data="http://l.yimg.com/rx/builds/4.4.0.1392343259/assets/player.swf" width="100%" height="100%"><param name="allowscriptaccess" value="always"><param name="allowfullscreen" value="true"><param name="wmode" value="opaque"><param name="flashVars" value="yId=yui_3_9_1_1_1393142546040_1&amp;YUISwfId=yuiswfyui_3_9_1_1_1393142546040_678&amp;YUIBridgeCallback=SWF.eventHandler&amp;allowedDomain=in.news.yahoo.com"></object></div></div></div></div>
//
//                        </div>
//                    </div>
//                </div>
