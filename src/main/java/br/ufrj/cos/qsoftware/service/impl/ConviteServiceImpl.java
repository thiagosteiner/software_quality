package br.ufrj.cos.qsoftware.service.impl;

import br.ufrj.cos.qsoftware.service.ConviteService;
import br.ufrj.cos.qsoftware.domain.Convite;
import br.ufrj.cos.qsoftware.repository.ConviteRepository;
import br.ufrj.cos.qsoftware.service.dto.ConviteDTO;
import br.ufrj.cos.qsoftware.service.mapper.ConviteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Convite.
 */
@Service
@Transactional
public class ConviteServiceImpl implements ConviteService{

    private final Logger log = LoggerFactory.getLogger(ConviteServiceImpl.class);
    
    @Inject
    private ConviteRepository conviteRepository;

    @Inject
    private ConviteMapper conviteMapper;

    /**
     * Save a convite.
     *
     * @param conviteDTO the entity to save
     * @return the persisted entity
     */
    public ConviteDTO save(ConviteDTO conviteDTO) {
        log.debug("Request to save Convite : {}", conviteDTO);
        Convite convite = conviteMapper.conviteDTOToConvite(conviteDTO);
        convite = conviteRepository.save(convite);
        ConviteDTO result = conviteMapper.conviteToConviteDTO(convite);
        return result;
    }

    /**
     *  Get all the convites.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ConviteDTO> findAll() {
        log.debug("Request to get all Convites");
        List<ConviteDTO> result = conviteRepository.findAll().stream()
            .map(conviteMapper::conviteToConviteDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one convite by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ConviteDTO findOne(Long id) {
        log.debug("Request to get Convite : {}", id);
        Convite convite = conviteRepository.findOne(id);
        ConviteDTO conviteDTO = conviteMapper.conviteToConviteDTO(convite);
        return conviteDTO;
    }

    /**
     *  Delete the  convite by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Convite : {}", id);
        conviteRepository.delete(id);
    }
}
