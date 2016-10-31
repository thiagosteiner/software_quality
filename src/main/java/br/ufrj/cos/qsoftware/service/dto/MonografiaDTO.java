package br.ufrj.cos.qsoftware.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import br.ufrj.cos.qsoftware.domain.enumeration.Situacao;
import br.ufrj.cos.qsoftware.domain.enumeration.TipoMonografia;

/**
 * A DTO for the Monografia entity.
 */
public class MonografiaDTO implements Serializable {

    private Long id;

    private Situacao status;

    private TipoMonografia tipo;

    private String tema;


    private Long comiteId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Situacao getStatus() {
        return status;
    }

    public void setStatus(Situacao status) {
        this.status = status;
    }
    public TipoMonografia getTipo() {
        return tipo;
    }

    public void setTipo(TipoMonografia tipo) {
        this.tipo = tipo;
    }
    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public Long getComiteId() {
        return comiteId;
    }

    public void setComiteId(Long comiteId) {
        this.comiteId = comiteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MonografiaDTO monografiaDTO = (MonografiaDTO) o;

        if ( ! Objects.equals(id, monografiaDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MonografiaDTO{" +
            "id=" + id +
            ", status='" + status + "'" +
            ", tipo='" + tipo + "'" +
            ", tema='" + tema + "'" +
            '}';
    }
}
