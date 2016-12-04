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

    @Mapping(source = "orientador.id", target = "orientadorId")
    @Mapping(source = "orientador.nome", target = "orientadorNome")
    @Mapping(source = "aluno.id", target = "alunoId")
    @Mapping(source = "aluno.nome", target = "alunoNome")
    DocumentoDTO documentoToDocumentoDTO(Documento documento);

    List<DocumentoDTO> documentosToDocumentoDTOs(List<Documento> documentos);

    @Mapping(source = "orientadorId", target = "orientador")
    @Mapping(source = "alunoId", target = "aluno")
    @Mapping(target = "comite", ignore = true)
    Documento documentoDTOToDocumento(DocumentoDTO documentoDTO);

    List<Documento> documentoDTOsToDocumentos(List<DocumentoDTO> documentoDTOs);

    default Professor professorFromId(Long id) {
        if (id == null) {
            return null;
        }
        Professor professor = new Professor();
        professor.setId(id);
        return professor;
    }

    default Aluno alunoFromId(Long id) {
        if (id == null) {
            return null;
        }
        Aluno aluno = new Aluno();
        aluno.setId(id);
        return aluno;
    }
}
