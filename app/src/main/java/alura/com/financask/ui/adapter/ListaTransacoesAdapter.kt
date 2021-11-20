package alura.com.financask.ui.adapter

import alura.com.financask.R
import alura.com.financask.model.Transacao
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.transacao_item.view.*
import java.text.SimpleDateFormat

class ListaTransacoesAdapter(
    trasacoes: List<Transacao>,
    context: Context) : BaseAdapter() {

    private val transacoes = trasacoes
    private val context = context

    override fun getCount(): Int {
        return transacoes.size
    }

    override fun getItem(position: Int): Transacao {
        return transacoes[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val viewCriada = LayoutInflater.from(context).inflate(R.layout.transacao_item, parent, false)
        val transacao = transacoes[position]
        viewCriada.transacao_valor.text = transacao.valor.toString()
        viewCriada.transacao_categoria.text = transacao.categoria

        val formatoBrasileiro = "dd/MM/yyyy"
        val format = SimpleDateFormat(formatoBrasileiro)
        val dataFormatada = format.format(transacao.data.time)
        viewCriada.transacao_data.text = dataFormatada
        return viewCriada
    }
}