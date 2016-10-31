package br.ufrj.cos.qsoftware.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import br.ufrj.cos.qsoftware.domain.enumeration.Situacao;

import br.ufrj.cos.qsoftware.domain.enumeration.TipoMonografia;

/**
 * A Monografia.
 */
@Entity
@Table(name = "monografia")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Monografia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Situacao status;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoMonografia tipo;

    @Column(name = "tema")
    private String tema;

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

    public Monografia status(Situacao status) {
        this.status = status;
        return this;
    }

    public void setStatus(Situacao status) {
        this.status = status;
    }

    public TipoMonografia getTipo() {
        return tipo;
    }

    public Monografia tipo(TipoMonografia tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoMonografia tipo) {
        this.tipo = tipo;
    }

    public String getTema() {
        return tema;
    }

    public Monografia tema(String tema) {
        this.tema = tema;
        return this;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public Comite getComite() {
        return comite;
    }

    public Monografia comite(Comite comite) {
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
        Monografia monografia = (Monografia) o;
        if(monografia.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, monografia.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Monografia{" +
            "id=" + id +
            ", status='" + status + "'" +
            ", tipo='" + tipo + "'" +
            ", tema='" + tema + "'" +
            '}';
    }
}
