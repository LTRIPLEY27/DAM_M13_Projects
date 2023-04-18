package com.reseed.interfaces;

/**
 * Listener para la clase LoginActivity, para que pueda retornar
 * el objeto de la request (Json).
 */
public interface VolleyResponseInterface {
    void onError(String message);
    boolean onResponse(Object response);
}
