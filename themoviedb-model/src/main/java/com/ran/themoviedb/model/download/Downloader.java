package com.ran.themoviedb.model.download;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.ran.themoviedb.model.utils.ApplicationUtils;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ran.themoviedb.model.R;

/**
 * Created by ranjith.suda on 3/3/2016.
 * <p/>
 * Responsible for Downloading Image Files into External Directory
 * a) Validates whether File Exists or not
 * b) Validates whether Download is possible or not
 */
public class Downloader {

  private static Downloader instance;
  private static final String TAG = Downloader.class.getSimpleName();
  private final DownloadManager DOWNLOAD_MANAGER;
  private final int DOWNLOAD_INVALID_REFERENCE = -1;
  private final ConcurrentHashMap<String, Long> currentRunningDownloads;
  private final DownloadLooper looperThread;

  private Downloader() {
    currentRunningDownloads = new ConcurrentHashMap<>();
    DOWNLOAD_MANAGER = (DownloadManager) ApplicationUtils.getApplication()
        .getSystemService(Context.DOWNLOAD_SERVICE);

    //Looper for continuous Monitoring of the Downloader..
    looperThread = new DownloadLooper();
    looperThread.start();
  }

  public synchronized static Downloader getInstance() {
    if (instance == null) {
      instance = new Downloader();
    }
    return instance;
  }

  /**
   * Start the Download of the Image
   *
   * @param fileDownloadUrl -- Complete Download Url
   * @param fileToDownload  -- File where to be downloaded
   * @param fileName        -- Name of file
   */
  public void startDownloadImage(String fileDownloadUrl, File fileToDownload, String fileName) {
    Uri downloadUri = Uri.parse(fileDownloadUrl);
    DownloadManager.Request request = new DownloadManager.Request(downloadUri);
    String notificationTitle = String.format(ApplicationUtils.getApplication().getResources().
        getString(R.string.download_notification_title), fileName);
    request.setTitle(notificationTitle);
    request.setDescription(ApplicationUtils.getApplication().getResources()
        .getString(R.string.download_notification_desc));

    request.allowScanningByMediaScanner();
    request.setVisibleInDownloadsUi(true);

    //Download location ..
    request.setDestinationUri(Uri.fromFile(fileToDownload));
    long referenceId = DOWNLOAD_MANAGER.enqueue(request);
    if (referenceId == DOWNLOAD_INVALID_REFERENCE) {
      //Reference Id is invalid ,ignore ..
      Log.d(TAG, "Some Issue with Reference Id , Cannot go inside");
    } else {
      currentRunningDownloads.put(fileDownloadUrl, referenceId);
    }
  }

  /**
   * Getter for getting the current Running Downloads
   *
   * @return -- get the list of downloads
   */
  public ConcurrentHashMap<String, Long> getCurrentRunningDownloads() {
    return currentRunningDownloads;
  }

  /**
   * Inner Download looper for checking the File Download Status..
   */
  private class DownloadLooper extends Thread {

    private final int EMPTY_MESSAGE = 121;
    private final int PROGRESS_POLLING_INTERVAL = 500;
    private Handler handler;

    @Override
    public void run() {
      Looper.prepare();
      handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
          updateFileDownloadStatus();
        }
      };
      startLooper();
      Looper.loop();
    }

    private void startLooper() {
      handler.sendEmptyMessage(EMPTY_MESSAGE);
    }

    private void updateFileDownloadStatus() {
      Log.d(TAG, "Looper in progress running");
      Iterator<Map.Entry<String, Long>> listItemsIterator = currentRunningDownloads.entrySet()
          .iterator();
      while (listItemsIterator.hasNext()) {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(listItemsIterator.next().getValue());
        Cursor cursor = null;
        try {
          cursor = DOWNLOAD_MANAGER.query(query);
          if (cursor == null || !cursor.moveToFirst()) {
            listItemsIterator.remove();
            return;
          }

          int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
          int downloadStatus = cursor.getInt(columnIndex);
          switch (downloadStatus) {
            case DownloadManager.STATUS_FAILED:
              Log.d(TAG, "Download is failure ");
              Toast.makeText(ApplicationUtils.getApplication(),
                  ApplicationUtils.getApplication().getResources().getString(
                      R.string.download_failure_toast), Toast.LENGTH_SHORT).show();
              listItemsIterator.remove();
              break;
            case DownloadManager.STATUS_SUCCESSFUL:
              Log.d(TAG, "Download is Success , File is created");
              Toast.makeText(ApplicationUtils.getApplication(),
                  ApplicationUtils.getApplication().getResources()
                      .getString(R.string.download_success_toast), Toast.LENGTH_SHORT).show();
              listItemsIterator.remove();
              break;
            case DownloadManager.STATUS_PAUSED:
            case DownloadManager.STATUS_PENDING:
            case DownloadManager.STATUS_RUNNING:
              Log.d(TAG, "Download is other state , we ignore");
              break;
          }
        } catch (Exception exception) {
          Log.d(TAG, exception.toString());
          listItemsIterator.remove();
        }
      }

      //Iterating every 200 milli seconds..
      handler.removeCallbacksAndMessages(null);
      handler.sendEmptyMessageDelayed(EMPTY_MESSAGE, PROGRESS_POLLING_INTERVAL);
    }
  }
}
