package br.ufrj.cos.qsoftware.service.mapper;

import br.ufrj.cos.qsoftware.domain.*;
import br.ufrj.cos.qsoftware.service.dto.MonografiaDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Monografia and its DTO MonografiaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MonografiaMapper {

    @Mapping(source = "comite.id", target = "comiteId")
    MonografiaDTO monografiaToMonografiaDTO(Monografia monografia);

    List<MonografiaDTO> monografiasToMonografiaDTOs(List<Monografia> monografias);

    @Mapping(source = "comiteId", target = "comite")
    Monografia monografiaDTOToMonografia(MonografiaDTO monografiaDTO);

    List<Monografia> monografiaDTOsToMonografias(List<MonografiaDTO> monografiaDTOs);

    default Comite comiteFromId(Long id) {
        if (id == null) {
            return null;
        }
        Comite comite = new Comite();
        comite.setId(id);
        return comite;
    }
}
