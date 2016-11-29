package br.ufrj.cos.qsoftware.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import br.ufrj.cos.qsoftware.domain.enumeration.SituacaoConvite;

import br.ufrj.cos.qsoftware.domain.enumeration.TipoConvite;

/**
 * A Convite.
 */
@Entity
@Table(name = "convite")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Convite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SituacaoConvite status;

    @Column(name = "data_criacao")
    private LocalDate dataCriacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_convite")
    private TipoConvite tipoConvite;

    @ManyToOne
    private Aluno aluno;

    @ManyToOne
    private Professor professor;

    @OneToOne
    @JoinColumn(unique = true)
    private Professor conviteprofessor;

    @OneToOne
    @JoinColumn(unique = true)
    private Comite conviteComite;

    @OneToOne
    @JoinColumn(unique = true)
    private Documento conviteDocumento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SituacaoConvite getStatus() {
        return status;
    }

    public Convite status(SituacaoConvite status) {
        this.status = status;
        return this;
    }

    public void setStatus(SituacaoConvite status) {
        this.status = status;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public Convite dataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
        return this;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public TipoConvite getTipoConvite() {
        return tipoConvite;
    }

    public Convite tipoConvite(TipoConvite tipoConvite) {
        this.tipoConvite = tipoConvite;
        return this;
    }

    public void setTipoConvite(TipoConvite tipoConvite) {
        this.tipoConvite = tipoConvite;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public Convite aluno(Aluno aluno) {
        this.aluno = aluno;
        return this;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Professor getProfessor() {
        return professor;
    }

    public Convite professor(Professor professor) {
        this.professor = professor;
        return this;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Professor getConviteprofessor() {
        return conviteprofessor;
    }

    public Convite conviteprofessor(Professor professor) {
        this.conviteprofessor = professor;
        return this;
    }

    public void setConviteprofessor(Professor professor) {
        this.conviteprofessor = professor;
    }

    public Comite getConviteComite() {
        return conviteComite;
    }

    public Convite conviteComite(Comite comite) {
        this.conviteComite = comite;
        return this;
    }

    public void setConviteComite(Comite comite) {
        this.conviteComite = comite;
    }

    public Documento getConviteDocumento() {
        return conviteDocumento;
    }

    public Convite conviteDocumento(Documento documento) {
        this.conviteDocumento = documento;
        return this;
    }

    public void setConviteDocumento(Documento documento) {
        this.conviteDocumento = documento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Convite convite = (Convite) o;
        if(convite.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, convite.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Convite{" +
            "id=" + id +
            ", status='" + status + "'" +
            ", dataCriacao='" + dataCriacao + "'" +
            ", tipoConvite='" + tipoConvite + "'" +
            '}';
    }
}
