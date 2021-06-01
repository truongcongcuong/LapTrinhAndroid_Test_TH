package com.example.laptrinhandroid_test_th;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.NameViewHolder> {
    List<User> users;
    LayoutInflater inflater;
    Context context;
    String url = "https://60ad9ae180a61f00173313b8.mockapi.io/user";

    public UserAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        getUsersFromData(url);
    }

    public void getUsersFromData(String url) {
     users = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                response -> {

                        try {
                            for (int i =0; i<response.length();i++){
                                JSONObject object = (JSONObject) response.get(i);
                                User user = new User();
                                user.setId(object.getString("id"));
                                user.setFullName(object.getString("fullName"));
                                user.setAge(object.getInt("age"));
                                users.add(user);
                            }
                            notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                }, error -> Toast.makeText(context,
                        "Error by get Json Array!", Toast.LENGTH_SHORT).show());

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }

    @NonNull
    @Override
    public NameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.line_item,parent,false);
        return new NameViewHolder(view,this);
    }

    @Override
    public void onBindViewHolder(@NonNull NameViewHolder holder, int position) {
        User user = users.get(position);
        holder.txt_line_id.setText("Id : "+user.getId());
        holder.txt_line_name.setText("Full name : "+user.getFullName());
        holder.txt_line_age.setText("Age : " + user.getAge());
        holder.itemView.setOnClickListener(v->{
            SendData sendData = (SendData) context;
            sendData.sendingData(users.get(position));
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class NameViewHolder extends RecyclerView.ViewHolder {
        UserAdapter adapter;
        TextView txt_line_id,txt_line_name,txt_line_age;
        public NameViewHolder(@NonNull View itemView , UserAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            txt_line_age = itemView.findViewById(R.id.txt_line_age);
            txt_line_id = itemView.findViewById(R.id.txt_line_id);
            txt_line_name = itemView.findViewById(R.id.txt_line_name);
        }
    }
}
