package br.ufrj.cos.qsoftware.service.mapper;

import br.ufrj.cos.qsoftware.domain.*;
import br.ufrj.cos.qsoftware.service.dto.AlunoDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Aluno and its DTO AlunoDTO.
 */
@Mapper(componentModel = "spring", uses = {PublicacaoMapper.class, })
public interface AlunoMapper {

    @Mapping(source = "monografia.id", target = "monografiaId")
    @Mapping(source = "professor.id", target = "professorId")
    AlunoDTO alunoToAlunoDTO(Aluno aluno);

    List<AlunoDTO> alunosToAlunoDTOs(List<Aluno> alunos);

    @Mapping(source = "monografiaId", target = "monografia")
    @Mapping(target = "propostas", ignore = true)
    @Mapping(source = "professorId", target = "professor")
    Aluno alunoDTOToAluno(AlunoDTO alunoDTO);

    List<Aluno> alunoDTOsToAlunos(List<AlunoDTO> alunoDTOs);

    default Monografia monografiaFromId(Long id) {
        if (id == null) {
            return null;
        }
        Monografia monografia = new Monografia();
        monografia.setId(id);
        return monografia;
    }

    default Publicacao publicacaoFromId(Long id) {
        if (id == null) {
            return null;
        }
        Publicacao publicacao = new Publicacao();
        publicacao.setId(id);
        return publicacao;
    }

    default Professor professorFromId(Long id) {
        if (id == null) {
            return null;
        }
        Professor professor = new Professor();
        professor.setId(id);
        return professor;
    }
}
