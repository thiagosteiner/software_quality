package br.ufrj.cos.qsoftware.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import br.ufrj.cos.qsoftware.domain.enumeration.SituacaoConvite;

/**
 * A ConviteOrientador.
 */
@Entity
@Table(name = "convite_orientador")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ConviteOrientador implements Serializable {

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
    private Professor orientadorconvidado;

    @OneToOne
    @JoinColumn(unique = true)
    private Documento documento;

    @ManyToOne
    private Aluno alunoqueconvidou;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SituacaoConvite getStatus() {
        return status;
    }

    public ConviteOrientador status(SituacaoConvite status) {
        this.status = status;
        return this;
    }

    public void setStatus(SituacaoConvite status) {
        this.status = status;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public ConviteOrientador dataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
        return this;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Professor getOrientadorconvidado() {
        return orientadorconvidado;
    }

    public ConviteOrientador orientadorconvidado(Professor professor) {
        this.orientadorconvidado = professor;
        return this;
    }

    public void setOrientadorconvidado(Professor professor) {
        this.orientadorconvidado = professor;
    }

    public Documento getDocumento() {
        return documento;
    }

    public ConviteOrientador documento(Documento documento) {
        this.documento = documento;
        return this;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public Aluno getAlunoqueconvidou() {
        return alunoqueconvidou;
    }

    public ConviteOrientador alunoqueconvidou(Aluno aluno) {
        this.alunoqueconvidou = aluno;
        return this;
    }

    public void setAlunoqueconvidou(Aluno aluno) {
        this.alunoqueconvidou = aluno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ConviteOrientador conviteOrientador = (ConviteOrientador) o;
        if (conviteOrientador.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, conviteOrientador.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ConviteOrientador{" +
            "id=" + id +
            ", status='" + status + "'" +
            ", dataCriacao='" + dataCriacao + "'" +
            '}';
    }
}
