package com.kalfian.stiki.stiki_e_appointment.modules

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import com.kalfian.stiki.stiki_e_appointment.R
import com.kalfian.stiki.stiki_e_appointment.databinding.ActivityLoginBinding
import com.kalfian.stiki.stiki_e_appointment.modules.lecture.DashboardLectureActivity
import com.kalfian.stiki.stiki_e_appointment.modules.student.DashboardStudentActivity
import com.kalfian.stiki.stiki_e_appointment.requests.LoginRequest
import com.kalfian.stiki.stiki_e_appointment.models.authResponse.LoginResponse
import com.kalfian.stiki.stiki_e_appointment.utils.Constant
import com.kalfian.stiki.stiki_e_appointment.utils.RetrofitClient
import com.kalfian.stiki.stiki_e_appointment.utils.SharedPreferenceUtil
import retrofit2.Call
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var b: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityLoginBinding.inflate(layoutInflater)
        val v = b.root
        setContentView(v)

        b.btnLogin.setOnClickListener {
            authLogin(b.emailEdit.text.toString(), b.passwordEdit.text.toString())
        }
    }

    private fun authLogin(email: String, password: String) {
        b.btnLogin.startAnimation()

        if (email.isBlank() || password.isBlank()) {
            MotionToast.createColorToast(this@LoginActivity,"Login Gagal!",
                "Email atau Password tidak boleh kosong!",
                MotionToastStyle.ERROR,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(this@LoginActivity, R.font.ubuntu_regular)
            )
            b.btnLogin.revertAnimation()
            return
        }

        val credential= LoginRequest(email, password)

        RetrofitClient.call().postLogin(credential).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                if (response.isSuccessful) {
                    b.btnLogin.revertAnimation()
                    val data = response.body()?.data

                    if(data != null) {
                        var role = Constant.ROLE_STUDENT
                        if (data.user.role.name == Constant.ROLE_LECTURE) {
                            role = Constant.ROLE_LECTURE
                        }

                        val isLecture = role == Constant.ROLE_LECTURE
                        SharedPreferenceUtil.store(applicationContext, Constant.SHARED_ROLE, data.user.role.name)
                        SharedPreferenceUtil.store(applicationContext, Constant.SHARED_NAME, data.user.name)
                        SharedPreferenceUtil.store(applicationContext, Constant.SHARED_IDENTITY, data.user.identity)
                        SharedPreferenceUtil.store(applicationContext, Constant.SHARED_TOKEN, data.token)
                        SharedPreferenceUtil.store(applicationContext, Constant.SHARED_IS_LECTURE, isLecture.toString())

                        var intent = Intent(this@LoginActivity, DashboardStudentActivity::class.java)

                        if (role == Constant.ROLE_LECTURE) {
                            intent = Intent(this@LoginActivity, DashboardLectureActivity::class.java)
                        }

                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        return
                    }

                    MotionToast.createColorToast(this@LoginActivity,"Login Gagal!",
                        "Email atau Password salah! [2]",
                        MotionToastStyle.ERROR,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION,
                        ResourcesCompat.getFont(this@LoginActivity, R.font.ubuntu_regular)
                    )

                } else {

                    val responseError = RetrofitClient.mapJsonToDataClass<LoginResponse>(response.errorBody())
                    var errorMessage = "Email atau Password salah! [1]"
                    if (responseError != null) {
                        errorMessage = responseError.message
                    }

                    b.btnLogin.revertAnimation()
                    MotionToast.createColorToast(this@LoginActivity,"Login Gagal!",
                        errorMessage,
                        MotionToastStyle.ERROR,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION,
                        ResourcesCompat.getFont(this@LoginActivity, R.font.ubuntu_regular)
                    )
                }
            }



            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                b.btnLogin.revertAnimation()

                MotionToast.createColorToast(this@LoginActivity,"Login Gagal!",
                    "Terjadi kesalahan pada server!",
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(this@LoginActivity, R.font.ubuntu_regular)
                )
            }
        })
    }
}