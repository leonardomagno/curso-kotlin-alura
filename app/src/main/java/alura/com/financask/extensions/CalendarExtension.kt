package alura.com.financask.extensions

import java.text.SimpleDateFormat
import java.util.Calendar

fun Calendar.formataParaBrasileiro(): String {
    val formatoBrasileiro = "dd/MM/yyyy"
    val format = SimpleDateFormat(formatoBrasileiro)
    return format.format(this.time)
}