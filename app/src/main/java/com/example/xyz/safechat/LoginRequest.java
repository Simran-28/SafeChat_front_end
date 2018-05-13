package com.example.xyz.safechat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
    // @TODO: Pass parameters properly.
    private static final String LOGIN_REQUEST_URL = "http://10.0.2.2:3000/login?username=a&password=b";
    private Map<String, String> params;

    public LoginRequest(String username, String password, Response.Listener<String> listener) {

        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.clear();
        params.put("username", username);
        params.put("password", password);
    }

    // this method is not working.
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}