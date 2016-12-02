package br.ufrj.cos.qsoftware.web.rest;

import br.ufrj.cos.qsoftware.QsoftwareApp;

import br.ufrj.cos.qsoftware.domain.ConviteComite;
import br.ufrj.cos.qsoftware.repository.ConviteComiteRepository;
import br.ufrj.cos.qsoftware.service.ConviteComiteService;
import br.ufrj.cos.qsoftware.service.dto.ConviteComiteDTO;
import br.ufrj.cos.qsoftware.service.mapper.ConviteComiteMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.ufrj.cos.qsoftware.domain.enumeration.SituacaoConvite;
/**
 * Test class for the ConviteComiteResource REST controller.
 *
 * @see ConviteComiteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QsoftwareApp.class)
public class ConviteComiteResourceIntTest {

    private static final SituacaoConvite DEFAULT_STATUS = SituacaoConvite.ACEITO;
    private static final SituacaoConvite UPDATED_STATUS = SituacaoConvite.REJEITADO;

    private static final LocalDate DEFAULT_DATA_CRIACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_CRIACAO = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private ConviteComiteRepository conviteComiteRepository;

    @Inject
    private ConviteComiteMapper conviteComiteMapper;

    @Inject
    private ConviteComiteService conviteComiteService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restConviteComiteMockMvc;

    private ConviteComite conviteComite;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ConviteComiteResource conviteComiteResource = new ConviteComiteResource();
        ReflectionTestUtils.setField(conviteComiteResource, "conviteComiteService", conviteComiteService);
        this.restConviteComiteMockMvc = MockMvcBuilders.standaloneSetup(conviteComiteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConviteComite createEntity(EntityManager em) {
        ConviteComite conviteComite = new ConviteComite()
                .status(DEFAULT_STATUS)
                .dataCriacao(DEFAULT_DATA_CRIACAO);
        return conviteComite;
    }

    @Before
    public void initTest() {
        conviteComite = createEntity(em);
    }

    @Test
    @Transactional
    public void createConviteComite() throws Exception {
        int databaseSizeBeforeCreate = conviteComiteRepository.findAll().size();

        // Create the ConviteComite
        ConviteComiteDTO conviteComiteDTO = conviteComiteMapper.conviteComiteToConviteComiteDTO(conviteComite);

        restConviteComiteMockMvc.perform(post("/api/convite-comites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conviteComiteDTO)))
            .andExpect(status().isCreated());

        // Validate the ConviteComite in the database
        List<ConviteComite> conviteComites = conviteComiteRepository.findAll();
        assertThat(conviteComites).hasSize(databaseSizeBeforeCreate + 1);
        ConviteComite testConviteComite = conviteComites.get(conviteComites.size() - 1);
        assertThat(testConviteComite.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testConviteComite.getDataCriacao()).isEqualTo(DEFAULT_DATA_CRIACAO);
    }

    @Test
    @Transactional
    public void getAllConviteComites() throws Exception {
        // Initialize the database
        conviteComiteRepository.saveAndFlush(conviteComite);

        // Get all the conviteComites
        restConviteComiteMockMvc.perform(get("/api/convite-comites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conviteComite.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].dataCriacao").value(hasItem(DEFAULT_DATA_CRIACAO.toString())));
    }

    @Test
    @Transactional
    public void getConviteComite() throws Exception {
        // Initialize the database
        conviteComiteRepository.saveAndFlush(conviteComite);

        // Get the conviteComite
        restConviteComiteMockMvc.perform(get("/api/convite-comites/{id}", conviteComite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(conviteComite.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.dataCriacao").value(DEFAULT_DATA_CRIACAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConviteComite() throws Exception {
        // Get the conviteComite
        restConviteComiteMockMvc.perform(get("/api/convite-comites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConviteComite() throws Exception {
        // Initialize the database
        conviteComiteRepository.saveAndFlush(conviteComite);
        int databaseSizeBeforeUpdate = conviteComiteRepository.findAll().size();

        // Update the conviteComite
        ConviteComite updatedConviteComite = conviteComiteRepository.findOne(conviteComite.getId());
        updatedConviteComite
                .status(UPDATED_STATUS)
                .dataCriacao(UPDATED_DATA_CRIACAO);
        ConviteComiteDTO conviteComiteDTO = conviteComiteMapper.conviteComiteToConviteComiteDTO(updatedConviteComite);

        restConviteComiteMockMvc.perform(put("/api/convite-comites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conviteComiteDTO)))
            .andExpect(status().isOk());

        // Validate the ConviteComite in the database
        List<ConviteComite> conviteComites = conviteComiteRepository.findAll();
        assertThat(conviteComites).hasSize(databaseSizeBeforeUpdate);
        ConviteComite testConviteComite = conviteComites.get(conviteComites.size() - 1);
        assertThat(testConviteComite.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testConviteComite.getDataCriacao()).isEqualTo(UPDATED_DATA_CRIACAO);
    }

    @Test
    @Transactional
    public void deleteConviteComite() throws Exception {
        // Initialize the database
        conviteComiteRepository.saveAndFlush(conviteComite);
        int databaseSizeBeforeDelete = conviteComiteRepository.findAll().size();

        // Get the conviteComite
        restConviteComiteMockMvc.perform(delete("/api/convite-comites/{id}", conviteComite.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ConviteComite> conviteComites = conviteComiteRepository.findAll();
        assertThat(conviteComites).hasSize(databaseSizeBeforeDelete - 1);
    }
}
