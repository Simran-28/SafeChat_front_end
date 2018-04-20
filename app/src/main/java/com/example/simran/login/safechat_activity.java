package com.example.simran.login;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



import com.android.mis.javac.Home.HomeActivity;
import com.android.mis.utils.NetworkRequest;
import com.android.mis.utils.SessionManagement;
import com.android.mis.utils.Urls;
import com.android.mis.utils.Util;


import org.json.JSONObject;

import java.util.HashMap;


/**
 * A login screen that offers login via admission_no/password.
 */
public class safechat_activity extends AppCompatActivity implements KeyEvent.Callback {


    // UI references.
    private AutoCompleteTextView TFUsername;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private View mErrorView;
    private TextView message;
    private HashMap<String,String> params;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_safechat_activity);
        // Set up the login form.
        TFUsername = (AutoCompleteTextView)findViewById(R.id.TFusername);
        // populateAutoComplete();

        message = (TextView)findViewById(R.id.message);
        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            String msg = extras.getString("msg");
            message.setVisibility(View.VISIBLE);
            message.setText(msg);
        }
        else{
            message.setVisibility(View.GONE);
        }
        mPasswordView = (EditText) findViewById(R.id.TFpassword);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.Blogin);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.loader);
        mErrorView = findViewById(R.id.err);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        TFUsername.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = TFUsername.getText().toString();
        String password = mPasswordView.getText().toString();
        params = new HashMap<String, String>();
        params.put("username",username);
        params.put("password",password);

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            TFUsername.setError(getString(R.string.error_field_required));
            focusView = TFUsername;
            cancel = true;
        } else if (!isEmailValid(username)) {
            TFUsername.setError(getString(R.string.error_invalid_email));
            focusView = TFUsername;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }
        else{
            NetworkRequest nr = new NetworkRequest(LoginActivity.this,mProgressView,mErrorView,this,"post", Urls.server_protocol,Urls.login_url,params,false,false,0);
            nr.setSnackbar_message(Urls.error_connection_message);
            nr.initiateRequest();
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return true;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length()>0;
    }


    @Override
    public void performAction(String result, int tag) {
        try{
            Log.d("result",result);
            JSONObject json = new JSONObject(result);
            Boolean success = json.getBoolean("success");
            if(success){
                String token = json.getString("token");
                String name = json.getString("name");
                String email = json.getString("email");
                String pic_path = json.getString("pic_path");
                SessionManagement session = new SessionManagement(getApplicationContext());
                session.createLoginSession(token,name,email,pic_path);
                Bundle bundle = new Bundle();
                Util.moveToActivity(safechat_activity.this,HomeActivity.class,bundle,true);
            }
            else{
                Util.viewSnackbar(findViewById(android.R.id.content),json.getString("err_msg"));
            }
        }catch (Exception e){
            Log.e("Exception",e.toString());
            Util.viewSnackbar(findViewById(android.R.id.content),Urls.parsing_error_message);
        }
    }
}
