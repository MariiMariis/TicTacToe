package br.infnet.tictacapp

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity(), View.OnClickListener {



    lateinit var campo1 : ImageView
    lateinit var campo2 : ImageView
    lateinit var campo3 : ImageView
    lateinit var campo4 : ImageView
    lateinit var campo5 : ImageView
    lateinit var campo6 : ImageView
    lateinit var campo7 : ImageView
    lateinit var campo8 : ImageView
    lateinit var campo9 : ImageView

    lateinit var layoutJog1 : LinearLayout
    lateinit var layoutJog2 : LinearLayout

    lateinit var goToHomeBtn : BottomNavigationItemView
    lateinit var goToRankingBtn : BottomNavigationItemView
    lateinit var doLogOutBtn : BottomNavigationItemView


    var player1 = 0
    var player2 = 1
    var activePlayer = player1
    lateinit var filledPos : IntArray

    var gameActive = true



    lateinit var db: FirebaseFirestore



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = FirebaseFirestore.getInstance()


        var nomeJogadorUm = intent.getStringExtra("NomeJogador1")

        val message1 = findViewById<TextView>(R.id.nomeJogadorUm).apply {
            text = nomeJogadorUm
        }

        var nomeJogadorDois = intent.getStringExtra("NomeJogador2")

        val message2 = findViewById<TextView>(R.id.nomeJogadorDois).apply {
            text = nomeJogadorDois
        }


        filledPos = intArrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1)

        layoutJog1 = findViewById(R.id.layoutJogadorUm)
        layoutJog2 = findViewById(R.id.layoutJogadorDois)

        campo1 = findViewById(R.id.campo1)
        campo2 = findViewById(R.id.campo2)
        campo3 = findViewById(R.id.campo3)
        campo4 = findViewById(R.id.campo4)
        campo5 = findViewById(R.id.campo5)
        campo6 = findViewById(R.id.campo6)
        campo7 = findViewById(R.id.campo7)
        campo8 = findViewById(R.id.campo8)
        campo9 = findViewById(R.id.campo9)

        campo1.setOnClickListener(this)
        campo2.setOnClickListener(this)
        campo3.setOnClickListener(this)
        campo4.setOnClickListener(this)
        campo5.setOnClickListener(this)
        campo6.setOnClickListener(this)
        campo7.setOnClickListener(this)
        campo8.setOnClickListener(this)
        campo9.setOnClickListener(this)

        goToHomeBtn = findViewById(R.id.homeBtn)
        goToRankingBtn = findViewById(R.id.rankingBtn)
        doLogOutBtn = findViewById(R.id.logoutBtn)

        goToHomeBtn.setOnClickListener {
            goToHome()
        }
        goToRankingBtn.setOnClickListener {
            goToRanking()
        }
        doLogOutBtn.setOnClickListener {
            doLogOut()
        }
    }


    override fun onClick(v: View?) {

        if(!gameActive)
            return

        var btnClick = findViewById<ImageView>(v!!.id)
        var clickedTag = Integer.parseInt(btnClick.tag.toString())



        if(filledPos[clickedTag]!=-1)
            return

        filledPos[clickedTag] = activePlayer

        if(activePlayer == player1){
            btnClick.setImageResource(R.drawable.x)
            activePlayer = player2
            layoutJog2.setBackgroundResource(R.drawable.transp_round_green)
            layoutJog1.setBackgroundResource(R.drawable.trnasp_btn_noborder)
        }
        else{

            btnClick.setImageResource(R.drawable.circle)
            activePlayer = player1
            layoutJog1.setBackgroundResource(R.drawable.transp_round_pink)
            layoutJog2.setBackgroundResource(R.drawable.trnasp_btn_noborder)
        }

        checkForWinner()

    }

    private fun checkForWinner() {



        var winPos = arrayOf(intArrayOf(0,1,2), intArrayOf(3,4,5),intArrayOf(6,7,8),
            intArrayOf(0,3,6), intArrayOf(1,4,7), intArrayOf(2,5,8),
            intArrayOf(0,4,8,), intArrayOf(2,4,6))

        for (i in 0 until winPos.size) {
            var val0 = winPos[i] [0]
            var val1 = winPos[i] [1]
            var val2 = winPos[i] [2]
            var vencedor: String
            var perdedor: String

            if(filledPos[val0] == filledPos[val1] && filledPos[val1] == filledPos[val2]){
                if(filledPos[val0] != -1){
                    gameActive = false
                    if(filledPos[val0] == player1) {
                        vencedor = intent.getStringExtra("NomeJogador1").toString()
                        perdedor = intent.getStringExtra("NomeJogador2").toString()

                    }
                    else{
                        vencedor = intent.getStringExtra("NomeJogador2").toString()
                        perdedor = intent.getStringExtra("NomeJogador1").toString()

                    }
                    val resultadoDb = PartidaDbModel(
                        vencedor,
                        perdedor
                    )
                    db.collection("partidas")
                        .add(resultadoDb)
                        .addOnSuccessListener {
                            Toast.makeText(this@MainActivity, "Ranking Saved!", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this@MainActivity, "Ranking Not Saved", Toast.LENGTH_SHORT).show()
                            Log.e("HA", "Error saving : Err :" + it.message)
                        }
                    showMessage(vencedor + " venceu!")
                    return
                }
            }
        }
        var count = 0
        for (i in 0 until filledPos.size) {
            if (filledPos[i] == -1) {
                count++
            }
        }

        if(count == 0){
            showMessage("EMPATOU!")
            return

        }
    }

    private fun showMessage (s: String) {
        AlertDialog.Builder(this)
            .setMessage(s)
            .setPositiveButton("Jogar Novamente!", DialogInterface.OnClickListener { dialog, which ->
                restartGame()
             //   goToRanking()
            })
            .show()
    }

    private fun restartGame() {
        filledPos = intArrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1)
        activePlayer = player1
        gameActive = true
        layoutJog2.setBackgroundResource(R.drawable.transp_round_green)
        layoutJog1.setBackgroundResource(R.drawable.trnasp_btn_noborder)

        campo1.setImageResource(R.drawable.trnasp_btn_noborder)
        campo2.setImageResource(R.drawable.trnasp_btn_noborder)
        campo3.setImageResource(R.drawable.trnasp_btn_noborder)
        campo4.setImageResource(R.drawable.trnasp_btn_noborder)
        campo5.setImageResource(R.drawable.trnasp_btn_noborder)
        campo6.setImageResource(R.drawable.trnasp_btn_noborder)
        campo7.setImageResource(R.drawable.trnasp_btn_noborder)
        campo8.setImageResource(R.drawable.trnasp_btn_noborder)
        campo9.setImageResource(R.drawable.trnasp_btn_noborder)

    }

    fun goToHome() {
        val intent = Intent(this, TelaInicio::class.java).also {
            startActivity(it)
        }
    }
    fun goToRanking() {
        val intent = Intent(this, RankingActivity::class.java).also {
            startActivity(it)
        }
    }
    fun doLogOut() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, splash_screen::class.java).also {
            startActivity(it)
        }
    }

}


