package br.com.orlandoburli.financask.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import br.com.orlandoburli.financask.R
import br.com.orlandoburli.financask.delegate.TransacaoDelegate
import br.com.orlandoburli.financask.model.Tipo
import br.com.orlandoburli.financask.model.Tipo.DESPESA
import br.com.orlandoburli.financask.model.Tipo.RECEITA
import br.com.orlandoburli.financask.model.Transacao
import br.com.orlandoburli.financask.ui.ResumoView
import br.com.orlandoburli.financask.ui.adapter.ListaTransacoesAdapter
import br.com.orlandoburli.financask.ui.dialog.AdicionaTransacaoDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import java.math.BigDecimal

class ListaTransacoesActivity : AppCompatActivity() {

    private val transacoes: MutableList<Transacao> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        atualizaTransacoes()

        configuraFab()
    }

    private fun configuraFab() {
        lista_transacoes_adiciona_receita.setOnClickListener {
            chamaDialogDeAdicao(RECEITA)
        }

        lista_transacoes_adiciona_despesa.setOnClickListener {
            chamaDialogDeAdicao(DESPESA)
        }
    }

    private fun chamaDialogDeAdicao(tipo: Tipo) {
        AdicionaTransacaoDialog(window.decorView as ViewGroup, this)
            .configuraDialog(tipo, object : TransacaoDelegate {
                override fun delegate(transacao: Transacao) {
                    transacoes.add(transacao)
                    atualizaTransacoes()
                    lista_transacoes_adiciona_menu.close(true)
                }
            })
    }

    private fun atualizaTransacoes() {
        configuraResumo()
        configuraLista()
    }

    private fun configuraResumo() {
        ResumoView(this, window.decorView, transacoes).atualiza()
    }

    private fun configuraLista() {
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)
        lista_transacoes_listview.setOnItemClickListener { parent, view, posicao, id ->
            val transacao = transacoes[posicao]

        }
    }

    private fun transacoesDeExemplo(): List<Transacao> {
        return listOf(
            Transacao(
                valor = BigDecimal(2000.5),
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