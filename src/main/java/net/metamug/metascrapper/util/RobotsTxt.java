/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.metamug.metascrapper.util;

//import com.amazonaws.http.HttpResponse;
import crawlercommons.robots.BaseRobotRules;
import crawlercommons.robots.SimpleRobotRules;
import crawlercommons.robots.SimpleRobotRules.RobotRulesMode;
import crawlercommons.robots.SimpleRobotRulesParser;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import static org.apache.http.protocol.HTTP.USER_AGENT;
import org.jsoup.Connection.Response;

/**
 *
 * @author cskksc
 */
public class RobotsTxt {

    String hostId;
    Map<String, BaseRobotRules> robotsTxtRules;
    BaseRobotRules rules;

    URL urlObj;
    SimpleRobotRulesParser robotParser;

    public RobotsTxt(String url) {
        try {
            urlObj = new URL(url);
        } catch (MalformedURLException e) {
            System.out.print("MalformedException occured");
        }

        hostId = urlObj.getProtocol() + "://" + urlObj.getHost()
                + (urlObj.getPort() > -1 ? ":" + urlObj.getPort() : "");
        robotsTxtRules = new HashMap<String, BaseRobotRules>();
        rules = robotsTxtRules.get(hostId);

        if (rules == null) {
            Response response = DownloadManager.getResponse(hostId + "/robots.txt");
            if (response.contentType().matches("[^image|^audio|application]") || response.statusMessage() != null && response.statusCode() == 404) {
                rules = new SimpleRobotRules(RobotRulesMode.ALLOW_ALL);
            } else {
                robotParser = new SimpleRobotRulesParser();
                rules = robotParser.parseContent(hostId, response.bodyAsBytes(),
                        "text/plain", USER_AGENT);
            }
            robotsTxtRules.put(hostId, rules);
        }

    }

    public boolean canAccess(String url) {
        return rules.isAllowed(url);
    }

}
