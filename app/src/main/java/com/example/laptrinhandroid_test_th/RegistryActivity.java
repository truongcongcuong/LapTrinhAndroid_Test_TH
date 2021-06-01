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

public class RegistryActivity extends AppCompatActivity {
    FirebaseAuth auth;
    EditText edt_registry_user,edt_registry_pass,edt_registry_re_enter_pass;
    Button btn_registry_registry,btn_registry_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);

        auth = FirebaseAuth.getInstance();

        btn_registry_login = findViewById(R.id.btn_registry_login);
        btn_registry_registry = findViewById(R.id.btn_registry_registry);
        edt_registry_user = findViewById(R.id.edt_registry_user);
        edt_registry_pass = findViewById(R.id.edt_registry_pass);
        edt_registry_re_enter_pass = findViewById(R.id.edt_registry_re_enter_pass);

        edt_registry_user.setHint("User name");
        edt_registry_pass.setHint("password");
        edt_registry_re_enter_pass.setHint("re-enter password");

        btn_registry_login.setOnClickListener(v->{
            startActivity(new Intent(RegistryActivity.this,Login_Activity.class));
            finish();;
        });

        btn_registry_registry.setOnClickListener(v->{
            String email = edt_registry_user.getText().toString();
            if(TextUtils.isEmpty(email)){
                Toast.makeText(RegistryActivity.this,"Enter user...",Toast.LENGTH_SHORT).show();
                return;
            }
            String pass = edt_registry_pass.getText().toString();
            if(TextUtils.isEmpty(pass)){
                Toast.makeText(RegistryActivity.this,"Enter pass...",Toast.LENGTH_SHORT).show();
                return;
            }
            String re_pass = edt_registry_re_enter_pass.getText().toString();
            if(TextUtils.isEmpty(re_pass)){
                Toast.makeText(RegistryActivity.this,"Enter re-pass...",Toast.LENGTH_SHORT).show();
                return;
            }
            if(!pass.equalsIgnoreCase(re_pass)){
                Toast.makeText(RegistryActivity.this,"password is not match...",Toast.LENGTH_SHORT).show();
                return;
            }

            auth.createUserWithEmailAndPassword(email,pass)
                    .addOnCompleteListener(RegistryActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(RegistryActivity.this,"registry failed , try again ...",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                startActivity(new Intent(RegistryActivity.this,MainActivity.class));
                                finish();
                            }
                        }
                    });
        });
    }
}