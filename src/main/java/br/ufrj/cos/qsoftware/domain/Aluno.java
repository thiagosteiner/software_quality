package br.ufrj.cos.qsoftware.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Aluno.
 */
@Entity
@Table(name = "aluno")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Aluno implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "previsao_formatura")
    private LocalDate previsaoFormatura;

    @OneToOne
    @JoinColumn(unique = true)
    private Tese tese;

    @OneToMany(mappedBy = "aluno")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Proposta> propostas = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "aluno_publicacao",
               joinColumns = @JoinColumn(name="alunos_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="publicacaos_id", referencedColumnName="ID"))
    private Set<Publicacao> publicacaos = new HashSet<>();

    @ManyToOne
    private Professor professor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Aluno nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getPrevisaoFormatura() {
        return previsaoFormatura;
    }

    public Aluno previsaoFormatura(LocalDate previsaoFormatura) {
        this.previsaoFormatura = previsaoFormatura;
        return this;
    }

    public void setPrevisaoFormatura(LocalDate previsaoFormatura) {
        this.previsaoFormatura = previsaoFormatura;
    }

    public Tese getTese() {
        return tese;
    }

    public Aluno tese(Tese tese) {
        this.tese = tese;
        return this;
    }

    public void setTese(Tese tese) {
        this.tese = tese;
    }

    public Set<Proposta> getPropostas() {
        return propostas;
    }

    public Aluno propostas(Set<Proposta> propostas) {
        this.propostas = propostas;
        return this;
    }

    public Aluno addProposta(Proposta proposta) {
        propostas.add(proposta);
        proposta.setAluno(this);
        return this;
    }

    public Aluno removeProposta(Proposta proposta) {
        propostas.remove(proposta);
        proposta.setAluno(null);
        return this;
    }

    public void setPropostas(Set<Proposta> propostas) {
        this.propostas = propostas;
    }

    public Set<Publicacao> getPublicacaos() {
        return publicacaos;
    }

    public Aluno publicacaos(Set<Publicacao> publicacaos) {
        this.publicacaos = publicacaos;
        return this;
    }

    public Aluno addPublicacao(Publicacao publicacao) {
        publicacaos.add(publicacao);
        return this;
    }

    public Aluno removePublicacao(Publicacao publicacao) {
        publicacaos.remove(publicacao);
        return this;
    }

    public void setPublicacaos(Set<Publicacao> publicacaos) {
        this.publicacaos = publicacaos;
    }

    public Professor getProfessor() {
        return professor;
    }

    public Aluno professor(Professor professor) {
        this.professor = professor;
        return this;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Aluno aluno = (Aluno) o;
        if(aluno.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, aluno.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Aluno{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            ", previsaoFormatura='" + previsaoFormatura + "'" +
            '}';
    }
}
