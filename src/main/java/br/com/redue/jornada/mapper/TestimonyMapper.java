package br.com.redue.jornada.mapper;

import br.com.redue.jornada.model.Testimony;
import br.com.redue.jornada.model.dto.testimony.TestimonyDto;
import br.com.redue.jornada.model.dto.testimony.TestimonyUpdateDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TestimonyMapper {

    TestimonyDto toDTO(Testimony entity);

    @InheritInverseConfiguration
    Testimony toEntity(TestimonyDto dto);

    TestimonyUpdateDto toUpdate(Testimony update);
}
