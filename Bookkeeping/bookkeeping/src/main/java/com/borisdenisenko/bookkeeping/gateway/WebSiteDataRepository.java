package com.borisdenisenko.bookkeeping.gateway;

import com.borisdenisenko.bookkeeping.gateway.entity.WebContent;

import java.util.Collection;
import java.util.List;

import rx.Observable;

/**
 * Created by bdenisenko on 16.02.2017.
 */

public interface WebSiteDataRepository {

    Observable<WebContent> downloadWebContent(String webSitesUrl);

}
