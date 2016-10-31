package br.ufrj.cos.qsoftware.web.rest;

import br.ufrj.cos.qsoftware.QsoftwareApp;

import br.ufrj.cos.qsoftware.domain.Proposta;
import br.ufrj.cos.qsoftware.repository.PropostaRepository;
import br.ufrj.cos.qsoftware.service.PropostaService;
import br.ufrj.cos.qsoftware.service.dto.PropostaDTO;
import br.ufrj.cos.qsoftware.service.mapper.PropostaMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.ufrj.cos.qsoftware.domain.enumeration.Situacao;
/**
 * Test class for the PropostaResource REST controller.
 *
 * @see PropostaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QsoftwareApp.class)
public class PropostaResourceIntTest {

    private static final Situacao DEFAULT_STATUS = Situacao.REJEITADA;
    private static final Situacao UPDATED_STATUS = Situacao.APROVADA;

    private static final LocalDate DEFAULT_DATA_APRESENTACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_APRESENTACAO = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private PropostaRepository propostaRepository;

    @Inject
    private PropostaMapper propostaMapper;

    @Inject
    private PropostaService propostaService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPropostaMockMvc;

    private Proposta proposta;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PropostaResource propostaResource = new PropostaResource();
        ReflectionTestUtils.setField(propostaResource, "propostaService", propostaService);
        this.restPropostaMockMvc = MockMvcBuilders.standaloneSetup(propostaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Proposta createEntity(EntityManager em) {
        Proposta proposta = new Proposta()
                .status(DEFAULT_STATUS)
                .dataApresentacao(DEFAULT_DATA_APRESENTACAO);
        return proposta;
    }

    @Before
    public void initTest() {
        proposta = createEntity(em);
    }

    @Test
    @Transactional
    public void createProposta() throws Exception {
        int databaseSizeBeforeCreate = propostaRepository.findAll().size();

        // Create the Proposta
        PropostaDTO propostaDTO = propostaMapper.propostaToPropostaDTO(proposta);

        restPropostaMockMvc.perform(post("/api/propostas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(propostaDTO)))
                .andExpect(status().isCreated());

        // Validate the Proposta in the database
        List<Proposta> propostas = propostaRepository.findAll();
        assertThat(propostas).hasSize(databaseSizeBeforeCreate + 1);
        Proposta testProposta = propostas.get(propostas.size() - 1);
        assertThat(testProposta.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProposta.getDataApresentacao()).isEqualTo(DEFAULT_DATA_APRESENTACAO);
    }

    @Test
    @Transactional
    public void getAllPropostas() throws Exception {
        // Initialize the database
        propostaRepository.saveAndFlush(proposta);

        // Get all the propostas
        restPropostaMockMvc.perform(get("/api/propostas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(proposta.getId().intValue())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
                .andExpect(jsonPath("$.[*].dataApresentacao").value(hasItem(DEFAULT_DATA_APRESENTACAO.toString())));
    }

    @Test
    @Transactional
    public void getProposta() throws Exception {
        // Initialize the database
        propostaRepository.saveAndFlush(proposta);

        // Get the proposta
        restPropostaMockMvc.perform(get("/api/propostas/{id}", proposta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(proposta.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.dataApresentacao").value(DEFAULT_DATA_APRESENTACAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProposta() throws Exception {
        // Get the proposta
        restPropostaMockMvc.perform(get("/api/propostas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProposta() throws Exception {
        // Initialize the database
        propostaRepository.saveAndFlush(proposta);
        int databaseSizeBeforeUpdate = propostaRepository.findAll().size();

        // Update the proposta
        Proposta updatedProposta = propostaRepository.findOne(proposta.getId());
        updatedProposta
                .status(UPDATED_STATUS)
                .dataApresentacao(UPDATED_DATA_APRESENTACAO);
        PropostaDTO propostaDTO = propostaMapper.propostaToPropostaDTO(updatedProposta);

        restPropostaMockMvc.perform(put("/api/propostas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(propostaDTO)))
                .andExpect(status().isOk());

        // Validate the Proposta in the database
        List<Proposta> propostas = propostaRepository.findAll();
        assertThat(propostas).hasSize(databaseSizeBeforeUpdate);
        Proposta testProposta = propostas.get(propostas.size() - 1);
        assertThat(testProposta.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProposta.getDataApresentacao()).isEqualTo(UPDATED_DATA_APRESENTACAO);
    }

    @Test
    @Transactional
    public void deleteProposta() throws Exception {
        // Initialize the database
        propostaRepository.saveAndFlush(proposta);
        int databaseSizeBeforeDelete = propostaRepository.findAll().size();

        // Get the proposta
        restPropostaMockMvc.perform(delete("/api/propostas/{id}", proposta.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Proposta> propostas = propostaRepository.findAll();
        assertThat(propostas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
