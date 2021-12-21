package br.com.alura.financask.ui.activity

import alura.com.financask.R
import alura.com.financask.model.Tipo
import alura.com.financask.model.Transacao
import alura.com.financask.ui.ResumoView
import alura.com.financask.ui.adapter.ListaTransacoesAdapter
import alura.com.financask.ui.dialog.AdicionaTransacaoDialog
import alura.com.financask.ui.dialog.AlteraTransacaoDialog
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

class ListaTransacoesActivity : AppCompatActivity() {

    private val transacoes: MutableList<Transacao> = mutableListOf()
    private val viewDaActivity by lazy { window.decorView }
    private val viewGroupDaActivity by lazy { viewDaActivity as ViewGroup }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        configuraResumo()
        configuraLista()
        configuraFab()
    }

    private fun configuraFab() {
        lista_transacoes_adiciona_receita.setOnClickListener {
            chamaDialogDeAdicao(Tipo.RECEITA)
        }

        lista_transacoes_adiciona_despesa.setOnClickListener {
            chamaDialogDeAdicao(Tipo.DESPESA)
        }
    }

    private fun chamaDialogDeAdicao(tipo: Tipo) {
        AdicionaTransacaoDialog(viewGroupDaActivity, this)
            .chama(tipo) { transacaoCriada ->
                adiciona(transacaoCriada)
                lista_transacoes_adiciona_menu.close(true)
            }
    }

    private fun adiciona(transacao: Transacao) {
        transacoes.add(transacao)
        atualizaTransacoes()
    }

    private fun atualizaTransacoes() {
        configuraLista()
        configuraResumo()
    }

    private fun configuraResumo() {
        val resumoView = ResumoView(this, viewDaActivity, transacoes)
        resumoView.atualiza()
    }

    private fun configuraLista() {
        val listaTransacoesAdapter = ListaTransacoesAdapter(transacoes, this)
        with(lista_transacoes_listview) {
            adapter = listaTransacoesAdapter
            setOnItemClickListener { _, _, position, _ ->
                val transacao = transacoes[position]
                chamaDialogDeAlteração(transacao, position)
            }
        }
    }

    private fun chamaDialogDeAlteração(
        transacao: Transacao,
        position: Int
    ) {
        AlteraTransacaoDialog(viewGroupDaActivity, this)
            .chama(transacao) { transacaoAlterada ->
                altera(transacaoAlterada, position)
            }
    }

    private fun altera(transacao: Transacao, position: Int) {
        transacoes[position] = transacao
        atualizaTransacoes()
    }
}