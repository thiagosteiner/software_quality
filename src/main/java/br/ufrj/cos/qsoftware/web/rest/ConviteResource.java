package br.ufrj.cos.qsoftware.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.ufrj.cos.qsoftware.service.ConviteService;
import br.ufrj.cos.qsoftware.web.rest.util.HeaderUtil;
import br.ufrj.cos.qsoftware.service.dto.ConviteDTO;
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
 * REST controller for managing Convite.
 */
@RestController
@RequestMapping("/api")
public class ConviteResource {

    private final Logger log = LoggerFactory.getLogger(ConviteResource.class);
        
    @Inject
    private ConviteService conviteService;

    /**
     * POST  /convites : Create a new convite.
     *
     * @param conviteDTO the conviteDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new conviteDTO, or with status 400 (Bad Request) if the convite has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/convites")
    @Timed
    public ResponseEntity<ConviteDTO> createConvite(@RequestBody ConviteDTO conviteDTO) throws URISyntaxException {
        log.debug("REST request to save Convite : {}", conviteDTO);
        if (conviteDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("convite", "idexists", "A new convite cannot already have an ID")).body(null);
        }
        ConviteDTO result = conviteService.save(conviteDTO);
        return ResponseEntity.created(new URI("/api/convites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("convite", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /convites : Updates an existing convite.
     *
     * @param conviteDTO the conviteDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated conviteDTO,
     * or with status 400 (Bad Request) if the conviteDTO is not valid,
     * or with status 500 (Internal Server Error) if the conviteDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/convites")
    @Timed
    public ResponseEntity<ConviteDTO> updateConvite(@RequestBody ConviteDTO conviteDTO) throws URISyntaxException {
        log.debug("REST request to update Convite : {}", conviteDTO);
        if (conviteDTO.getId() == null) {
            return createConvite(conviteDTO);
        }
        ConviteDTO result = conviteService.save(conviteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("convite", conviteDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /convites : get all the convites.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of convites in body
     */
    @GetMapping("/convites")
    @Timed
    public List<ConviteDTO> getAllConvites() {
        log.debug("REST request to get all Convites");
        return conviteService.findAll();
    }

    /**
     * GET  /convites/:id : get the "id" convite.
     *
     * @param id the id of the conviteDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the conviteDTO, or with status 404 (Not Found)
     */
    @GetMapping("/convites/{id}")
    @Timed
    public ResponseEntity<ConviteDTO> getConvite(@PathVariable Long id) {
        log.debug("REST request to get Convite : {}", id);
        ConviteDTO conviteDTO = conviteService.findOne(id);
        return Optional.ofNullable(conviteDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /convites/:id : delete the "id" convite.
     *
     * @param id the id of the conviteDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/convites/{id}")
    @Timed
    public ResponseEntity<Void> deleteConvite(@PathVariable Long id) {
        log.debug("REST request to delete Convite : {}", id);
        conviteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("convite", id.toString())).build();
    }

}
