package br.com.orlandoburli.financask.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.orlandoburli.financask.R
import br.com.orlandoburli.financask.model.Tipo.DESPESA
import br.com.orlandoburli.financask.model.Tipo.RECEITA
import br.com.orlandoburli.financask.model.Transacao
import br.com.orlandoburli.financask.ui.adapter.ListaTransacoesAdapter
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import java.math.BigDecimal

class ListaTransacoesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        configuraLista(
            transacoesDeExemplo()
        )
    }

    private fun configuraLista(transacoes: List<Transacao>) {
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)
    }

    private fun transacoesDeExemplo(): List<Transacao> {
        return listOf(
            Transacao(
                valor = BigDecimal(20.5),
                categoria = "Almoço de final de semana com a galera",
                tipo = DESPESA
            ),
            Transacao(
                valor = BigDecimal(100),
                categoria = "Economia",
                tipo = RECEITA
            ),
            Transacao(
                valor = BigDecimal(200),
                tipo = DESPESA
            ),
            Transacao(
                valor = BigDecimal(500),
                tipo = RECEITA,
                categoria = "Prêmio"
            )
        )
    }
}