package br.ufrj.cos.qsoftware.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import br.ufrj.cos.qsoftware.domain.enumeration.Situacao;

/**
 * A DTO for the Tese entity.
 */
public class TeseDTO implements Serializable {

    private Long id;

    private Situacao status;


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

        TeseDTO teseDTO = (TeseDTO) o;

        if ( ! Objects.equals(id, teseDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TeseDTO{" +
            "id=" + id +
            ", status='" + status + "'" +
            '}';
    }
}
