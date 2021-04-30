package com.example.mislugaresfavoritos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    Button btnRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        email = (EditText)findViewById(R.id.registration_email);
        password = (EditText)findViewById(R.id.registration_password);
        btnRegistration = (Button)findViewById(R.id.registration_btn_create_account);
        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
                    Log.e("REG", "reg");
                    Toast t = Toast.makeText(getApplicationContext(), getString(R.string.error_create_profile), Toast.LENGTH_LONG);
                    t.show();
                }else {
                    registration();
                }
            }
        });
    }

    private void registration() {
        SharedPreferences sharedPreferences = getSharedPreferences("misLugares", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email.getText().toString());
        editor.putString("password", password.getText().toString());
        editor.putBoolean("logged", true);
        editor.apply();
        Intent i = new Intent(this, PlaceList.class);
        startActivity(i);
    }
}