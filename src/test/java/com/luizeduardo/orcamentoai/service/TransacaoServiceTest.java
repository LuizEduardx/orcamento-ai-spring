package com.luizeduardo.orcamentoai.service;

import com.luizeduardo.orcamentoai.domain.TipoTransacao;
import com.luizeduardo.orcamentoai.domain.Transacao;
import com.luizeduardo.orcamentoai.dto.TransacaoRequest;
import com.luizeduardo.orcamentoai.repository.TransacaoRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TransacaoServiceTest {

    private final TransacaoRepository repository = mock(TransacaoRepository.class);
    private final TransacaoService service = new TransacaoService(repository);

    @Test
    void deveCalcularTotalGastoPorCategoria() {
        when(repository.findByCategoriaIgnoreCase("Alimentacao")).thenReturn(List.of(
                new Transacao("Mercado", new BigDecimal("120.00"), TipoTransacao.DESPESA, "Alimentacao"),
                new Transacao("Restaurante", new BigDecimal("80.00"), TipoTransacao.DESPESA, "Alimentacao")
        ));

        assertEquals(new BigDecimal("200.00"), service.totalGastoPorCategoria("Alimentacao"));
    }

    @Test
    void deveRejeitarValorNaoPositivo() {
        var request = new TransacaoRequest("Teste", BigDecimal.ZERO, TipoTransacao.DESPESA, "Outros");
        assertThrows(IllegalArgumentException.class, () -> service.registrar(request));
    }
}
