package br.ufrj.cos.qsoftware.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
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

    @OneToOne
    @JoinColumn(unique = true)
    private Comite comite;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "documento_orientador",
               joinColumns = @JoinColumn(name="documentos_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="orientadors_id", referencedColumnName="ID"))
    private Set<Professor> orientadors = new HashSet<>();

    @ManyToMany(mappedBy = "documentos")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Aluno> alunos = new HashSet<>();

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

    public Set<Professor> getOrientadors() {
        return orientadors;
    }

    public Documento orientadors(Set<Professor> professors) {
        this.orientadors = professors;
        return this;
    }

    public Documento addOrientador(Professor professor) {
        orientadors.add(professor);
        professor.getDocumentosorientados().add(this);
        return this;
    }

    public Documento removeOrientador(Professor professor) {
        orientadors.remove(professor);
        professor.getDocumentosorientados().remove(this);
        return this;
    }

    public void setOrientadors(Set<Professor> professors) {
        this.orientadors = professors;
    }

    public Set<Aluno> getAlunos() {
        return alunos;
    }

    public Documento alunos(Set<Aluno> alunos) {
        this.alunos = alunos;
        return this;
    }

    public Documento addAluno(Aluno aluno) {
        alunos.add(aluno);
        aluno.getDocumentos().add(this);
        return this;
    }

    public Documento removeAluno(Aluno aluno) {
        alunos.remove(aluno);
        aluno.getDocumentos().remove(this);
        return this;
    }

    public void setAlunos(Set<Aluno> alunos) {
        this.alunos = alunos;
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
            ", tipoMonografia='" + tipoMonografia + "'" +
            ", arquivo='" + arquivo + "'" +
            ", arquivoContentType='" + arquivoContentType + "'" +
            '}';
    }
}
