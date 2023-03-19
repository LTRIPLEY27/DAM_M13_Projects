package com.example.reseed.util;

/**
 * Response listener for the LoginActivity class.
 */
public interface VolleyResponseListener {
    void onError(String message);
    void onResponse(Object response);
}
