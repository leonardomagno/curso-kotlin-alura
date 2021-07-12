package alura.com.financask.ui.adapter

import alura.com.financask.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

class ListaTransacoesAdapter(trasacoes: List<String>,
                             context: Context) : BaseAdapter() {

    private val transacoes = trasacoes
    private val context = context

    override fun getCount(): Int {
        return transacoes.size
    }

    override fun getItem(position: Int): String {
        return transacoes[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        return LayoutInflater.from(context).inflate(R.layout.transacao_item, parent, false)
    }
}