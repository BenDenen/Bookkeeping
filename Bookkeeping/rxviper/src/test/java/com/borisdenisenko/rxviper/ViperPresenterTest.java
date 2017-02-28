package com.borisdenisenko.rxviper;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Proxy;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public final class ViperPresenterTest {
  @Rule public final ExpectedException thrown = ExpectedException.none();
  private TestRouter         router;
  private TestViperPresenter presenter;

  @Before
  public void setUp() {
    router = mock(TestRouter.class);
    presenter = spy(new TestViperPresenter());
  }

  @Test
  public void routerShouldNeverBeNull() {
    assertThat(presenter.getRouter()).isNotNull();

    presenter.takeRouter(router);
    assertThat(presenter.getRouter()).isNotNull();

    presenter.dropRouter(router);
    assertThat(presenter.getRouter()).isNotNull();
  }

  @Test
  public void shouldNotHaveRouter() {
    assertThat(presenter.hasRouter()).isFalse();
  }

  @Test
  public void shouldTakeRouter() {
    presenter.takeRouter(router);
    assertThat(presenter.getRouter()).isEqualTo(router);
    assertThat(presenter.hasRouter()).isTrue();
  }

  @Test
  public void shouldDropRouter() {
    presenter.takeRouter(router);

    presenter.dropRouter(router);
    assertThat(presenter.hasRouter()).isFalse();
  }

  @Test
  public void shouldCallOnTakeRouter() {
    presenter.takeRouter(router);
    verify(presenter).onTakeRouter(router);
  }

  @Test
  public void shouldCallOnTakeRouterOncePerRouter() {
    presenter.takeRouter(router);
    presenter.takeRouter(router);
    verify(presenter).onTakeRouter(router);
  }

  @Test
  public void shouldNotCallOnDropIfRouterIsNotAttached() {
    presenter.dropRouter(router);
    verify(presenter, never()).onDropRouter(router);
  }

  @Test
  public void shouldIgnoreOnDropIfRouterIsNotTheSame() {
    presenter.takeRouter(router);

    final TestRouter anotherRouter = mock(TestRouter.class);
    presenter.dropRouter(anotherRouter);
    verify(presenter, never()).onDropRouter(router);
  }

  @Test
  public void shouldDropPreviousRouterWhenNewRouterIsTaken() {
    presenter.takeRouter(router);

    final TestRouter newRouter = mock(TestRouter.class);
    presenter.takeRouter(newRouter);
    verify(presenter).onDropRouter(router);
  }

  @Test
  public void shouldCallOnTakeRouterAfterRouterIsTaken() {
    presenter.dummy = false;
    presenter.takeRouter(router);
  }

  @Test
  public void shouldCallOnDropRouterBeforeRouterIsDropped() {
    presenter.dummy = false;
    presenter.takeRouter(router);
    presenter.dropRouter(router);
  }

  @Test
  public void takenRouterShouldNotBeNull() {
    thrown.expect(IllegalArgumentException.class);
    presenter.takeRouter(null);
  }

  @Test
  public void droppedRouterShouldNotBeNull() {
    thrown.expect(IllegalArgumentException.class);
    presenter.dropRouter(null);
  }

  @Test
  public void shouldBePresenter() {
    assertThat(presenter).isInstanceOf(Presenter.class);
  }

  @Test
  public void constructorArgsShouldNotBeNull() {
    TestUtil.check(() -> new TestViperPresenter((TestRouter) null), true, IllegalArgumentException.class);
    TestUtil.check(() -> new TestViperPresenter((TestViewCallbacks) null), true, IllegalArgumentException.class);
    TestUtil.check(() -> new TestViperPresenter(null, router), true, IllegalArgumentException.class);
    TestUtil.check(() -> new TestViperPresenter(mock(TestViewCallbacks.class), null), true, IllegalArgumentException.class);
  }

  @Test
  public void constructorShouldSetArgs() {
    TestUtil.check(() -> new TestViperPresenter(router), false, IllegalArgumentException.class);
    TestUtil.check(() -> new TestViperPresenter(mock(TestViewCallbacks.class)), false, IllegalArgumentException.class);
    TestUtil.check(() -> new TestViperPresenter(mock(TestViewCallbacks.class), router), false, IllegalArgumentException.class);
  }

  @Test
  public void shouldReturnProxyRouter() {
    presenter.takeRouter(router);

    final TestRouter proxyRouter = presenter.getRouter();
    assertThat(proxyRouter).isNotSameAs(router);
    assertThat(Proxy.isProxyClass(proxyRouter.getClass())).isTrue();
    assertThat(Proxy.getInvocationHandler(proxyRouter)).isInstanceOf(NullObject.class);
  }

  @Test
  public void proxyShouldWrapRouter() {
    final NullObject<TestRouter> nullObject = RxViper.getProxy(presenter.getRouter());
    assertThat(nullObject.get()).isNull();

    presenter.takeRouter(router);
    assertThat(nullObject.get()).isSameAs(router);

    presenter.dropRouter(router);
    assertThat(nullObject.get()).isNull();
  }
}