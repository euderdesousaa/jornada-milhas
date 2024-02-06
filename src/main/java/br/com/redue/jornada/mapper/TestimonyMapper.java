package br.com.redue.jornada.mapper;

import br.com.redue.jornada.model.Testimony;
import br.com.redue.jornada.model.dto.TestimonyDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TestimonyMapper {

    TestimonyDto toDTO(Testimony entity);

    @InheritInverseConfiguration
    Testimony toEntity(TestimonyDto dto);

    Testimony toUpdate(Testimony update);
}
