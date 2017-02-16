package com.borisdenisenko.rxviper;

/**
 * Created by bdenisenko on 16.02.2017.
 */

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Actions;
import rx.internal.util.ActionSubscriber;
import rx.internal.util.InternalObservableUtils;
import rx.subscriptions.CompositeSubscription;

/**
 * Contains the business logic as specified by a use case
 *
 * @param <RequestModel>  the type of request message
 * @param <ResponseModel> the type of response message
 */
public abstract class Interactor<RequestModel, ResponseModel> implements Subscription {
    private final Scheduler subscribeScheduler;
    private final Scheduler observeScheduler;
    private final CompositeSubscription subscriptions;

    /**
     * @param subscribeScheduler the Scheduler that modifies source Observable returned from {@link #createObservable} to perform its emissions on.
     * @param observeScheduler   the Scheduler that modifies source Observable returned from {@link #createObservable} to notify its Observers on.
     */
    protected Interactor(Scheduler subscribeScheduler, Scheduler observeScheduler) {
        RxViper.requireNotNull(subscribeScheduler);
        RxViper.requireNotNull(observeScheduler);

        this.subscribeScheduler = subscribeScheduler;
        this.observeScheduler = observeScheduler;
        subscriptions = new CompositeSubscription();
    }

    /**
     * Subscribes to an Observable and provides a Subscriber that implements functions to handle the items the Observable emits and any
     * error or completion notification it issues.
     *
     * @param subscriber   the Subscriber that will handle emissions and notifications from the Observable
     * @param requestModel parameter which will be passed to {@link #createObservable(Object)}.
     * @throws IllegalStateException                        if {@code subscribe} is unable to obtain an {@code OnSubscribe<>} function
     * @throws IllegalArgumentException                     if the {@link Subscriber} provided as the argument to {@code subscribe} is {@code null}
     * @throws rx.exceptions.OnErrorNotImplementedException if the {@link Subscriber}'s {@code onError} method is null
     * @throws RuntimeException                             if the {@link Subscriber}'s {@code onError} method itself threw a {@code Throwable}
     * @see Observable#subscribe(Subscriber)
     */
    public final void execute(Subscriber<? super ResponseModel> subscriber, RequestModel requestModel) {
        RxViper.requireNotNull(subscriber);

        subscriptions.add(createObservable(requestModel).subscribeOn(subscribeScheduler)
                .observeOn(observeScheduler)
                .subscribe(subscriber));
    }

    /**
     * Subscribes to an Observable and provides a Subscriber that implements functions to handle the items the Observable emits and any
     * error or completion notification it issues.
     *
     * @param subscriber the Subscriber that will handle emissions and notifications from the Observable
     * @see #execute(Subscriber, Object)
     */
    public final void execute(Subscriber<? super ResponseModel> subscriber) {
        execute(subscriber, null);
    }

    /**
     * Subscribes to an Observable and provides a callback to handle the items it emits.
     *
     * @param onNext the {@code Action1<Result>} you have designed to accept emissions from the Observable
     * @throws IllegalArgumentException                     if {@code onNext} is null
     * @throws rx.exceptions.OnErrorNotImplementedException if the Observable calls {@code onError}
     * @see #execute(Action1, Object)
     */
    public final void execute(Action1<? super ResponseModel> onNext) {
        execute(onNext, (RequestModel) null);
    }

    /**
     * Subscribes to an Observable and provides a callback to handle the items it emits.
     *
     * @param onNext       the {@code Action1<Result>} you have designed to accept emissions from the Observable
     * @param requestModel parameter which will be passed to {@link #createObservable(Object)}.
     * @throws IllegalArgumentException                     if {@code onNext} is null
     * @throws rx.exceptions.OnErrorNotImplementedException if the Observable calls {@code onError}
     * @see Observable#subscribe(Action1)
     */
    public final void execute(Action1<? super ResponseModel> onNext, RequestModel requestModel) {
        RxViper.requireNotNull(onNext);

        execute(new ActionSubscriber<>(onNext, InternalObservableUtils.ERROR_NOT_IMPLEMENTED, Actions.empty()), requestModel);
    }

