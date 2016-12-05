package br.ufrj.cos.qsoftware.service.impl;

import br.ufrj.cos.qsoftware.service.DocumentoService;
import br.ufrj.cos.qsoftware.domain.Documento;
import br.ufrj.cos.qsoftware.repository.DocumentoRepository;
import br.ufrj.cos.qsoftware.service.dto.DocumentoDTO;
import br.ufrj.cos.qsoftware.service.mapper.DocumentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing Documento.
 */
@Service
@Transactional
public class DocumentoServiceImpl implements DocumentoService{

    private final Logger log = LoggerFactory.getLogger(DocumentoServiceImpl.class);

    @Inject
    private DocumentoRepository documentoRepository;

    @Inject
    private DocumentoMapper documentoMapper;

    /**
     * Save a documento.
     *
     * @param documentoDTO the entity to save
     * @return the persisted entity
     */
    public DocumentoDTO save(DocumentoDTO documentoDTO) {
        log.debug("Request to save Documento : {}", documentoDTO);
        Documento documento = documentoMapper.documentoDTOToDocumento(documentoDTO);
        documento = documentoRepository.save(documento);
        DocumentoDTO result = documentoMapper.documentoToDocumentoDTO(documento);
        return result;
    }

    /**
     *  Get all the documentos.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<DocumentoDTO> findAll() {
        log.debug("Request to get all Documentos");
        List<DocumentoDTO> result = documentoRepository.findAll().stream()
            .map(documentoMapper::documentoToDocumentoDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }


    /**
     *  get all the documentos where Comite is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<DocumentoDTO> findAllWhereComiteIsNull() {
        log.debug("Request to get all documentos where Comite is null");
        return StreamSupport
            .stream(documentoRepository.findAll().spliterator(), false)
            .filter(documento -> documento.getComite() == null)
            .map(documentoMapper::documentoToDocumentoDTO)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one documento by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public DocumentoDTO findOne(Long id) {
        log.debug("Request to get Documento : {}", id);
        Documento documento = documentoRepository.findOne(id);
        DocumentoDTO documentoDTO = documentoMapper.documentoToDocumentoDTO(documento);
        return documentoDTO;
    }

    /**
     *  Delete the  documento by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Documento : {}", id);
        documentoRepository.delete(id);
    }


    @Transactional(readOnly = true)
    public List<DocumentoDTO> findAllWhereAlunoIs(String userName) {
        log.debug("Request to get all documentos where AlunoEmail is "+userName);
        return StreamSupport
            .stream(documentoRepository.findAll().spliterator(), false)
            .filter(documento -> documento.getAluno().getUser().getLogin().equals(userName))
            .map(documentoMapper::documentoToDocumentoDTO)
            .collect(Collectors.toCollection(LinkedList::new));
    }






}
