package com.developers.serenity.rimointerview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {

    private String ph, pass;
    private Button btn;
    private EditText edt1, edt2;
    private RequestQueue requestQueue;
    private ProgressBar prog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_layout);

        edt1 = findViewById(R.id.edt1);
        edt2 = findViewById(R.id.edt2);
        btn = findViewById(R.id.btn);
        prog = findViewById(R.id.prog);
        ImageView back = findViewById(R.id.back_btn);

        back.setOnClickListener(v-> onBackPressed());

        requestQueue = Volley.newRequestQueue(this);

        prog.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.home), PorterDuff.Mode.SRC_IN);


        btn.setOnClickListener(v-> {
            if(TextUtils.isEmpty(edt1.getText().toString().trim())){
                showSnack(v, "Please Enter Your Phone Number");
                return;
            }

            if(TextUtils.isEmpty(edt2.getText().toString().trim())){
                showSnack(v, "Please Enter Your Password");
                return;
            }

            ph = edt1.getText().toString().trim();
            pass = edt2.getText().toString().trim();

            prog.bringToFront();
            prog.setVisibility(View.VISIBLE);
            btn.setClickable(false);

            signIn(v);

        });

    }

    private void signIn(View v) {
        JSONObject dataObject = new JSONObject();
        try {
            dataObject.put("phone", ph);
            dataObject.put("password", pass);
            dataObject.put("device_id", "d780544a42bdb1c4QP1A.190711.020");
            dataObject.put("device_name", "samsung");
        } catch (JSONException e) {
            showSnack(v, "  Invalid Details  ");
        }
        String url = "http://52.15.82.204/api/v1/login";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String message = jsonObject.getString("message");
                if (message.equals("login was successful")) {
                    prog.setVisibility(View.GONE);
                    startActivity(new Intent(getApplicationContext(), HomePage.class));
                } else {
                    prog.setVisibility(View.GONE);
                    showSnack(v, message);
                    btn.setClickable(true);
                }
            } catch (JSONException e) {
                prog.setVisibility(View.GONE);
                showSnack(v, e.getLocalizedMessage());
                btn.setClickable(true);
            }
        }, error -> {
            prog.setVisibility(View.GONE);
            showSnack(v, error.getLocalizedMessage());
            btn.setClickable(true);
        }) {
            @Override
            public byte[] getBody() {
                return dataObject.toString().getBytes();
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void showSnack(View v, String s) {
        Snackbar snackbar = Snackbar.make(v, s, Snackbar.LENGTH_SHORT);
        snackbar.setBackgroundTint(getResources().getColor(R.color.home, getTheme()));
        snackbar.show();
        snackbar.addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
            }
        });
    }
}