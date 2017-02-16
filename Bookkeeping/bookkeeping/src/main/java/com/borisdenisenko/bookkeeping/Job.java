package com.borisdenisenko.bookkeeping;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by bdenisenko on 16.02.2017.
 */

@Qualifier
@Documented
@Retention(RUNTIME)
public @interface Job {
}
