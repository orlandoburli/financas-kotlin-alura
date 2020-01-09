package br.com.orlandoburli.financask.ui.dialog

import android.content.Context
import android.view.ViewGroup
import br.com.orlandoburli.financask.R
import br.com.orlandoburli.financask.extension.formataParaBrasileiro
import br.com.orlandoburli.financask.model.Tipo
import br.com.orlandoburli.financask.model.Transacao

class AlteraTransacaoDialog(viewGroup: ViewGroup, context: Context) :
    FormularioTransacaoDialog(viewGroup, context) {

    override val titulo: String get() = "Alterar"

    fun chama(transacao: Transacao, delegate: (transacao: Transacao) -> Unit) {
        val tipo = transacao.tipo

        chama(tipo, delegate)

        inicializarCampos(transacao)
    }

    private fun inicializarCampos(transacao: Transacao) {
        inicializaCampoValor(transacao)
        inicializaCampoData(transacao)
        inicializaCampoCategoria(transacao)
    }

    private fun inicializaCampoCategoria(transacao: Transacao) {
        val categoriasRetornadas = context.resources.getStringArray(categoriasPor(transacao.tipo))
        val posicaoCategoria = categoriasRetornadas.indexOf(transacao.categoria)
        campoCategoria.setSelection(posicaoCategoria, true)
    }

    private fun inicializaCampoData(transacao: Transacao) {
        campoData.setText(transacao.data.formataParaBrasileiro())
    }

    private fun inicializaCampoValor(transacao: Transacao) {
        campoValor.setText(transacao.valor.toString())
    }

    override fun tituloPor(tipo: Tipo): Int {
        return when (tipo) {
            Tipo.RECEITA -> R.string.altera_receita
            Tipo.DESPESA -> R.string.altera_despesa
        }
    }
}