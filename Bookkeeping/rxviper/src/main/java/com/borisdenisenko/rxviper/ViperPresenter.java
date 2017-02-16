package com.borisdenisenko.rxviper;

import static com.borisdenisenko.rxviper.RxViper.getProxy;
import static com.borisdenisenko.rxviper.RxViper.requireNotNull;

/**
 * Created by bdenisenko on 16.02.2017.
 */

/**
 * Contains view logic for preparing content for display (as received from the {@link Interactor}) and for reacting to user inputs (by
 * requesting new data from the Interactor).
 * <p>
 * Contains additional routing logic for switching screens.
 */

public abstract class ViperPresenter<V extends ViewCallbacks, R extends Router> extends Presenter<V> {
    private final R routerProxy = RxViper.createRouter(null, getClass());

    /**
     * Creates a presenter with pre-attached view and router.
     * <p>
     * Doesn't call {@link #onTakeView} and {@link #onTakeRouter} callbacks.
     *
     * @param view   view that will be returned from {@link #getView()}.
     * @param router router that will be returned from {@link #getRouter()}.
     * @since 0.11.0
     */
    protected ViperPresenter(V view, R router) {
        super(view);
        requireNotNull(router);
        getProxy(routerProxy).set(router);
    }

    /**
     * Creates a presenter with pre-attached view.
     * <p>
     * Doesn't call {@link #onTakeView} callback.
     *
     * @param view view that will be returned from {@link #getView()}.
     * @since 0.11.0
     */
    protected ViperPresenter(V view) {
        super(view);
    }

    /**
     * Creates a presenter with pre-attached router.
     * <p>
     * Doesn't call {@link #onTakeRouter} callback.
     *
     * @param router router that will be returned from {@link #getRouter()}.
     * @since 0.11.0
     */
    protected ViperPresenter(R router) {
        requireNotNull(router);
        getProxy(routerProxy).set(router);
    }

    /**
     * Creates a presenter without pre-attached view and router.
     *
     * @since 0.11.0
     */
    protected ViperPresenter() {
    }

    /**
     * Called to surrender control of taken router.
     * <p>
     * It is expected that this method will be called with the same argument as {@link #takeRouter}. Mismatched routers are ignored. This
     * is to provide protection in the not uncommon case that {@code dropRouter} and {@code takeRouter} are called out of order.
     * <p>
     * Calls {@link #onDropRouter} before the reference to the router is cleared.
     *
     * @param router router is going to be dropped
     * @since 0.1.0
     */
    public final void dropRouter(R router) {
        requireNotNull(router);

        if (currentRouter() == router) {
            onDropRouter(router);
            getProxy(routerProxy).clear();
        }
    }

    /**
     * Checks if a router is attached to this presenter.
     *
     * @return {@code true} if presenter has attached router
     * @see #getRouter()
     * @since 0.7.0
     */
    public final boolean hasRouter() {
        return currentRouter() != null;
    }

    /**
     * Called to give this presenter control of a router.
     * <p>
     * As soon as the reference to the router is assigned, it calls {@link #onTakeRouter} callback.
     *
     * @param router router that will be returned from {@link #getRouter()}.
     * @see #dropRouter(Router)
     * @since 0.1.0
     */
    public final void takeRouter(R router) {
        requireNotNull(router);

        final R currentRouter = currentRouter();
        if (currentRouter != router) {
            if (currentRouter != null) {
                dropRouter(currentRouter);
            }
            getProxy(routerProxy).set(router);
            onTakeRouter(router);
        }
    }

    /**
     * Returns the router managed by this presenter. You should always call {@link #hasRouter} to check if the router is taken to avoid
     * no-op behavior.
     *
     * @return an instance of a proxy class for the specified router interface
     * @see #takeRouter(Router)
     * @since 0.1.0
     */
    protected final R getRouter() {
        return routerProxy;
    }

    /**
     * Called before router is dropped.
     *
     * @param router router is going to be dropped
     * @see #dropRouter(Router)
     * @since 0.7.0
     */
    protected void onDropRouter(R router) {
    }

    /**
     * Called after router is taken.
     *
     * @param router router attached to this presenter
     * @see #takeRouter(Router)
     * @since 0.6.0
     */
    protected void onTakeRouter(R router) {
    }

    private R currentRouter() {
        return getProxy(routerProxy).get();
    }
}

