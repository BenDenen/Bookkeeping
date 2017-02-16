package com.borisdenisenko.bookkeeping.mainscreen.di;

import com.borisdenisenko.bookkeeping.Job;
import com.borisdenisenko.bookkeeping.Main;
import com.borisdenisenko.bookkeeping.gateway.RepositoryModule;
import com.borisdenisenko.bookkeeping.gateway.web.RetrofitModule;
import com.borisdenisenko.bookkeeping.mainscreen.MainActivity;
import com.borisdenisenko.bookkeeping.mainscreen.router.MainScreenRouter;
import com.borisdenisenko.bookkeeping.mainscreen.router.MainScreenRouterImpl;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
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
    static GetCheesesInteractor provideGetCheeseInteractor(@Job Scheduler jobScheduler, @Main Scheduler mainScheduler,
                                                           Retrofit.Builder builder, CheeseMapper mapper) {
        return new GetCheesesInteractor(jobScheduler, mainScheduler, storage, mapper);
    }

    @Provides
    @MainScreenScope
    static CheeseMapper provideCheeseMapper() {
        return new CheeseMapper();
    }

    @Provides
    @MainScreenScope
    static MainPresenter provideMainPresenter(GetCheesesInteractor interactor) {
        return new MainPresenter(interactor);
    }
}

