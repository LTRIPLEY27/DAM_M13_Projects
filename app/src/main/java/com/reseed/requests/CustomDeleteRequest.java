package com.reseed.requests;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class CustomDeleteRequest extends Request<String> {
	private final Response.Listener<String> mListener;
	private final JSONObject mRequestBody;

	public CustomDeleteRequest(int method, String url, JSONObject requestBody,
                               Response.Listener<String> listener, Response.ErrorListener errorListener) {
		super(method, url, errorListener);
		mListener = listener;
		mRequestBody = requestBody;
	}

	@Override
	public byte[] getBody() throws AuthFailureError {
		return mRequestBody.toString().getBytes();
	}

	@Override
	public String getBodyContentType() {
		return "application/json";
	}

	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response) {
		try {
			String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			return Response.success(new String(jsonString), HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		}
	}

	@Override
	protected void deliverResponse(String response) {
		mListener.onResponse(response);
	}
}
