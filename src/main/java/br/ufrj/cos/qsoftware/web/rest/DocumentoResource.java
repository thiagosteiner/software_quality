package br.ufrj.cos.qsoftware.web.rest;

import com.codahale.metrics.annotation.Timed;

import br.ufrj.cos.qsoftware.domain.enumeration.SituacaoAprovacao;
import br.ufrj.cos.qsoftware.service.DocumentoService;
import br.ufrj.cos.qsoftware.web.rest.util.HeaderUtil;
import br.ufrj.cos.qsoftware.service.dto.DocumentoDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing Documento.
 */
@RestController
@RequestMapping("/api")
public class DocumentoResource {

    private final Logger log = LoggerFactory.getLogger(DocumentoResource.class);
        
    @Inject
    private DocumentoService documentoService;

    /**
     * POST  /documentos : Create a new documento.
     *
     * @param documentoDTO the documentoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new documentoDTO, or with status 400 (Bad Request) if the documento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/documentos")
    @Timed
    public ResponseEntity<DocumentoDTO> createDocumento(@RequestBody DocumentoDTO documentoDTO) throws URISyntaxException {
    	SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String userName = null;
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                userName = springSecurityUser.getUsername();
            } else if (authentication.getPrincipal() instanceof String) {
                userName = (String) authentication.getPrincipal();
            }
        }
    	     	
    	log.debug("=====================================================================");
    	log.debug("Usuario: "+userName+" efetuou o caso de Uso - Enviar Documento");
    	log.debug("=====================================================================");
    	
    	
    	
        log.debug("REST request to save Documento : {}", documentoDTO);
        if (documentoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("documento", "idexists", "A new documento cannot already have an ID")).body(null);
        }
        DocumentoDTO result = documentoService.save(documentoDTO);
        return ResponseEntity.created(new URI("/api/documentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("documento", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /documentos : Updates an existing documento.
     *
     * @param documentoDTO the documentoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated documentoDTO,
     * or with status 400 (Bad Request) if the documentoDTO is not valid,
     * or with status 500 (Internal Server Error) if the documentoDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/documentos")
    @Timed
    public ResponseEntity<DocumentoDTO> updateDocumento(@RequestBody DocumentoDTO documentoDTO) throws URISyntaxException {
    	
        log.debug("REST request to update Documento : {}", documentoDTO);
        if (documentoDTO.getId() == null) {
            return createDocumento(documentoDTO);
        }
        
        if(documentoDTO.getStatus()==SituacaoAprovacao.APROVADO||documentoDTO.getStatus()==SituacaoAprovacao.REJEITADO){        	
        	SecurityContext securityContext = SecurityContextHolder.getContext();
            Authentication authentication = securityContext.getAuthentication();
            String userName = null;
            if (authentication != null) {
                if (authentication.getPrincipal() instanceof UserDetails) {
                    UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                    userName = springSecurityUser.getUsername();
                } else if (authentication.getPrincipal() instanceof String) {
                    userName = (String) authentication.getPrincipal();
                }
            }
        	     	
        	log.debug("=====================================================================");
        	log.debug("Usuario: "+userName+" efetuou o caso de Uso - Aprovar Documento");
        	log.debug("=====================================================================");
        }
        
        
        DocumentoDTO result = documentoService.save(documentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("documento", documentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /documentos : get all the documentos.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of documentos in body
     */
    @GetMapping("/documentos")
    @Timed
    public List<DocumentoDTO> getAllDocumentos(@RequestParam(required = false) String filter) {
    	SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String userName = null;
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                userName = springSecurityUser.getUsername();
            } else if (authentication.getPrincipal() instanceof String) {
                userName = (String) authentication.getPrincipal();
            }
        }
        if(userName.contains("aluno")){
        	return documentoService.findAllWhereAlunoIs(userName);
        }
      
    	
    	
        if ("comite-is-null".equals(filter)) {
            log.debug("REST request to get all Documentos where comite is null");
            return documentoService.findAllWhereComiteIsNull();
        }
        log.debug("REST request to get all Documentos");
        return documentoService.findAll();
    }

    /**
     * GET  /documentos/:id : get the "id" documento.
     *
     * @param id the id of the documentoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the documentoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/documentos/{id}")
    @Timed
    public ResponseEntity<DocumentoDTO> getDocumento(@PathVariable Long id) {
        log.debug("REST request to get Documento : {}", id);
        DocumentoDTO documentoDTO = documentoService.findOne(id);
        return Optional.ofNullable(documentoDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /documentos/:id : delete the "id" documento.
     *
     * @param id the id of the documentoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/documentos/{id}")
    @Timed
    public ResponseEntity<Void> deleteDocumento(@PathVariable Long id) {
        log.debug("REST request to delete Documento : {}", id);
        documentoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("documento", id.toString())).build();
    }
    
    @GetMapping("/showdocuments")
    @Timed
    public List<DocumentoDTO> getAllDocuments(@RequestParam(required = false) String filter) {
    	 SecurityContext securityContext = SecurityContextHolder.getContext();
         Authentication authentication = securityContext.getAuthentication();
         String userName = null;
         if (authentication != null) {
             if (authentication.getPrincipal() instanceof UserDetails) {
                 UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                 userName = springSecurityUser.getUsername();
             } else if (authentication.getPrincipal() instanceof String) {
                 userName = (String) authentication.getPrincipal();
             }
         }
    	
        log.debug("=====================================================================");
    	log.debug("Usuario: "+userName+" efetuou o caso de Uso - Consultar Documentos");
    	log.debug("=====================================================================");
        return documentoService.findAll();
    }
    
    @GetMapping("/showdocuments/{id}")
    @Timed
    public ResponseEntity<DocumentoDTO> getDocument(@PathVariable Long id) {
        log.debug("REST request to get Documento : {}", id);
        DocumentoDTO documentoDTO = documentoService.findOne(id);
        return Optional.ofNullable(documentoDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }	

}
