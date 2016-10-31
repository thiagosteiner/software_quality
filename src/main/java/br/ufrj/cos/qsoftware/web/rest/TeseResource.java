package br.ufrj.cos.qsoftware.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.ufrj.cos.qsoftware.service.TeseService;
import br.ufrj.cos.qsoftware.web.rest.util.HeaderUtil;
import br.ufrj.cos.qsoftware.service.dto.TeseDTO;
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
 * REST controller for managing Tese.
 */
@RestController
@RequestMapping("/api")
public class TeseResource {

    private final Logger log = LoggerFactory.getLogger(TeseResource.class);
        
    @Inject
    private TeseService teseService;

    /**
     * POST  /tese : Create a new tese.
     *
     * @param teseDTO the teseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new teseDTO, or with status 400 (Bad Request) if the tese has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tese")
    @Timed
    public ResponseEntity<TeseDTO> createTese(@RequestBody TeseDTO teseDTO) throws URISyntaxException {
        log.debug("REST request to save Tese : {}", teseDTO);
        if (teseDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tese", "idexists", "A new tese cannot already have an ID")).body(null);
        }
        TeseDTO result = teseService.save(teseDTO);
        return ResponseEntity.created(new URI("/api/tese/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tese", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tese : Updates an existing tese.
     *
     * @param teseDTO the teseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated teseDTO,
     * or with status 400 (Bad Request) if the teseDTO is not valid,
     * or with status 500 (Internal Server Error) if the teseDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tese")
    @Timed
    public ResponseEntity<TeseDTO> updateTese(@RequestBody TeseDTO teseDTO) throws URISyntaxException {
        log.debug("REST request to update Tese : {}", teseDTO);
        if (teseDTO.getId() == null) {
            return createTese(teseDTO);
        }
        TeseDTO result = teseService.save(teseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tese", teseDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tese : get all the tese.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tese in body
     */
    @GetMapping("/tese")
    @Timed
    public List<TeseDTO> getAllTese() {
        log.debug("REST request to get all Tese");
        return teseService.findAll();
    }

    /**
     * GET  /tese/:id : get the "id" tese.
     *
     * @param id the id of the teseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the teseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tese/{id}")
    @Timed
    public ResponseEntity<TeseDTO> getTese(@PathVariable Long id) {
        log.debug("REST request to get Tese : {}", id);
        TeseDTO teseDTO = teseService.findOne(id);
        return Optional.ofNullable(teseDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tese/:id : delete the "id" tese.
     *
     * @param id the id of the teseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tese/{id}")
    @Timed
    public ResponseEntity<Void> deleteTese(@PathVariable Long id) {
        log.debug("REST request to delete Tese : {}", id);
        teseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tese", id.toString())).build();
    }

}
