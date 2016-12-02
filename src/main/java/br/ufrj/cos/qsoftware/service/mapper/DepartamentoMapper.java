package br.ufrj.cos.qsoftware.service.mapper;

import br.ufrj.cos.qsoftware.domain.*;
import br.ufrj.cos.qsoftware.service.dto.DepartamentoDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Departamento and its DTO DepartamentoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DepartamentoMapper {

    DepartamentoDTO departamentoToDepartamentoDTO(Departamento departamento);

    List<DepartamentoDTO> departamentosToDepartamentoDTOs(List<Departamento> departamentos);

    Departamento departamentoDTOToDepartamento(DepartamentoDTO departamentoDTO);

    List<Departamento> departamentoDTOsToDepartamentos(List<DepartamentoDTO> departamentoDTOs);
}
