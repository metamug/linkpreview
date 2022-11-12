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
