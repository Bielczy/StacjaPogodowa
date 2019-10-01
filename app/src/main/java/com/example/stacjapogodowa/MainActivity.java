package com.example.stacjapogodowa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private int counter = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Name = (EditText)findViewById(R.id.etLogin);
        Password = (EditText)findViewById(R.id.etPassword);
        Info = (TextView) findViewById(R.id.tvCounter);
        Login = (Button)findViewById(R.id.btnLogin);

        Info.setText("Nomber of attempts remaining: 5");

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation(Name.getText().toString(), Password.getText().toString());
            }
        });
    }
    private void validation(String userName, String userPassword){
        if ((userName.equals("Admin")) && (userPassword.equals("bielczy"))
                || (userName.equals("Host"))&& (userPassword.equals("everything"))){
                Intent intent = new Intent(MainActivity.this, StartActivity.class);
                startActivity(intent);
        }
        else {
        counter--;
        Info.setText("Nomber of attempts remaining: " + String.valueOf(counter));
                 if (counter == 0){
                     Login.setEnabled(false);
                     }
            }
          }
        }
