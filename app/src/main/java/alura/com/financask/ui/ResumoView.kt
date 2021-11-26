package alura.com.financask.ui

import alura.com.financask.R
import alura.com.financask.extensions.formataParaBrasileiro
import alura.com.financask.model.Resumo
import alura.com.financask.model.Transacao
import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.resumo_card.view.*
import java.math.BigDecimal

class ResumoView(
    context: Context,
    private val view: View?,
    transacoes: List<Transacao>
) {

    private val resumo: Resumo = Resumo(transacoes)
    private val corReceita = ContextCompat.getColor(context, R.color.receita)
    private val corDespesa = ContextCompat.getColor(context, R.color.despesa)

    fun atualiza() {
        adicionaReceita()
        adicionaDespesa()
        adicionaTotal()
    }

    private fun adicionaReceita() {
        val totalReceita = resumo.receita
        view?.let {
            with(it.resumo_card_receita) {
                setTextColor(corReceita)
                text = totalReceita.formataParaBrasileiro()
            }
        }
    }

    private fun adicionaDespesa() {
        val totalDespesa = resumo.despesa
        view?.let {
            with(it.resumo_card_despesa) {
                text = totalDespesa.formataParaBrasileiro()
                setTextColor(corDespesa)
            }
        }
    }

    private fun adicionaTotal() {
        val total = resumo.total()
        val cor = corPor(total)
        view?.let {
            with(it.resumo_card_total) {
                setTextColor(cor)
                text = total.formataParaBrasileiro()
            }
        }
    }

    private fun corPor(valor: BigDecimal): Int {
        if (valor >= BigDecimal.ZERO) {
            return corReceita
        }
        return corDespesa
    }


}