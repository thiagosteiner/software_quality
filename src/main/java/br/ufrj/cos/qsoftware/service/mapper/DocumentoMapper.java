package br.ufrj.cos.qsoftware.service.mapper;

import br.ufrj.cos.qsoftware.domain.*;
import br.ufrj.cos.qsoftware.service.dto.DocumentoDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Documento and its DTO DocumentoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DocumentoMapper {

    @Mapping(source = "aluno.id", target = "alunoId")
    @Mapping(source = "documentocomite.id", target = "documentocomiteId")
    @Mapping(source = "documentoorientador.id", target = "documentoorientadorId")
    DocumentoDTO documentoToDocumentoDTO(Documento documento);

    List<DocumentoDTO> documentosToDocumentoDTOs(List<Documento> documentos);

    @Mapping(source = "alunoId", target = "aluno")
    @Mapping(source = "documentocomiteId", target = "documentocomite")
    @Mapping(source = "documentoorientadorId", target = "documentoorientador")
    Documento documentoDTOToDocumento(DocumentoDTO documentoDTO);

    List<Documento> documentoDTOsToDocumentos(List<DocumentoDTO> documentoDTOs);

    default Aluno alunoFromId(Long id) {
        if (id == null) {
            return null;
        }
        Aluno aluno = new Aluno();
        aluno.setId(id);
        return aluno;
    }

    default Comite comiteFromId(Long id) {
        if (id == null) {
            return null;
        }
        Comite comite = new Comite();
        comite.setId(id);
        return comite;
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
