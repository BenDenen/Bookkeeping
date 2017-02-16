package com.borisdenisenko.bookkeeping.gateway.entity;

/**
 * Created by bdenisenko on 16.02.2017.
 */

public class WebContent {

    private final String mWebAddress;
    private final String mFilePath;
    private final int mStatusCode;

    public WebContent(String webAddress, String filePath, int statusCode) {
        mWebAddress = webAddress;
        mFilePath = filePath;
        mStatusCode = statusCode;
    }

    public WebContent(String webAddress) {
        this(webAddress, null, -1);
    }

    public String getWebAddress() {
        return mWebAddress;
    }

    public String getFilePath() {
        return mFilePath;
    }
}
