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
    @Column(name = "codigo", nullable = false)
    private String codigo;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @ManyToOne
    private Departamento departamento;

    @ManyToMany(mappedBy = "professors")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Comite> comites = new HashSet<>();

    @ManyToMany(mappedBy = "orientadors")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Documento> documentosorientados = new HashSet<>();

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

    public String getCodigo() {
        return codigo;
    }

    public Professor codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public User getUser() {
        return user;
    }

    public Professor user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Set<Comite> getComites() {
        return comites;
    }

    public Professor comites(Set<Comite> comites) {
        this.comites = comites;
        return this;
    }

    public Professor addComite(Comite comite) {
        comites.add(comite);
        comite.getProfessors().add(this);
        return this;
    }

    public Professor removeComite(Comite comite) {
        comites.remove(comite);
        comite.getProfessors().remove(this);
        return this;
    }

    public void setComites(Set<Comite> comites) {
        this.comites = comites;
    }

    public Set<Documento> getDocumentosorientados() {
        return documentosorientados;
    }

    public Professor documentosorientados(Set<Documento> documentos) {
        this.documentosorientados = documentos;
        return this;
    }

    public Professor addDocumentosorientados(Documento documento) {
        documentosorientados.add(documento);
        documento.getOrientadors().add(this);
        return this;
    }

    public Professor removeDocumentosorientados(Documento documento) {
        documentosorientados.remove(documento);
        documento.getOrientadors().remove(this);
        return this;
    }

    public void setDocumentosorientados(Set<Documento> documentos) {
        this.documentosorientados = documentos;
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
        if (professor.id == null || id == null) {
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
            ", codigo='" + codigo + "'" +
            '}';
    }
}
