package br.ufrj.cos.qsoftware.web.rest;

import br.ufrj.cos.qsoftware.QsoftwareApp;

import br.ufrj.cos.qsoftware.domain.Comite;
import br.ufrj.cos.qsoftware.repository.ComiteRepository;
import br.ufrj.cos.qsoftware.service.ComiteService;
import br.ufrj.cos.qsoftware.service.dto.ComiteDTO;
import br.ufrj.cos.qsoftware.service.mapper.ComiteMapper;

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

/**
 * Test class for the ComiteResource REST controller.
 *
 * @see ComiteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QsoftwareApp.class)
public class ComiteResourceIntTest {

    private static final String DEFAULT_LOCAL = "AAAAAAAAAA";
    private static final String UPDATED_LOCAL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_OCORRENCIA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_OCORRENCIA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ATA_COMITE = "AAAAAAAAAA";
    private static final String UPDATED_ATA_COMITE = "BBBBBBBBBB";

    @Inject
    private ComiteRepository comiteRepository;

    @Inject
    private ComiteMapper comiteMapper;

    @Inject
    private ComiteService comiteService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restComiteMockMvc;

    private Comite comite;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ComiteResource comiteResource = new ComiteResource();
        ReflectionTestUtils.setField(comiteResource, "comiteService", comiteService);
        this.restComiteMockMvc = MockMvcBuilders.standaloneSetup(comiteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comite createEntity(EntityManager em) {
        Comite comite = new Comite()
                .local(DEFAULT_LOCAL)
                .dataOcorrencia(DEFAULT_DATA_OCORRENCIA)
                .ataComite(DEFAULT_ATA_COMITE);
        return comite;
    }

    @Before
    public void initTest() {
        comite = createEntity(em);
    }

    @Test
    @Transactional
    public void createComite() throws Exception {
        int databaseSizeBeforeCreate = comiteRepository.findAll().size();

        // Create the Comite
        ComiteDTO comiteDTO = comiteMapper.comiteToComiteDTO(comite);

        restComiteMockMvc.perform(post("/api/comites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comiteDTO)))
            .andExpect(status().isCreated());

        // Validate the Comite in the database
        List<Comite> comites = comiteRepository.findAll();
        assertThat(comites).hasSize(databaseSizeBeforeCreate + 1);
        Comite testComite = comites.get(comites.size() - 1);
        assertThat(testComite.getLocal()).isEqualTo(DEFAULT_LOCAL);
        assertThat(testComite.getDataOcorrencia()).isEqualTo(DEFAULT_DATA_OCORRENCIA);
        assertThat(testComite.getAtaComite()).isEqualTo(DEFAULT_ATA_COMITE);
    }

    @Test
    @Transactional
    public void getAllComites() throws Exception {
        // Initialize the database
        comiteRepository.saveAndFlush(comite);

        // Get all the comites
        restComiteMockMvc.perform(get("/api/comites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comite.getId().intValue())))
            .andExpect(jsonPath("$.[*].local").value(hasItem(DEFAULT_LOCAL.toString())))
            .andExpect(jsonPath("$.[*].dataOcorrencia").value(hasItem(DEFAULT_DATA_OCORRENCIA.toString())))
            .andExpect(jsonPath("$.[*].ataComite").value(hasItem(DEFAULT_ATA_COMITE.toString())));
    }

    @Test
    @Transactional
    public void getComite() throws Exception {
        // Initialize the database
        comiteRepository.saveAndFlush(comite);

        // Get the comite
        restComiteMockMvc.perform(get("/api/comites/{id}", comite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(comite.getId().intValue()))
            .andExpect(jsonPath("$.local").value(DEFAULT_LOCAL.toString()))
            .andExpect(jsonPath("$.dataOcorrencia").value(DEFAULT_DATA_OCORRENCIA.toString()))
            .andExpect(jsonPath("$.ataComite").value(DEFAULT_ATA_COMITE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingComite() throws Exception {
        // Get the comite
        restComiteMockMvc.perform(get("/api/comites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComite() throws Exception {
        // Initialize the database
        comiteRepository.saveAndFlush(comite);
        int databaseSizeBeforeUpdate = comiteRepository.findAll().size();

        // Update the comite
        Comite updatedComite = comiteRepository.findOne(comite.getId());
        updatedComite
                .local(UPDATED_LOCAL)
                .dataOcorrencia(UPDATED_DATA_OCORRENCIA)
                .ataComite(UPDATED_ATA_COMITE);
        ComiteDTO comiteDTO = comiteMapper.comiteToComiteDTO(updatedComite);

        restComiteMockMvc.perform(put("/api/comites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comiteDTO)))
            .andExpect(status().isOk());

        // Validate the Comite in the database
        List<Comite> comites = comiteRepository.findAll();
        assertThat(comites).hasSize(databaseSizeBeforeUpdate);
        Comite testComite = comites.get(comites.size() - 1);
        assertThat(testComite.getLocal()).isEqualTo(UPDATED_LOCAL);
        assertThat(testComite.getDataOcorrencia()).isEqualTo(UPDATED_DATA_OCORRENCIA);
        assertThat(testComite.getAtaComite()).isEqualTo(UPDATED_ATA_COMITE);
    }

    @Test
    @Transactional
    public void deleteComite() throws Exception {
        // Initialize the database
        comiteRepository.saveAndFlush(comite);
        int databaseSizeBeforeDelete = comiteRepository.findAll().size();

        // Get the comite
        restComiteMockMvc.perform(delete("/api/comites/{id}", comite.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Comite> comites = comiteRepository.findAll();
        assertThat(comites).hasSize(databaseSizeBeforeDelete - 1);
    }
}
