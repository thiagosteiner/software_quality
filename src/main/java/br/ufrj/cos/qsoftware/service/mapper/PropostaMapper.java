package br.ufrj.cos.qsoftware.service.mapper;

import br.ufrj.cos.qsoftware.domain.*;
import br.ufrj.cos.qsoftware.service.dto.PropostaDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Proposta and its DTO PropostaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PropostaMapper {

    @Mapping(source = "aluno.id", target = "alunoId")
    @Mapping(source = "comite.id", target = "comiteId")
    PropostaDTO propostaToPropostaDTO(Proposta proposta);

    List<PropostaDTO> propostasToPropostaDTOs(List<Proposta> propostas);

    @Mapping(source = "alunoId", target = "aluno")
    @Mapping(source = "comiteId", target = "comite")
    Proposta propostaDTOToProposta(PropostaDTO propostaDTO);

    List<Proposta> propostaDTOsToPropostas(List<PropostaDTO> propostaDTOs);

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
}
