package com.desafio.itau.backend.controller;


import com.desafio.itau.backend.dto.EstatisticasDTO;
import com.desafio.itau.backend.dto.TransacaoDTO;
import com.desafio.itau.backend.mapper.EstatisticasMapper;
import com.desafio.itau.backend.mapper.TransacaoMapper;
import com.desafio.itau.backend.model.Estatisticas;
import com.desafio.itau.backend.model.Transacao;
import com.desafio.itau.backend.service.TransacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class TransacaoController {

    private final TransacaoService service;
    private final TransacaoMapper transacaoMapper;
    private final EstatisticasMapper estatisticasMapper;

    public TransacaoController(TransacaoService service,
                               TransacaoMapper transacaoMapper,
                               EstatisticasMapper estatisticasMapper) {
        this.service = service;
        this.transacaoMapper = transacaoMapper;
        this.estatisticasMapper = estatisticasMapper;
    }

    @PostMapping("/transacao")
    public ResponseEntity<Void> criar(@RequestBody @Valid TransacaoDTO dto) {
        Transacao transacao = transacaoMapper.toEntity(dto);
        return service.adicionar(transacao)
                ? ResponseEntity.status(HttpStatus.CREATED).build()
                : ResponseEntity.unprocessableEntity().build();
    }

    @DeleteMapping("/transacao")
    public ResponseEntity<Void> limpar() {
        service.limpar();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/estatistica")
    public ResponseEntity<EstatisticasDTO> obter() {
        Estatisticas estatisticas = service.calcularEstatisticas();
        EstatisticasDTO dto = estatisticasMapper.toDTO(estatisticas);
        return ResponseEntity.ok(dto);
    }
}

