package alura.com.financask.ui.dialog

import alura.com.financask.R
import alura.com.financask.delegate.TransacaoDelegate
import alura.com.financask.extensions.formataParaBrasileiro
import alura.com.financask.model.Tipo
import alura.com.financask.model.Transacao
import android.content.Context
import android.view.ViewGroup

class AlteraTransacaoDialog(
    viewGroup: ViewGroup,
    private val context: Context
) : FormularioTransacaoDialog(context, viewGroup) {

    override val tituloBotaoPositivo: String
        get() = "Altera"

    fun chama(transacao: Transacao, transacaoDelegate: TransacaoDelegate) {
        val tipo = transacao.tipo

        super.chama(tipo, transacaoDelegate)

        inicializaCampos(transacao)
    }

    private fun inicializaCampos(transacao: Transacao) {
        val tipo = transacao.tipo
        inicializaCampoValor(transacao)
        inicializaCampoData(transacao)
        InicializaCampoCategoria(tipo, transacao)
    }

    private fun InicializaCampoCategoria(tipo: Tipo, transacao: Transacao) {
        val categoriasRetornadas = context.resources.getStringArray(categoriasPor(tipo))
        val posicaoCategoria = categoriasRetornadas.indexOf(transacao.categoria)
        campoCategoria.setSelection(posicaoCategoria, true)
    }

    private fun inicializaCampoData(transacao: Transacao) {
        campoData.setText(transacao.data.formataParaBrasileiro())
    }

    private fun inicializaCampoValor(transacao: Transacao) {
        campoValor.setText(transacao.valor.toString())
    }

    override fun tituloPor(tipo: Tipo): Int {
        return if (tipo == Tipo.RECEITA) {
            R.string.altera_receita
        } else {
            R.string.altera_despesa
        }
    }
}

