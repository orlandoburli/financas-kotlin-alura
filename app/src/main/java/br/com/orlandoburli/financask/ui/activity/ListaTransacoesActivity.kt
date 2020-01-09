package br.com.orlandoburli.financask.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import br.com.orlandoburli.financask.R
import br.com.orlandoburli.financask.dao.TransacaoDAO
import br.com.orlandoburli.financask.model.Tipo
import br.com.orlandoburli.financask.model.Tipo.DESPESA
import br.com.orlandoburli.financask.model.Tipo.RECEITA
import br.com.orlandoburli.financask.model.Transacao
import br.com.orlandoburli.financask.ui.ResumoView
import br.com.orlandoburli.financask.ui.adapter.ListaTransacoesAdapter
import br.com.orlandoburli.financask.ui.dialog.AdicionaTransacaoDialog
import br.com.orlandoburli.financask.ui.dialog.AlteraTransacaoDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import java.math.BigDecimal

class ListaTransacoesActivity : AppCompatActivity() {

    private val dao = TransacaoDAO()
    private val transacoes = dao.transacoes

    private val viewDaActivity: View by lazy()
    {
        window.decorView
    }

    private val viewGroupDaActivity: ViewGroup by lazy {
        viewDaActivity as ViewGroup
    }

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
        AdicionaTransacaoDialog(viewGroupDaActivity, this)
            .configuraDialog(tipo) {
                adicionaTransacao(it)

                atualizaTransacoes()

                lista_transacoes_adiciona_menu.close(true)
            }
    }

    private fun configuraResumo() {
        ResumoView(this, viewGroupDaActivity, transacoes).atualiza()
    }

    private fun configuraLista() {
        with(lista_transacoes_listview) {
            adapter = ListaTransacoesAdapter(transacoes, this@ListaTransacoesActivity)
            setOnItemClickListener { _, _, posicao, _ ->
                val transacao = transacoes[posicao]
                chamaDialogDeAlteracao(transacao, posicao)
            }
            setOnCreateContextMenuListener { menu, view, context ->
                menu.add(Menu.NONE, 1, Menu.NONE, "Remover")
            }
        }
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        val itemId = item?.itemId
        if (itemId == 1) {
            val menuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo

            val posicaoDaTransacao = menuInfo.position

            removeTransacao(posicaoDaTransacao)
        }
        return super.onContextItemSelected(item)
    }

    private fun chamaDialogDeAlteracao(
        transacao: Transacao,
        posicao: Int
    ) {
        AlteraTransacaoDialog(viewGroupDaActivity, this)
            .chama(transacao) {
                alteraTransacao(transacao, posicao)

                atualizaTransacoes()

                lista_transacoes_adiciona_menu.close(true)
            }
    }

    private fun atualizaTransacoes() {
        configuraResumo()
        configuraLista()
    }

    private fun alteraTransacao(
        transacao: Transacao,
        posicao: Int
    ) {
        dao.altera(transacao, posicao)
    }

    private fun removeTransacao(posicao: Int) {
        dao.remove(posicao)

        atualizaTransacoes()
    }

    private fun adicionaTransacao(transacao: Transacao) {
        dao.adiciona(transacao)
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
