/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.metamug.metascrapper.util;

import static net.metamug.metascrapper.util.StorageManager.getBytes;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import org.imgscalr.Scalr;
import static org.imgscalr.Scalr.*;

/**
 *
 * @author deepak
 */
public class ImageManipulation {

    public static final int CROP_OFFSET = 40;

    public static BufferedImage resizeLargeThumbnail(BufferedImage buffImage, int width) {
        //resize the image
        buffImage = resize(buffImage, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_TO_WIDTH, width, Scalr.OP_ANTIALIAS);
        int height = (int) (width * 1.0 / 1.617);
        if (buffImage.getHeight() > height) { // full stretch view e.g 401, 450, 600
            //crop the image from the center to max height 
            //int y = buffImage.getHeight() / 2 - height / 2; 
            //cropped from top
            int y = (int) (buffImage.getHeight() * 0.05f);
            buffImage = crop(buffImage, 0, y, width, height);

        }
        return buffImage;
    }

    public static String getLargestImage(List<String> images) {
        String largestImage = null;
        //List<Integer> imageSizes = new ArrayList<>();
//        int[] imageSizes = new int[images.size()];
        long largestImageSize = 0;
        for (String currentImage : images) {
            long currentImageSize = getImageSize(currentImage);
            if (largestImageSize < currentImageSize) {
                largestImageSize = currentImageSize;
                largestImage = currentImage;
            }
        }

        //Collections.sort(imageSizes);
        //imageSizes.get(imageSizes.size() - 1);
        //imageSizes.sort((i1,i2)->i1.compareTo(i2));
        return largestImage;
    }

    public static long getImageSize(String resourceFile) {
        long length = 0;
        try {
            URL url = new URL(resourceFile);
            System.setProperty("http.agent", StorageManager.USER_AGENT);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("HEAD");
            //urlConnection.getInputStream();
            length = (urlConnection).getContentLengthLong();
        } catch (MalformedURLException ex) {
            Logger.getLogger(ImageManipulation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ImageManipulation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return length;
    }

    public static Dimension getImageDimension(String path) {
        Dimension d = null;
        try {

            byte[] bytes = getBytes(path);
            BufferedImage imBuff = ImageIO.read(new ByteArrayInputStream(bytes));
            d = new Dimension(imBuff.getWidth(), imBuff.getHeight());
        } catch (IOException ex) {
            Logger.getLogger(StorageManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d;
    }

    public static BufferedImage squareThumb(BufferedImage image, int size) {
        int height = image.getHeight();
        int width = image.getWidth();

        //for wide images
        if (width > size && width > height) {

            int x = (int) (width / 2 - height / 2);
            //crop to square image
            image = crop(image, x, 0, height, height);
            //resize to size
            image = resize(image, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_TO_WIDTH, size, Scalr.OP_ANTIALIAS);
        } else {
            image = resize(image, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_TO_WIDTH, size, Scalr.OP_ANTIALIAS);
        }
        return image;
    }

//    public static BufferedImage squareThumb(BufferedImage buffImage) {
//        //crop according to aspect ratio for compact view
//        //strategy resize-crop-resize
//
//        //if(buffImage.getWidth())
//        //Can be cropped to look clearer for wider images in compact view
//        if ((buffImage.getWidth() - buffImage.getHeight()) > CROP_OFFSET * 2) { //crop to small size
//            int x = CROP_OFFSET;
//            int y = 0;//(buffImage.getHeight() - StorageManager.SMALLER_IMAGE_SIZE) > CROP_OFFSET ? CROP_OFFSET : 0;
//
////                    125x
//            buffImage = crop(buffImage, x, y, StorageManager.SMALLER_IMAGE_SIZE, buffImage.getHeight());
//        } else { //scale to small size
//
//            //scale the image to fit 125px and let height flow down
//            buffImage = resize(buffImage, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_TO_WIDTH, StorageManager.SMALLER_IMAGE_SIZE, Scalr.OP_ANTIALIAS);
//        }
//        return null;
//
//    }
}
