package com.example.xyz.safechat;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static android.support.v4.os.LocaleListCompat.create;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etFirstname= (EditText) findViewById(R.id.etFirstName);
        final EditText etMiddlename= (EditText) findViewById(R.id.etMiddleName);
        final EditText etLastname= (EditText) findViewById(R.id.etLastName);
        final EditText etUsername= (EditText) findViewById(R.id.etusername);
        final EditText etPhoneno= (EditText) findViewById(R.id.etPhone);
        final EditText etPassword= (EditText) findViewById(R.id.etPassword);
        final EditText etemail = (EditText) findViewById(R.id.etEmail);
        final Button bRegister = (Button) findViewById(R.id.bRegister);

        bRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final String fname = etFirstname.getText().toString();
                final String mname = etMiddlename.getText().toString();
                final String lname = etLastname.getText().toString();
                final String Username = etUsername.getText().toString();
                final String email = etemail.getText().toString();
                final String Password = etPassword.getText().toString();
                final int phone = Integer.parseInt(etPhoneno.getText().toString());

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Register failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                                ;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                };

                RegisterRequest registerRequest = new RegisterRequest(fname, mname, lname, Username, phone, email, Password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}
