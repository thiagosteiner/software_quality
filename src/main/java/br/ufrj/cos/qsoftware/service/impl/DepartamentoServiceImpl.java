package br.ufrj.cos.qsoftware.service.impl;

import br.ufrj.cos.qsoftware.service.DepartamentoService;
import br.ufrj.cos.qsoftware.domain.Departamento;
import br.ufrj.cos.qsoftware.repository.DepartamentoRepository;
import br.ufrj.cos.qsoftware.service.dto.DepartamentoDTO;
import br.ufrj.cos.qsoftware.service.mapper.DepartamentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Departamento.
 */
@Service
@Transactional
public class DepartamentoServiceImpl implements DepartamentoService{

    private final Logger log = LoggerFactory.getLogger(DepartamentoServiceImpl.class);
    
    @Inject
    private DepartamentoRepository departamentoRepository;

    @Inject
    private DepartamentoMapper departamentoMapper;

    /**
     * Save a departamento.
     *
     * @param departamentoDTO the entity to save
     * @return the persisted entity
     */
    public DepartamentoDTO save(DepartamentoDTO departamentoDTO) {
        log.debug("Request to save Departamento : {}", departamentoDTO);
        Departamento departamento = departamentoMapper.departamentoDTOToDepartamento(departamentoDTO);
        departamento = departamentoRepository.save(departamento);
        DepartamentoDTO result = departamentoMapper.departamentoToDepartamentoDTO(departamento);
        return result;
    }

    /**
     *  Get all the departamentos.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<DepartamentoDTO> findAll() {
        log.debug("Request to get all Departamentos");
        List<DepartamentoDTO> result = departamentoRepository.findAll().stream()
            .map(departamentoMapper::departamentoToDepartamentoDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one departamento by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public DepartamentoDTO findOne(Long id) {
        log.debug("Request to get Departamento : {}", id);
        Departamento departamento = departamentoRepository.findOne(id);
        DepartamentoDTO departamentoDTO = departamentoMapper.departamentoToDepartamentoDTO(departamento);
        return departamentoDTO;
    }

    /**
     *  Delete the  departamento by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Departamento : {}", id);
        departamentoRepository.delete(id);
    }
}
