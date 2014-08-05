/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.metamug.scrapper.strategy;

import net.metamug.scrapper.entity.QnAMetadata;
import net.metamug.scrapper.entity.WebMetaData;
import static net.metamug.scrapper.util.ScrapperUtil.getHost;
import static net.metamug.scrapper.util.StrategyHelper.exists;
import static net.metamug.scrapper.util.StrategyHelper.getFirstText;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author cskksc
 */
public class QnAStrategy extends WebMetaStrategy {

    Element metablock;

    public QnAStrategy(Document doc, String url, Element metablock) {
        super(doc, url);
        this.metablock = metablock;
    }

    @Override
    public WebMetaData getMeta() {
        QnAMetadata meta = new QnAMetadata();
        meta.setTitle(getTitle());
        meta.setDomain(getHost(url));
        meta.setType(WebMetaData.QnA);
        meta.setDescription(getStackoverflowD());
        meta.setPicture(getThumbnailURL());
        return meta;
    }

    public String getStackoverflowD() {
        String desc = "";

        Element element = metablock.select("div.post-text").first();

        if (exists(element, "p")) {
            desc = getFirstText(element, "p");
        }

        if (exists(element, "p + p")) {
            desc += getFirstText(element, "p + p");
//            desc += element.select("p + p").text();
        }

        return desc;

    }

    public String getQuestionAskedTime() {
        //<div class="user-action-time">
        //asked <span title="2010-12-21 04:45:54Z" class="relativetime">Dec 21 '10 at 4:45</span>
        //</div>
        return null;
    }

}
