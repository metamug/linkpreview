/**
 * ***********************************************************************
 *
 * METAMUG.COM CONFIDENTIAL __________________
 *
 * [2013] - [2014] metamug.com All Rights Reserved.
 *
 * NOTICE: All information contained herein is, and remains the property of
 * metamug.com and its suppliers, if any. The intellectual and technical
 * concepts contained herein are proprietary to metamug.com and its suppliers
 * and may be covered by Indian and Foreign Patents, patents in process, and are
 * protected by trade secret or copyright law. Dissemination of this information
 * or reproduction of this material is strictly forbidden unless prior written
 * permission is obtained from metamug.com
 */
package net.metamug.scrapper.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author deepak
 */
public class ImageUtil {

    public static String getLargestImage(List<String> images) {
        String largestImage = null;
        //List<Integer> imageSizes = new ArrayList<>();
//        int[] imageSizes = new int[images.size()];
        long largestImageSize = 0;
        for (String currentImage : images) {
            long currentImageSize = getImageSize(currentImage);
            if (currentImageSize > largestImageSize) {
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
            System.setProperty("http.agent", DownloadManager.USER_AGENT);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("HEAD");
            //urlConnection.getInputStream();
            length = (urlConnection).getContentLengthLong();
        } catch (MalformedURLException ex) {
            Logger.getLogger(ImageUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ImageUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return length;
    }
}
