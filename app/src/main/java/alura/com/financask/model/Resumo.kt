package alura.com.financask.model

import java.math.BigDecimal

class Resumo(private val transacoes: List<Transacao>) {

    val receita get() = somaPor(Tipo.RECEITA)

    val despesa get() = somaPor(Tipo.DESPESA)

    fun total() = receita.subtract(despesa) //Single Expression Function

    private fun somaPor(tipo: Tipo): BigDecimal {
        val somadeTransacoesPor = BigDecimal(transacoes
            .filter { it.tipo == tipo }// Lambda Function
            .sumOf { it.valor.toDouble() })// Lambda Function
        return somadeTransacoesPor
    }
}