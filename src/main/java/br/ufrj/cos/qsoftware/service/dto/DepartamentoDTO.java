package br.ufrj.cos.qsoftware.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Departamento entity.
 */
public class DepartamentoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DepartamentoDTO departamentoDTO = (DepartamentoDTO) o;

        if ( ! Objects.equals(id, departamentoDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DepartamentoDTO{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            '}';
    }
}
