package br.ufrj.cos.qsoftware.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.ufrj.cos.qsoftware.service.DepartamentoService;
import br.ufrj.cos.qsoftware.web.rest.util.HeaderUtil;
import br.ufrj.cos.qsoftware.service.dto.DepartamentoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Departamento.
 */
@RestController
@RequestMapping("/api")
public class DepartamentoResource {

    private final Logger log = LoggerFactory.getLogger(DepartamentoResource.class);
        
    @Inject
    private DepartamentoService departamentoService;

    /**
     * POST  /departamentos : Create a new departamento.
     *
     * @param departamentoDTO the departamentoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new departamentoDTO, or with status 400 (Bad Request) if the departamento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/departamentos")
    @Timed
    public ResponseEntity<DepartamentoDTO> createDepartamento(@Valid @RequestBody DepartamentoDTO departamentoDTO) throws URISyntaxException {
        log.debug("REST request to save Departamento : {}", departamentoDTO);
        if (departamentoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("departamento", "idexists", "A new departamento cannot already have an ID")).body(null);
        }
        DepartamentoDTO result = departamentoService.save(departamentoDTO);
        return ResponseEntity.created(new URI("/api/departamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("departamento", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /departamentos : Updates an existing departamento.
     *
     * @param departamentoDTO the departamentoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated departamentoDTO,
     * or with status 400 (Bad Request) if the departamentoDTO is not valid,
     * or with status 500 (Internal Server Error) if the departamentoDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/departamentos")
    @Timed
    public ResponseEntity<DepartamentoDTO> updateDepartamento(@Valid @RequestBody DepartamentoDTO departamentoDTO) throws URISyntaxException {
        log.debug("REST request to update Departamento : {}", departamentoDTO);
        if (departamentoDTO.getId() == null) {
            return createDepartamento(departamentoDTO);
        }
        DepartamentoDTO result = departamentoService.save(departamentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("departamento", departamentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /departamentos : get all the departamentos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of departamentos in body
     */
    @GetMapping("/departamentos")
    @Timed
    public List<DepartamentoDTO> getAllDepartamentos() {
        log.debug("REST request to get all Departamentos");
        return departamentoService.findAll();
    }

    /**
     * GET  /departamentos/:id : get the "id" departamento.
     *
     * @param id the id of the departamentoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the departamentoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/departamentos/{id}")
    @Timed
    public ResponseEntity<DepartamentoDTO> getDepartamento(@PathVariable Long id) {
        log.debug("REST request to get Departamento : {}", id);
        DepartamentoDTO departamentoDTO = departamentoService.findOne(id);
        return Optional.ofNullable(departamentoDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /departamentos/:id : delete the "id" departamento.
     *
     * @param id the id of the departamentoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/departamentos/{id}")
    @Timed
    public ResponseEntity<Void> deleteDepartamento(@PathVariable Long id) {
        log.debug("REST request to delete Departamento : {}", id);
        departamentoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("departamento", id.toString())).build();
    }

}
