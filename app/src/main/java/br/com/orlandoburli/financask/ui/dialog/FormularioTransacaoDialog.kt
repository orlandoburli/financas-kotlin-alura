package br.com.orlandoburli.financask.ui.dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import br.com.orlandoburli.financask.R
import br.com.orlandoburli.financask.extension.converteParaCalendarBR
import br.com.orlandoburli.financask.extension.formataParaBrasileiro
import br.com.orlandoburli.financask.model.Tipo
import br.com.orlandoburli.financask.model.Transacao
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*

open abstract class FormularioTransacaoDialog(val viewGroup: ViewGroup, val context: Context) {
    val viewCriada = criaLayout()
    val campoValor = viewCriada.form_transacao_valor
    val campoData = viewCriada.form_transacao_data
    val campoCategoria = viewCriada.form_transacao_categoria

    abstract protected val titulo: String

    fun configuraDialog(tipo: Tipo, delegate: (transacao: Transacao) -> Unit) {
        chama(tipo, delegate)
    }

    protected fun chama(
        tipo: Tipo,
        delegate: (transacao: Transacao) -> Unit
    ) {
        configuraCampoData()
        configuraCampoCategoria(tipo)
        configuraFormulario(tipo, delegate)
    }

    protected fun configuraFormulario(tipo: Tipo, delegate: (transacao: Transacao) -> Unit) {
        val titulo = tituloPor(tipo)

        AlertDialog.Builder(context)
            .setTitle(titulo)
            .setView(viewCriada)
            .setPositiveButton(titulo) { _, _ ->
                val valorEmTexto = campoValor.text.toString()
                val dataEmTexto = campoData.text.toString()
                val categoriaEmTexto = campoCategoria.selectedItem.toString()

                var valor =
                    converteCampoEmValor(valorEmTexto)

                val data = dataEmTexto.converteParaCalendarBR()

                val transacao = Transacao(
                    tipo = tipo,
                    valor = valor,
                    data = data,
                    categoria = categoriaEmTexto
                )

                delegate(transacao)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    abstract protected fun tituloPor(tipo: Tipo): Int

    protected fun configuraCampoCategoria(tipo: Tipo) {
        var categorias = categoriasPor(tipo)

        val adapter = ArrayAdapter.createFromResource(
            context,
            categorias,
            android.R.layout.simple_spinner_dropdown_item
        )

        campoCategoria.adapter = adapter
    }

    protected fun categoriasPor(tipo: Tipo): Int {
        return when (tipo) {
            Tipo.RECEITA -> R.array.categorias_de_receita
            Tipo.DESPESA -> R.array.categorias_de_despesa
        }
    }

    private fun converteCampoEmValor(valorEmTexto: String): BigDecimal {
        return try {
            BigDecimal(valorEmTexto)
        } catch (e: NumberFormatException) {
            Toast.makeText(context, "Falha na conversão de valor", Toast.LENGTH_LONG)
                .show()
            BigDecimal.ZERO
        }
    }

    protected fun configuraCampoData() {
        val hoje = Calendar.getInstance()
        val ano = hoje.get(Calendar.YEAR)
        val mes = hoje.get(Calendar.MONTH)
        val dia = hoje.get(Calendar.DAY_OF_MONTH)

        campoData.setText(hoje.formataParaBrasileiro())
        campoData.setOnClickListener {
            DatePickerDialog(
                context,
                { view, ano, mes, dia ->
                    val dataSelecionada = Calendar.getInstance()
                    dataSelecionada.set(ano, mes, dia)
                    campoData.setText(dataSelecionada.formataParaBrasileiro())
                }, ano, mes, dia
            ).show()
        }
    }

    private fun criaLayout(): View {
        return LayoutInflater.from(context)
            .inflate(R.layout.form_transacao, viewGroup, false)
    }
}