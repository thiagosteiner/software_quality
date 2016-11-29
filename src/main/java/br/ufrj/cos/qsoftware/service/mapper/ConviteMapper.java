package br.ufrj.cos.qsoftware.service.mapper;

import br.ufrj.cos.qsoftware.domain.*;
import br.ufrj.cos.qsoftware.service.dto.ConviteDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Convite and its DTO ConviteDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConviteMapper {

    @Mapping(source = "aluno.id", target = "alunoId")
    @Mapping(source = "professor.id", target = "professorId")
    @Mapping(source = "conviteprofessor.id", target = "conviteprofessorId")
    @Mapping(source = "conviteComite.id", target = "conviteComiteId")
    @Mapping(source = "conviteDocumento.id", target = "conviteDocumentoId")
    ConviteDTO conviteToConviteDTO(Convite convite);

    List<ConviteDTO> convitesToConviteDTOs(List<Convite> convites);

    @Mapping(source = "alunoId", target = "aluno")
    @Mapping(source = "professorId", target = "professor")
    @Mapping(source = "conviteprofessorId", target = "conviteprofessor")
    @Mapping(source = "conviteComiteId", target = "conviteComite")
    @Mapping(source = "conviteDocumentoId", target = "conviteDocumento")
    Convite conviteDTOToConvite(ConviteDTO conviteDTO);

    List<Convite> conviteDTOsToConvites(List<ConviteDTO> conviteDTOs);

    default Aluno alunoFromId(Long id) {
        if (id == null) {
            return null;
        }
        Aluno aluno = new Aluno();
        aluno.setId(id);
        return aluno;
    }

    default Professor professorFromId(Long id) {
        if (id == null) {
            return null;
        }
        Professor professor = new Professor();
        professor.setId(id);
        return professor;
    }

    default Comite comiteFromId(Long id) {
        if (id == null) {
            return null;
        }
        Comite comite = new Comite();
        comite.setId(id);
        return comite;
    }

    default Documento documentoFromId(Long id) {
        if (id == null) {
            return null;
        }
        Documento documento = new Documento();
        documento.setId(id);
        return documento;
    }
}
