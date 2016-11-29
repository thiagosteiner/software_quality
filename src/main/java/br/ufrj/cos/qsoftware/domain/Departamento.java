package br.ufrj.cos.qsoftware.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Departamento.
 */
@Entity
@Table(name = "departamento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Departamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @OneToMany(mappedBy = "departamento")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Professor> departamentoprofessors = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Departamento nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Professor> getDepartamentoprofessors() {
        return departamentoprofessors;
    }

    public Departamento departamentoprofessors(Set<Professor> professors) {
        this.departamentoprofessors = professors;
        return this;
    }

    public Departamento addDepartamentoprofessor(Professor professor) {
        departamentoprofessors.add(professor);
        professor.setDepartamento(this);
        return this;
    }

    public Departamento removeDepartamentoprofessor(Professor professor) {
        departamentoprofessors.remove(professor);
        professor.setDepartamento(null);
        return this;
    }

    public void setDepartamentoprofessors(Set<Professor> professors) {
        this.departamentoprofessors = professors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Departamento departamento = (Departamento) o;
        if(departamento.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, departamento.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Departamento{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            '}';
    }
}
