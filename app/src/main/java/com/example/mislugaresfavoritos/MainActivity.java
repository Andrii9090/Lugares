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

import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button btnSignIn;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editorSharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("misLugares", MODE_PRIVATE);
        Boolean isLogged = sharedPreferences.getBoolean("logged", false);
        Log.e("MAIN", String.valueOf(isLogged));

        if(isLogged){
            Intent i = new Intent(this, PlaceList.class);
            startActivity(i);
        }else {
            setContentView(R.layout.activity_main);

            email = (EditText) findViewById(R.id.main_activity_email);
            password = (EditText) findViewById(R.id.main_activity_password);
            btnSignIn = (Button) findViewById(R.id.main_activity_btn_signin);

            btnSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String emailText = email.getText().toString();
                    String passwordText = password.getText().toString();
                    singIn(emailText, passwordText);
                }
            });
        }

    }

    private void singIn(String email, String password){
        sharedPreferences = this.getSharedPreferences("misLugares", MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("email", null);
        String userPassword = sharedPreferences.getString("password", null);
        Toast t = Toast.makeText(getApplicationContext(), R.string.error_enter, Toast.LENGTH_LONG);

        if (userEmail != null && userPassword != null){
            if(userEmail.equals(email) && userPassword.equals(password)){
                editorSharedPreference = sharedPreferences.edit();
                editorSharedPreference.putBoolean("logged", true);
                Intent i = new Intent(this, PlaceList.class);
                startActivity(i);
            }else{
                t.show();
            }
        }else {
            t.show();
        }
    }

    public void registration(View view) {
        Intent i = new Intent(this, RegistrationActivity.class);
        startActivity(i);
    }
}