package br.infnet.tictacapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Handler
import android.os.Looper
import javax.security.auth.login.LoginException

class splash_screen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        changeToLogin()
    }

    fun changeToLogin() {
        val intent = Intent(this, TelaInicio::class.java)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)

        Handler(Looper.getMainLooper()).postDelayed({
            intent.change()
        }, 3000)
    }
    fun Intent.change(){
        startActivity(this)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        finish()
    }
}