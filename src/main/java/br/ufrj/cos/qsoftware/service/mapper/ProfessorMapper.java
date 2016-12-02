package br.ufrj.cos.qsoftware.service.mapper;

import br.ufrj.cos.qsoftware.domain.*;
import br.ufrj.cos.qsoftware.service.dto.ProfessorDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Professor and its DTO ProfessorDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface ProfessorMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.email", target = "userEmail")
    @Mapping(source = "departamento.id", target = "departamentoId")
    @Mapping(source = "departamento.nome", target = "departamentoNome")
    ProfessorDTO professorToProfessorDTO(Professor professor);

    List<ProfessorDTO> professorsToProfessorDTOs(List<Professor> professors);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "departamentoId", target = "departamento")
    @Mapping(target = "comites", ignore = true)
    @Mapping(target = "documentosorientados", ignore = true)
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
