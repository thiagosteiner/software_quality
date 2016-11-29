package br.ufrj.cos.qsoftware.web.rest;

import br.ufrj.cos.qsoftware.QsoftwareApp;

import br.ufrj.cos.qsoftware.domain.Aluno;
import br.ufrj.cos.qsoftware.repository.AlunoRepository;
import br.ufrj.cos.qsoftware.service.AlunoService;
import br.ufrj.cos.qsoftware.service.dto.AlunoDTO;
import br.ufrj.cos.qsoftware.service.mapper.AlunoMapper;

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

import br.ufrj.cos.qsoftware.domain.enumeration.TipoAluno;
/**
 * Test class for the AlunoResource REST controller.
 *
 * @see AlunoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QsoftwareApp.class)
public class AlunoResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_SENHA = "AAAAAAAAAA";
    private static final String UPDATED_SENHA = "BBBBBBBBBB";

    private static final String DEFAULT_DRE = "AAAAAAAAAA";
    private static final String UPDATED_DRE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_INGRESSO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_INGRESSO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PREVISAO_FORMATURA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PREVISAO_FORMATURA = LocalDate.now(ZoneId.systemDefault());

    private static final TipoAluno DEFAULT_TIPO = TipoAluno.MESTRADO;
    private static final TipoAluno UPDATED_TIPO = TipoAluno.DOUTORADO;

    @Inject
    private AlunoRepository alunoRepository;

    @Inject
    private AlunoMapper alunoMapper;

    @Inject
    private AlunoService alunoService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restAlunoMockMvc;

    private Aluno aluno;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AlunoResource alunoResource = new AlunoResource();
        ReflectionTestUtils.setField(alunoResource, "alunoService", alunoService);
        this.restAlunoMockMvc = MockMvcBuilders.standaloneSetup(alunoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aluno createEntity(EntityManager em) {
        Aluno aluno = new Aluno()
                .nome(DEFAULT_NOME)
                .senha(DEFAULT_SENHA)
                .dre(DEFAULT_DRE)
                .dataIngresso(DEFAULT_DATA_INGRESSO)
                .previsaoFormatura(DEFAULT_PREVISAO_FORMATURA)
                .tipo(DEFAULT_TIPO);
        return aluno;
    }

    @Before
    public void initTest() {
        aluno = createEntity(em);
    }

    @Test
    @Transactional
    public void createAluno() throws Exception {
        int databaseSizeBeforeCreate = alunoRepository.findAll().size();

        // Create the Aluno
        AlunoDTO alunoDTO = alunoMapper.alunoToAlunoDTO(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
                .andExpect(status().isCreated());

        // Validate the Aluno in the database
        List<Aluno> alunos = alunoRepository.findAll();
        assertThat(alunos).hasSize(databaseSizeBeforeCreate + 1);
        Aluno testAluno = alunos.get(alunos.size() - 1);
        assertThat(testAluno.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAluno.getSenha()).isEqualTo(DEFAULT_SENHA);
        assertThat(testAluno.getDre()).isEqualTo(DEFAULT_DRE);
        assertThat(testAluno.getDataIngresso()).isEqualTo(DEFAULT_DATA_INGRESSO);
        assertThat(testAluno.getPrevisaoFormatura()).isEqualTo(DEFAULT_PREVISAO_FORMATURA);
        assertThat(testAluno.getTipo()).isEqualTo(DEFAULT_TIPO);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setNome(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.alunoToAlunoDTO(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
                .andExpect(status().isBadRequest());

        List<Aluno> alunos = alunoRepository.findAll();
        assertThat(alunos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSenhaIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setSenha(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.alunoToAlunoDTO(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
                .andExpect(status().isBadRequest());

        List<Aluno> alunos = alunoRepository.findAll();
        assertThat(alunos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDreIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setDre(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.alunoToAlunoDTO(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
                .andExpect(status().isBadRequest());

        List<Aluno> alunos = alunoRepository.findAll();
        assertThat(alunos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAlunos() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunos
        restAlunoMockMvc.perform(get("/api/alunos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(aluno.getId().intValue())))
                .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
                .andExpect(jsonPath("$.[*].senha").value(hasItem(DEFAULT_SENHA.toString())))
                .andExpect(jsonPath("$.[*].dre").value(hasItem(DEFAULT_DRE.toString())))
                .andExpect(jsonPath("$.[*].dataIngresso").value(hasItem(DEFAULT_DATA_INGRESSO.toString())))
                .andExpect(jsonPath("$.[*].previsaoFormatura").value(hasItem(DEFAULT_PREVISAO_FORMATURA.toString())))
                .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())));
    }

    @Test
    @Transactional
    public void getAluno() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get the aluno
        restAlunoMockMvc.perform(get("/api/alunos/{id}", aluno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(aluno.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.senha").value(DEFAULT_SENHA.toString()))
            .andExpect(jsonPath("$.dre").value(DEFAULT_DRE.toString()))
            .andExpect(jsonPath("$.dataIngresso").value(DEFAULT_DATA_INGRESSO.toString()))
            .andExpect(jsonPath("$.previsaoFormatura").value(DEFAULT_PREVISAO_FORMATURA.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAluno() throws Exception {
        // Get the aluno
        restAlunoMockMvc.perform(get("/api/alunos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAluno() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);
        int databaseSizeBeforeUpdate = alunoRepository.findAll().size();

        // Update the aluno
        Aluno updatedAluno = alunoRepository.findOne(aluno.getId());
        updatedAluno
                .nome(UPDATED_NOME)
                .senha(UPDATED_SENHA)
                .dre(UPDATED_DRE)
                .dataIngresso(UPDATED_DATA_INGRESSO)
                .previsaoFormatura(UPDATED_PREVISAO_FORMATURA)
                .tipo(UPDATED_TIPO);
        AlunoDTO alunoDTO = alunoMapper.alunoToAlunoDTO(updatedAluno);

        restAlunoMockMvc.perform(put("/api/alunos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
                .andExpect(status().isOk());

        // Validate the Aluno in the database
        List<Aluno> alunos = alunoRepository.findAll();
        assertThat(alunos).hasSize(databaseSizeBeforeUpdate);
        Aluno testAluno = alunos.get(alunos.size() - 1);
        assertThat(testAluno.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAluno.getSenha()).isEqualTo(UPDATED_SENHA);
        assertThat(testAluno.getDre()).isEqualTo(UPDATED_DRE);
        assertThat(testAluno.getDataIngresso()).isEqualTo(UPDATED_DATA_INGRESSO);
        assertThat(testAluno.getPrevisaoFormatura()).isEqualTo(UPDATED_PREVISAO_FORMATURA);
        assertThat(testAluno.getTipo()).isEqualTo(UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void deleteAluno() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);
        int databaseSizeBeforeDelete = alunoRepository.findAll().size();

        // Get the aluno
        restAlunoMockMvc.perform(delete("/api/alunos/{id}", aluno.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Aluno> alunos = alunoRepository.findAll();
        assertThat(alunos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
