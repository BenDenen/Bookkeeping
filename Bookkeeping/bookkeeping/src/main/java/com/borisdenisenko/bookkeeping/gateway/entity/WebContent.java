package com.borisdenisenko.bookkeeping.gateway.entity;

/**
 * Created by bdenisenko on 16.02.2017.
 */

public class WebContent {

    private final String mWebAddress;
    private final String mFilePath;
    private final boolean isError;

    public WebContent(String webAddress, String filePath, boolean error) {
        mWebAddress = webAddress;
        mFilePath = filePath;
        isError = error;
    }

    public WebContent(String webAddress) {
        this(webAddress, null, true);
    }

    public String getWebAddress() {
        return mWebAddress;
    }

    public String getFilePath() {
        return mFilePath;
    }

    public boolean isError() {
        return isError;
    }
}
