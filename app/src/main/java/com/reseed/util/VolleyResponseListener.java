package com.reseed.util;

/**
 * Listener para la clase LoginActivity, para que pueda retornar
 * el objeto de la request (Json).
 */
public interface VolleyResponseListener {
    void onError(String message);
    void onResponse(Object response);
}
