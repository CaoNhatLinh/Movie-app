package com.appxemphim.dao;

import android.util.Log;

import com.appxemphim.Api.ApiClient;
import com.appxemphim.data.ChangePasswordRequest;
import com.appxemphim.data.NguoiDung;
import com.appxemphim.data.LoginRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NguoiDungDAO {
    private ApiClient apiClient;

    public NguoiDungDAO() {
        apiClient = ApiClient.apiClient;
    }
    public void getProfileById(int MaNguoiDung, final NguoiDungCallback callback) {
        Call<NguoiDung> call = apiClient.getProfileById(MaNguoiDung);

        // Log URL request
        Log.d("API Request", call.request().url().toString());

        call.enqueue(new Callback<NguoiDung>() {
            @Override
            public void onResponse(Call<NguoiDung> call, Response<NguoiDung> response) {
                if (response.isSuccessful() && response.body() != null) {
                    NguoiDung nguoiDung = response.body();

                    Log.d("API Response", "Success: " + nguoiDung.getMaNguoiDung());

                    callback.onSuccess(nguoiDung);
                } else {
                    String errorMessage = "Failed to fetch data: " + response.message();

                    // Log error response
                    Log.e("API Response", errorMessage);

                    callback.onFailure(errorMessage);
                }
            }

            @Override
            public void onFailure(Call<NguoiDung> call, Throwable t) {
                String errorMessage = "API call failed: " + t.getMessage();

                // Log failure response
                Log.e("API Failure", errorMessage);

                callback.onFailure(errorMessage);
            }
        });
    }

//Triệu theem
    // Phương thức đăng nhập
    public void login(String emailOrUsername, String password, final LoginCallback callback) {
        Call<NguoiDung> call = apiClient.login(new LoginRequest(emailOrUsername, password));
        call.enqueue(new Callback<NguoiDung>() {
            @Override
            public void onResponse(Call<NguoiDung> call, Response<NguoiDung> response) {
                if (response.isSuccessful()) {
                    NguoiDung nguoiDung = response.body();
                    callback.onSuccess(nguoiDung);
                } else {
                    callback.onFailure("Failed to login: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<NguoiDung> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }
    public void getProfile(String TenDangNhap, final NguoiDungCallback callback) {
        Call<NguoiDung> call = apiClient.getProfileByTenDangNhap(TenDangNhap);
        call.enqueue(new Callback<NguoiDung>() {
            @Override
            public void onResponse(Call<NguoiDung> call, Response<NguoiDung> response) {
                if (response.isSuccessful()) {
                    NguoiDung nguoiDung = response.body();
                    callback.onSuccess(nguoiDung);
                } else {
                    callback.onFailure("Failed to login: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<NguoiDung> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }
    public void register(NguoiDung nguoiDung, final RegisterCallback callback) {
        Call<Void> call = apiClient.register(nguoiDung);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess();
                } else {
                    callback.onFailure("Failed to register: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public void editNguoiDung(int id,NguoiDung nguoiDung, final NguoiDungCallback callback) {
        Call<NguoiDung> call = apiClient.editNguoiDung(id,nguoiDung);
        call.enqueue(new Callback<NguoiDung>() {
            @Override
            public void onResponse(Call<NguoiDung> call, Response<NguoiDung> response) {
                if (response.isSuccessful()) {
                    NguoiDung nguoiDung1 = response.body();
                    callback.onSuccess(nguoiDung1);
                } else {
                    callback.onFailure("Failed to edit: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<NguoiDung> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public void changePassword(String emailOrUsername, String oldPassword, String newPassword, final ChangePasswordCallback callback) {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(emailOrUsername, oldPassword, newPassword);
        Call<Void> call = apiClient.changePassword(changePasswordRequest);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess("Đổi mật khẩu thành công");
                    // Chuyển từ LoginActivity sang RegisterActivity

                } else {
                    callback.onFailure("Đổi mật khẩu thất bại");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onFailure("Có lỗi xảy ra: " + t.getMessage());
            }
        });
    }

    public interface ChangePasswordCallback {
        void onSuccess(String message);
        void onFailure(String errorMessage);
    }

    public interface RegisterCallback {
        void onSuccess();
        void onFailure(String message);
    }


    public interface NguoiDungCallback {
        void onSuccess(NguoiDung nguoiDung);
        void onFailure(String message);
    }

    public interface LoginCallback {
        void onSuccess(NguoiDung nguoiDung);
        void onFailure(String message);
    }

}
