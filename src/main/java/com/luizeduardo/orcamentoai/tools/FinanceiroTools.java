package com.luizeduardo.orcamentoai.tools;

import com.luizeduardo.orcamentoai.domain.TipoTransacao;
import com.luizeduardo.orcamentoai.dto.TransacaoRequest;
import com.luizeduardo.orcamentoai.service.TransacaoService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class FinanceiroTools {

    private final TransacaoService service;

    public FinanceiroTools(TransacaoService service) {
        this.service = service;
    }

    @Tool(name = "registrar-transacao", description = "Registra uma receita ou despesa financeira")
    public String registrarTransacao(
            @ToolParam(description = "Descrição da transação") String descricao,
            @ToolParam(description = "Valor positivo da transação") BigDecimal valor,
            @ToolParam(description = "Tipo da transação: RECEITA ou DESPESA") TipoTransacao tipo,
            @ToolParam(description = "Categoria da transação") String categoria) {

        var transacao = service.registrar(new TransacaoRequest(descricao, valor, tipo, categoria));
        return "Transação registrada com sucesso. ID: " + transacao.getId();
    }

    @Tool(name = "consultar-saldo", description = "Consulta o saldo financeiro atual")
    public BigDecimal consultarSaldo() {
        return service.calcularSaldo();
    }

    @Tool(name = "consultar-total-gasto-por-categoria", description = "Consulta o total de despesas em uma categoria")
    public BigDecimal consultarTotalGastoPorCategoria(
            @ToolParam(description = "Categoria a ser consultada") String categoria) {
        return service.totalGastoPorCategoria(categoria);
    }
}
