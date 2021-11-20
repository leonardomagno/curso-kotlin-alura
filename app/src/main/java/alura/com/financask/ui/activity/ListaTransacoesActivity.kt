package br.com.alura.financask.ui.activity

import alura.com.financask.R
import alura.com.financask.model.Tipo
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
            Transacao(valor = BigDecimal(20.5), tipo = Tipo.DESPESA, data = Calendar.getInstance()),
            Transacao(valor = BigDecimal(100.0), categoria = "Economia", tipo = Tipo.RECEITA),
            Transacao(valor = BigDecimal.valueOf(200.0), tipo = Tipo.DESPESA),
            Transacao(valor = BigDecimal.valueOf(500.0), categoria = "Prêmio", tipo = Tipo.RECEITA)
        )

        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)
    }
}