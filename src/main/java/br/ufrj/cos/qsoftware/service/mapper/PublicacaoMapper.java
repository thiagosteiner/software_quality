package br.ufrj.cos.qsoftware.service.mapper;

import br.ufrj.cos.qsoftware.domain.*;
import br.ufrj.cos.qsoftware.service.dto.PublicacaoDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Publicacao and its DTO PublicacaoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PublicacaoMapper {

    PublicacaoDTO publicacaoToPublicacaoDTO(Publicacao publicacao);

    List<PublicacaoDTO> publicacaosToPublicacaoDTOs(List<Publicacao> publicacaos);

    Publicacao publicacaoDTOToPublicacao(PublicacaoDTO publicacaoDTO);

    List<Publicacao> publicacaoDTOsToPublicacaos(List<PublicacaoDTO> publicacaoDTOs);
}
