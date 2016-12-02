package br.ufrj.cos.qsoftware.service.impl;

import br.ufrj.cos.qsoftware.service.ConviteComiteService;
import br.ufrj.cos.qsoftware.domain.ConviteComite;
import br.ufrj.cos.qsoftware.repository.ConviteComiteRepository;
import br.ufrj.cos.qsoftware.service.dto.ConviteComiteDTO;
import br.ufrj.cos.qsoftware.service.mapper.ConviteComiteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ConviteComite.
 */
@Service
@Transactional
public class ConviteComiteServiceImpl implements ConviteComiteService{

    private final Logger log = LoggerFactory.getLogger(ConviteComiteServiceImpl.class);
    
    @Inject
    private ConviteComiteRepository conviteComiteRepository;

    @Inject
    private ConviteComiteMapper conviteComiteMapper;

    /**
     * Save a conviteComite.
     *
     * @param conviteComiteDTO the entity to save
     * @return the persisted entity
     */
    public ConviteComiteDTO save(ConviteComiteDTO conviteComiteDTO) {
        log.debug("Request to save ConviteComite : {}", conviteComiteDTO);
        ConviteComite conviteComite = conviteComiteMapper.conviteComiteDTOToConviteComite(conviteComiteDTO);
        conviteComite = conviteComiteRepository.save(conviteComite);
        ConviteComiteDTO result = conviteComiteMapper.conviteComiteToConviteComiteDTO(conviteComite);
        return result;
    }

    /**
     *  Get all the conviteComites.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ConviteComiteDTO> findAll() {
        log.debug("Request to get all ConviteComites");
        List<ConviteComiteDTO> result = conviteComiteRepository.findAll().stream()
            .map(conviteComiteMapper::conviteComiteToConviteComiteDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one conviteComite by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ConviteComiteDTO findOne(Long id) {
        log.debug("Request to get ConviteComite : {}", id);
        ConviteComite conviteComite = conviteComiteRepository.findOne(id);
        ConviteComiteDTO conviteComiteDTO = conviteComiteMapper.conviteComiteToConviteComiteDTO(conviteComite);
        return conviteComiteDTO;
    }

    /**
     *  Delete the  conviteComite by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ConviteComite : {}", id);
        conviteComiteRepository.delete(id);
    }
}
