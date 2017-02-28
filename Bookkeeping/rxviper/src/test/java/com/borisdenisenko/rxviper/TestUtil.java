package com.borisdenisenko.rxviper;

import static com.google.common.truth.Truth.assertThat;

final class TestUtil {
  static void check(Runnable r, boolean shouldThrow, Class<? extends Throwable> throwableClass) {
    boolean thrown = false;
    try {
      thrown = false;
      r.run();
    } catch (Throwable t) {
      if (throwableClass.isInstance(t)) {
        thrown = true;
      }
    }
    if (shouldThrow) {
      assertThat(thrown).isTrue();
    } else {
      assertThat(thrown).isFalse();
    }
  }
}
