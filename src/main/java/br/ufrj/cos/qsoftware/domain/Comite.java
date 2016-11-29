package br.ufrj.cos.qsoftware.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import br.ufrj.cos.qsoftware.domain.enumeration.TipoComite;

/**
 * A Comite.
 */
@Entity
@Table(name = "comite")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Comite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "local")
    private String local;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoComite tipo;

    @Column(name = "data_ocorrencia")
    private LocalDate dataOcorrencia;

    @Column(name = "ata_comite")
    private String ataComite;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "comite_comiteprofessor",
               joinColumns = @JoinColumn(name="comites_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="comiteprofessors_id", referencedColumnName="ID"))
    private Set<Professor> comiteprofessors = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocal() {
        return local;
    }

    public Comite local(String local) {
        this.local = local;
        return this;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public TipoComite getTipo() {
        return tipo;
    }

    public Comite tipo(TipoComite tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoComite tipo) {
        this.tipo = tipo;
    }

    public LocalDate getDataOcorrencia() {
        return dataOcorrencia;
    }

    public Comite dataOcorrencia(LocalDate dataOcorrencia) {
        this.dataOcorrencia = dataOcorrencia;
        return this;
    }

    public void setDataOcorrencia(LocalDate dataOcorrencia) {
        this.dataOcorrencia = dataOcorrencia;
    }

    public String getAtaComite() {
        return ataComite;
    }

    public Comite ataComite(String ataComite) {
        this.ataComite = ataComite;
        return this;
    }

    public void setAtaComite(String ataComite) {
        this.ataComite = ataComite;
    }

    public Set<Professor> getComiteprofessors() {
        return comiteprofessors;
    }

    public Comite comiteprofessors(Set<Professor> professors) {
        this.comiteprofessors = professors;
        return this;
    }

    public Comite addComiteprofessor(Professor professor) {
        comiteprofessors.add(professor);
        return this;
    }

    public Comite removeComiteprofessor(Professor professor) {
        comiteprofessors.remove(professor);
        return this;
    }

    public void setComiteprofessors(Set<Professor> professors) {
        this.comiteprofessors = professors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comite comite = (Comite) o;
        if(comite.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, comite.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Comite{" +
            "id=" + id +
            ", local='" + local + "'" +
            ", tipo='" + tipo + "'" +
            ", dataOcorrencia='" + dataOcorrencia + "'" +
            ", ataComite='" + ataComite + "'" +
            '}';
    }
}
