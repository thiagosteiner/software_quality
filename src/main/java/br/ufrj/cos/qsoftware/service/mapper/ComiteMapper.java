package br.ufrj.cos.qsoftware.service.mapper;

import br.ufrj.cos.qsoftware.domain.*;
import br.ufrj.cos.qsoftware.service.dto.ComiteDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Comite and its DTO ComiteDTO.
 */
@Mapper(componentModel = "spring", uses = {ProfessorMapper.class, })
public interface ComiteMapper {

    ComiteDTO comiteToComiteDTO(Comite comite);

    List<ComiteDTO> comitesToComiteDTOs(List<Comite> comites);

    @Mapping(target = "documento", ignore = true)
    Comite comiteDTOToComite(ComiteDTO comiteDTO);

    List<Comite> comiteDTOsToComites(List<ComiteDTO> comiteDTOs);

    default Professor professorFromId(Long id) {
        if (id == null) {
            return null;
        }
        Professor professor = new Professor();
        professor.setId(id);
        return professor;
    }
}
