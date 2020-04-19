package com.example.android.tregma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.android.tregma.config.AppConfig;
import com.example.android.tregma.config.AppController;
import com.example.android.tregma.config.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpTalaba extends AppCompatActivity {
    private Button buttom;
    private EditText username_field;
    private EditText password_field;
    private ProgressDialog progressDialog;
    private SessionManager session;
    private Button btnLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sgntalaba);

        username_field = (EditText)findViewById(R.id.username);
        password_field = (EditText)findViewById(R.id.password);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        session = new SessionManager(getApplicationContext());

        if(session.isLoggedIn()) {
            startActivity(new Intent(SignUpTalaba.this, Talaba.class));
            finish();
        }

        btnLogIn = (Button) findViewById(R.id.ButtonSgnTalaba);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = username_field.getText().toString().trim();
                String password = password_field.getText().toString().trim();

                if (!username.isEmpty() && !password.isEmpty()) {

                    checkLogin(username, password);
                } else {

                    Toast.makeText(getApplicationContext(),
                            "Malumotlarni kiriting!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

    }

    private void checkLogin(final String username, final String password) {
        String tag_string_req = "req_login";
        progressDialog.setMessage("Kirmoqda...");
        showDialog();

        Log.d(password, "mana " + password);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_LOGIN,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        hideDialog();
                        Log.d(username, "mana " + username);
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            boolean error = jsonObject.getBoolean("error");

                            if (!error) {

                                session.setLogin(true);
                                String id = jsonObject.getString("id");

                                JSONObject user = jsonObject.getJSONObject("user");
                                String name = user.getString("name");
                                String surname = user.getString("surname");
                                String father_name = user.getString("father_name");
                                String level = user.getString("level");
                                String profession = user.getString("profession");

                                Intent intent = new Intent(SignUpTalaba.this, Talaba.class);
                                intent.putExtra("name", name);
                                intent.putExtra("surname", surname);
                                intent.putExtra("father_name", father_name);
                                intent.putExtra("level", level);
                                intent.putExtra("profession", profession);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Kirishda hatolik", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(),
                                    error.getMessage(), Toast.LENGTH_LONG).show();
                            hideDialog();
                        }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);

                return params;
            }
        };
        if(stringRequest != null)
             AppController.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
}

