package br.ufrj.cos.qsoftware.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.ufrj.cos.qsoftware.service.AlunoService;
import br.ufrj.cos.qsoftware.web.rest.util.HeaderUtil;
import br.ufrj.cos.qsoftware.service.dto.AlunoDTO;
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
 * REST controller for managing Aluno.
 */
@RestController
@RequestMapping("/api")
public class AlunoResource {

    private final Logger log = LoggerFactory.getLogger(AlunoResource.class);
        
    @Inject
    private AlunoService alunoService;

    /**
     * POST  /alunos : Create a new aluno.
     *
     * @param alunoDTO the alunoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new alunoDTO, or with status 400 (Bad Request) if the aluno has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/alunos")
    @Timed
    public ResponseEntity<AlunoDTO> createAluno(@Valid @RequestBody AlunoDTO alunoDTO) throws URISyntaxException {
        log.debug("REST request to save Aluno : {}", alunoDTO);
        if (alunoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("aluno", "idexists", "A new aluno cannot already have an ID")).body(null);
        }
        AlunoDTO result = alunoService.save(alunoDTO);
        return ResponseEntity.created(new URI("/api/alunos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("aluno", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /alunos : Updates an existing aluno.
     *
     * @param alunoDTO the alunoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated alunoDTO,
     * or with status 400 (Bad Request) if the alunoDTO is not valid,
     * or with status 500 (Internal Server Error) if the alunoDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/alunos")
    @Timed
    public ResponseEntity<AlunoDTO> updateAluno(@Valid @RequestBody AlunoDTO alunoDTO) throws URISyntaxException {
        log.debug("REST request to update Aluno : {}", alunoDTO);
        if (alunoDTO.getId() == null) {
            return createAluno(alunoDTO);
        }
        AlunoDTO result = alunoService.save(alunoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("aluno", alunoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /alunos : get all the alunos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of alunos in body
     */
    @GetMapping("/alunos")
    @Timed
    public List<AlunoDTO> getAllAlunos() {
        log.debug("REST request to get all Alunos");
        return alunoService.findAll();
    }

    /**
     * GET  /alunos/:id : get the "id" aluno.
     *
     * @param id the id of the alunoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the alunoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/alunos/{id}")
    @Timed
    public ResponseEntity<AlunoDTO> getAluno(@PathVariable Long id) {
        log.debug("REST request to get Aluno : {}", id);
        AlunoDTO alunoDTO = alunoService.findOne(id);
        return Optional.ofNullable(alunoDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /alunos/:id : delete the "id" aluno.
     *
     * @param id the id of the alunoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/alunos/{id}")
    @Timed
    public ResponseEntity<Void> deleteAluno(@PathVariable Long id) {
        log.debug("REST request to delete Aluno : {}", id);
        alunoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("aluno", id.toString())).build();
    }

}
