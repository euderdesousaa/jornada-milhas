package br.com.redue.jornada.mapper;

import br.com.redue.jornada.model.Destination;
import br.com.redue.jornada.model.dto.destination.DestinationDto;
import br.com.redue.jornada.model.dto.destination.DestinationUpdateDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DestinationMapper {

    DestinationDto toDTO(Destination entity);

    @InheritInverseConfiguration
    Destination toEntity(DestinationDto dto);

    DestinationUpdateDTO toUpdate(Destination destination);

}
