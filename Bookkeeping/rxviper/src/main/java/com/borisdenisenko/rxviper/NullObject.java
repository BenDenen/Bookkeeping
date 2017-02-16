package com.borisdenisenko.rxviper;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by bdenisenko on 16.02.2017.
 */

/**
 * Implementation of <a href="https://en.wikipedia.org/wiki/Null_Object_pattern">Null Object pattern</a>.
 */
final class NullObject<T> implements InvocationHandler {
    private WeakReference<T> targetRef;

    NullObject(T target) {
        set(target);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final T target = get();
        return target == null ? null : method.invoke(target, args);
    }

    void clear() {
        if (targetRef != null) {
            targetRef.clear();
            targetRef = null;
        }
    }

    T get() {
        return targetRef == null ? null : targetRef.get();
    }

    void set(T target) {
        targetRef = new WeakReference<>(target);
    }
}
