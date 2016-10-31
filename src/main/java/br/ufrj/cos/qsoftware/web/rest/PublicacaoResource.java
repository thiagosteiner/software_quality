package br.ufrj.cos.qsoftware.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.ufrj.cos.qsoftware.service.PublicacaoService;
import br.ufrj.cos.qsoftware.web.rest.util.HeaderUtil;
import br.ufrj.cos.qsoftware.service.dto.PublicacaoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Publicacao.
 */
@RestController
@RequestMapping("/api")
public class PublicacaoResource {

    private final Logger log = LoggerFactory.getLogger(PublicacaoResource.class);
        
    @Inject
    private PublicacaoService publicacaoService;

    /**
     * POST  /publicacaos : Create a new publicacao.
     *
     * @param publicacaoDTO the publicacaoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new publicacaoDTO, or with status 400 (Bad Request) if the publicacao has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/publicacaos")
    @Timed
    public ResponseEntity<PublicacaoDTO> createPublicacao(@RequestBody PublicacaoDTO publicacaoDTO) throws URISyntaxException {
        log.debug("REST request to save Publicacao : {}", publicacaoDTO);
        if (publicacaoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("publicacao", "idexists", "A new publicacao cannot already have an ID")).body(null);
        }
        PublicacaoDTO result = publicacaoService.save(publicacaoDTO);
        return ResponseEntity.created(new URI("/api/publicacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("publicacao", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /publicacaos : Updates an existing publicacao.
     *
     * @param publicacaoDTO the publicacaoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated publicacaoDTO,
     * or with status 400 (Bad Request) if the publicacaoDTO is not valid,
     * or with status 500 (Internal Server Error) if the publicacaoDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/publicacaos")
    @Timed
    public ResponseEntity<PublicacaoDTO> updatePublicacao(@RequestBody PublicacaoDTO publicacaoDTO) throws URISyntaxException {
        log.debug("REST request to update Publicacao : {}", publicacaoDTO);
        if (publicacaoDTO.getId() == null) {
            return createPublicacao(publicacaoDTO);
        }
        PublicacaoDTO result = publicacaoService.save(publicacaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("publicacao", publicacaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /publicacaos : get all the publicacaos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of publicacaos in body
     */
    @GetMapping("/publicacaos")
    @Timed
    public List<PublicacaoDTO> getAllPublicacaos() {
        log.debug("REST request to get all Publicacaos");
        return publicacaoService.findAll();
    }

    /**
     * GET  /publicacaos/:id : get the "id" publicacao.
     *
     * @param id the id of the publicacaoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the publicacaoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/publicacaos/{id}")
    @Timed
    public ResponseEntity<PublicacaoDTO> getPublicacao(@PathVariable Long id) {
        log.debug("REST request to get Publicacao : {}", id);
        PublicacaoDTO publicacaoDTO = publicacaoService.findOne(id);
        return Optional.ofNullable(publicacaoDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /publicacaos/:id : delete the "id" publicacao.
     *
     * @param id the id of the publicacaoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/publicacaos/{id}")
    @Timed
    public ResponseEntity<Void> deletePublicacao(@PathVariable Long id) {
        log.debug("REST request to delete Publicacao : {}", id);
        publicacaoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("publicacao", id.toString())).build();
    }

}
