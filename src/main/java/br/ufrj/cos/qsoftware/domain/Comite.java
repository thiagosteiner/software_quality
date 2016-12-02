package br.ufrj.cos.qsoftware.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JoinTable(name = "comite_professor",
               joinColumns = @JoinColumn(name="comites_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="professors_id", referencedColumnName="ID"))
    private Set<Professor> professors = new HashSet<>();

    @OneToOne(mappedBy = "comite")
    @JsonIgnore
    private Documento documento;

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

    public Set<Professor> getProfessors() {
        return professors;
    }

    public Comite professors(Set<Professor> professors) {
        this.professors = professors;
        return this;
    }

    public Comite addProfessor(Professor professor) {
        professors.add(professor);
        professor.getComites().add(this);
        return this;
    }

    public Comite removeProfessor(Professor professor) {
        professors.remove(professor);
        professor.getComites().remove(this);
        return this;
    }

    public void setProfessors(Set<Professor> professors) {
        this.professors = professors;
    }

    public Documento getDocumento() {
        return documento;
    }

    public Comite documento(Documento documento) {
        this.documento = documento;
        return this;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
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
        if (comite.id == null || id == null) {
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
