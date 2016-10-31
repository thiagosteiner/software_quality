package br.ufrj.cos.qsoftware.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import br.ufrj.cos.qsoftware.domain.enumeration.Situacao;

/**
 * A Proposta.
 */
@Entity
@Table(name = "proposta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Proposta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Situacao status;

    @Column(name = "data_apresentacao")
    private LocalDate dataApresentacao;

    @ManyToOne
    private Aluno aluno;

    @OneToOne
    @JoinColumn(unique = true)
    private Comite comite;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Situacao getStatus() {
        return status;
    }

    public Proposta status(Situacao status) {
        this.status = status;
        return this;
    }

    public void setStatus(Situacao status) {
        this.status = status;
    }

    public LocalDate getDataApresentacao() {
        return dataApresentacao;
    }

    public Proposta dataApresentacao(LocalDate dataApresentacao) {
        this.dataApresentacao = dataApresentacao;
        return this;
    }

    public void setDataApresentacao(LocalDate dataApresentacao) {
        this.dataApresentacao = dataApresentacao;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public Proposta aluno(Aluno aluno) {
        this.aluno = aluno;
        return this;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Comite getComite() {
        return comite;
    }

    public Proposta comite(Comite comite) {
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
        Proposta proposta = (Proposta) o;
        if(proposta.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, proposta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Proposta{" +
            "id=" + id +
            ", status='" + status + "'" +
            ", dataApresentacao='" + dataApresentacao + "'" +
            '}';
    }
}
