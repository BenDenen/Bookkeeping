package com.borisdenisenko.bookkeeping.gateway.web.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by bdenisenko on 16.02.2017.
 */

public interface DownloadService {

    @GET
    Call<ResponseBody> downloadWebContent(@Url String url);
}
