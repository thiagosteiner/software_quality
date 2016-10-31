package br.ufrj.cos.qsoftware.web.rest;

import br.ufrj.cos.qsoftware.QsoftwareApp;

import br.ufrj.cos.qsoftware.domain.Publicacao;
import br.ufrj.cos.qsoftware.repository.PublicacaoRepository;
import br.ufrj.cos.qsoftware.service.PublicacaoService;
import br.ufrj.cos.qsoftware.service.dto.PublicacaoDTO;
import br.ufrj.cos.qsoftware.service.mapper.PublicacaoMapper;

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

/**
 * Test class for the PublicacaoResource REST controller.
 *
 * @see PublicacaoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QsoftwareApp.class)
public class PublicacaoResourceIntTest {

    private static final LocalDate DEFAULT_DATA_PUBLICACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_PUBLICACAO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA_APRESENTACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_APRESENTACAO = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private PublicacaoRepository publicacaoRepository;

    @Inject
    private PublicacaoMapper publicacaoMapper;

    @Inject
    private PublicacaoService publicacaoService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPublicacaoMockMvc;

    private Publicacao publicacao;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PublicacaoResource publicacaoResource = new PublicacaoResource();
        ReflectionTestUtils.setField(publicacaoResource, "publicacaoService", publicacaoService);
        this.restPublicacaoMockMvc = MockMvcBuilders.standaloneSetup(publicacaoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Publicacao createEntity(EntityManager em) {
        Publicacao publicacao = new Publicacao()
                .dataPublicacao(DEFAULT_DATA_PUBLICACAO)
                .dataApresentacao(DEFAULT_DATA_APRESENTACAO);
        return publicacao;
    }

    @Before
    public void initTest() {
        publicacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createPublicacao() throws Exception {
        int databaseSizeBeforeCreate = publicacaoRepository.findAll().size();

        // Create the Publicacao
        PublicacaoDTO publicacaoDTO = publicacaoMapper.publicacaoToPublicacaoDTO(publicacao);

        restPublicacaoMockMvc.perform(post("/api/publicacaos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(publicacaoDTO)))
                .andExpect(status().isCreated());

        // Validate the Publicacao in the database
        List<Publicacao> publicacaos = publicacaoRepository.findAll();
        assertThat(publicacaos).hasSize(databaseSizeBeforeCreate + 1);
        Publicacao testPublicacao = publicacaos.get(publicacaos.size() - 1);
        assertThat(testPublicacao.getDataPublicacao()).isEqualTo(DEFAULT_DATA_PUBLICACAO);
        assertThat(testPublicacao.getDataApresentacao()).isEqualTo(DEFAULT_DATA_APRESENTACAO);
    }

    @Test
    @Transactional
    public void getAllPublicacaos() throws Exception {
        // Initialize the database
        publicacaoRepository.saveAndFlush(publicacao);

        // Get all the publicacaos
        restPublicacaoMockMvc.perform(get("/api/publicacaos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(publicacao.getId().intValue())))
                .andExpect(jsonPath("$.[*].dataPublicacao").value(hasItem(DEFAULT_DATA_PUBLICACAO.toString())))
                .andExpect(jsonPath("$.[*].dataApresentacao").value(hasItem(DEFAULT_DATA_APRESENTACAO.toString())));
    }

    @Test
    @Transactional
    public void getPublicacao() throws Exception {
        // Initialize the database
        publicacaoRepository.saveAndFlush(publicacao);

        // Get the publicacao
        restPublicacaoMockMvc.perform(get("/api/publicacaos/{id}", publicacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(publicacao.getId().intValue()))
            .andExpect(jsonPath("$.dataPublicacao").value(DEFAULT_DATA_PUBLICACAO.toString()))
            .andExpect(jsonPath("$.dataApresentacao").value(DEFAULT_DATA_APRESENTACAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPublicacao() throws Exception {
        // Get the publicacao
        restPublicacaoMockMvc.perform(get("/api/publicacaos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePublicacao() throws Exception {
        // Initialize the database
        publicacaoRepository.saveAndFlush(publicacao);
        int databaseSizeBeforeUpdate = publicacaoRepository.findAll().size();

        // Update the publicacao
        Publicacao updatedPublicacao = publicacaoRepository.findOne(publicacao.getId());
        updatedPublicacao
                .dataPublicacao(UPDATED_DATA_PUBLICACAO)
                .dataApresentacao(UPDATED_DATA_APRESENTACAO);
        PublicacaoDTO publicacaoDTO = publicacaoMapper.publicacaoToPublicacaoDTO(updatedPublicacao);

        restPublicacaoMockMvc.perform(put("/api/publicacaos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(publicacaoDTO)))
                .andExpect(status().isOk());

        // Validate the Publicacao in the database
        List<Publicacao> publicacaos = publicacaoRepository.findAll();
        assertThat(publicacaos).hasSize(databaseSizeBeforeUpdate);
        Publicacao testPublicacao = publicacaos.get(publicacaos.size() - 1);
        assertThat(testPublicacao.getDataPublicacao()).isEqualTo(UPDATED_DATA_PUBLICACAO);
        assertThat(testPublicacao.getDataApresentacao()).isEqualTo(UPDATED_DATA_APRESENTACAO);
    }

    @Test
    @Transactional
    public void deletePublicacao() throws Exception {
        // Initialize the database
        publicacaoRepository.saveAndFlush(publicacao);
        int databaseSizeBeforeDelete = publicacaoRepository.findAll().size();

        // Get the publicacao
        restPublicacaoMockMvc.perform(delete("/api/publicacaos/{id}", publicacao.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Publicacao> publicacaos = publicacaoRepository.findAll();
        assertThat(publicacaos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
