package br.ufrj.cos.qsoftware.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.ufrj.cos.qsoftware.service.ConviteOrientadorService;
import br.ufrj.cos.qsoftware.web.rest.util.HeaderUtil;
import br.ufrj.cos.qsoftware.service.dto.ConviteOrientadorDTO;
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
 * REST controller for managing ConviteOrientador.
 */
@RestController
@RequestMapping("/api")
public class ConviteOrientadorResource {

    private final Logger log = LoggerFactory.getLogger(ConviteOrientadorResource.class);
        
    @Inject
    private ConviteOrientadorService conviteOrientadorService;

    /**
     * POST  /convite-orientadors : Create a new conviteOrientador.
     *
     * @param conviteOrientadorDTO the conviteOrientadorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new conviteOrientadorDTO, or with status 400 (Bad Request) if the conviteOrientador has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/convite-orientadors")
    @Timed
    public ResponseEntity<ConviteOrientadorDTO> createConviteOrientador(@RequestBody ConviteOrientadorDTO conviteOrientadorDTO) throws URISyntaxException {
        log.debug("REST request to save ConviteOrientador : {}", conviteOrientadorDTO);
        if (conviteOrientadorDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("conviteOrientador", "idexists", "A new conviteOrientador cannot already have an ID")).body(null);
        }
        ConviteOrientadorDTO result = conviteOrientadorService.save(conviteOrientadorDTO);
        return ResponseEntity.created(new URI("/api/convite-orientadors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("conviteOrientador", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /convite-orientadors : Updates an existing conviteOrientador.
     *
     * @param conviteOrientadorDTO the conviteOrientadorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated conviteOrientadorDTO,
     * or with status 400 (Bad Request) if the conviteOrientadorDTO is not valid,
     * or with status 500 (Internal Server Error) if the conviteOrientadorDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/convite-orientadors")
    @Timed
    public ResponseEntity<ConviteOrientadorDTO> updateConviteOrientador(@RequestBody ConviteOrientadorDTO conviteOrientadorDTO) throws URISyntaxException {
        log.debug("REST request to update ConviteOrientador : {}", conviteOrientadorDTO);
        if (conviteOrientadorDTO.getId() == null) {
            return createConviteOrientador(conviteOrientadorDTO);
        }
        ConviteOrientadorDTO result = conviteOrientadorService.save(conviteOrientadorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("conviteOrientador", conviteOrientadorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /convite-orientadors : get all the conviteOrientadors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of conviteOrientadors in body
     */
    @GetMapping("/convite-orientadors")
    @Timed
    public List<ConviteOrientadorDTO> getAllConviteOrientadors() {
        log.debug("REST request to get all ConviteOrientadors");
        return conviteOrientadorService.findAll();
    }

    /**
     * GET  /convite-orientadors/:id : get the "id" conviteOrientador.
     *
     * @param id the id of the conviteOrientadorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the conviteOrientadorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/convite-orientadors/{id}")
    @Timed
    public ResponseEntity<ConviteOrientadorDTO> getConviteOrientador(@PathVariable Long id) {
        log.debug("REST request to get ConviteOrientador : {}", id);
        ConviteOrientadorDTO conviteOrientadorDTO = conviteOrientadorService.findOne(id);
        return Optional.ofNullable(conviteOrientadorDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /convite-orientadors/:id : delete the "id" conviteOrientador.
     *
     * @param id the id of the conviteOrientadorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/convite-orientadors/{id}")
    @Timed
    public ResponseEntity<Void> deleteConviteOrientador(@PathVariable Long id) {
        log.debug("REST request to delete ConviteOrientador : {}", id);
        conviteOrientadorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("conviteOrientador", id.toString())).build();
    }

}