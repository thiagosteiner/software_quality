package br.ufrj.cos.qsoftware.service.mapper;

import br.ufrj.cos.qsoftware.domain.*;
import br.ufrj.cos.qsoftware.service.dto.ProfessorDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Professor and its DTO ProfessorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProfessorMapper {

    @Mapping(source = "departamento.id", target = "departamentoId")
    ProfessorDTO professorToProfessorDTO(Professor professor);

    List<ProfessorDTO> professorsToProfessorDTOs(List<Professor> professors);

    @Mapping(target = "alunos", ignore = true)
    @Mapping(source = "departamentoId", target = "departamento")
    Professor professorDTOToProfessor(ProfessorDTO professorDTO);

    List<Professor> professorDTOsToProfessors(List<ProfessorDTO> professorDTOs);

    default Departamento departamentoFromId(Long id) {
        if (id == null) {
            return null;
        }
        Departamento departamento = new Departamento();
        departamento.setId(id);
        return departamento;
    }
}
