package br.infnet.tictacapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RankingActivity : AppCompatActivity() {

    lateinit var db: FirebaseFirestore



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)

        db = FirebaseFirestore.getInstance()

        db.collection("users").get()

        val recyclerView = findViewById<TextView>(R.id.rankingView)

        recyclerView.text = "hello"


    }
}

