package br.ufrj.cos.qsoftware.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.ufrj.cos.qsoftware.service.PropostaService;
import br.ufrj.cos.qsoftware.web.rest.util.HeaderUtil;
import br.ufrj.cos.qsoftware.service.dto.PropostaDTO;
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
 * REST controller for managing Proposta.
 */
@RestController
@RequestMapping("/api")
public class PropostaResource {

    private final Logger log = LoggerFactory.getLogger(PropostaResource.class);
        
    @Inject
    private PropostaService propostaService;

    /**
     * POST  /propostas : Create a new proposta.
     *
     * @param propostaDTO the propostaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new propostaDTO, or with status 400 (Bad Request) if the proposta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/propostas")
    @Timed
    public ResponseEntity<PropostaDTO> createProposta(@RequestBody PropostaDTO propostaDTO) throws URISyntaxException {
        log.debug("REST request to save Proposta : {}", propostaDTO);
        if (propostaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("proposta", "idexists", "A new proposta cannot already have an ID")).body(null);
        }
        PropostaDTO result = propostaService.save(propostaDTO);
        return ResponseEntity.created(new URI("/api/propostas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("proposta", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /propostas : Updates an existing proposta.
     *
     * @param propostaDTO the propostaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated propostaDTO,
     * or with status 400 (Bad Request) if the propostaDTO is not valid,
     * or with status 500 (Internal Server Error) if the propostaDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/propostas")
    @Timed
    public ResponseEntity<PropostaDTO> updateProposta(@RequestBody PropostaDTO propostaDTO) throws URISyntaxException {
        log.debug("REST request to update Proposta : {}", propostaDTO);
        if (propostaDTO.getId() == null) {
            return createProposta(propostaDTO);
        }
        PropostaDTO result = propostaService.save(propostaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("proposta", propostaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /propostas : get all the propostas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of propostas in body
     */
    @GetMapping("/propostas")
    @Timed
    public List<PropostaDTO> getAllPropostas() {
        log.debug("REST request to get all Propostas");
        return propostaService.findAll();
    }

    /**
     * GET  /propostas/:id : get the "id" proposta.
     *
     * @param id the id of the propostaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the propostaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/propostas/{id}")
    @Timed
    public ResponseEntity<PropostaDTO> getProposta(@PathVariable Long id) {
        log.debug("REST request to get Proposta : {}", id);
        PropostaDTO propostaDTO = propostaService.findOne(id);
        return Optional.ofNullable(propostaDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /propostas/:id : delete the "id" proposta.
     *
     * @param id the id of the propostaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/propostas/{id}")
    @Timed
    public ResponseEntity<Void> deleteProposta(@PathVariable Long id) {
        log.debug("REST request to delete Proposta : {}", id);
        propostaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("proposta", id.toString())).build();
    }

}
