package br.com.orlandoburli.financask.ui.dialog

import android.content.Context
import android.view.ViewGroup
import br.com.orlandoburli.financask.R
import br.com.orlandoburli.financask.model.Tipo

class AdicionaTransacaoDialog(viewGroup: ViewGroup, context: Context) :
    FormularioTransacaoDialog(viewGroup, context) {

    override val titulo: String get() = "Adicionar"

    override fun tituloPor(tipo: Tipo): Int {
        return when (tipo) {
            Tipo.RECEITA -> R.string.adiciona_receita
            Tipo.DESPESA -> R.string.adiciona_despesa
        }
    }
}