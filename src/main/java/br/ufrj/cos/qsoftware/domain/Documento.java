package br.ufrj.cos.qsoftware.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import br.ufrj.cos.qsoftware.domain.enumeration.SituacaoPublicacao;

import br.ufrj.cos.qsoftware.domain.enumeration.TipoDocumento;

import br.ufrj.cos.qsoftware.domain.enumeration.TipoMonografia;

/**
 * A Documento.
 */
@Entity
@Table(name = "documento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Documento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "resumo")
    private String resumo;

    @Column(name = "data_criacao")
    private LocalDate dataCriacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SituacaoPublicacao status;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento")
    private TipoDocumento tipoDocumento;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_monografia")
    private TipoMonografia tipoMonografia;

    @Lob
    @Column(name = "arquivo")
    private byte[] arquivo;

    @Column(name = "arquivo_content_type")
    private String arquivoContentType;

    @ManyToOne
    private Aluno aluno;

    @OneToOne
    @JoinColumn(unique = true)
    private Comite documentocomite;

    @OneToOne
    @JoinColumn(unique = true)
    private Professor documentoorientador;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Documento titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getResumo() {
        return resumo;
    }

    public Documento resumo(String resumo) {
        this.resumo = resumo;
        return this;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public Documento dataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
        return this;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public SituacaoPublicacao getStatus() {
        return status;
    }

    public Documento status(SituacaoPublicacao status) {
        this.status = status;
        return this;
    }

    public void setStatus(SituacaoPublicacao status) {
        this.status = status;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public Documento tipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
        return this;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public TipoMonografia getTipoMonografia() {
        return tipoMonografia;
    }

    public Documento tipoMonografia(TipoMonografia tipoMonografia) {
        this.tipoMonografia = tipoMonografia;
        return this;
    }

    public void setTipoMonografia(TipoMonografia tipoMonografia) {
        this.tipoMonografia = tipoMonografia;
    }

    public byte[] getArquivo() {
        return arquivo;
    }

    public Documento arquivo(byte[] arquivo) {
        this.arquivo = arquivo;
        return this;
    }

    public void setArquivo(byte[] arquivo) {
        this.arquivo = arquivo;
    }

    public String getArquivoContentType() {
        return arquivoContentType;
    }

    public Documento arquivoContentType(String arquivoContentType) {
        this.arquivoContentType = arquivoContentType;
        return this;
    }

    public void setArquivoContentType(String arquivoContentType) {
        this.arquivoContentType = arquivoContentType;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public Documento aluno(Aluno aluno) {
        this.aluno = aluno;
        return this;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Comite getDocumentocomite() {
        return documentocomite;
    }

    public Documento documentocomite(Comite comite) {
        this.documentocomite = comite;
        return this;
    }

    public void setDocumentocomite(Comite comite) {
        this.documentocomite = comite;
    }

    public Professor getDocumentoorientador() {
        return documentoorientador;
    }

    public Documento documentoorientador(Professor professor) {
        this.documentoorientador = professor;
        return this;
    }

    public void setDocumentoorientador(Professor professor) {
        this.documentoorientador = professor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Documento documento = (Documento) o;
        if(documento.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, documento.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Documento{" +
            "id=" + id +
            ", titulo='" + titulo + "'" +
            ", resumo='" + resumo + "'" +
            ", dataCriacao='" + dataCriacao + "'" +
            ", status='" + status + "'" +
            ", tipoDocumento='" + tipoDocumento + "'" +
            ", tipoMonografia='" + tipoMonografia + "'" +
            ", arquivo='" + arquivo + "'" +
            ", arquivoContentType='" + arquivoContentType + "'" +
            '}';
    }
}
