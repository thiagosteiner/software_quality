package br.ufrj.cos.qsoftware.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Publicacao.
 */
@Entity
@Table(name = "publicacao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Publicacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "data_publicacao")
    private LocalDate dataPublicacao;

    @Column(name = "data_apresentacao")
    private LocalDate dataApresentacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataPublicacao() {
        return dataPublicacao;
    }

    public Publicacao dataPublicacao(LocalDate dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
        return this;
    }

    public void setDataPublicacao(LocalDate dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public LocalDate getDataApresentacao() {
        return dataApresentacao;
    }

    public Publicacao dataApresentacao(LocalDate dataApresentacao) {
        this.dataApresentacao = dataApresentacao;
        return this;
    }

    public void setDataApresentacao(LocalDate dataApresentacao) {
        this.dataApresentacao = dataApresentacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Publicacao publicacao = (Publicacao) o;
        if(publicacao.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, publicacao.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Publicacao{" +
            "id=" + id +
            ", dataPublicacao='" + dataPublicacao + "'" +
            ", dataApresentacao='" + dataApresentacao + "'" +
            '}';
    }
}
