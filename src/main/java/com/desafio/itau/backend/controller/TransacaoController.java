package com.desafio.itau.backend.controller;


import com.desafio.itau.backend.model.Estatisticas;
import com.desafio.itau.backend.model.Transacao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping
public class TransacaoController {

    private final List<Transacao> transacoes = new CopyOnWriteArrayList<>();

    @PostMapping("/transacao")
    public ResponseEntity<Void> criarTransacao(@RequestBody Transacao transacao){
        boolean adicionada = adicionarTransacao(transacao);

        if (adicionada == true){
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    private boolean adicionarTransacao(Transacao transacao) {

        if (transacao.getValor() < 0 || transacao.getDataHora().isAfter(OffsetDateTime.now())) {
            return false;
        }

        transacoes.add(transacao);
        return true;
    }

    @DeleteMapping("/transacao")
    public ResponseEntity<Void> deletarTransacoes(){
        transacoes.clear();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/estatistica")
    public ResponseEntity<Estatisticas> obterEstatisticas(){
        OffsetDateTime agora = OffsetDateTime.now();

        DoubleSummaryStatistics status60s = transacoes.stream().filter( t -> t.getDataHora().isAfter(agora.minusSeconds(60)))
                .mapToDouble(Transacao::getValor)
                .summaryStatistics();

        Estatisticas estatisticas = new Estatisticas(
                status60s.getCount(),
                status60s.getSum(),
                status60s.getAverage(),
                status60s.getCount() == 0 ? 0 : status60s.getMin(),
                status60s.getCount() == 0 ? 0 : status60s.getMax()
        );

        return ResponseEntity.ok(estatisticas);
    }

}
