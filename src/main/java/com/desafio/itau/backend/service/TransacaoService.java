package com.desafio.itau.backend.service;

import com.desafio.itau.backend.model.Estatisticas;
import com.desafio.itau.backend.model.Transacao;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class TransacaoService {

    private final List<Transacao> transacoes = new CopyOnWriteArrayList<>();

    public boolean adicionar(Transacao transacao) {
        if (!isTransacaoValida(transacao)) {
            return false;
        }
        transacoes.add(transacao);
        return true;
    }

    private boolean isTransacaoValida(Transacao transacao) {
        OffsetDateTime agora = OffsetDateTime.now();
        return transacao.getValor() >= 0 && !transacao.getDataHora().isAfter(agora);
    }

    public void limpar() {
        transacoes.clear();
    }

    public Estatisticas calcularEstatisticas() {
        OffsetDateTime agora = OffsetDateTime.now();

        DoubleSummaryStatistics stats = transacoes.stream()
                .filter(t -> t.getDataHora().isAfter(agora.minusSeconds(60)))
                .mapToDouble(Transacao::getValor)
                .summaryStatistics();

        return new Estatisticas(
                stats.getCount(),
                stats.getSum(),
                stats.getAverage(),
                stats.getCount() == 0 ? 0 : stats.getMin(),
                stats.getCount() == 0 ? 0 : stats.getMax()
        );
    }
}
