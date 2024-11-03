package com.ice.imageediting;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ice.imageediting.api.ApiClient;
import com.ice.imageediting.api.ApiService;
import com.ice.imageediting.model.LoginResponse;
import com.ice.imageediting.model.User;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
    }

    public void loginUser(View view) {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        User user = new User(username, password);
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<LoginResponse> call = apiService.login(user);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 登录成功，获取响应数据（假设响应体包含用户信息或token）
                    LoginResponse loginResponse = response.body();

                    // 显示成功消息
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();

                    // 跳转到 MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // 清除栈中的所有活动
                    startActivity(intent);
                    finish(); // 结束当前活动
                } else {
                    // 登录失败，显示失败消息
                    Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override

            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // 打印错误信息
                Log.e("LoginActivity", "登录请求失败: " + t.getMessage(), t);
                Toast.makeText(LoginActivity.this, "网络请求失败: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }

    public void goToRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}