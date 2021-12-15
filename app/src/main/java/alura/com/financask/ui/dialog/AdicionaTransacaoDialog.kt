package alura.com.financask.ui.dialog

import alura.com.financask.R
import alura.com.financask.model.Tipo
import android.content.Context
import android.view.ViewGroup

class AdicionaTransacaoDialog(
        viewGroup: ViewGroup,
        context: Context
) : FormularioTransacaoDialog(context, viewGroup) {

    override val tituloBotaoPositivo: String
        get() = "Adicionar"

    override fun tituloPor(tipo: Tipo): Int {
        return if (tipo == Tipo.RECEITA) {
            R.string.adiciona_receita
        } else {
            R.string.adiciona_despesa
        }
    }
}

