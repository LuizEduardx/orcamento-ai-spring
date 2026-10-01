package com.luizeduardo.orcamentoai.service;

import com.luizeduardo.orcamentoai.domain.TipoTransacao;
import com.luizeduardo.orcamentoai.domain.Transacao;
import com.luizeduardo.orcamentoai.dto.TransacaoRequest;
import com.luizeduardo.orcamentoai.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransacaoService {

    private final TransacaoRepository repository;

    public TransacaoService(TransacaoRepository repository) {
        this.repository = repository;
    }

    public Transacao registrar(TransacaoRequest request) {
        validar(request.valor(), request.categoria());
        return repository.save(new Transacao(
                request.descricao(),
                request.valor(),
                request.tipo(),
                request.categoria().trim()
        ));
    }

    public List<Transacao> listarTodas() {
        return repository.findAll();
    }

    public List<Transacao> listarPorCategoria(String categoria) {
        return repository.findByCategoriaIgnoreCase(categoria);
    }

    public BigDecimal calcularSaldo() {
        return repository.findAll().stream()
                .map(t -> t.getTipo() == TipoTransacao.RECEITA ? t.getValor() : t.getValor().negate())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal totalGastoPorCategoria(String categoria) {
        return repository.findByCategoriaIgnoreCase(categoria).stream()
                .filter(t -> t.getTipo() == TipoTransacao.DESPESA)
                .map(Transacao::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void validar(BigDecimal valor, String categoria) {
        if (valor == null || valor.signum() <= 0) {
            throw new IllegalArgumentException("O valor deve ser maior que zero.");
        }
        if (categoria == null || categoria.isBlank()) {
            throw new IllegalArgumentException("A categoria é obrigatória.");
        }
    }
}
