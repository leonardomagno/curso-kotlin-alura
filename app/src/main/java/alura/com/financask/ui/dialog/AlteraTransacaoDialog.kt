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

class AlteraTransacaoDialog(
    private val viewGroup: ViewGroup,
    private val context: Context
) {
    private val viewCriada = criaLayout()
    private val campoValor = viewCriada.form_transacao_valor
    private val campoCategoria = viewCriada.form_transacao_categoria
    private val campoData = viewCriada.form_transacao_data

    fun chama(transacao: Transacao, transacaoDelegate: TransacaoDelegate) {
        val tipo = transacao.tipo

        configuraCampoData()
        configuraCampoCategoria(tipo)
        configuraFormulario(tipo, transacaoDelegate)

        campoValor.setText(transacao.valor.toString())
        campoData.setText(transacao.data.formataParaBrasileiro())
        val categoriasRetornadas = context.resources.getStringArray(categoriasPor(tipo))
        val posicaoCategoria = categoriasRetornadas.indexOf(transacao.categoria)
        campoCategoria.setSelection(posicaoCategoria, true)
    }

    private fun configuraFormulario(tipo: Tipo, transacaoDelegate: TransacaoDelegate) {

        val titulo = tituloPor(tipo)

        AlertDialog.Builder(context)
            .setTitle(titulo)
            .setView(viewCriada)
            .setPositiveButton("Alterar")
            { _, _ ->
                val valorEmTexto = campoValor.text.toString()
                val dataEmTexto = campoData.text.toString()
                val categoriaEmTexto = campoCategoria.selectedItem.toString()

                val valor = converteCampoValor(valorEmTexto)
                val data = dataEmTexto.converteParaCalendar()

                val transacaoCriada = Transacao(
                    tipo = tipo,
                    valor = valor,
                    data = data,
                    categoria = categoriaEmTexto
                )

                transacaoDelegate.delegate(transacaoCriada)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun tituloPor(tipo: Tipo): Int {
        return if (tipo == Tipo.RECEITA) {
            R.string.altera_receita
        } else {
            R.string.altera_despesa
        }
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

    private fun configuraCampoCategoria(tipo: Tipo) {
        val categorias = categoriasPor(tipo)

        val adapter = ArrayAdapter
            .createFromResource(
                context,
                categorias,
                android.R.layout.simple_spinner_dropdown_item
            )
        campoCategoria.adapter = adapter
    }

    private fun categoriasPor(tipo: Tipo) = if (tipo == Tipo.RECEITA) {
        R.array.categorias_de_receita
    } else {
        R.array.categorias_de_despesa
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
                        campoData.setText(dataSelecionada.formataParaBrasileiro())// Formata a data e exibe na tela
                    }, ano, mes, dia
                ) // data padrão exibiada ao abrir o date picker
                    .show()
            }
        }
    }
}

