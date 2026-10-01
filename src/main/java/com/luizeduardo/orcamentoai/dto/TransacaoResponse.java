package com.luizeduardo.orcamentoai.dto;

import com.luizeduardo.orcamentoai.domain.TipoTransacao;
import com.luizeduardo.orcamentoai.domain.Transacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransacaoResponse(
        Long id,
        String descricao,
        BigDecimal valor,
        TipoTransacao tipo,
        String categoria,
        LocalDateTime dataHora
) {
    public static TransacaoResponse from(Transacao transacao) {
        return new TransacaoResponse(
                transacao.getId(),
                transacao.getDescricao(),
                transacao.getValor(),
                transacao.getTipo(),
                transacao.getCategoria(),
                transacao.getDataHora()
        );
    }
}
