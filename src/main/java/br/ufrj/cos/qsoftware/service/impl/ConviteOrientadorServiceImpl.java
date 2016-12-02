package br.ufrj.cos.qsoftware.service.impl;

import br.ufrj.cos.qsoftware.service.ConviteOrientadorService;
import br.ufrj.cos.qsoftware.domain.ConviteOrientador;
import br.ufrj.cos.qsoftware.repository.ConviteOrientadorRepository;
import br.ufrj.cos.qsoftware.service.dto.ConviteOrientadorDTO;
import br.ufrj.cos.qsoftware.service.mapper.ConviteOrientadorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ConviteOrientador.
 */
@Service
@Transactional
public class ConviteOrientadorServiceImpl implements ConviteOrientadorService{

    private final Logger log = LoggerFactory.getLogger(ConviteOrientadorServiceImpl.class);
    
    @Inject
    private ConviteOrientadorRepository conviteOrientadorRepository;

    @Inject
    private ConviteOrientadorMapper conviteOrientadorMapper;

    /**
     * Save a conviteOrientador.
     *
     * @param conviteOrientadorDTO the entity to save
     * @return the persisted entity
     */
    public ConviteOrientadorDTO save(ConviteOrientadorDTO conviteOrientadorDTO) {
        log.debug("Request to save ConviteOrientador : {}", conviteOrientadorDTO);
        ConviteOrientador conviteOrientador = conviteOrientadorMapper.conviteOrientadorDTOToConviteOrientador(conviteOrientadorDTO);
        conviteOrientador = conviteOrientadorRepository.save(conviteOrientador);
        ConviteOrientadorDTO result = conviteOrientadorMapper.conviteOrientadorToConviteOrientadorDTO(conviteOrientador);
        return result;
    }

    /**
     *  Get all the conviteOrientadors.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ConviteOrientadorDTO> findAll() {
        log.debug("Request to get all ConviteOrientadors");
        List<ConviteOrientadorDTO> result = conviteOrientadorRepository.findAll().stream()
            .map(conviteOrientadorMapper::conviteOrientadorToConviteOrientadorDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one conviteOrientador by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ConviteOrientadorDTO findOne(Long id) {
        log.debug("Request to get ConviteOrientador : {}", id);
        ConviteOrientador conviteOrientador = conviteOrientadorRepository.findOne(id);
        ConviteOrientadorDTO conviteOrientadorDTO = conviteOrientadorMapper.conviteOrientadorToConviteOrientadorDTO(conviteOrientador);
        return conviteOrientadorDTO;
    }

    /**
     *  Delete the  conviteOrientador by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ConviteOrientador : {}", id);
        conviteOrientadorRepository.delete(id);
    }
}
