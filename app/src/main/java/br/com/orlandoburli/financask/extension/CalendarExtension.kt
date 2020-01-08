package br.com.orlandoburli.financask.extension

import java.text.SimpleDateFormat
import java.util.*

val formatadorData = SimpleDateFormat("dd/MM/yyyy")

fun Calendar.formataParaBrasileiro(): String {
    return formatadorData.format(this.time)
}