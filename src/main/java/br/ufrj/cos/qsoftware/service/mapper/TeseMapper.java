package br.ufrj.cos.qsoftware.service.mapper;

import br.ufrj.cos.qsoftware.domain.*;
import br.ufrj.cos.qsoftware.service.dto.TeseDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Tese and its DTO TeseDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TeseMapper {

    @Mapping(source = "comite.id", target = "comiteId")
    TeseDTO teseToTeseDTO(Tese tese);

    List<TeseDTO> teseToTeseDTOs(List<Tese> tese);

    @Mapping(source = "comiteId", target = "comite")
    Tese teseDTOToTese(TeseDTO teseDTO);

    List<Tese> teseDTOsToTese(List<TeseDTO> teseDTOs);

    default Comite comiteFromId(Long id) {
        if (id == null) {
            return null;
        }
        Comite comite = new Comite();
        comite.setId(id);
        return comite;
    }
}
