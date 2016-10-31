package br.ufrj.cos.qsoftware.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.ufrj.cos.qsoftware.service.MonografiaService;
import br.ufrj.cos.qsoftware.web.rest.util.HeaderUtil;
import br.ufrj.cos.qsoftware.service.dto.MonografiaDTO;
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
 * REST controller for managing Monografia.
 */
@RestController
@RequestMapping("/api")
public class MonografiaResource {

    private final Logger log = LoggerFactory.getLogger(MonografiaResource.class);
        
    @Inject
    private MonografiaService monografiaService;

    /**
     * POST  /monografias : Create a new monografia.
     *
     * @param monografiaDTO the monografiaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new monografiaDTO, or with status 400 (Bad Request) if the monografia has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/monografias")
    @Timed
    public ResponseEntity<MonografiaDTO> createMonografia(@RequestBody MonografiaDTO monografiaDTO) throws URISyntaxException {
        log.debug("REST request to save Monografia : {}", monografiaDTO);
        if (monografiaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("monografia", "idexists", "A new monografia cannot already have an ID")).body(null);
        }
        MonografiaDTO result = monografiaService.save(monografiaDTO);
        return ResponseEntity.created(new URI("/api/monografias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("monografia", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /monografias : Updates an existing monografia.
     *
     * @param monografiaDTO the monografiaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated monografiaDTO,
     * or with status 400 (Bad Request) if the monografiaDTO is not valid,
     * or with status 500 (Internal Server Error) if the monografiaDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/monografias")
    @Timed
    public ResponseEntity<MonografiaDTO> updateMonografia(@RequestBody MonografiaDTO monografiaDTO) throws URISyntaxException {
        log.debug("REST request to update Monografia : {}", monografiaDTO);
        if (monografiaDTO.getId() == null) {
            return createMonografia(monografiaDTO);
        }
        MonografiaDTO result = monografiaService.save(monografiaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("monografia", monografiaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /monografias : get all the monografias.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of monografias in body
     */
    @GetMapping("/monografias")
    @Timed
    public List<MonografiaDTO> getAllMonografias() {
        log.debug("REST request to get all Monografias");
        return monografiaService.findAll();
    }

    /**
     * GET  /monografias/:id : get the "id" monografia.
     *
     * @param id the id of the monografiaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the monografiaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/monografias/{id}")
    @Timed
    public ResponseEntity<MonografiaDTO> getMonografia(@PathVariable Long id) {
        log.debug("REST request to get Monografia : {}", id);
        MonografiaDTO monografiaDTO = monografiaService.findOne(id);
        return Optional.ofNullable(monografiaDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /monografias/:id : delete the "id" monografia.
     *
     * @param id the id of the monografiaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/monografias/{id}")
    @Timed
    public ResponseEntity<Void> deleteMonografia(@PathVariable Long id) {
        log.debug("REST request to delete Monografia : {}", id);
        monografiaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("monografia", id.toString())).build();
    }

}
