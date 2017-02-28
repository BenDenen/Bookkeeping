package com.borisdenisenko.rxviper;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public final class NullObjectTest {
  private TestViewCallbacks target;

  @Before
  public void setUp() {
    target = new TestViewCallbacksImpl();
  }

  @Test
  public void shoulInvokeTargetMethods() throws Throwable {
    final TestViewCallbacks spy = spy(target);
    final Method doJob = TestViewCallbacks.class.getMethod("doJob");
    final NullObject<TestViewCallbacks> nullObject = new NullObject<>(spy);
    nullObject.invoke(null, doJob, new Object[0]);
    verify(spy).doJob();
  }

  @Test
  public void shouldClearTarget() {
    final NullObject<TestViewCallbacks> nullObject = new NullObject<>(target);
    nullObject.clear();
    assertThat(nullObject.get()).isNull();
  }

  @Test
  public void shouldHaveWeakTarget() {
    final NullObject<TestViewCallbacks> nullObject = new NullObject<>(target);
    target = null;
    System.gc();
    assertThat(nullObject.get()).isNull();
  }

  @Test
  public void shouldImplementInvocationHandler() {
    assertThat(NullObject.class).isAssignableTo(InvocationHandler.class);
  }

  @Test
  public void shouldReturnPassedTarget() {
    final NullObject<TestViewCallbacks> nullObject1 = new NullObject<>(target);
    assertThat(nullObject1.get()).isSameAs(target);

    final NullObject<TestViewCallbacks> nullObject2 = new NullObject<>(null);
    assertThat(nullObject2.get()).isNull();
  }

  @Test
  public void shouldSetTarget() {
    final NullObject<TestViewCallbacks> nullObject = new NullObject<>(null);
    assertThat(nullObject.get()).isNull();

    nullObject.set(target);
    assertThat(nullObject.get()).isSameAs(target);

    final TestViewCallbacks target2 = new TestViewCallbacksImpl();
    nullObject.set(target2);
    assertThat(nullObject.get()).isNotSameAs(target);
    assertThat(nullObject.get()).isSameAs(target2);

    nullObject.set(null);
    assertThat(nullObject.get()).isNull();
  }
}