package br.ufrj.cos.qsoftware.service.mapper;

import br.ufrj.cos.qsoftware.domain.*;
import br.ufrj.cos.qsoftware.service.dto.ConviteComiteDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity ConviteComite and its DTO ConviteComiteDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConviteComiteMapper {

    @Mapping(source = "professorconvidadocomite.id", target = "professorconvidadocomiteId")
    @Mapping(source = "professorconvidadocomite.codigo", target = "professorconvidadocomiteCodigo")
    @Mapping(source = "comite.id", target = "comiteId")
    @Mapping(source = "documento.id", target = "documentoId")
    @Mapping(source = "documento.titulo", target = "documentoTitulo")
    @Mapping(source = "orientadorqueconvidou.id", target = "orientadorqueconvidouId")
    @Mapping(source = "orientadorqueconvidou.codigo", target = "orientadorqueconvidouCodigo")
    ConviteComiteDTO conviteComiteToConviteComiteDTO(ConviteComite conviteComite);

    List<ConviteComiteDTO> conviteComitesToConviteComiteDTOs(List<ConviteComite> conviteComites);

    @Mapping(source = "professorconvidadocomiteId", target = "professorconvidadocomite")
    @Mapping(source = "comiteId", target = "comite")
    @Mapping(source = "documentoId", target = "documento")
    @Mapping(source = "orientadorqueconvidouId", target = "orientadorqueconvidou")
    ConviteComite conviteComiteDTOToConviteComite(ConviteComiteDTO conviteComiteDTO);

    List<ConviteComite> conviteComiteDTOsToConviteComites(List<ConviteComiteDTO> conviteComiteDTOs);

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
