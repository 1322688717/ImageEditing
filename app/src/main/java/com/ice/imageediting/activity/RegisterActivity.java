package com.ice.imageediting.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ice.imageediting.R;
import com.ice.imageediting.api.ApiClient;
import com.ice.imageediting.api.ApiService;

import com.ice.imageediting.model.RegisterResponse;
import com.ice.imageediting.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
    }

    public void registerUser(View view) {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        User user = new User(username, password);
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<RegisterResponse> call = apiService.register(user);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 注册成功，显示成功消息
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();

                    // 返回到登录页面
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // 清除栈中的所有活动
                    startActivity(intent);
                    finish(); // 结束当前活动
                } else {
                    // 注册失败，显示失败消息
                    Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                // 处理请求失败的情况
                Toast.makeText(RegisterActivity.this, "网络请求失败: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
