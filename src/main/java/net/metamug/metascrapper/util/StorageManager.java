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
public class StorageManager {

    public static String upload(BufferedImage image) {
        return null;
    }

    public static String uploadFromURL(String path) {
        if (StringUtils.isNotBlank(path)) {
            byte[] buffer = getBytes(path);
            return upload(new ByteArrayInputStream(buffer),
                    buffer.length,
                    OTHER_IMAGE_FOLDER + "/" + RandomStringUtils.random(32) + "." + IMAGE_TYPE);
        } else {
            return null;
        }
    }

    /**
     * Method to generate Profile picture of publishers Tile image is not stored
     * in the database Tile image file name is same as profile picture, so it
     * can be referenced from the same metaimage id as profile picture but in a
     * different folder on s3 Not Stored in database
     *
     * @param path
     * @return filename
     */
    public static String uploadPublisherPicture(String path) {

        if (StringUtils.isNotBlank(path)) {
            MetaImage metaImage = new MetaImage();
            metaImage.setUrl(path);
            String randomString = RandomStringUtils.randomAlphanumeric(32) + "_" + RandomStringUtils.randomAlphanumeric(32);
            metaImage.setId("metamug_publisher_" + randomString + "." + IMAGE_TYPE);
            metaImage.setHeight(mugshotThumbWidth);
            metaImage.setWidth(mugshotThumbWidth);
            upload(metaImage, PUBLISHER_MUGSHOT_TILE_FOLDER);

            metaImage = new MetaImage();
            metaImage.setUrl(path);
            metaImage.setId("metamug_publisher_" + randomString + "." + IMAGE_TYPE);
            metaImage.setHeight(mugshotWidth);
            metaImage.setWidth(mugshotWidth);
            return upload(metaImage, PUBLISHER_MUGSHOT_FOLDER).getId();

        } else {
            return null;
        }
    }

