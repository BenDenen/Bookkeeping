package com.borisdenisenko.bookkeeping;

import com.borisdenisenko.bookkeeping.gateway.WebSiteDataRepository;
import com.borisdenisenko.bookkeeping.mainscreen.domain.DownloadWebContentUserCase;
import com.borisdenisenko.bookkeeping.mainscreen.domain.WebContentMapper;
import com.borisdenisenko.rxviper.Interactor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Actions;
import rx.internal.util.ActionSubscriber;
import rx.schedulers.Schedulers;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static rx.Observable.just;

public final class InteractorTest {
  private static final int                PARAM        = 1;
  private static final Action1<String>    ON_NEXT      = Actions.empty();
  private static final Action1<Throwable> ON_ERROR     = Actions.empty();
  private static final Action0            ON_COMPLETED = Actions.empty();
  private static final Subscriber<String> SUBSCRIBER   = new ActionSubscriber<>(ON_NEXT, ON_ERROR, ON_COMPLETED);

  @Rule public final ExpectedException thrown = ExpectedException.none();

  private DownloadWebContentUserCase spyInteractor;
  private WebSiteDataRepository repository;
  private WebContentMapper mapper;

  @Before
  public void setUp() {
    repository = mock(WebSiteDataRepository.class);
    mapper = new WebContentMapper();
    spyInteractor = spy(new DownloadWebContentUserCase(Schedulers.immediate(), Schedulers.immediate(), repository,  mapper));
  }

}