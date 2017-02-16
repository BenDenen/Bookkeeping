package com.borisdenisenko.bookkeeping.mainscreen.di;

import com.borisdenisenko.bookkeeping.Job;
import com.borisdenisenko.bookkeeping.Main;
import com.borisdenisenko.bookkeeping.gateway.RepositoryModule;
import com.borisdenisenko.bookkeeping.gateway.WebSiteDataRepository;
import com.borisdenisenko.bookkeeping.mainscreen.MainActivity;
import com.borisdenisenko.bookkeeping.mainscreen.domain.DownloadWebContentUserCase;
import com.borisdenisenko.bookkeeping.mainscreen.domain.WebContentMapper;
import com.borisdenisenko.bookkeeping.mainscreen.presenter.MainScreenPresenter;
import com.borisdenisenko.bookkeeping.mainscreen.router.MainScreenRouter;
import com.borisdenisenko.bookkeeping.mainscreen.router.MainScreenRouterImpl;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

/**
 * Created by bdenisenko on 16.02.2017.
 */

@Module(includes = RepositoryModule.class)
public final class MainScreenModule {
    private MainActivity activity;

    public void setMainActivity(MainActivity activity) {
        this.activity = activity;
    }

    @Provides
    MainScreenRouter provideMainRouter() {
        return new MainScreenRouterImpl(activity);
    }

    @Provides
    @MainScreenScope
    static DownloadWebContentUserCase provideDownloadWebContentUserCase(@Job Scheduler jobScheduler, @Main Scheduler mainScheduler,
                                                                        WebSiteDataRepository repository, WebContentMapper mapper) {
        return new DownloadWebContentUserCase(jobScheduler, mainScheduler, repository, mapper);
    }

    @Provides
    @MainScreenScope
    static WebContentMapper provideWebContentMapper() {
        return new WebContentMapper();
    }

    @Provides
    @MainScreenScope
    static MainScreenPresenter provideMainPresenter(DownloadWebContentUserCase interactor) {
        return new MainScreenPresenter(interactor);
    }
}