    /**
     * Subscribes to an Observable and provides callbacks to handle the items it emits and any error notification it issues.
     *
     * @param onNext  the {@code Action1<Result>} you have designed to accept emissions from the Observable
     * @param onError the {@code Action1<Throwable>} you have designed to accept any error notification from the Observable
     * @throws IllegalArgumentException if {@code onNext} is null, or if {@code onError} is null
     * @see #execute(Action1, Object)
     */
    public final void execute(Action1<? super ResponseModel> onNext, Action1<Throwable> onError) {
        execute(onNext, onError, (RequestModel) null);
    }

    /**
     * Subscribes to an Observable and provides callbacks to handle the items it emits and any error notification it issues.
     *
     * @param onNext       the {@code Action1<Result>} you have designed to accept emissions from the Observable
     * @param onError      the {@code Action1<Throwable>} you have designed to accept any error notification from the Observable
     * @param requestModel parameter which will be passed to {@link #createObservable(Object)}.
     * @throws IllegalArgumentException if {@code onNext} is null, or if {@code onError} is null
     * @see Observable#subscribe(Action1, Action1)
     */
    public final void execute(Action1<? super ResponseModel> onNext, Action1<Throwable> onError, RequestModel requestModel) {
        RxViper.requireNotNull(onNext);
        RxViper.requireNotNull(onError);

        execute(new ActionSubscriber<>(onNext, onError, Actions.empty()), requestModel);
    }

    /**
     * Subscribes to an Observable and provides callbacks to handle the items it emits and any error or completion notification it issues.
     *
     * @param onNext      the {@code Action1<Result>} you have designed to accept emissions from the Observable
     * @param onError     the {@code Action1<Throwable>} you have designed to accept any error notification from the Observable
     * @param onCompleted the {@code Action0} you have designed to accept a completion notification from the Observable
     * @throws IllegalArgumentException if {@code onNext} is null, or if {@code onError} is null, or if {@code onComplete} is null
     * @see #execute(Action1, Action1, Action0, Object)
     */
    public final void execute(Action1<? super ResponseModel> onNext, Action1<Throwable> onError, Action0 onCompleted) {
        execute(onNext, onError, onCompleted, null);
    }

    /**
     * Subscribes to an Observable and provides callbacks to handle the items it emits and any error or completion notification it issues.
     *
     * @param onNext       the {@code Action1<Result>} you have designed to accept emissions from the Observable
     * @param onError      the {@code Action1<Throwable>} you have designed to accept any error notification from the Observable
     * @param onCompleted  the {@code Action0} you have designed to accept a completion notification from the Observable
     * @param requestModel parameter which will be passed to {@link #createObservable(Object)}.
     * @throws IllegalArgumentException if {@code onNext} is null, or if {@code onError} is null, or if {@code onComplete} is null
     * @see Observable#subscribe(Action1, Action1, Action0)
     */
    public final void execute(Action1<? super ResponseModel> onNext, Action1<Throwable> onError, Action0 onCompleted,
                              RequestModel requestModel) {
        RxViper.requireNotNull(onNext);
        RxViper.requireNotNull(onError);
        RxViper.requireNotNull(onCompleted);

        execute(new ActionSubscriber<>(onNext, onError, onCompleted), requestModel);
    }

    /**
     * Stops the receipt of notifications on the {@link Subscriber}s that were registered.
     * <p>
     * This allows unregistering executed {@link Subscriber}s before they have finished receiving all events (i.e. before onCompleted is
     * called).
     */
    @Override
    public final void unsubscribe() {
        // call clear() instead of unsubscribe() to be able to manage new subscriptions
        subscriptions.clear();
    }

    /**
     * Indicates whether this {@code Interactor} is currently unsubscribed.
     *
     * @return {@code true} if this {@code Interactor} is currently unsubscribed, {@code false} otherwise
     */
    @Override
    public final boolean isUnsubscribed() {
        return !subscriptions.hasSubscriptions();
    }

    /**
     * Provides source Observable that will execute the specified parameter when {@code execute()} method is called.
     * <p>
     * It will use schedulers provided in {@link #Interactor(Scheduler, Scheduler)}.
     *
     * @param requestModel request message to a replier system which receives and processes the request
     * @return source Observable
     * @see #execute(Subscriber)
     * @see #execute(Subscriber, Object)
     * @see #execute(Action1)
     * @see #execute(Action1, Object)
     * @see #execute(Action1, Action1)
     * @see #execute(Action1, Action1, Object)
     * @see #execute(Action1, Action1, Action0)
     * @see #execute(Action1, Action1, Action0, Object)
     */
    protected abstract Observable<ResponseModel> createObservable(RequestModel requestModel);
}
