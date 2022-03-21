package br.infnet.tictacapp


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.infnet.tictacapp.databinding.PartidaCardBinding

class PartidaAdapter(val partidasList: ArrayList<PartidaDbModel>, val context: Context) :
    RecyclerView.Adapter<PartidaAdapter.MyHoler>() {


    class MyHoler(val binding: PartidaCardBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHoler {

        val binding = PartidaCardBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyHoler(binding)
    }

    override fun onBindViewHolder(holder: MyHoler, position: Int) {

        val partida = partidasList[position]
        with(holder) {
            binding.tvPartidaNumero.text = position.toString()
            binding.tvPartidaVencedor.text = partida.winner
            binding.tvPartidaPerdedor.text = partida.looser
        }

    }

    override fun getItemCount(): Int {
        return partidasList.size
    }

}