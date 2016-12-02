package br.ufrj.cos.qsoftware.web.rest;

import br.ufrj.cos.qsoftware.QsoftwareApp;

import br.ufrj.cos.qsoftware.domain.ConviteOrientador;
import br.ufrj.cos.qsoftware.repository.ConviteOrientadorRepository;
import br.ufrj.cos.qsoftware.service.ConviteOrientadorService;
import br.ufrj.cos.qsoftware.service.dto.ConviteOrientadorDTO;
import br.ufrj.cos.qsoftware.service.mapper.ConviteOrientadorMapper;

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
 * Test class for the ConviteOrientadorResource REST controller.
 *
 * @see ConviteOrientadorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QsoftwareApp.class)
public class ConviteOrientadorResourceIntTest {

    private static final SituacaoConvite DEFAULT_STATUS = SituacaoConvite.ACEITO;
    private static final SituacaoConvite UPDATED_STATUS = SituacaoConvite.REJEITADO;

    private static final LocalDate DEFAULT_DATA_CRIACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_CRIACAO = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private ConviteOrientadorRepository conviteOrientadorRepository;

    @Inject
    private ConviteOrientadorMapper conviteOrientadorMapper;

    @Inject
    private ConviteOrientadorService conviteOrientadorService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restConviteOrientadorMockMvc;

    private ConviteOrientador conviteOrientador;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ConviteOrientadorResource conviteOrientadorResource = new ConviteOrientadorResource();
        ReflectionTestUtils.setField(conviteOrientadorResource, "conviteOrientadorService", conviteOrientadorService);
        this.restConviteOrientadorMockMvc = MockMvcBuilders.standaloneSetup(conviteOrientadorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConviteOrientador createEntity(EntityManager em) {
        ConviteOrientador conviteOrientador = new ConviteOrientador()
                .status(DEFAULT_STATUS)
                .dataCriacao(DEFAULT_DATA_CRIACAO);
        return conviteOrientador;
    }

    @Before
    public void initTest() {
        conviteOrientador = createEntity(em);
    }

    @Test
    @Transactional
    public void createConviteOrientador() throws Exception {
        int databaseSizeBeforeCreate = conviteOrientadorRepository.findAll().size();

        // Create the ConviteOrientador
        ConviteOrientadorDTO conviteOrientadorDTO = conviteOrientadorMapper.conviteOrientadorToConviteOrientadorDTO(conviteOrientador);

        restConviteOrientadorMockMvc.perform(post("/api/convite-orientadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conviteOrientadorDTO)))
            .andExpect(status().isCreated());

        // Validate the ConviteOrientador in the database
        List<ConviteOrientador> conviteOrientadors = conviteOrientadorRepository.findAll();
        assertThat(conviteOrientadors).hasSize(databaseSizeBeforeCreate + 1);
        ConviteOrientador testConviteOrientador = conviteOrientadors.get(conviteOrientadors.size() - 1);
        assertThat(testConviteOrientador.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testConviteOrientador.getDataCriacao()).isEqualTo(DEFAULT_DATA_CRIACAO);
    }

    @Test
    @Transactional
    public void getAllConviteOrientadors() throws Exception {
        // Initialize the database
        conviteOrientadorRepository.saveAndFlush(conviteOrientador);

        // Get all the conviteOrientadors
        restConviteOrientadorMockMvc.perform(get("/api/convite-orientadors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conviteOrientador.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].dataCriacao").value(hasItem(DEFAULT_DATA_CRIACAO.toString())));
    }

    @Test
    @Transactional
    public void getConviteOrientador() throws Exception {
        // Initialize the database
        conviteOrientadorRepository.saveAndFlush(conviteOrientador);

        // Get the conviteOrientador
        restConviteOrientadorMockMvc.perform(get("/api/convite-orientadors/{id}", conviteOrientador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(conviteOrientador.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.dataCriacao").value(DEFAULT_DATA_CRIACAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConviteOrientador() throws Exception {
        // Get the conviteOrientador
        restConviteOrientadorMockMvc.perform(get("/api/convite-orientadors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConviteOrientador() throws Exception {
        // Initialize the database
        conviteOrientadorRepository.saveAndFlush(conviteOrientador);
        int databaseSizeBeforeUpdate = conviteOrientadorRepository.findAll().size();

        // Update the conviteOrientador
        ConviteOrientador updatedConviteOrientador = conviteOrientadorRepository.findOne(conviteOrientador.getId());
        updatedConviteOrientador
                .status(UPDATED_STATUS)
                .dataCriacao(UPDATED_DATA_CRIACAO);
        ConviteOrientadorDTO conviteOrientadorDTO = conviteOrientadorMapper.conviteOrientadorToConviteOrientadorDTO(updatedConviteOrientador);

        restConviteOrientadorMockMvc.perform(put("/api/convite-orientadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conviteOrientadorDTO)))
            .andExpect(status().isOk());

        // Validate the ConviteOrientador in the database
        List<ConviteOrientador> conviteOrientadors = conviteOrientadorRepository.findAll();
        assertThat(conviteOrientadors).hasSize(databaseSizeBeforeUpdate);
        ConviteOrientador testConviteOrientador = conviteOrientadors.get(conviteOrientadors.size() - 1);
        assertThat(testConviteOrientador.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testConviteOrientador.getDataCriacao()).isEqualTo(UPDATED_DATA_CRIACAO);
    }

    @Test
    @Transactional
    public void deleteConviteOrientador() throws Exception {
        // Initialize the database
        conviteOrientadorRepository.saveAndFlush(conviteOrientador);
        int databaseSizeBeforeDelete = conviteOrientadorRepository.findAll().size();

        // Get the conviteOrientador
        restConviteOrientadorMockMvc.perform(delete("/api/convite-orientadors/{id}", conviteOrientador.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ConviteOrientador> conviteOrientadors = conviteOrientadorRepository.findAll();
        assertThat(conviteOrientadors).hasSize(databaseSizeBeforeDelete - 1);
    }
}
