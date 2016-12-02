package br.ufrj.cos.qsoftware.web.rest;

import br.ufrj.cos.qsoftware.QsoftwareApp;

import br.ufrj.cos.qsoftware.domain.Departamento;
import br.ufrj.cos.qsoftware.repository.DepartamentoRepository;
import br.ufrj.cos.qsoftware.service.DepartamentoService;
import br.ufrj.cos.qsoftware.service.dto.DepartamentoDTO;
import br.ufrj.cos.qsoftware.service.mapper.DepartamentoMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DepartamentoResource REST controller.
 *
 * @see DepartamentoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QsoftwareApp.class)
public class DepartamentoResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Inject
    private DepartamentoRepository departamentoRepository;

    @Inject
    private DepartamentoMapper departamentoMapper;

    @Inject
    private DepartamentoService departamentoService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restDepartamentoMockMvc;

    private Departamento departamento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DepartamentoResource departamentoResource = new DepartamentoResource();
        ReflectionTestUtils.setField(departamentoResource, "departamentoService", departamentoService);
        this.restDepartamentoMockMvc = MockMvcBuilders.standaloneSetup(departamentoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Departamento createEntity(EntityManager em) {
        Departamento departamento = new Departamento()
                .nome(DEFAULT_NOME);
        return departamento;
    }

    @Before
    public void initTest() {
        departamento = createEntity(em);
    }

    @Test
    @Transactional
    public void createDepartamento() throws Exception {
        int databaseSizeBeforeCreate = departamentoRepository.findAll().size();

        // Create the Departamento
        DepartamentoDTO departamentoDTO = departamentoMapper.departamentoToDepartamentoDTO(departamento);

        restDepartamentoMockMvc.perform(post("/api/departamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departamentoDTO)))
            .andExpect(status().isCreated());

        // Validate the Departamento in the database
        List<Departamento> departamentos = departamentoRepository.findAll();
        assertThat(departamentos).hasSize(databaseSizeBeforeCreate + 1);
        Departamento testDepartamento = departamentos.get(departamentos.size() - 1);
        assertThat(testDepartamento.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = departamentoRepository.findAll().size();
        // set the field null
        departamento.setNome(null);

        // Create the Departamento, which fails.
        DepartamentoDTO departamentoDTO = departamentoMapper.departamentoToDepartamentoDTO(departamento);

        restDepartamentoMockMvc.perform(post("/api/departamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departamentoDTO)))
            .andExpect(status().isBadRequest());

        List<Departamento> departamentos = departamentoRepository.findAll();
        assertThat(departamentos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDepartamentos() throws Exception {
        // Initialize the database
        departamentoRepository.saveAndFlush(departamento);

        // Get all the departamentos
        restDepartamentoMockMvc.perform(get("/api/departamentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(departamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())));
    }

    @Test
    @Transactional
    public void getDepartamento() throws Exception {
        // Initialize the database
        departamentoRepository.saveAndFlush(departamento);

        // Get the departamento
        restDepartamentoMockMvc.perform(get("/api/departamentos/{id}", departamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(departamento.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDepartamento() throws Exception {
        // Get the departamento
        restDepartamentoMockMvc.perform(get("/api/departamentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDepartamento() throws Exception {
        // Initialize the database
        departamentoRepository.saveAndFlush(departamento);
        int databaseSizeBeforeUpdate = departamentoRepository.findAll().size();

        // Update the departamento
        Departamento updatedDepartamento = departamentoRepository.findOne(departamento.getId());
        updatedDepartamento
                .nome(UPDATED_NOME);
        DepartamentoDTO departamentoDTO = departamentoMapper.departamentoToDepartamentoDTO(updatedDepartamento);

        restDepartamentoMockMvc.perform(put("/api/departamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departamentoDTO)))
            .andExpect(status().isOk());

        // Validate the Departamento in the database
        List<Departamento> departamentos = departamentoRepository.findAll();
        assertThat(departamentos).hasSize(databaseSizeBeforeUpdate);
        Departamento testDepartamento = departamentos.get(departamentos.size() - 1);
        assertThat(testDepartamento.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void deleteDepartamento() throws Exception {
        // Initialize the database
        departamentoRepository.saveAndFlush(departamento);
        int databaseSizeBeforeDelete = departamentoRepository.findAll().size();

        // Get the departamento
        restDepartamentoMockMvc.perform(delete("/api/departamentos/{id}", departamento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Departamento> departamentos = departamentoRepository.findAll();
        assertThat(departamentos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
