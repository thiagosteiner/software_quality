package br.ufrj.cos.qsoftware.web.rest;

import br.ufrj.cos.qsoftware.QsoftwareApp;

import br.ufrj.cos.qsoftware.domain.Monografia;
import br.ufrj.cos.qsoftware.repository.MonografiaRepository;
import br.ufrj.cos.qsoftware.service.MonografiaService;
import br.ufrj.cos.qsoftware.service.dto.MonografiaDTO;
import br.ufrj.cos.qsoftware.service.mapper.MonografiaMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.ufrj.cos.qsoftware.domain.enumeration.Situacao;
import br.ufrj.cos.qsoftware.domain.enumeration.TipoMonografia;
/**
 * Test class for the MonografiaResource REST controller.
 *
 * @see MonografiaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QsoftwareApp.class)
public class MonografiaResourceIntTest {

    private static final Situacao DEFAULT_STATUS = Situacao.REJEITADA;
    private static final Situacao UPDATED_STATUS = Situacao.APROVADA;

    private static final TipoMonografia DEFAULT_TIPO = TipoMonografia.DISSERTACAO;
    private static final TipoMonografia UPDATED_TIPO = TipoMonografia.TESE;

    private static final String DEFAULT_TEMA = "AAAAA";
    private static final String UPDATED_TEMA = "BBBBB";

    @Inject
    private MonografiaRepository monografiaRepository;

    @Inject
    private MonografiaMapper monografiaMapper;

    @Inject
    private MonografiaService monografiaService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restMonografiaMockMvc;

    private Monografia monografia;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MonografiaResource monografiaResource = new MonografiaResource();
        ReflectionTestUtils.setField(monografiaResource, "monografiaService", monografiaService);
        this.restMonografiaMockMvc = MockMvcBuilders.standaloneSetup(monografiaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Monografia createEntity(EntityManager em) {
        Monografia monografia = new Monografia()
                .status(DEFAULT_STATUS)
                .tipo(DEFAULT_TIPO)
                .tema(DEFAULT_TEMA);
        return monografia;
    }

    @Before
    public void initTest() {
        monografia = createEntity(em);
    }

    @Test
    @Transactional
    public void createMonografia() throws Exception {
        int databaseSizeBeforeCreate = monografiaRepository.findAll().size();

        // Create the Monografia
        MonografiaDTO monografiaDTO = monografiaMapper.monografiaToMonografiaDTO(monografia);

        restMonografiaMockMvc.perform(post("/api/monografias")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(monografiaDTO)))
                .andExpect(status().isCreated());

        // Validate the Monografia in the database
        List<Monografia> monografias = monografiaRepository.findAll();
        assertThat(monografias).hasSize(databaseSizeBeforeCreate + 1);
        Monografia testMonografia = monografias.get(monografias.size() - 1);
        assertThat(testMonografia.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMonografia.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testMonografia.getTema()).isEqualTo(DEFAULT_TEMA);
    }

    @Test
    @Transactional
    public void getAllMonografias() throws Exception {
        // Initialize the database
        monografiaRepository.saveAndFlush(monografia);

        // Get all the monografias
        restMonografiaMockMvc.perform(get("/api/monografias?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(monografia.getId().intValue())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
                .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
                .andExpect(jsonPath("$.[*].tema").value(hasItem(DEFAULT_TEMA.toString())));
    }

    @Test
    @Transactional
    public void getMonografia() throws Exception {
        // Initialize the database
        monografiaRepository.saveAndFlush(monografia);

        // Get the monografia
        restMonografiaMockMvc.perform(get("/api/monografias/{id}", monografia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(monografia.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.tema").value(DEFAULT_TEMA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMonografia() throws Exception {
        // Get the monografia
        restMonografiaMockMvc.perform(get("/api/monografias/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMonografia() throws Exception {
        // Initialize the database
        monografiaRepository.saveAndFlush(monografia);
        int databaseSizeBeforeUpdate = monografiaRepository.findAll().size();

        // Update the monografia
        Monografia updatedMonografia = monografiaRepository.findOne(monografia.getId());
        updatedMonografia
                .status(UPDATED_STATUS)
                .tipo(UPDATED_TIPO)
                .tema(UPDATED_TEMA);
        MonografiaDTO monografiaDTO = monografiaMapper.monografiaToMonografiaDTO(updatedMonografia);

        restMonografiaMockMvc.perform(put("/api/monografias")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(monografiaDTO)))
                .andExpect(status().isOk());

        // Validate the Monografia in the database
        List<Monografia> monografias = monografiaRepository.findAll();
        assertThat(monografias).hasSize(databaseSizeBeforeUpdate);
        Monografia testMonografia = monografias.get(monografias.size() - 1);
        assertThat(testMonografia.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMonografia.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testMonografia.getTema()).isEqualTo(UPDATED_TEMA);
    }

    @Test
    @Transactional
    public void deleteMonografia() throws Exception {
        // Initialize the database
        monografiaRepository.saveAndFlush(monografia);
        int databaseSizeBeforeDelete = monografiaRepository.findAll().size();

        // Get the monografia
        restMonografiaMockMvc.perform(delete("/api/monografias/{id}", monografia.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Monografia> monografias = monografiaRepository.findAll();
        assertThat(monografias).hasSize(databaseSizeBeforeDelete - 1);
    }
}
