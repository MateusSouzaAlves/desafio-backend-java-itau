package com.desafio.itau.backend.mapper;

import com.desafio.itau.backend.dto.TransacaoDTO;
import com.desafio.itau.backend.model.Transacao;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TransacaoMapper {

    private final ModelMapper modelMapper;

    public TransacaoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Transacao toEntity(TransacaoDTO dto) {
        return modelMapper.map(dto, Transacao.class);
    }

    public TransacaoDTO toDTO(Transacao entity) {
        return modelMapper.map(entity, TransacaoDTO.class);
    }
}
