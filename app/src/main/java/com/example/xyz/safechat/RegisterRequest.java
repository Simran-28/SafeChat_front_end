package com.example.xyz.safechat;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "";
    private Map<String, String> params;

    public RegisterRequest(String firstname,String middlename, String lastname, String username, int phone, String email, String password,
                           Response.Listener<String> listener) {

        super(Method.POST,REGISTER_REQUEST_URL , listener, null);
        params = new HashMap<>();
        params.put("fname", firstname);
        params.put("mname", middlename);
        params.put("lname", lastname);
        params.put("username", username);
        params.put("phone", phone + "");
        params.put("email", email);
        params.put("password",password);
    }

    public Map<String,String> getParams(){
        return params;
    }
}
