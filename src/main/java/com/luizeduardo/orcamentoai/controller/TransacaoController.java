package com.luizeduardo.orcamentoai.controller;

import com.luizeduardo.orcamentoai.dto.TransacaoRequest;
import com.luizeduardo.orcamentoai.dto.TransacaoResponse;
import com.luizeduardo.orcamentoai.service.TransacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/transacoes")
public class TransacaoController {

    private final TransacaoService service;

    public TransacaoController(TransacaoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransacaoResponse criar(@Valid @RequestBody TransacaoRequest request) {
        return TransacaoResponse.from(service.registrar(request));
    }

    @GetMapping
    public List<TransacaoResponse> listar() {
        return service.listarTodas().stream().map(TransacaoResponse::from).toList();
    }

    @GetMapping("/categoria/{categoria}")
    public List<TransacaoResponse> listarPorCategoria(@PathVariable String categoria) {
        return service.listarPorCategoria(categoria).stream().map(TransacaoResponse::from).toList();
    }

    @GetMapping("/saldo")
    public BigDecimal saldo() {
        return service.calcularSaldo();
    }

    @GetMapping("/total-gasto/{categoria}")
    public BigDecimal totalGastoPorCategoria(@PathVariable String categoria) {
        return service.totalGastoPorCategoria(categoria);
    }
}
