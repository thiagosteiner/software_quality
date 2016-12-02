package br.ufrj.cos.qsoftware.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import br.ufrj.cos.qsoftware.domain.enumeration.SituacaoConvite;

/**
 * A ConviteComite.
 */
@Entity
@Table(name = "convite_comite")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ConviteComite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SituacaoConvite status;

    @Column(name = "data_criacao")
    private LocalDate dataCriacao;

    @OneToOne
    @JoinColumn(unique = true)
    private Professor professorconvidadocomite;

    @OneToOne
    @JoinColumn(unique = true)
    private Comite comite;

    @OneToOne
    @JoinColumn(unique = true)
    private Documento documento;

    @ManyToOne
    private Professor orientadorqueconvidou;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SituacaoConvite getStatus() {
        return status;
    }

    public ConviteComite status(SituacaoConvite status) {
        this.status = status;
        return this;
    }

    public void setStatus(SituacaoConvite status) {
        this.status = status;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public ConviteComite dataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
        return this;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Professor getProfessorconvidadocomite() {
        return professorconvidadocomite;
    }

    public ConviteComite professorconvidadocomite(Professor professor) {
        this.professorconvidadocomite = professor;
        return this;
    }

    public void setProfessorconvidadocomite(Professor professor) {
        this.professorconvidadocomite = professor;
    }

    public Comite getComite() {
        return comite;
    }

    public ConviteComite comite(Comite comite) {
        this.comite = comite;
        return this;
    }

    public void setComite(Comite comite) {
        this.comite = comite;
    }

    public Documento getDocumento() {
        return documento;
    }

    public ConviteComite documento(Documento documento) {
        this.documento = documento;
        return this;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public Professor getOrientadorqueconvidou() {
        return orientadorqueconvidou;
    }

    public ConviteComite orientadorqueconvidou(Professor professor) {
        this.orientadorqueconvidou = professor;
        return this;
    }

    public void setOrientadorqueconvidou(Professor professor) {
        this.orientadorqueconvidou = professor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ConviteComite conviteComite = (ConviteComite) o;
        if (conviteComite.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, conviteComite.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ConviteComite{" +
            "id=" + id +
            ", status='" + status + "'" +
            ", dataCriacao='" + dataCriacao + "'" +
            '}';
    }
}
