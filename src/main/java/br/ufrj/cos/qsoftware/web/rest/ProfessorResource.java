package br.ufrj.cos.qsoftware.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.ufrj.cos.qsoftware.service.ProfessorService;
import br.ufrj.cos.qsoftware.web.rest.util.HeaderUtil;
import br.ufrj.cos.qsoftware.service.dto.ProfessorDTO;
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
 * REST controller for managing Professor.
 */
@RestController
@RequestMapping("/api")
public class ProfessorResource {

    private final Logger log = LoggerFactory.getLogger(ProfessorResource.class);
        
    @Inject
    private ProfessorService professorService;

    /**
     * POST  /professors : Create a new professor.
     *
     * @param professorDTO the professorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new professorDTO, or with status 400 (Bad Request) if the professor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/professors")
    @Timed
    public ResponseEntity<ProfessorDTO> createProfessor(@Valid @RequestBody ProfessorDTO professorDTO) throws URISyntaxException {
        log.debug("REST request to save Professor : {}", professorDTO);
        if (professorDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("professor", "idexists", "A new professor cannot already have an ID")).body(null);
        }
        ProfessorDTO result = professorService.save(professorDTO);
        return ResponseEntity.created(new URI("/api/professors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("professor", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /professors : Updates an existing professor.
     *
     * @param professorDTO the professorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated professorDTO,
     * or with status 400 (Bad Request) if the professorDTO is not valid,
     * or with status 500 (Internal Server Error) if the professorDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/professors")
    @Timed
    public ResponseEntity<ProfessorDTO> updateProfessor(@Valid @RequestBody ProfessorDTO professorDTO) throws URISyntaxException {
        log.debug("REST request to update Professor : {}", professorDTO);
        if (professorDTO.getId() == null) {
            return createProfessor(professorDTO);
        }
        ProfessorDTO result = professorService.save(professorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("professor", professorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /professors : get all the professors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of professors in body
     */
    @GetMapping("/professors")
    @Timed
    public List<ProfessorDTO> getAllProfessors() {
        log.debug("REST request to get all Professors");
        return professorService.findAll();
    }

    /**
     * GET  /professors/:id : get the "id" professor.
     *
     * @param id the id of the professorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the professorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/professors/{id}")
    @Timed
    public ResponseEntity<ProfessorDTO> getProfessor(@PathVariable Long id) {
        log.debug("REST request to get Professor : {}", id);
        ProfessorDTO professorDTO = professorService.findOne(id);
        return Optional.ofNullable(professorDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /professors/:id : delete the "id" professor.
     *
     * @param id the id of the professorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/professors/{id}")
    @Timed
    public ResponseEntity<Void> deleteProfessor(@PathVariable Long id) {
        log.debug("REST request to delete Professor : {}", id);
        professorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("professor", id.toString())).build();
    }

}
