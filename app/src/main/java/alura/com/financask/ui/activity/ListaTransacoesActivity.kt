package br.com.alura.financask.ui.activity

import alura.com.financask.R
import alura.com.financask.extensions.formataParaBrasileiro
import alura.com.financask.model.Tipo
import alura.com.financask.model.Transacao
import alura.com.financask.ui.ResumoView
import alura.com.financask.ui.adapter.ListaTransacoesAdapter
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.Calendar

class ListaTransacoesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        val transacoes: List<Transacao> = transacoesDeExemplo()

        configuraResumo(transacoes)
        configuraLista(transacoes)

        lista_transacoes_adiciona_receita.setOnClickListener {
            val view: View = window.decorView
            val viewCriada = LayoutInflater.from(this)
                .inflate(R.layout.form_transacao, view as ViewGroup, false)

            val ano = 2021
            val mes = 10
            val dia = 23
            val hoje = Calendar.getInstance()

            with(viewCriada) {
                form_transacao_data.setText(hoje.formataParaBrasileiro())
                setOnClickListener {
                    DatePickerDialog(it.context,
                        { view, ano, mes, dia ->
                            val dataSelecionada = Calendar.getInstance()// Cria a instância da Data
                            dataSelecionada.set(ano, mes, dia)// Salava a data de acordo com seleção do usuário
                            viewCriada.form_transacao_data.setText(dataSelecionada.formataParaBrasileiro())// Formata a data e exibe na tela
                        }, ano, mes, dia) // data padrão exibiada ao abrir o date picker
                        .show()
                }
                val adapter = ArrayAdapter
                    .createFromResource(it.context, R.array.categorias_de_receita, android.R.layout.simple_spinner_dropdown_item)
                form_transacao_categoria.adapter = adapter
            }

            AlertDialog.Builder(this)
                .setTitle(R.string.adiciona_receita)
                .setView(viewCriada)
                .setPositiveButton("Adicionar", null)
                .setNegativeButton("Cancelar", null)
                .show()
        }
    }

    private fun configuraResumo(transacoes: List<Transacao>) {
        val view = window.decorView
        val resumoView = ResumoView(this, view, transacoes)
        resumoView.atualiza()
    }

    private fun configuraLista(transacoes: List<Transacao>) {
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)
    }

    private fun transacoesDeExemplo(): List<Transacao> {
        val transacoes = listOf(
            Transacao(
                valor = BigDecimal(20.5),
                categoria = "Almoço de fim de Semana",
                tipo = Tipo.DESPESA,
                data = Calendar.getInstance()
            ),
            Transacao(valor = BigDecimal(100.0), categoria = "Economia", tipo = Tipo.RECEITA),
            Transacao(valor = BigDecimal.valueOf(200.0), tipo = Tipo.DESPESA),
            Transacao(valor = BigDecimal.valueOf(500.0), categoria = "Prêmio", tipo = Tipo.RECEITA)
        )
        return transacoes
    }
}