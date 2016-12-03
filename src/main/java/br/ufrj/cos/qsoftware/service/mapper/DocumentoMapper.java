package br.ufrj.cos.qsoftware.service.mapper;

import br.ufrj.cos.qsoftware.domain.*;
import br.ufrj.cos.qsoftware.service.dto.DocumentoDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Documento and its DTO DocumentoDTO.
 */
@Mapper(componentModel = "spring", uses = {ProfessorMapper.class, })
public interface DocumentoMapper {

    DocumentoDTO documentoToDocumentoDTO(Documento documento);

    List<DocumentoDTO> documentosToDocumentoDTOs(List<Documento> documentos);

    @Mapping(target = "comite", ignore = true)
    @Mapping(target = "alunos", ignore = true)
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
}
