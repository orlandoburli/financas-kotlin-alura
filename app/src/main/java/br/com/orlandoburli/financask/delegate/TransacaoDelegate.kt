package br.com.orlandoburli.financask.delegate

import br.com.orlandoburli.financask.model.Transacao

interface TransacaoDelegate {

    fun delegate(transacao: Transacao)
}