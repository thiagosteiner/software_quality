package br.ufrj.cos.qsoftware.web.rest;

import com.codahale.metrics.annotation.Timed;

import br.ufrj.cos.qsoftware.service.ComiteService;
import br.ufrj.cos.qsoftware.web.rest.util.HeaderUtil;
import br.ufrj.cos.qsoftware.service.dto.ComiteDTO;

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

/**
 * REST controller for managing Comite.
 */
@RestController
@RequestMapping("/api")
public class ComiteResource {

    private final Logger log = LoggerFactory.getLogger(ComiteResource.class);
        
    @Inject
    private ComiteService comiteService;

    /**
     * POST  /comites : Create a new comite.
     *
     * @param comiteDTO the comiteDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new comiteDTO, or with status 400 (Bad Request) if the comite has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/comites")
    @Timed
    public ResponseEntity<ComiteDTO> createComite(@RequestBody ComiteDTO comiteDTO) throws URISyntaxException {
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
    	log.debug("Usuario: "+userName+" efetuou o caso de Uso - Solicitar formação de Comitê");
    	log.debug("=====================================================================");
    	
    	
        log.debug("REST request to save Comite : {}", comiteDTO);
        if (comiteDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("comite", "idexists", "A new comite cannot already have an ID")).body(null);
        }
        ComiteDTO result = comiteService.save(comiteDTO);
        return ResponseEntity.created(new URI("/api/comites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("comite", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /comites : Updates an existing comite.
     *
     * @param comiteDTO the comiteDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated comiteDTO,
     * or with status 400 (Bad Request) if the comiteDTO is not valid,
     * or with status 500 (Internal Server Error) if the comiteDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/comites")
    @Timed
    public ResponseEntity<ComiteDTO> updateComite(@RequestBody ComiteDTO comiteDTO) throws URISyntaxException {
        log.debug("REST request to update Comite : {}", comiteDTO);
        if (comiteDTO.getId() == null) {
            return createComite(comiteDTO);
        }
        ComiteDTO result = comiteService.save(comiteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("comite", comiteDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /comites : get all the comites.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of comites in body
     */
    @GetMapping("/comites")
    @Timed
    public List<ComiteDTO> getAllComites() {
        log.debug("REST request to get all Comites");
        return comiteService.findAll();
    }

    /**
     * GET  /comites/:id : get the "id" comite.
     *
     * @param id the id of the comiteDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the comiteDTO, or with status 404 (Not Found)
     */
    @GetMapping("/comites/{id}")
    @Timed
    public ResponseEntity<ComiteDTO> getComite(@PathVariable Long id) {
        log.debug("REST request to get Comite : {}", id);
        ComiteDTO comiteDTO = comiteService.findOne(id);
        return Optional.ofNullable(comiteDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /comites/:id : delete the "id" comite.
     *
     * @param id the id of the comiteDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/comites/{id}")
    @Timed
    public ResponseEntity<Void> deleteComite(@PathVariable Long id) {
        log.debug("REST request to delete Comite : {}", id);
        comiteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("comite", id.toString())).build();
    }

}
