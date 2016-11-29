package br.ufrj.cos.qsoftware.web.rest;

import br.ufrj.cos.qsoftware.QsoftwareApp;

import br.ufrj.cos.qsoftware.domain.Convite;
import br.ufrj.cos.qsoftware.repository.ConviteRepository;
import br.ufrj.cos.qsoftware.service.ConviteService;
import br.ufrj.cos.qsoftware.service.dto.ConviteDTO;
import br.ufrj.cos.qsoftware.service.mapper.ConviteMapper;

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

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.ufrj.cos.qsoftware.domain.enumeration.SituacaoConvite;
import br.ufrj.cos.qsoftware.domain.enumeration.TipoConvite;
/**
 * Test class for the ConviteResource REST controller.
 *
 * @see ConviteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QsoftwareApp.class)
public class ConviteResourceIntTest {

    private static final SituacaoConvite DEFAULT_STATUS = SituacaoConvite.ACEITO;
    private static final SituacaoConvite UPDATED_STATUS = SituacaoConvite.REJEITADO;

    private static final LocalDate DEFAULT_DATA_CRIACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_CRIACAO = LocalDate.now(ZoneId.systemDefault());

    private static final TipoConvite DEFAULT_TIPO_CONVITE = TipoConvite.ORIENTADOR;
    private static final TipoConvite UPDATED_TIPO_CONVITE = TipoConvite.COMITE;

    @Inject
    private ConviteRepository conviteRepository;

    @Inject
    private ConviteMapper conviteMapper;

    @Inject
    private ConviteService conviteService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restConviteMockMvc;

    private Convite convite;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ConviteResource conviteResource = new ConviteResource();
        ReflectionTestUtils.setField(conviteResource, "conviteService", conviteService);
        this.restConviteMockMvc = MockMvcBuilders.standaloneSetup(conviteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Convite createEntity(EntityManager em) {
        Convite convite = new Convite()
                .status(DEFAULT_STATUS)
                .dataCriacao(DEFAULT_DATA_CRIACAO)
                .tipoConvite(DEFAULT_TIPO_CONVITE);
        return convite;
    }

    @Before
    public void initTest() {
        convite = createEntity(em);
    }

    @Test
    @Transactional
    public void createConvite() throws Exception {
        int databaseSizeBeforeCreate = conviteRepository.findAll().size();

        // Create the Convite
        ConviteDTO conviteDTO = conviteMapper.conviteToConviteDTO(convite);

        restConviteMockMvc.perform(post("/api/convites")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(conviteDTO)))
                .andExpect(status().isCreated());

        // Validate the Convite in the database
        List<Convite> convites = conviteRepository.findAll();
        assertThat(convites).hasSize(databaseSizeBeforeCreate + 1);
        Convite testConvite = convites.get(convites.size() - 1);
        assertThat(testConvite.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testConvite.getDataCriacao()).isEqualTo(DEFAULT_DATA_CRIACAO);
        assertThat(testConvite.getTipoConvite()).isEqualTo(DEFAULT_TIPO_CONVITE);
    }

    @Test
    @Transactional
    public void getAllConvites() throws Exception {
        // Initialize the database
        conviteRepository.saveAndFlush(convite);

        // Get all the convites
        restConviteMockMvc.perform(get("/api/convites?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(convite.getId().intValue())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
                .andExpect(jsonPath("$.[*].dataCriacao").value(hasItem(DEFAULT_DATA_CRIACAO.toString())))
                .andExpect(jsonPath("$.[*].tipoConvite").value(hasItem(DEFAULT_TIPO_CONVITE.toString())));
    }

    @Test
    @Transactional
    public void getConvite() throws Exception {
        // Initialize the database
        conviteRepository.saveAndFlush(convite);

        // Get the convite
        restConviteMockMvc.perform(get("/api/convites/{id}", convite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(convite.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.dataCriacao").value(DEFAULT_DATA_CRIACAO.toString()))
            .andExpect(jsonPath("$.tipoConvite").value(DEFAULT_TIPO_CONVITE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConvite() throws Exception {
        // Get the convite
        restConviteMockMvc.perform(get("/api/convites/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConvite() throws Exception {
        // Initialize the database
        conviteRepository.saveAndFlush(convite);
        int databaseSizeBeforeUpdate = conviteRepository.findAll().size();

        // Update the convite
        Convite updatedConvite = conviteRepository.findOne(convite.getId());
        updatedConvite
                .status(UPDATED_STATUS)
                .dataCriacao(UPDATED_DATA_CRIACAO)
                .tipoConvite(UPDATED_TIPO_CONVITE);
        ConviteDTO conviteDTO = conviteMapper.conviteToConviteDTO(updatedConvite);

        restConviteMockMvc.perform(put("/api/convites")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(conviteDTO)))
                .andExpect(status().isOk());

        // Validate the Convite in the database
        List<Convite> convites = conviteRepository.findAll();
        assertThat(convites).hasSize(databaseSizeBeforeUpdate);
        Convite testConvite = convites.get(convites.size() - 1);
        assertThat(testConvite.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testConvite.getDataCriacao()).isEqualTo(UPDATED_DATA_CRIACAO);
        assertThat(testConvite.getTipoConvite()).isEqualTo(UPDATED_TIPO_CONVITE);
    }

    @Test
    @Transactional
    public void deleteConvite() throws Exception {
        // Initialize the database
        conviteRepository.saveAndFlush(convite);
        int databaseSizeBeforeDelete = conviteRepository.findAll().size();

        // Get the convite
        restConviteMockMvc.perform(delete("/api/convites/{id}", convite.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Convite> convites = conviteRepository.findAll();
        assertThat(convites).hasSize(databaseSizeBeforeDelete - 1);
    }
}
