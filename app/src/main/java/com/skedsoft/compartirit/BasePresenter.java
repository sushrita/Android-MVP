package com.skedsoft.compartirit;

/**
 * Compartir It, All rights Reserved
 * Created by Sanjeet on 12-Oct-16.
 */

/**
 * Serves as base interface for all presenters
 */
public interface BasePresenter {
    /**
     * Supposed to notify the presenter if the view(Fragments/Activity onDestroy method has called
     * So presenter should not access the view methods once this get called.
     */
    void onDestroy();

    /**
     * Supposed to be called when a event is supposed to be trigger when fragment loads
     */
    void start();
}
