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

    @Mapping(source = "tese.id", target = "teseId")
    @Mapping(source = "professor.id", target = "professorId")
    AlunoDTO alunoToAlunoDTO(Aluno aluno);

    List<AlunoDTO> alunosToAlunoDTOs(List<Aluno> alunos);

    @Mapping(source = "teseId", target = "tese")
    @Mapping(target = "propostas", ignore = true)
    @Mapping(source = "professorId", target = "professor")
    Aluno alunoDTOToAluno(AlunoDTO alunoDTO);

    List<Aluno> alunoDTOsToAlunos(List<AlunoDTO> alunoDTOs);

    default Tese teseFromId(Long id) {
        if (id == null) {
            return null;
        }
        Tese tese = new Tese();
        tese.setId(id);
        return tese;
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
