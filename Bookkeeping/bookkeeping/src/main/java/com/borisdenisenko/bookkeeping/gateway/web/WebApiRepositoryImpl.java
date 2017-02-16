package com.borisdenisenko.bookkeeping.gateway.web;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.borisdenisenko.bookkeeping.gateway.WebSiteDataRepository;
import com.borisdenisenko.bookkeeping.gateway.entity.WebContent;
import com.borisdenisenko.bookkeeping.gateway.utils.FileUtils;
import com.borisdenisenko.bookkeeping.gateway.web.api.DownloadService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import rx.Observable;

import static rx.Observable.just;

/**
 * Created by bdenisenko on 16.02.2017.
 */

public class WebApiRepositoryImpl implements WebSiteDataRepository {

    private static final String TAG = WebApiRepositoryImpl.class.getSimpleName();

    private final DownloadService mDownloadService;

    public WebApiRepositoryImpl() {
        Retrofit retrofit = new Retrofit.Builder().build();
        mDownloadService = retrofit.create(DownloadService.class);
    }

    @Override
    public Observable<WebContent> downloadWebContent(@NonNull String webSitesUrl) {
        return just(getWebContent(webSitesUrl));
    }

    private WebContent getWebContent(String webSitesUrl) {
        Call<ResponseBody> call = mDownloadService.downloadWebContent(webSitesUrl);
        try {
            Response<ResponseBody> response = call.execute();
            if (response == null || response.body() == null) {
                return new WebContent(webSitesUrl);
            }
            File downloadedFile = writeResponseBodyToDisk(response.body(), FileUtils.getFileForSite(webSitesUrl));
            return new WebContent(webSitesUrl, downloadedFile.getAbsolutePath(), (response.code() >= 200) && (response.code() < 300));
        } catch (IOException e) {
            // No Internet
            return new WebContent(webSitesUrl);
        }
    }

    @Nullable
    private File writeResponseBodyToDisk(@NonNull ResponseBody body, File localFile) {
        try {

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(localFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return localFile;
            } catch (FileNotFoundException e) {
                Log.e(TAG, "Can not find file with path " + localFile.getAbsolutePath() + " : " + e.getLocalizedMessage());
                return null;
            } catch (SecurityException e) {
                Log.e(TAG, "Can not get an access to file with path " + localFile.getAbsolutePath() + " : " + e.getLocalizedMessage());
                return null;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Can not create file : " + e.getLocalizedMessage());
            return null;
        }
    }
}
