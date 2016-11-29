package br.ufrj.cos.qsoftware.web.rest;

import br.ufrj.cos.qsoftware.QsoftwareApp;

import br.ufrj.cos.qsoftware.domain.Documento;
import br.ufrj.cos.qsoftware.repository.DocumentoRepository;
import br.ufrj.cos.qsoftware.service.DocumentoService;
import br.ufrj.cos.qsoftware.service.dto.DocumentoDTO;
import br.ufrj.cos.qsoftware.service.mapper.DocumentoMapper;

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
import org.springframework.util.Base64Utils;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.ufrj.cos.qsoftware.domain.enumeration.SituacaoPublicacao;
import br.ufrj.cos.qsoftware.domain.enumeration.TipoDocumento;
import br.ufrj.cos.qsoftware.domain.enumeration.TipoMonografia;
/**
 * Test class for the DocumentoResource REST controller.
 *
 * @see DocumentoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QsoftwareApp.class)
public class DocumentoResourceIntTest {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_RESUMO = "AAAAAAAAAA";
    private static final String UPDATED_RESUMO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_CRIACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_CRIACAO = LocalDate.now(ZoneId.systemDefault());

    private static final SituacaoPublicacao DEFAULT_STATUS = SituacaoPublicacao.NULL;
    private static final SituacaoPublicacao UPDATED_STATUS = SituacaoPublicacao.REJEITADA;

    private static final TipoDocumento DEFAULT_TIPO_DOCUMENTO = TipoDocumento.MONOGRAFIA;
    private static final TipoDocumento UPDATED_TIPO_DOCUMENTO = TipoDocumento.PROPOSTAMONOGRAFIA;

    private static final TipoMonografia DEFAULT_TIPO_MONOGRAFIA = TipoMonografia.NULL;
    private static final TipoMonografia UPDATED_TIPO_MONOGRAFIA = TipoMonografia.DISSERTACAO;

    private static final byte[] DEFAULT_ARQUIVO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ARQUIVO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_ARQUIVO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ARQUIVO_CONTENT_TYPE = "image/png";

    @Inject
    private DocumentoRepository documentoRepository;

    @Inject
    private DocumentoMapper documentoMapper;

    @Inject
    private DocumentoService documentoService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restDocumentoMockMvc;

    private Documento documento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DocumentoResource documentoResource = new DocumentoResource();
        ReflectionTestUtils.setField(documentoResource, "documentoService", documentoService);
        this.restDocumentoMockMvc = MockMvcBuilders.standaloneSetup(documentoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Documento createEntity(EntityManager em) {
        Documento documento = new Documento()
                .titulo(DEFAULT_TITULO)
                .resumo(DEFAULT_RESUMO)
                .dataCriacao(DEFAULT_DATA_CRIACAO)
                .status(DEFAULT_STATUS)
                .tipoDocumento(DEFAULT_TIPO_DOCUMENTO)
                .tipoMonografia(DEFAULT_TIPO_MONOGRAFIA)
                .arquivo(DEFAULT_ARQUIVO)
                .arquivoContentType(DEFAULT_ARQUIVO_CONTENT_TYPE);
        return documento;
    }

    @Before
    public void initTest() {
        documento = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocumento() throws Exception {
        int databaseSizeBeforeCreate = documentoRepository.findAll().size();

        // Create the Documento
        DocumentoDTO documentoDTO = documentoMapper.documentoToDocumentoDTO(documento);

        restDocumentoMockMvc.perform(post("/api/documentos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(documentoDTO)))
                .andExpect(status().isCreated());

        // Validate the Documento in the database
        List<Documento> documentos = documentoRepository.findAll();
        assertThat(documentos).hasSize(databaseSizeBeforeCreate + 1);
        Documento testDocumento = documentos.get(documentos.size() - 1);
        assertThat(testDocumento.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testDocumento.getResumo()).isEqualTo(DEFAULT_RESUMO);
        assertThat(testDocumento.getDataCriacao()).isEqualTo(DEFAULT_DATA_CRIACAO);
        assertThat(testDocumento.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDocumento.getTipoDocumento()).isEqualTo(DEFAULT_TIPO_DOCUMENTO);
        assertThat(testDocumento.getTipoMonografia()).isEqualTo(DEFAULT_TIPO_MONOGRAFIA);
        assertThat(testDocumento.getArquivo()).isEqualTo(DEFAULT_ARQUIVO);
        assertThat(testDocumento.getArquivoContentType()).isEqualTo(DEFAULT_ARQUIVO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllDocumentos() throws Exception {
        // Initialize the database
        documentoRepository.saveAndFlush(documento);

        // Get all the documentos
        restDocumentoMockMvc.perform(get("/api/documentos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(documento.getId().intValue())))
                .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO.toString())))
                .andExpect(jsonPath("$.[*].resumo").value(hasItem(DEFAULT_RESUMO.toString())))
                .andExpect(jsonPath("$.[*].dataCriacao").value(hasItem(DEFAULT_DATA_CRIACAO.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
                .andExpect(jsonPath("$.[*].tipoDocumento").value(hasItem(DEFAULT_TIPO_DOCUMENTO.toString())))
                .andExpect(jsonPath("$.[*].tipoMonografia").value(hasItem(DEFAULT_TIPO_MONOGRAFIA.toString())))
                .andExpect(jsonPath("$.[*].arquivoContentType").value(hasItem(DEFAULT_ARQUIVO_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].arquivo").value(hasItem(Base64Utils.encodeToString(DEFAULT_ARQUIVO))));
    }

    @Test
    @Transactional
    public void getDocumento() throws Exception {
        // Initialize the database
        documentoRepository.saveAndFlush(documento);

        // Get the documento
        restDocumentoMockMvc.perform(get("/api/documentos/{id}", documento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(documento.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO.toString()))
            .andExpect(jsonPath("$.resumo").value(DEFAULT_RESUMO.toString()))
            .andExpect(jsonPath("$.dataCriacao").value(DEFAULT_DATA_CRIACAO.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.tipoDocumento").value(DEFAULT_TIPO_DOCUMENTO.toString()))
            .andExpect(jsonPath("$.tipoMonografia").value(DEFAULT_TIPO_MONOGRAFIA.toString()))
            .andExpect(jsonPath("$.arquivoContentType").value(DEFAULT_ARQUIVO_CONTENT_TYPE))
            .andExpect(jsonPath("$.arquivo").value(Base64Utils.encodeToString(DEFAULT_ARQUIVO)));
    }

    @Test
    @Transactional
    public void getNonExistingDocumento() throws Exception {
        // Get the documento
        restDocumentoMockMvc.perform(get("/api/documentos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocumento() throws Exception {
        // Initialize the database
        documentoRepository.saveAndFlush(documento);
        int databaseSizeBeforeUpdate = documentoRepository.findAll().size();

        // Update the documento
        Documento updatedDocumento = documentoRepository.findOne(documento.getId());
        updatedDocumento
                .titulo(UPDATED_TITULO)
                .resumo(UPDATED_RESUMO)
                .dataCriacao(UPDATED_DATA_CRIACAO)
                .status(UPDATED_STATUS)
                .tipoDocumento(UPDATED_TIPO_DOCUMENTO)
                .tipoMonografia(UPDATED_TIPO_MONOGRAFIA)
                .arquivo(UPDATED_ARQUIVO)
                .arquivoContentType(UPDATED_ARQUIVO_CONTENT_TYPE);
        DocumentoDTO documentoDTO = documentoMapper.documentoToDocumentoDTO(updatedDocumento);

        restDocumentoMockMvc.perform(put("/api/documentos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(documentoDTO)))
                .andExpect(status().isOk());

        // Validate the Documento in the database
        List<Documento> documentos = documentoRepository.findAll();
        assertThat(documentos).hasSize(databaseSizeBeforeUpdate);
        Documento testDocumento = documentos.get(documentos.size() - 1);
        assertThat(testDocumento.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testDocumento.getResumo()).isEqualTo(UPDATED_RESUMO);
        assertThat(testDocumento.getDataCriacao()).isEqualTo(UPDATED_DATA_CRIACAO);
        assertThat(testDocumento.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDocumento.getTipoDocumento()).isEqualTo(UPDATED_TIPO_DOCUMENTO);
        assertThat(testDocumento.getTipoMonografia()).isEqualTo(UPDATED_TIPO_MONOGRAFIA);
        assertThat(testDocumento.getArquivo()).isEqualTo(UPDATED_ARQUIVO);
        assertThat(testDocumento.getArquivoContentType()).isEqualTo(UPDATED_ARQUIVO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void deleteDocumento() throws Exception {
        // Initialize the database
        documentoRepository.saveAndFlush(documento);
        int databaseSizeBeforeDelete = documentoRepository.findAll().size();

        // Get the documento
        restDocumentoMockMvc.perform(delete("/api/documentos/{id}", documento.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Documento> documentos = documentoRepository.findAll();
        assertThat(documentos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
