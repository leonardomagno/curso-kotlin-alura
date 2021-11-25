package alura.com.financask.ui.dialog

import alura.com.financask.R
import alura.com.financask.delegate.TransacaoDelegate
import alura.com.financask.extensions.converteParaCalendar
import alura.com.financask.extensions.formataParaBrasileiro
import alura.com.financask.model.Tipo
import alura.com.financask.model.Transacao
import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*

class AdicionaTransacaoDialog(
    private val viewGroup: ViewGroup,
    private val context: Context
) {
    private val viewCriada = criaLayout()

    fun configuraDialog(transacaoDelegate: TransacaoDelegate) {
        configuraCampoData()
        configuraCampoCategoria()
        configuraFormulario(transacaoDelegate)
    }

    private fun configuraFormulario(transacaoDelegate: TransacaoDelegate) {
        AlertDialog.Builder(context)
            .setTitle(R.string.adiciona_receita)
            .setView(viewCriada)
            .setPositiveButton("Adicionar")
            { _, _ ->
                val valorEmTexto = viewCriada.form_transacao_valor.text.toString()
                val dataEmTexto = viewCriada.form_transacao_data.text.toString()
                val categoriaEmTexto = viewCriada.form_transacao_categoria.selectedItem.toString()

                val valor = converteCampoValor(valorEmTexto)
                val data = dataEmTexto.converteParaCalendar()

                val transacaoCriada = Transacao(
                    tipo = Tipo.RECEITA,
                    valor = valor,
                    data = data,
                    categoria = categoriaEmTexto
                )

                transacaoDelegate.delegate(transacaoCriada)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun converteCampoValor(valorEmTexto: String): BigDecimal {
        return try {
            BigDecimal(valorEmTexto)
        } catch (excepetion: NumberFormatException) {
            Toast.makeText(context, "Falha na conversão de valor", Toast.LENGTH_LONG)
                .show()
            BigDecimal.ZERO
        }
    }

    private fun configuraCampoCategoria() {
        val adapter = ArrayAdapter
            .createFromResource(
                context,
                R.array.categorias_de_receita,
                android.R.layout.simple_spinner_dropdown_item
            )
        viewCriada.form_transacao_categoria.adapter = adapter
    }

    private fun criaLayout(): View {
        return LayoutInflater.from(context)
            .inflate(R.layout.form_transacao, viewGroup, false)
    }

    private fun configuraCampoData() {
        val hoje = Calendar.getInstance()
        val ano = hoje.get(Calendar.YEAR)
        val mes = hoje.get(Calendar.MONTH)
        val dia = hoje.get(Calendar.DAY_OF_MONTH)

        with(viewCriada) {
            form_transacao_data.setText(hoje.formataParaBrasileiro())
            setOnClickListener {
                DatePickerDialog(
                    it.context,
                    { _, ano, mes, dia ->
                        val dataSelecionada = Calendar.getInstance()// Cria a instância da Data
                        dataSelecionada.set(
                            ano,
                            mes,
                            dia
                        )// Salava a data de acordo com seleção do usuário
                        viewCriada.form_transacao_data.setText(dataSelecionada.formataParaBrasileiro())// Formata a data e exibe na tela
                    }, ano, mes, dia
                ) // data padrão exibiada ao abrir o date picker
                    .show()
            }
        }

    }
}