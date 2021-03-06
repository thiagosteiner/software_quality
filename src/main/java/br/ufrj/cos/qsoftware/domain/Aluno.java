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

import br.ufrj.cos.qsoftware.domain.enumeration.TipoAluno;

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

    @NotNull
    @Column(name = "dre", nullable = false)
    private String dre;

    @Column(name = "data_ingresso")
    private LocalDate dataIngresso;

    @Column(name = "previsao_formatura")
    private LocalDate previsaoFormatura;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoAluno tipo;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "aluno")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Documento> documentos = new HashSet<>();

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

    public String getDre() {
        return dre;
    }

    public Aluno dre(String dre) {
        this.dre = dre;
        return this;
    }

    public void setDre(String dre) {
        this.dre = dre;
    }

    public LocalDate getDataIngresso() {
        return dataIngresso;
    }

    public Aluno dataIngresso(LocalDate dataIngresso) {
        this.dataIngresso = dataIngresso;
        return this;
    }

    public void setDataIngresso(LocalDate dataIngresso) {
        this.dataIngresso = dataIngresso;
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

    public TipoAluno getTipo() {
        return tipo;
    }

    public Aluno tipo(TipoAluno tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoAluno tipo) {
        this.tipo = tipo;
    }

    public User getUser() {
        return user;
    }

    public Aluno user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Documento> getDocumentos() {
        return documentos;
    }

    public Aluno documentos(Set<Documento> documentos) {
        this.documentos = documentos;
        return this;
    }

    public Aluno addDocumento(Documento documento) {
        documentos.add(documento);
        documento.setAluno(this);
        return this;
    }

    public Aluno removeDocumento(Documento documento) {
        documentos.remove(documento);
        documento.setAluno(null);
        return this;
    }

    public void setDocumentos(Set<Documento> documentos) {
        this.documentos = documentos;
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
        if (aluno.id == null || id == null) {
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
            ", dre='" + dre + "'" +
            ", dataIngresso='" + dataIngresso + "'" +
            ", previsaoFormatura='" + previsaoFormatura + "'" +
            ", tipo='" + tipo + "'" +
            '}';
    }
}
