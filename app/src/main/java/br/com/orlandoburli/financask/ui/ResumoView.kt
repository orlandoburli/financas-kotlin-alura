package br.com.orlandoburli.financask.ui

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import br.com.orlandoburli.financask.R
import br.com.orlandoburli.financask.extension.formataParaBrasileiro
import br.com.orlandoburli.financask.model.Resumo
import br.com.orlandoburli.financask.model.Transacao
import kotlinx.android.synthetic.main.resumo_card.view.*
import java.math.BigDecimal

class ResumoView(
    private val context: Context,
    private val view: View,
    private val transacoes: List<Transacao>
) {
    private val resumo = Resumo(transacoes)
    private val corReceita = ContextCompat.getColor(context, R.color.receita)
    private val corDespesa = ContextCompat.getColor(context, R.color.despesa)

    fun atualiza() {
        adicionaReceitaNoResumo()
        adicionaDespesaNoResumo()
        adicionaTotal()
    }

    private fun adicionaReceitaNoResumo() {
        with(view.resumo_card_receita) {
            setTextColor(corReceita)
            text = resumo.receita.formataParaBrasileiro()
        }
    }

    private fun adicionaDespesaNoResumo() {
        with(view.resumo_card_despesa) {
            setTextColor(corDespesa)
            text = resumo.despesa.formataParaBrasileiro()
        }
    }

    private fun adicionaTotal() {
        val total = resumo.total
        with(view.resumo_card_total) {
            text = total.formataParaBrasileiro()
            setTextColor(corPor(total))
        }
    }

    private fun corPor(valor: BigDecimal): Int {
        return if (valor < BigDecimal.ZERO) {
            corDespesa
        } else {
            corReceita
        }
    }
}


