package com.borisdenisenko.rxviper;

/**
 * Created by bdenisenko on 16.02.2017.
 */

import static com.borisdenisenko.rxviper.RxViper.getProxy;
import static com.borisdenisenko.rxviper.RxViper.requireNotNull;

/**
 * Contains view logic for preparing content for display (as received from the {@link Interactor}) and for reacting to user inputs (by
 * requesting new data from the Interactor).
 */
public abstract class Presenter<V extends ViewCallbacks> {
    private final V viewProxy = RxViper.createView(null, getClass());

    /**
     * Creates a presenter with pre-attached view.
     * <p>
     * Doesn't call {@link #onTakeView} callback.
     *
     * @param view
     *     view that will be returned from {@link #getView()}.
     *
     * @since 0.11.0
     */
    protected Presenter(V view) {
        requireNotNull(view);
        getProxy(viewProxy).set(view);
    }

    /**
     * Creates a presenter without pre-attached view.
     *
     * @since 0.11.0
     */
    protected Presenter() {}

    /**
     * Called to surrender control of taken view.
     * <p>
     * It is expected that this method will be called with the same argument as {@link #takeView}. Mismatched views are ignored. This is
     * to provide protection in the not uncommon case that {@code dropView} and {@code takeView} are called out of order.
     * <p>
     * Calls {@link #onDropView} before the reference to the view is cleared.
     *
     * @param view
     *     view is going to be dropped.
     *
     * @since 0.1.0
     */
    public final void dropView(V view) {
        requireNotNull(view);

        if (currentView() == view) {
            onDropView(view);
            getProxy(viewProxy).clear();
        }
    }

    /**
     * Checks if a view is attached to this presenter.
     *
     * @return {@code true} if presenter has attached view
     *
     * @see #getView()
     * @since 0.7.0
     */
    public final boolean hasView() {
        return currentView() != null;
    }

    /**
     * Called to give this presenter control of a view.
     * <p>
     * As soon as the reference to the view is assigned, it calls {@link #onTakeView} callback.
     *
     * @param view
     *     view that will be returned from {@link #getView()}.
     *
     * @see #dropView(ViewCallbacks)
     * @since 0.1.0
     */
    public final void takeView(V view) {
        requireNotNull(view);

        final V currentView = currentView();
        if (currentView != view) {
            if (currentView != null) {
                dropView(currentView);
            }
            getProxy(viewProxy).set(view);
            onTakeView(view);
        }
    }

    /**
     * Returns the view managed by this presenter. You should always call {@link #hasView} to check if the view is taken to avoid no-op
     * behavior.
     *
     * @return an instance of a proxy class for the specified view interface
     *
     * @see #takeView(ViewCallbacks)
     * @since 0.1.0
     */
    protected final V getView() {
        return viewProxy;
    }

    /**
     * Called before view is dropped.
     *
     * @param view
     *     view is going to be dropped
     *
     * @see #dropView(ViewCallbacks)
     * @since 0.7.0
     */
    protected void onDropView(final V view) {
    }

    /**
     * Called after view is taken.
     *
     * @param view
     *     attached to this presenter
     *
     * @see #takeView(ViewCallbacks)
     * @since 0.6.0
     */
    protected void onTakeView(V view) {
    }

    private V currentView() {
        return getProxy(viewProxy).get();
    }
}
