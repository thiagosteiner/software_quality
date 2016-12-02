package br.ufrj.cos.qsoftware.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.ufrj.cos.qsoftware.service.ConviteComiteService;
import br.ufrj.cos.qsoftware.web.rest.util.HeaderUtil;
import br.ufrj.cos.qsoftware.service.dto.ConviteComiteDTO;
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
 * REST controller for managing ConviteComite.
 */
@RestController
@RequestMapping("/api")
public class ConviteComiteResource {

    private final Logger log = LoggerFactory.getLogger(ConviteComiteResource.class);
        
    @Inject
    private ConviteComiteService conviteComiteService;

    /**
     * POST  /convite-comites : Create a new conviteComite.
     *
     * @param conviteComiteDTO the conviteComiteDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new conviteComiteDTO, or with status 400 (Bad Request) if the conviteComite has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/convite-comites")
    @Timed
    public ResponseEntity<ConviteComiteDTO> createConviteComite(@RequestBody ConviteComiteDTO conviteComiteDTO) throws URISyntaxException {
        log.debug("REST request to save ConviteComite : {}", conviteComiteDTO);
        if (conviteComiteDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("conviteComite", "idexists", "A new conviteComite cannot already have an ID")).body(null);
        }
        ConviteComiteDTO result = conviteComiteService.save(conviteComiteDTO);
        return ResponseEntity.created(new URI("/api/convite-comites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("conviteComite", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /convite-comites : Updates an existing conviteComite.
     *
     * @param conviteComiteDTO the conviteComiteDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated conviteComiteDTO,
     * or with status 400 (Bad Request) if the conviteComiteDTO is not valid,
     * or with status 500 (Internal Server Error) if the conviteComiteDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/convite-comites")
    @Timed
    public ResponseEntity<ConviteComiteDTO> updateConviteComite(@RequestBody ConviteComiteDTO conviteComiteDTO) throws URISyntaxException {
        log.debug("REST request to update ConviteComite : {}", conviteComiteDTO);
        if (conviteComiteDTO.getId() == null) {
            return createConviteComite(conviteComiteDTO);
        }
        ConviteComiteDTO result = conviteComiteService.save(conviteComiteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("conviteComite", conviteComiteDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /convite-comites : get all the conviteComites.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of conviteComites in body
     */
    @GetMapping("/convite-comites")
    @Timed
    public List<ConviteComiteDTO> getAllConviteComites() {
        log.debug("REST request to get all ConviteComites");
        return conviteComiteService.findAll();
    }

    /**
     * GET  /convite-comites/:id : get the "id" conviteComite.
     *
     * @param id the id of the conviteComiteDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the conviteComiteDTO, or with status 404 (Not Found)
     */
    @GetMapping("/convite-comites/{id}")
    @Timed
    public ResponseEntity<ConviteComiteDTO> getConviteComite(@PathVariable Long id) {
        log.debug("REST request to get ConviteComite : {}", id);
        ConviteComiteDTO conviteComiteDTO = conviteComiteService.findOne(id);
        return Optional.ofNullable(conviteComiteDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /convite-comites/:id : delete the "id" conviteComite.
     *
     * @param id the id of the conviteComiteDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/convite-comites/{id}")
    @Timed
    public ResponseEntity<Void> deleteConviteComite(@PathVariable Long id) {
        log.debug("REST request to delete ConviteComite : {}", id);
        conviteComiteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("conviteComite", id.toString())).build();
    }

}
