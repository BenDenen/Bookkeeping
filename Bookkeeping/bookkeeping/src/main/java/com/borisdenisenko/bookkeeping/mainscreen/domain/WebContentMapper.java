package com.borisdenisenko.bookkeeping.mainscreen.domain;

import com.borisdenisenko.bookkeeping.gateway.entity.WebContent;
import com.borisdenisenko.rxviper.Mapper;

/**
 * Created by bdenisenko on 16.02.2017.
 */

public class WebContentMapper extends Mapper<WebContent, WebContentViewModel> {

    @Override
    public WebContentViewModel map(WebContent entity) {
        WebContentViewModel webContentViewModel = new WebContentViewModel(entity.getWebAddress(), entity.getFilePath());
        webContentViewModel.setDownloaded(true);
        webContentViewModel.setError(entity.isError());
        return webContentViewModel;
    }
}
