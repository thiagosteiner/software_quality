package br.ufrj.cos.qsoftware.service.mapper;

import br.ufrj.cos.qsoftware.domain.*;
import br.ufrj.cos.qsoftware.service.dto.AlunoDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Aluno and its DTO AlunoDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface AlunoMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.email", target = "userEmail")
    AlunoDTO alunoToAlunoDTO(Aluno aluno);

    List<AlunoDTO> alunosToAlunoDTOs(List<Aluno> alunos);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "documentos", ignore = true)
    Aluno alunoDTOToAluno(AlunoDTO alunoDTO);

    List<Aluno> alunoDTOsToAlunos(List<AlunoDTO> alunoDTOs);
}
