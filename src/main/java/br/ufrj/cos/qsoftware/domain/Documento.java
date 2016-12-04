package br.ufrj.cos.qsoftware.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import br.ufrj.cos.qsoftware.domain.enumeration.SituacaoAprovacao;

import br.ufrj.cos.qsoftware.domain.enumeration.TipoDocumento;

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
    private SituacaoAprovacao status;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento")
    private TipoDocumento tipoDocumento;

    @Lob
    @Column(name = "arquivo")
    private byte[] arquivo;

    @Column(name = "arquivo_content_type")
    private String arquivoContentType;

    @ManyToOne
    private Professor orientador;

    @ManyToOne
    private Aluno aluno;

    @OneToOne(mappedBy = "documento")
    @JsonIgnore
    private Comite comite;

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

    public SituacaoAprovacao getStatus() {
        return status;
    }

    public Documento status(SituacaoAprovacao status) {
        this.status = status;
        return this;
    }

    public void setStatus(SituacaoAprovacao status) {
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

    public Professor getOrientador() {
        return orientador;
    }

    public Documento orientador(Professor professor) {
        this.orientador = professor;
        return this;
    }

    public void setOrientador(Professor professor) {
        this.orientador = professor;
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

    public Comite getComite() {
        return comite;
    }

    public Documento comite(Comite comite) {
        this.comite = comite;
        return this;
    }

    public void setComite(Comite comite) {
        this.comite = comite;
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
        if (documento.id == null || id == null) {
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
            ", arquivo='" + arquivo + "'" +
            ", arquivoContentType='" + arquivoContentType + "'" +
            '}';
    }
}
