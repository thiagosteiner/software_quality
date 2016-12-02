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

    @Mapping(source = "comite.id", target = "comiteId")
    DocumentoDTO documentoToDocumentoDTO(Documento documento);

    List<DocumentoDTO> documentosToDocumentoDTOs(List<Documento> documentos);

    @Mapping(source = "comiteId", target = "comite")
    @Mapping(target = "alunos", ignore = true)
    Documento documentoDTOToDocumento(DocumentoDTO documentoDTO);

    List<Documento> documentoDTOsToDocumentos(List<DocumentoDTO> documentoDTOs);

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
