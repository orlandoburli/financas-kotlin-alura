package br.com.orlandoburli.financask.ui.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import br.com.orlandoburli.financask.R
import br.com.orlandoburli.financask.extension.formataParaBrasileiro
import br.com.orlandoburli.financask.extension.limitaEmAte
import br.com.orlandoburli.financask.model.Tipo
import br.com.orlandoburli.financask.model.Transacao
import kotlinx.android.synthetic.main.transacao_item.view.*

class ListaTransacoesAdapter(
    private val transacoes: List<Transacao>,
    private val context: Context
) : BaseAdapter() {

    val LIMITE_DA_CATEGORIA = 14

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val viewCriada = LayoutInflater
            .from(context)
            .inflate(R.layout.transacao_item, parent, false)

        val transacao = getItem(position)

        configuraCorValor(transacao, viewCriada)
        configuraIconeTipo(transacao, viewCriada)
        configuraCampos(viewCriada, transacao)

        return viewCriada
    }

    private fun configuraCampos(
        viewCriada: View,
        transacao: Transacao
    ) {
        viewCriada.transacao_categoria.text = transacao.categoria.limitaEmAte(LIMITE_DA_CATEGORIA)
        viewCriada.transacao_valor.text = transacao.valor.formataParaBrasileiro()
        viewCriada.transacao_data.text = transacao.data.formataParaBrasileiro()
    }

    private fun configuraCorValor(
        transacao: Transacao,
        viewCriada: View
    ) {
        viewCriada.transacao_valor.setTextColor(corPor(transacao))
    }

    private fun configuraIconeTipo(
        transacao: Transacao,
        viewCriada: View
    ) {
        viewCriada.transacao_icone.setBackgroundResource(iconePor(transacao))
    }

    private fun iconePor(transacao: Transacao): Int {
        return when (transacao.tipo) {
            Tipo.RECEITA -> R.drawable.icone_transacao_item_receita
            Tipo.DESPESA -> R.drawable.icone_transacao_item_despesa
        }
    }

    private fun corPor(transacao: Transacao): Int {
        return when (transacao.tipo) {
            Tipo.RECEITA -> ContextCompat.getColor(context, R.color.receita)
            Tipo.DESPESA -> ContextCompat.getColor(context, R.color.despesa)
        }

    }

    override fun getItem(position: Int): Transacao {
        return transacoes[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return transacoes.size
    }
}