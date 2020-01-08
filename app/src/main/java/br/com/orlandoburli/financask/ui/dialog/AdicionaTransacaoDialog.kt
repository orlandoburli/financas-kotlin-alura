package br.com.orlandoburli.financask.ui.dialog

import android.app.DatePickerDialog
import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import br.com.orlandoburli.financask.R
import br.com.orlandoburli.financask.delegate.TransacaoDelegate
import br.com.orlandoburli.financask.extension.converteParaCalendarBR
import br.com.orlandoburli.financask.extension.formataParaBrasileiro
import br.com.orlandoburli.financask.model.Tipo
import br.com.orlandoburli.financask.model.Transacao
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*

class AdicionaTransacaoDialog(
    val viewGroup: ViewGroup,
    val context: Context
) {

    val viewCriada = criaLayout()
    val campoValor = viewCriada.form_transacao_valor
    val campoData = viewCriada.form_transacao_data
    val campoCategoria = viewCriada.form_transacao_categoria

    fun configuraDialog(tipo: Tipo, transacaoDelegate: TransacaoDelegate) {
        configuraCampoData()
        configuraCampoCategoria(tipo)
        configuraFormulario(tipo, transacaoDelegate)
    }

    private fun configuraFormulario(tipo: Tipo, transacaoDelegate: TransacaoDelegate) {
        val titulo = when (tipo) {
            Tipo.RECEITA -> R.string.adiciona_receita
            Tipo.DESPESA -> R.string.adiciona_despesa
        }

        AlertDialog.Builder(context)
            .setTitle(titulo)
            .setView(viewCriada)
            .setPositiveButton("Adicionar") { _, _ ->

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

                transacaoDelegate.delegate(transacao)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun configuraCampoCategoria(tipo: Tipo) {
        var categorias = when (tipo) {
            Tipo.RECEITA -> R.array.categorias_de_receita
            Tipo.DESPESA -> R.array.categorias_de_despesa
        }

        val adapter = ArrayAdapter.createFromResource(
            context,
            categorias,
            android.R.layout.simple_spinner_dropdown_item
        )

        campoCategoria.adapter = adapter
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

    private fun configuraCampoData() {
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