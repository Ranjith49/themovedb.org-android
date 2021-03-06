package com.ran.themoviedb.utils;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.ran.themoviedb.R;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.download.Downloader;
import com.ran.themoviedb.model.utils.ApplicationUtils;

import java.io.File;

/**
 * Created by ranjith.suda on 3/3/2016.
 * <p/>
 * Image Download Utils Class , that transforms the calls from App --> Downloader Module
 * Validates ,
 * a) whether Download can proceed or not
 * b) Space Available for Download or not
 */
public class ImageDownloadUtils {

  private final static String IMAGE_DIRECTORY = "TheMovieDB";
  private final static String TAG = ImageDownloadUtils.class.getSimpleName();
  private final static long FREE_MB = 25 * 1000 * 1000; // 25 MB free Space is required to download

  /**
   * Initial validator , whether to actually make the Download call or not
   *
   * @param downloadUrl -- Url / file to be downloaded
   */
  public static void startDownload(String downloadUrl) {

    String splitString[] = downloadUrl.split(TheMovieDbConstants.BACKSLASH);
    String fileName = splitString[splitString.length - 1];
    if (Downloader.getInstance().getCurrentRunningDownloads().containsKey(downloadUrl)) {
      Toast.makeText(ApplicationUtils.getApplication(), R.string.download_inProgress, Toast
          .LENGTH_SHORT).show();
      return;
    }

    //Validate the Mount Status ..
    if (!isExternalStorageWritable()) {
      Log.d(TAG, "Not Writable Storage ,Ignore it");
      Toast.makeText(ApplicationUtils.getApplication(), R.string.download_error_fileMount, Toast
          .LENGTH_SHORT).show();
      return;
    }

    //Validate the Directory Status ..
    File fileDir = createAlbumPathIfRequired(IMAGE_DIRECTORY);
    if (null == fileDir) {
      Toast.makeText(ApplicationUtils.getApplication(), R.string.download_error_fileMount, Toast
          .LENGTH_SHORT).show();
      Log.d(TAG, "No Directory created,Ignore it");
      return;
    }

    //Validate the Space ..
    long availableSpace = fileDir.getFreeSpace();
    if (FREE_MB > availableSpace) {
      Log.d(TAG, "No space to download the image");
      Toast.makeText(ApplicationUtils.getApplication(), R.string.download_error_noSpace, Toast
          .LENGTH_SHORT).show();
      return;
    }

    //Now Create the File Path for the file to be created..
    File imageFile = new File(fileDir, fileName);
    if (imageFile.isFile()) {
      Log.d(TAG, "File Already Exists, No need to download again");
      Toast.makeText(ApplicationUtils.getApplication(), R.string.download_error_fileExists, Toast
          .LENGTH_SHORT).show();
    } else {
      Downloader.getInstance().startDownloadImage(downloadUrl, imageFile, fileName);
    }
  }

  /* Checks if external storage is available for read and write */
  private static boolean isExternalStorageWritable() {
    String state = Environment.getExternalStorageState();
    if (Environment.MEDIA_MOUNTED.equals(state)) {
      return true;
    }
    return false;
  }

  /**
   * Method to create the  TheMovieDB Album if required
   *
   * @param albumName -- Album directory to be appened
   * @return -- Directory path
   */
  private static File createAlbumPathIfRequired(String albumName) {
    File file = new File(Environment.getExternalStoragePublicDirectory(
        Environment.DIRECTORY_PICTURES), albumName);
    if (file.exists()) {
      Log.d(TAG, "Directory already exists, return it");
      return file;
    }
    //Create the Directory here ..
    file.mkdirs();

    //Validate whether the Directory is created or not ..
    if (file.isDirectory()) {
      return file;
    } else {
      return null;
    }
  }
}
