package com.example.laptrinhandroid_test_th;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.midi.MidiDeviceService;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements SendData{
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authListener;
    EditText edt_main_name,edt_main_age;
    Button btn_main_add,btn_main_update, btn_main_logout, btn_main_delete;
    RecyclerView rcv_main_users;
    UserAdapter adapter;
    int id = 0;
    String url = "https://60ad9ae180a61f00173313b8.mockapi.io/user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();


        rcv_main_users = findViewById(R.id.rcv_main_users);
        edt_main_name = findViewById(R.id.edt_main_name);
        edt_main_age = findViewById(R.id.edt_main_age);
        btn_main_add = findViewById(R.id.btn_main_add);
        btn_main_update = findViewById(R.id.btn_main_update);
        btn_main_logout = findViewById(R.id.btn_main_logout);
        btn_main_delete = findViewById(R.id.btn_main_delete);



        adapter = new UserAdapter(this);

        rcv_main_users.setAdapter(adapter);
        rcv_main_users.setLayoutManager(new GridLayoutManager(this,1));

        btn_main_add.setOnClickListener(v->{
            addNewUser(url);
            adapter.getUsersFromData(url);
        });

        btn_main_update.setOnClickListener(v->{
            if(id!=0){
                updateUser(url);
                adapter.getUsersFromData(url);
                id=0;
            }else Toast.makeText(MainActivity.this, "Choose user to update...!", Toast.LENGTH_SHORT).show();
        });

        btn_main_delete.setOnClickListener(v->{
            if(id!=0){
                deleteUser(url);
                adapter.getUsersFromData(url);
                id=0;
            }else Toast.makeText(MainActivity.this, "Choose user to delete...!", Toast.LENGTH_SHORT).show();
        });

        btn_main_logout.setOnClickListener(v->{
            auth.signOut();
            startActivity(new Intent(MainActivity.this,Login_Activity.class));
            finish();
        });

    }

    private void deleteUser(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE,url+"/"+id,
                response -> Toast.makeText(MainActivity.this, "Successfully", Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(MainActivity.this, "Error by Delete data!", Toast.LENGTH_SHORT).show()
        );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void updateUser(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.PUT , url + "/"+id,
                response -> Toast.makeText(MainActivity.this, "Successfully", Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(MainActivity.this, "Error by Put data!", Toast.LENGTH_SHORT).show()
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("fullName",edt_main_name.getText().toString());
                map.put("age",edt_main_age.getText().toString());
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void addNewUser(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                response ->  Toast.makeText(MainActivity.this, "Successfully", Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(MainActivity.this, "Error by Post data!", Toast.LENGTH_SHORT).show()
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("fullName",edt_main_name.getText().toString());
                map.put("age",edt_main_age.getText().toString());
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @Override
    public void sendingData(Serializable serializable) {
        User user = (User) serializable;
        edt_main_age.setText(user.getAge()+"");
        edt_main_name.setText(user.getFullName());
        id = Integer.parseInt(user.getId());

    }
}