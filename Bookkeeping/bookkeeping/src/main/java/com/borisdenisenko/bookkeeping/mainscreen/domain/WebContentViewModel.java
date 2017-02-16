package com.borisdenisenko.bookkeeping.mainscreen.domain;

/**
 * Created by bdenisenko on 16.02.2017.
 */

public class WebContentViewModel {

    private final String mWebAddress;
    private final String mFilePath;

    private boolean isDownloaded;

    public WebContentViewModel(String webAddress, String filePath) {
        mWebAddress = webAddress;
        mFilePath = filePath;
    }

    public String getWebAddress() {
        return mWebAddress;
    }

    public String getFilePath() {
        return mFilePath;
    }

    public boolean isDownloaded() {
        return isDownloaded;
    }

    public void setDownloaded(boolean downloaded) {
        isDownloaded = downloaded;
    }
}
