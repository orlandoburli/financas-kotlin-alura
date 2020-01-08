package br.com.orlandoburli.financask.extension

import java.util.*

fun String.limitaEmAte(caracteres: Int): String {
    if (this.length > caracteres) {
        return "${this.substring(0, caracteres)}..."
    }
    return this
}

fun String.converteParaCalendarBR(): Calendar {
    val dataConvertida = formatadorData.parse(this)
    val calendar = Calendar.getInstance()
    calendar.time = dataConvertida
    return calendar
}