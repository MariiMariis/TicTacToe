package br.infnet.tictacapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.infnet.tictacapp.databinding.ActivityRankingBinding


class RankingActivity : AppCompatActivity() {

    lateinit var db: FirebaseFirestore



    lateinit var binding: ActivityRankingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)

        binding = ActivityRankingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()

        loadPartidas()


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

