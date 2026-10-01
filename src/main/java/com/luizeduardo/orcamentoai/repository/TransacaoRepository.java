package com.luizeduardo.orcamentoai.repository;

import com.luizeduardo.orcamentoai.domain.Transacao;
import com.luizeduardo.orcamentoai.domain.TipoTransacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findByCategoriaIgnoreCase(String categoria);
    List<Transacao> findByTipo(TipoTransacao tipo);
}
