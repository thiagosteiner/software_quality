package br.ufrj.cos.qsoftware.service.impl;

import br.ufrj.cos.qsoftware.service.PublicacaoService;
import br.ufrj.cos.qsoftware.domain.Publicacao;
import br.ufrj.cos.qsoftware.repository.PublicacaoRepository;
import br.ufrj.cos.qsoftware.service.dto.PublicacaoDTO;
import br.ufrj.cos.qsoftware.service.mapper.PublicacaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Publicacao.
 */
@Service
@Transactional
public class PublicacaoServiceImpl implements PublicacaoService{

    private final Logger log = LoggerFactory.getLogger(PublicacaoServiceImpl.class);
    
    @Inject
    private PublicacaoRepository publicacaoRepository;

    @Inject
    private PublicacaoMapper publicacaoMapper;

    /**
     * Save a publicacao.
     *
     * @param publicacaoDTO the entity to save
     * @return the persisted entity
     */
    public PublicacaoDTO save(PublicacaoDTO publicacaoDTO) {
        log.debug("Request to save Publicacao : {}", publicacaoDTO);
        Publicacao publicacao = publicacaoMapper.publicacaoDTOToPublicacao(publicacaoDTO);
        publicacao = publicacaoRepository.save(publicacao);
        PublicacaoDTO result = publicacaoMapper.publicacaoToPublicacaoDTO(publicacao);
        return result;
    }

    /**
     *  Get all the publicacaos.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<PublicacaoDTO> findAll() {
        log.debug("Request to get all Publicacaos");
        List<PublicacaoDTO> result = publicacaoRepository.findAll().stream()
            .map(publicacaoMapper::publicacaoToPublicacaoDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one publicacao by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PublicacaoDTO findOne(Long id) {
        log.debug("Request to get Publicacao : {}", id);
        Publicacao publicacao = publicacaoRepository.findOne(id);
        PublicacaoDTO publicacaoDTO = publicacaoMapper.publicacaoToPublicacaoDTO(publicacao);
        return publicacaoDTO;
    }

    /**
     *  Delete the  publicacao by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Publicacao : {}", id);
        publicacaoRepository.delete(id);
    }
}
