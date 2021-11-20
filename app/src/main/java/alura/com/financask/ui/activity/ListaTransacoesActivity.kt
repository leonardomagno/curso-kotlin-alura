package br.com.alura.financask.ui.activity

import alura.com.financask.R
import alura.com.financask.model.Transacao
import alura.com.financask.ui.adapter.ListaTransacoesAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import java.math.BigDecimal
import java.util.*

class ListaTransacoesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        val transacoes = listOf(
            Transacao(BigDecimal(20.5), "Comida", Calendar.getInstance()),
            Transacao(BigDecimal(100.0), "Economia", Calendar.getInstance())
        )

        lista_transacoes_listview.setAdapter(ListaTransacoesAdapter(transacoes, this))
    }
}