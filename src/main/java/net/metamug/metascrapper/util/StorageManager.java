/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.metamug.metascrapper.util;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import net.metamug.metascrapper.entity.MetaImage;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import net.sf.image4j.codec.ico.ICODecoder;
import net.sf.image4j.codec.ico.ICOEncoder;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.imgscalr.Scalr;
import static org.imgscalr.Scalr.OP_ANTIALIAS;
import static org.imgscalr.Scalr.OP_BRIGHTER;
import static org.imgscalr.Scalr.crop;
import static org.imgscalr.Scalr.crop;
import static org.imgscalr.Scalr.resize;
import static org.imgscalr.Scalr.resize;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

/**
 *
 * @author deepak
 */
public class StorageManager {

    private static final String AWS_S3_BUCKET = "metamug-cdn";
    private static final String AWS_ACCESS_KEY = "AKIAJSRRZTMKD7T2U73A";
    private static final String AWS_SECRET_KEY = "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";
    private static final String AWS_S3_FOLDER = "images-2";
    private static final String AWS_S3_SITE = "s3-ap-southeast-1";
    public final static AWSCredentials credentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY);
    private final static AmazonS3Client s3Client = new AmazonS3Client(credentials);
    public static final int LARGE_IMAGE_SIZE = 340;
    public static final int LARGER_IMAGE_SIZE = 400;
    public static final int SMALL_IMAGE_SIZE = 150;
    public static final int SMALLER_IMAGE_SIZE = 125;
    public static final int LARGER_IMAGE_HEIGHT = 248; //according to golden ratio 400/1.617
    public static final int LARGE_IMAGE_HEIGHT = 210; //according to golden ratio 340/1.617
    public static final int MAX_IMAGE_WIDTH = 320;
    public static final String IMAGE_TYPE = "png";
    public static final String IMAGE_CONTENT_TYPE = "image/png";
    public static final String IMAGE_LOCATION = "https://" + AWS_S3_SITE + ".amazonaws.com/" + AWS_S3_BUCKET + "/" + AWS_S3_FOLDER + "/";
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";

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
            Logger.getLogger(StorageManager.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(StorageManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return res;
    }

    public static String upload(InputStream inputStream, long fileSize, String fileName) {
        String publicURL = null;
        //InputStream is = new ByteArrayInputStream(buffer);
        //byte[] buffer = IOUtils.toByteArray(is);
        //ClientConfiguration max retry
        ObjectMetadata objectMetaData = new ObjectMetadata();
        objectMetaData.setContentLength(fileSize);
        objectMetaData.setContentType(IMAGE_CONTENT_TYPE);
        objectMetaData.setCacheControl("public");
        Calendar c = Calendar.getInstance();
        c.setTime(c.getTime());
        c.add(Calendar.MONTH, 6);
        String sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss zzz").format(c.getTime());
//        objectMetaData.setHeader("Expires", "Thu, 21 Mar 2042 08:16:32 GMT");
        objectMetaData.setHeader("Expires", sdf);

        PutObjectResult por = s3Client.putObject(
                new PutObjectRequest(AWS_S3_BUCKET, AWS_S3_FOLDER + "/" + fileName,
                        inputStream,
                        objectMetaData)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        publicURL = IMAGE_LOCATION + fileName;
        //String publicURL = "http://metamug.net" + "/" + AWS_S3_FOLDER + "/" + randomFileName;
        return publicURL;

    }

    public static String uploadFromURL(String path) {
        if (StringUtils.isNotBlank(path)) {
            byte[] buffer = getBytes(path);
            return upload(new ByteArrayInputStream(buffer),
                    buffer.length,
                    FilenameUtils.getBaseName(path));
        } else {
            return null;
        }
    }

    public static String uploadFromURL(String path, int size, int x, int y) {

        if (StringUtils.isNotBlank(path)) {
            byte[] buffer = getBytes(path);
            BufferedImage buffImage = null;
            try {
                buffImage = ImageIO.read(new ByteArrayInputStream(buffer));
                if (buffImage.getWidth() > MAX_IMAGE_WIDTH) {
                    buffImage = resize(buffImage, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_TO_WIDTH, MAX_IMAGE_WIDTH, Scalr.OP_ANTIALIAS);
                }
                buffImage = crop(buffImage, x, y, size, size, Scalr.OP_ANTIALIAS);
            } catch (IOException ex) {
                Logger.getLogger(StorageManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            //crop image

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            String fileName = "metamug_publisher_"
                    + RandomStringUtils.randomAlphanumeric(16)
                    + "." + IMAGE_TYPE;
            return upload(getInputStream(buffImage, bos),
                    bos.size(),
                    fileName);

        } else {
            return null;
        }
    }

    //convert gif to jpg ... they make browser lag..
    public static MetaImage updateImageMap(MetaImage im) {
        try {
            byte[] buff;
            String fileName;
            BufferedImage buffImage;

            Response response = StorageManager.getResponse(im.getUrl());
            buff = response.bodyAsBytes();
            //contentType = response.contentType();
            buffImage = ImageIO.read(new ByteArrayInputStream(buff));
            if (im.getId().equals("0")) {
                fileName = "metamug_" + RandomStringUtils.randomAlphanumeric(20)
                        + "_" + RandomStringUtils.randomAlphanumeric(6)
                        + "." + IMAGE_TYPE;
            } else {
                fileName = im.getId();
            }
            //extn = FilenameUtils.getExtension(im.getUrl());

            if (buffImage.getWidth() >= StorageManager.LARGER_IMAGE_SIZE) {
                buffImage = ImageManipulation.resizeLargeThumbnail(buffImage, StorageManager.LARGER_IMAGE_SIZE);
            } else if (buffImage.getWidth() >= StorageManager.SMALLER_IMAGE_SIZE) { //compact view eg. 125
                buffImage = ImageManipulation.squareThumb(buffImage, SMALLER_IMAGE_SIZE);
            }
            
            im.setWidth((short) buffImage.getWidth());
            im.setHeight((short) buffImage.getHeight());

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            System.out.println("Metamug Thumbnail: " + StorageManager.upload(getInputStream(buffImage, os), os.size(), fileName));
            im.setId(fileName);

        } catch (IOException ex) {
            Logger.getLogger(StorageManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return im;
    }

    public static InputStream getInputStream(BufferedImage buffImage, ByteArrayOutputStream os) {
        try {
            ImageIO.write(buffImage, IMAGE_TYPE, os);
            return new ByteArrayInputStream(os.toByteArray());
        } catch (IOException ex) {
            Logger.getLogger(StorageManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    //favicon
    public static MetaImage updateFaviconImageMap(MetaImage im, int size) {
        try {
            byte[] buff;
            String fileName;
            InputStream is;
            BufferedImage buffImage;
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            Response response = StorageManager.getResponse("http://www.google.com/s2/favicons?domain=" + im.getUrl());
            buff = response.bodyAsBytes();
            //contentType = response.contentType();

            buffImage = ImageIO.read(new ByteArrayInputStream(buff));
            //List<BufferedImage> images = ICODecoder.read(new ByteArrayInputStream(buff));
            //ICOEncoder.write(image, os);
//            buffImage = images.get(0);
            fileName = "metamug_favicon_"
                    + RandomStringUtils.randomAlphanumeric(12)
                    + "_" + RandomStringUtils.randomAlphanumeric(6)
                    + "." + IMAGE_TYPE;
            //extn = IMAGE_TYPE;// FilenameUtils.getExtension(im.getUrl());

            if (buffImage.getWidth() > size) {
                //resize to 16x16
                buffImage = resize(buffImage, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_TO_WIDTH, size, Scalr.OP_ANTIALIAS);

                if (buffImage.getHeight() > size) {
                    //crop the image from the center to max height 
                    int y = buffImage.getHeight() / 2 - size / 2;
                    buffImage = crop(buffImage, 0, y, size, size);

                }
            }

            im.setWidth((short) buffImage.getWidth());
            im.setHeight((short) buffImage.getHeight());
            ImageIO.write(buffImage, IMAGE_TYPE, os);
            is = new ByteArrayInputStream(os.toByteArray());

            System.out.println("Metamug Favicon: " + StorageManager.upload(is, os.size(), fileName));
            //set id after upload is complete
            im.setId(fileName);
        } catch (IOException ex) {
            Logger.getLogger(StorageManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return im;
    }

    public static MetaImage updateImageMap(MetaImage im, int size, int x, int y) {
        try {
            byte[] buff;
            String fileName;
            InputStream is;
            BufferedImage buffImage;
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            Response response = StorageManager.getResponse(im.getUrl());
            buff = response.bodyAsBytes();
            //contentType = response.contentType();

            buffImage = ImageIO.read(new ByteArrayInputStream(buff));
            fileName = "metamug_user_pic_"
                    + RandomStringUtils.randomAlphanumeric(12)
                    + "_" + RandomStringUtils.randomAlphanumeric(6)
                    + "." + IMAGE_TYPE;

            if (buffImage.getWidth() > size) {
                buffImage = crop(buffImage, x, y, size, size);
                buffImage = resize(buffImage, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_TO_WIDTH, size, Scalr.OP_ANTIALIAS);
            }

            im.setWidth((short) buffImage.getWidth());
            im.setHeight((short) buffImage.getHeight());
            ImageIO.write(buffImage, IMAGE_TYPE, os);
            is = new ByteArrayInputStream(os.toByteArray());

            System.out.println("Metamug Publiser Image: " + StorageManager.upload(is, os.size(), fileName));
            //set id after upload is complete
            im.setId(fileName);
        } catch (IOException ex) {
            Logger.getLogger(StorageManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return im;
    }
//        String base64String;
//        try {
//            
//            Base64 b = new Base64();
//            base64String = b.encode(IOUtils.toByteArray(is));
//        } catch (IOException ex) {
//            Logger.getLogger(StorageManager.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }

}
