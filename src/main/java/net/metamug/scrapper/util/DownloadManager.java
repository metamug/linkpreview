/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.metamug.scrapper.util;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import net.metamug.scrapper.entity.MetaImage;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.imgscalr.Scalr;
import static org.imgscalr.Scalr.crop;
import static org.imgscalr.Scalr.resize;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

/**
 *
 * @author deepak Upload Images on S3. Prefix all images with metamug and the
 * image content type So that when image is downloaded by users. It is known to
 * be downloaded from metamug.
 */
public class DownloadManager {


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

}
