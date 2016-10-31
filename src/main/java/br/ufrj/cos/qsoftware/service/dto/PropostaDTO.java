package br.ufrj.cos.qsoftware.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import br.ufrj.cos.qsoftware.domain.enumeration.Situacao;

/**
 * A DTO for the Proposta entity.
 */
public class PropostaDTO implements Serializable {

    private Long id;

    private Situacao status;

    private LocalDate dataApresentacao;


    private Long alunoId;
    
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
    public LocalDate getDataApresentacao() {
        return dataApresentacao;
    }

    public void setDataApresentacao(LocalDate dataApresentacao) {
        this.dataApresentacao = dataApresentacao;
    }

    public Long getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Long alunoId) {
        this.alunoId = alunoId;
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

        PropostaDTO propostaDTO = (PropostaDTO) o;

        if ( ! Objects.equals(id, propostaDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PropostaDTO{" +
            "id=" + id +
            ", status='" + status + "'" +
            ", dataApresentacao='" + dataApresentacao + "'" +
            '}';
    }
}
