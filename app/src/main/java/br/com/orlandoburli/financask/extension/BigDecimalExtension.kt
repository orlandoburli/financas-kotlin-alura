package br.com.orlandoburli.financask.extension

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

val formatoBrasileiro: NumberFormat = DecimalFormat.getCurrencyInstance(Locale("pt", "br"))

fun BigDecimal.formataParaBrasileiro(): String {
    return formatoBrasileiro.format(this)
}