    /**
     * Publisher picture uploaded after cropping. Not Stored in database.
     */
    public static String uploadPublisherImage(MetaImage im, int x, int y) {
        byte[] buffer = getBytes(im.getUrl());
        BufferedImage buffImage;
        String randomString = RandomStringUtils.randomAlphanumeric(32) + "_" + RandomStringUtils.randomAlphanumeric(32);
        String fileName = "metamug_publisher_" + randomString + "." + IMAGE_TYPE;
        try {
            buffImage = ImageIO.read(new ByteArrayInputStream(buffer));
            //resize to fit in crop screen.
            if (buffImage.getWidth() > MAX_IMAGE_WIDTH) {
                buffImage = resize(buffImage, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_TO_WIDTH, MAX_IMAGE_WIDTH, Scalr.OP_ANTIALIAS);
            }

            //user cropped image
            buffImage = crop(buffImage, x, y, im.getWidth(), im.getHeight(), Scalr.OP_ANTIALIAS);

            //resize to tile 
            buffImage = resize(buffImage, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_TO_WIDTH, mugshotThumbWidth, Scalr.OP_ANTIALIAS);
            upload(buffImage, PUBLISHER_MUGSHOT_TILE_FOLDER + fileName);

            //resize to profile picture
            buffImage = resize(buffImage, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_TO_WIDTH, mugshotWidth, Scalr.OP_ANTIALIAS);
            upload(buffImage, PUBLISHER_MUGSHOT_FOLDER + fileName);

            return fileName;

        } catch (IOException ex) {
            Logger.getLogger(StorageManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Upload Post Images Stored in database Hence returns a MetaImage Object
     */
    public static MetaImage uploadPostImage(MetaImage im) {
        try {
            byte[] buff;
            String fileName, randomString;
            BufferedImage buffImage;

            Response response = StorageManager.getResponse(im.getUrl());
            buff = response.bodyAsBytes();
            buffImage = ImageIO.read(new ByteArrayInputStream(buff));

            /*Check if image already exists*/
            if (im.getId().equals("0")) {
                randomString = RandomStringUtils.randomAlphanumeric(32)
                        + "_" + RandomStringUtils.randomAlphanumeric(62);
                fileName = "metamug_post_" + randomString + "." + IMAGE_TYPE;
            } else {//update the image on s3
                fileName = im.getId();
            }

//            String folder = null;
            if (buffImage.getWidth() >= StorageManager.LARGER_IMAGE_SIZE) {
                buffImage = ImageManipulation.resizeLargeThumbnail(buffImage, StorageManager.LARGER_IMAGE_SIZE);
//                folder = POST_MUGSHOT_FOLDER ;//+ "/" + StorageManager.LARGER_IMAGE_SIZE + "/";
            } else if (buffImage.getWidth() >= StorageManager.SMALLER_IMAGE_SIZE) { //compact view eg. 125
                buffImage = ImageManipulation.squareThumb(buffImage, SMALLER_IMAGE_SIZE);
//                folder = POST_MUGSHOT_FOLDER + "/" + StorageManager.SMALLER_IMAGE_SIZE + "/";
            }

            im.setWidth(buffImage.getWidth());
            im.setHeight(buffImage.getHeight());
            im.setId(fileName);
            System.out.println("Metamug Thumbnail: " + upload(buffImage, POST_MUGSHOT_FOLDER + im.getId()));
            return im;
        } catch (IOException ex) {
            Logger.getLogger(StorageManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    /**
     * Stored In database
     *
     * @param domain
     * @return MetaImage object
     */
    public static MetaImage uploadFavicon(String domain) {
        MetaImage metaImage = new MetaImage();
        //http://www.google.com/s2/favicons?domain="
        String randomString = RandomStringUtils.randomAlphanumeric(20)
                + "_" + RandomStringUtils.randomAlphanumeric(32);
        String fileName = "metamug_favicon_" + randomString + "." + IMAGE_TYPE;
        metaImage.setId(fileName);
        metaImage.setUrl("https://plus.google.com/_/favicon?domain=" + domain);
        metaImage.setHeight(FAVICON_SIZE);
        metaImage.setWidth(FAVICON_SIZE);
        return upload(metaImage, FAVICON_IMAGE_FOLDER);
    }

    /**
     * Take A metaImage object with url initilized Upload the image on s3 from
     * the url and populate the fields of the MetaImage object.
     *
     * @param MetaImage
     */
    public static MetaImage uploadFavicon(MetaImage im) {
        try {
            byte[] buff;
            String fileName;
            InputStream is;
            BufferedImage buffImage;
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            Response response = StorageManager.getResponse("https://plus.google.com/_/favicon?domain=" + im.getUrl());
//            Response response = StorageManager.getResponse("http://www.google.com/s2/favicons?domain=" + im.getUrl());
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

            if (buffImage.getWidth() > FAVICON_SIZE) {
                //resize to 16x16
                buffImage = resize(buffImage, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_TO_WIDTH, FAVICON_SIZE, Scalr.OP_ANTIALIAS);

                if (buffImage.getHeight() > FAVICON_SIZE) {
                    //crop the image from the center to max height 
                    int y = buffImage.getHeight() / 2 - FAVICON_SIZE / 2;
                    buffImage = crop(buffImage, 0, y, FAVICON_SIZE, FAVICON_SIZE);

                }
            }

            im.setWidth((short) buffImage.getWidth());
            im.setHeight((short) buffImage.getHeight());
            im.setId(fileName);
            System.out.println("Metamug Favicon: " + upload(buffImage, FAVICON_IMAGE_FOLDER + "/16/" + fileName));
            return im;
        } catch (IOException ex) {
            Logger.getLogger(StorageManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Upload Image according to dimensions defined in
     *
     * @param metaImage dimensions
     * @param folder - in the folder
     * @return
     */
    public static MetaImage upload(MetaImage metaImage, String folder) {
        try {
            BufferedImage buffImage;
            Response response = StorageManager.getResponse(metaImage.getUrl());
            buffImage = ImageIO.read(new ByteArrayInputStream(response.bodyAsBytes()));
            if (buffImage.getWidth() > metaImage.getWidth()) {
                //resize to meta image width
                buffImage = resize(buffImage, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_TO_WIDTH, metaImage.getWidth(), Scalr.OP_ANTIALIAS);
                //crop to meta image dimensions
                if (buffImage.getHeight() > metaImage.getHeight()) {
                    //crop the image from the center to max height
                    int center = buffImage.getHeight() / 2 - metaImage.getHeight() / 2;
                    buffImage = crop(buffImage, 0, center, metaImage.getWidth(), metaImage.getHeight());
                }
            }

            upload(buffImage, folder + metaImage.getId());
            return metaImage;
        } catch (IOException ex) {
            Logger.getLogger(StorageManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Upload Image according to bufferedImage Use when custom sizes are needed
     *
     * @param buffImage
     * @param URI
     * @return
     * @throws java.io.IOException
     */
    public static String upload(BufferedImage buffImage, String URI) throws IOException {
        String publicURL;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(buffImage, IMAGE_TYPE, os);
        InputStream inputStream = new ByteArrayInputStream(os.toByteArray());
        //ClientConfiguration max retry
        ObjectMetadata objectMetaData = new ObjectMetadata();
        objectMetaData.setContentLength(os.size());
        objectMetaData.setContentType(IMAGE_CONTENT_TYPE);
        objectMetaData.setCacheControl("public");
        Calendar c = Calendar.getInstance();
        c.setTime(c.getTime());
        c.add(Calendar.MONTH, 6);
        String sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss zzz").format(c.getTime());
        objectMetaData.setHeader("Expires", sdf);

        PutObjectResult por = s3Client.putObject(
                new PutObjectRequest(AWS_S3_BUCKET, URI,
                        inputStream,
                        objectMetaData)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        publicURL = IMAGE_SITE + URI;
        return publicURL;

    }

    public static String upload(InputStream inputStream, long fileSize, String URI) {
        String publicURL;
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
                new PutObjectRequest(AWS_S3_BUCKET, URI,
                        inputStream,
                        objectMetaData)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        publicURL = "http://metamug.net/" + URI;
        //String publicURL = "http://metamug.net" + "/" + AWS_S3_FOLDER + "/" + randomFileName;
        return publicURL;

    }

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

    public static InputStream getInputStream(BufferedImage buffImage, ByteArrayOutputStream os) {
        try {
            ImageIO.write(buffImage, IMAGE_TYPE, os);
            return new ByteArrayInputStream(os.toByteArray());
        } catch (IOException ex) {
            Logger.getLogger(StorageManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    private static final String AWS_S3_BUCKET = "metamug.net";
    private static final String AWS_ACCESS_KEY = "AKIAJSRRZTMKD7T2U73A";
    private static final String AWS_SECRET_KEY = "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";
    private static final String AWS_S3_FOLDER = "images";
//    private static final String AWS_S3_SITE = "metamug.net";
    public final static AWSCredentials credentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY);
    private final static AmazonS3Client s3Client = new AmazonS3Client(credentials);
    public static final int LARGE_IMAGE_SIZE = 340;
    public static final int LARGER_IMAGE_SIZE = 400;
    public static final int SMALL_IMAGE_SIZE = 150;
    public static final int SMALLER_IMAGE_SIZE = 125;
    public static final int LARGER_IMAGE_HEIGHT = 248; //according to golden ratio 400/1.617
    public static final int LARGE_IMAGE_HEIGHT = 210; //according to golden ratio 340/1.617
    public static final int MAX_IMAGE_WIDTH = 320;
    public static final int PUBLISHER_IMAGE_SIZE = 150;
    public static final int FAVICON_SIZE = 16;
    public static final String IMAGE_TYPE = "png";
    public static final String IMAGE_CONTENT_TYPE = "image/png";
    public static final String FAVICON_IMAGE_FOLDER = "images/favicon/";
    public static final String PUBLISHER_IMAGE_FOLDER = "images/publisher/";
    public static final String POST_MUGSHOT_FOLDER = "images/post/";
    public static final String OTHER_IMAGE_FOLDER = "images/others/";
//    public static final String IMAGE_LOCATION = "https://" + AWS_S3_SITE + ".amazonaws.com/" + AWS_S3_BUCKET + "/" + AWS_S3_FOLDER + "/";
    public static final String IMAGE_SITE = "http://metamug.net/";
    public static final String PUBLISHER_MUGSHOT_TILE_FOLDER = "images/pubisher/32/";
    public static final String PUBLISHER_MUGSHOT_FOLDER = "images/pubisher/160/";
    public static final String POST_LARGER_MUGSHOT_FOLDER = "images/post/400/";
    public static final String POST_LARGE_MUGSHOT_FOLDER = "images/post/340/";
    public static final String POST_SMALL_MUGSHOT_FOLDER = "images/post/150/";
    public static final String POST_SMALLER_MUGSHOT_FOLDER = "images/post/125/";
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";
    private static final int mugshotThumbWidth = 32;
    private static final int mugshotWidth = 160;

}
