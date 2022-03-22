package br.infnet.tictacapp


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.infnet.tictacapp.databinding.ActivityRankingBinding
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.firebase.auth.FirebaseAuth


class RankingActivity : AppCompatActivity() {

    lateinit var db: FirebaseFirestore

    lateinit var goToHomeBtn : BottomNavigationItemView
    lateinit var goToRankingBtn : BottomNavigationItemView
    lateinit var doLogOutBtn : BottomNavigationItemView



    lateinit var binding: ActivityRankingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)

        binding = ActivityRankingBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        db = FirebaseFirestore.getInstance()

        loadPartidas()



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

    fun loadPartidas() {

        val partidasList = ArrayList<PartidaDbModel>()

        var ref = db.collection("partidas")
            .get()
            .addOnSuccessListener {
                if (it.isEmpty) {
                    Toast.makeText(this@RankingActivity, "sem historico de partidas", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }
                for (doc in it) {
                    val partidaModel = doc.toObject(PartidaDbModel::class.java)
                    partidasList.add(partidaModel)
                //    Log.d("BANCO", partidaModel.toString())
                }

                binding.rvListaPartidas.apply {
                    layoutManager =
                        LinearLayoutManager(this@RankingActivity, RecyclerView.VERTICAL, false)
                    adapter = PartidaAdapter(partidasList, this@RankingActivity)
                }

            }

    }
}

