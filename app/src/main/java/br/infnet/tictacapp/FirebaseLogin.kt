package br.infnet.tictacapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.button.MaterialButton

class FirebaseLogin : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_login)

        auth = FirebaseAuth.getInstance()

        //is user is alreay Login
        if (auth.currentUser != null) {
            Toast.makeText(this@FirebaseLogin, "User Already Loged", Toast.LENGTH_SHORT).show()
            goTelaInicio()
        }

        //click event for login button
        //for user login
        findViewById<MaterialButton>(R.id.btLogin).setOnClickListener {

            val email = findViewById<EditText>(R.id.etEmail).text.toString().trim()
            val password = findViewById<EditText>(R.id.etPassword).text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this@FirebaseLogin, "User Login", Toast.LENGTH_SHORT).show()
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
        findViewById<TextView>(R.id.btRegister).setOnClickListener {

            val email = findViewById<EditText>(R.id.etEmail).text.toString().trim()
            val password = findViewById<EditText>(R.id.etPassword).text.toString()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {

                    if (it.isSuccessful) {
                        Toast.makeText(
                            this@FirebaseLogin,
                            "User successfully created",
                            Toast.LENGTH_SHORT
                        ).show()
                        goTelaInicio()

                    } else {
                        Toast.makeText(this@FirebaseLogin, "User Auth Failed", Toast.LENGTH_SHORT)
                            .show()
                        Log.e("MA", "auth failed :" + (it.exception!!.message))
                    }
                }

        }
    }

    private fun goTelaInicio() {
        startActivity(Intent(this@FirebaseLogin, TelaInicio::class.java))
        finish()
    }





}