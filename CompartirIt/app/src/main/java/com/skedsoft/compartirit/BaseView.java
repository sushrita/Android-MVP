package com.skedsoft.compartirit;

/**
 * Compartir It, All rights Reserved
 * Created by Sanjeet on 12-Oct-16.
 */

/**
 * Base View which serves as skeleton for the all View, Please not here the term view is overloaded
 * this view is nothing to do with view belongs to the android.view.View.
 * @param <T> Presenter to present this show.
 */
public interface BaseView<T> {
    /**
     * Sets the presenter to access it across the view
     * @param presenter the presenter
     */
    void setPresenter(T presenter);

    /**
     * Supposed to show if some background task is ruing
     * @param active whether task is active
     */
    void showProgressIndicator(boolean active);

    /**
     * Supposed to show error in the implemented class
     * @param error the error message
     */
    void showError(String error);
}
