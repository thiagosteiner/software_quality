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
 * A Professor.
 */
@Entity
@Table(name = "professor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Professor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "senha", nullable = false)
    private String senha;

    @OneToMany(mappedBy = "professor")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Convite> orientadorconviteprofessorcomites = new HashSet<>();

    @ManyToOne
    private Departamento departamento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Professor nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public Professor senha(String senha) {
        this.senha = senha;
        return this;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Set<Convite> getOrientadorconviteprofessorcomites() {
        return orientadorconviteprofessorcomites;
    }

    public Professor orientadorconviteprofessorcomites(Set<Convite> convites) {
        this.orientadorconviteprofessorcomites = convites;
        return this;
    }

    public Professor addOrientadorconviteprofessorcomite(Convite convite) {
        orientadorconviteprofessorcomites.add(convite);
        convite.setProfessor(this);
        return this;
    }

    public Professor removeOrientadorconviteprofessorcomite(Convite convite) {
        orientadorconviteprofessorcomites.remove(convite);
        convite.setProfessor(null);
        return this;
    }

    public void setOrientadorconviteprofessorcomites(Set<Convite> convites) {
        this.orientadorconviteprofessorcomites = convites;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public Professor departamento(Departamento departamento) {
        this.departamento = departamento;
        return this;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Professor professor = (Professor) o;
        if(professor.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, professor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Professor{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            ", senha='" + senha + "'" +
            '}';
    }
}
