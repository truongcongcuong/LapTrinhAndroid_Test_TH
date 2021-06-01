package com.example.laptrinhandroid_test_th;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_Activity extends AppCompatActivity {
    FirebaseAuth auth;
    Button btn_login_login,btn_login_registry;
    EditText edt_login_user,edt_login_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        btn_login_login = findViewById(R.id.btn_login_login);
        btn_login_registry = findViewById(R.id.btn_login_registry);
        edt_login_user = findViewById(R.id.edt_login_user);
        edt_login_password = findViewById(R.id.edt_login_password);

        btn_login_login.setText("Log in");
        edt_login_password.setHint("Password");
        edt_login_user.setHint("User name");

        edt_login_user.setText("abc@gmail.com");
        edt_login_password.setText("123456");

        btn_login_registry.setOnClickListener(v->{
            startActivity(new Intent(Login_Activity.this,RegistryActivity.class));
            finish();
        });

        btn_login_login.setOnClickListener(v->{
            String email = edt_login_user.getText().toString();
            if(TextUtils.isEmpty(email)){
                Toast.makeText(Login_Activity.this,"Enter your email....",Toast.LENGTH_SHORT).show();
                return;
            }
            String password = edt_login_password.getText().toString();
            if(TextUtils.isEmpty(password)){
                Toast.makeText(Login_Activity.this,"Enter your password....",Toast.LENGTH_SHORT).show();
                return;
            }
            auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(Login_Activity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(Login_Activity.this,"Log in failed , try again....",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                startActivity(new Intent(Login_Activity.this,MainActivity.class));
                                finish();
                            }
                        }
                    });
        });
    }
}