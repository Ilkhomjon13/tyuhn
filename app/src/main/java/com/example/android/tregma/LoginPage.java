package com.example.android.tregma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.android.tregma.config.AppConfig;
import com.example.android.tregma.config.AppController;
import com.example.android.tregma.help.CheckConnectivity;
import com.example.android.tregma.help.SessionManager;
import com.example.android.tregma.database.SQLiteHandler;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

public class LoginPage extends AppCompatActivity {

    private EditText username_field;
    private EditText password_field;
    private ProgressDialog progressDialog;
    private SessionManager session;
    private SQLiteHandler db;
    private boolean isUserTypeRadioButtonChecked;

    enum User_Type_Radio_Button {
        TEACHER("t"),
        PARENT("p"),
        STUDENT("s");

        public String asString() {
            return asString;
        }

        private final String asString;

        User_Type_Radio_Button(String asString) {
            this.asString = asString;
        }
    }

    private User_Type_Radio_Button userTypeRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_login);

        isUserTypeRadioButtonChecked = false;

        username_field = findViewById(R.id.username);
        password_field = findViewById(R.id.password);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        db = new SQLiteHandler(getApplicationContext());

        session = new SessionManager(getApplicationContext());

        Button btnLogIn = findViewById(R.id.ButtonSgnTalaba);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isUserTypeRadioButtonChecked) {
                    if(CheckConnectivity.isConnected(LoginPage.this)) {
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
                } else {
                    Toast.makeText(getApplicationContext(), "Talaba, O'qituvchi yoki Ota-Ona belgilang!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void checkLogin(final String username, final String password) {
        String tag_string_req = "req_login";
        progressDialog.setMessage("Kirmoqda...");
        showDialog();

        final String url = AppConfig.URL_LOGIN + "?" + "username=" + username
                            + "&password=" + password + "&user_type=" + userTypeRadioButton.asString();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        hideDialog();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean error = jsonObject.getBoolean("error");

                            if (!error) {

                                session.setLogin(true);
                                String user_id = jsonObject.getString("id");

                                JSONObject user = jsonObject.getJSONObject("user");
                                String name = user.getString("name");
                                String surname = user.getString("surname");
                                String father_name = user.getString("father_name");
                                String level = user.getString("level");
                                String profession = user.getString("profession");

                                int level_int;
                                try {
                                    level_int = Integer.parseInt(level);
                                } catch (NumberFormatException e) {
                                    level_int = 0;
                                }

                                db.addUser(name, surname, father_name, user_id, level_int, profession, userTypeRadioButton.asString());

                                switch (userTypeRadioButton.asString()) {
                                    case "s":
                                        session.setUserType("s");
                                        startActivity(new Intent(LoginPage.this, Student.class));
                                        finish();
                                        break;
                                    case "t":
                                        session.setUserType("t");
                                        startActivity(new Intent(LoginPage.this, Teacher.class));
                                        finish();
                                        break;
                                    case "p":
                                        session.setUserType("p");
                                        startActivity(new Intent(LoginPage.this, Parent.class));
                                        finish();
                                        break;

                                }
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
        });

        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public void onUserTypeRadioButtonClicked(View view) {

        isUserTypeRadioButtonChecked = ((RadioButton) view).isChecked();

        if(isUserTypeRadioButtonChecked) {
            switch (view.getId()) {
                case R.id.student_radio:
                    userTypeRadioButton = User_Type_Radio_Button.STUDENT;
                    break;
                case R.id.teacher_radio:
                    userTypeRadioButton = User_Type_Radio_Button.TEACHER;
                    break;
                case R.id.parent_radio:
                    userTypeRadioButton = User_Type_Radio_Button.PARENT;
                    break;
            }
        }
    }
}

