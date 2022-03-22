package br.infnet.tictacapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds



class TelaInicio : AppCompatActivity() {

    lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_inicio)

        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        val startGameBtn = findViewById<Button>(R.id.startGameBtn)

        startGameBtn.setOnClickListener {
            callAct()

        }

    }



    private fun callAct() {
        val playerOneName = findViewById<EditText>(R.id.playerOneName)
        val message1 = playerOneName.text.toString()

        val playerTwoName = findViewById<EditText>(R.id.playerTwoName)
        val message2 = playerTwoName.text.toString()

        val intent = Intent(this, MainActivity::class.java).also {
            it.putExtra("NomeJogador1", message1)
            it.putExtra("NomeJogador2", message2)
            startActivity(it)

        }
    }
}