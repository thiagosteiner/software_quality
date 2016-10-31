package br.ufrj.cos.qsoftware.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import br.ufrj.cos.qsoftware.domain.enumeration.Situacao;

/**
 * A Tese.
 */
@Entity
@Table(name = "tese")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tese implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Situacao status;

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

    public Tese status(Situacao status) {
        this.status = status;
        return this;
    }

    public void setStatus(Situacao status) {
        this.status = status;
    }

    public Comite getComite() {
        return comite;
    }

    public Tese comite(Comite comite) {
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
        Tese tese = (Tese) o;
        if(tese.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tese.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tese{" +
            "id=" + id +
            ", status='" + status + "'" +
            '}';
    }
}
