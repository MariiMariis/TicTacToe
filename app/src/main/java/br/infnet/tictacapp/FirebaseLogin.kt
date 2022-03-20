package br.infnet.tictacapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import android.widget.EditText
import com.google.android.material.button.MaterialButton

class FirebaseLogin : AppCompatActivity() {
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_login)

        auth = FirebaseAuth.getInstance()

        //is user is alreay Login
        if (auth.currentUser != null) {
            Toast.makeText(this@FirebaseLogin, "User Already Login", Toast.LENGTH_SHORT).show()
            goTelaInicio()
        }

        //click event for login button
        //for user login
        findViewById<MaterialButton>(R.id.btLogin).setOnClickListener {

            var email = findViewById<EditText>(R.id.etEmail).text.toString().trim()
            var password = findViewById<EditText>(R.id.etPassword).text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this@FirebaseLogin, "User Login", Toast.LENGTH_SHORT)
                        goTelaInicio()
                    } else {
                        Toast.makeText(this@FirebaseLogin, "User Auth Failed", Toast.LENGTH_SHORT)
                            .show()
                        Log.e("MA", "auth failed :" + (it.exception!!.message))
                    }

                }

        }


        //click register button
        // for registering user
        findViewById<MaterialButton>(R.id.btRegister).setOnClickListener {

            var email = findViewById<EditText>(R.id.etEmail).text.toString().trim()
            var password = findViewById<EditText>(R.id.etPassword).text.toString()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {

                    if (it.isSuccessful) {
                        Toast.makeText(
                            this@FirebaseLogin,
                            "User successfully created",
                            Toast.LENGTH_SHORT
                        )
                        goTelaInicio()
                    } else {
                        Toast.makeText(this@FirebaseLogin, "User Auth Failed", Toast.LENGTH_SHORT)
                            .show()
                        Log.e("MA", "auth failed :" + (it.exception!!.message))
                    }
                }

        }
    }

    fun goTelaInicio() {
        startActivity(Intent(this@FirebaseLogin, TelaInicio::class.java))
        finish()
    }



}