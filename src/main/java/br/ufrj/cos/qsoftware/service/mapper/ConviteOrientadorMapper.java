package br.ufrj.cos.qsoftware.service.mapper;

import br.ufrj.cos.qsoftware.domain.*;
import br.ufrj.cos.qsoftware.service.dto.ConviteOrientadorDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity ConviteOrientador and its DTO ConviteOrientadorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConviteOrientadorMapper {

    @Mapping(source = "orientadorconvidado.id", target = "orientadorconvidadoId")
    @Mapping(source = "orientadorconvidado.codigo", target = "orientadorconvidadoCodigo")
    @Mapping(source = "documento.id", target = "documentoId")
    @Mapping(source = "documento.titulo", target = "documentoTitulo")
    @Mapping(source = "alunoqueconvidou.id", target = "alunoqueconvidouId")
    @Mapping(source = "alunoqueconvidou.dre", target = "alunoqueconvidouDre")
    ConviteOrientadorDTO conviteOrientadorToConviteOrientadorDTO(ConviteOrientador conviteOrientador);

    List<ConviteOrientadorDTO> conviteOrientadorsToConviteOrientadorDTOs(List<ConviteOrientador> conviteOrientadors);

    @Mapping(source = "orientadorconvidadoId", target = "orientadorconvidado")
    @Mapping(source = "documentoId", target = "documento")
    @Mapping(source = "alunoqueconvidouId", target = "alunoqueconvidou")
    ConviteOrientador conviteOrientadorDTOToConviteOrientador(ConviteOrientadorDTO conviteOrientadorDTO);

    List<ConviteOrientador> conviteOrientadorDTOsToConviteOrientadors(List<ConviteOrientadorDTO> conviteOrientadorDTOs);

    default Professor professorFromId(Long id) {
        if (id == null) {
            return null;
        }
        Professor professor = new Professor();
        professor.setId(id);
        return professor;
    }

    default Documento documentoFromId(Long id) {
        if (id == null) {
            return null;
        }
        Documento documento = new Documento();
        documento.setId(id);
        return documento;
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
