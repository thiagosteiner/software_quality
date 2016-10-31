package br.ufrj.cos.qsoftware.service.impl;

import br.ufrj.cos.qsoftware.service.PropostaService;
import br.ufrj.cos.qsoftware.domain.Proposta;
import br.ufrj.cos.qsoftware.repository.PropostaRepository;
import br.ufrj.cos.qsoftware.service.dto.PropostaDTO;
import br.ufrj.cos.qsoftware.service.mapper.PropostaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Proposta.
 */
@Service
@Transactional
public class PropostaServiceImpl implements PropostaService{

    private final Logger log = LoggerFactory.getLogger(PropostaServiceImpl.class);
    
    @Inject
    private PropostaRepository propostaRepository;

    @Inject
    private PropostaMapper propostaMapper;

    /**
     * Save a proposta.
     *
     * @param propostaDTO the entity to save
     * @return the persisted entity
     */
    public PropostaDTO save(PropostaDTO propostaDTO) {
        log.debug("Request to save Proposta : {}", propostaDTO);
        Proposta proposta = propostaMapper.propostaDTOToProposta(propostaDTO);
        proposta = propostaRepository.save(proposta);
        PropostaDTO result = propostaMapper.propostaToPropostaDTO(proposta);
        return result;
    }

    /**
     *  Get all the propostas.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<PropostaDTO> findAll() {
        log.debug("Request to get all Propostas");
        List<PropostaDTO> result = propostaRepository.findAll().stream()
            .map(propostaMapper::propostaToPropostaDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one proposta by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PropostaDTO findOne(Long id) {
        log.debug("Request to get Proposta : {}", id);
        Proposta proposta = propostaRepository.findOne(id);
        PropostaDTO propostaDTO = propostaMapper.propostaToPropostaDTO(proposta);
        return propostaDTO;
    }

    /**
     *  Delete the  proposta by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Proposta : {}", id);
        propostaRepository.delete(id);
    }
}
