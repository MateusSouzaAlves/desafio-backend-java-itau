package com.desafio.itau.backend.mapper;

import com.desafio.itau.backend.dto.EstatisticasDTO;
import com.desafio.itau.backend.model.Estatisticas;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EstatisticasMapper {

    private final ModelMapper modelMapper;

    public EstatisticasMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public EstatisticasDTO toDTO(Estatisticas estatisticas) {
        return modelMapper.map(estatisticas, EstatisticasDTO.class);
    }
}
