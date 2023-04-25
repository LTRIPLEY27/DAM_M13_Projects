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

public class CustomJSONArrayRequest extends Request<JSONArray> {
	private final Response.Listener<JSONArray> mListener;
	private final JSONObject mRequestBody;

	public CustomJSONArrayRequest(int method, String url, JSONObject requestBody,
	                              Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
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
	protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
		try {
			String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			return Response.success(new JSONArray(jsonString), HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException | JSONException e) {
			return Response.error(new ParseError(e));
		}
	}

	@Override
	protected void deliverResponse(JSONArray response) {
		mListener.onResponse(response);
	}
}
