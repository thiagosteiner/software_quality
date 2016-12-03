package br.ufrj.cos.qsoftware.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import br.ufrj.cos.qsoftware.domain.enumeration.SituacaoConvite;

/**
 * A DTO for the ConviteComite entity.
 */
public class ConviteComiteDTO implements Serializable {

    private Long id;

    private SituacaoConvite status;

    private LocalDate dataCriacao;


    private Long professorconvidadocomiteId;
    

    private String professorconvidadocomiteCodigo;

    private Long comiteId;
    
    private Long orientadorqueconvidouId;
    

    private String orientadorqueconvidouCodigo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public SituacaoConvite getStatus() {
        return status;
    }

    public void setStatus(SituacaoConvite status) {
        this.status = status;
    }
    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Long getProfessorconvidadocomiteId() {
        return professorconvidadocomiteId;
    }

    public void setProfessorconvidadocomiteId(Long professorId) {
        this.professorconvidadocomiteId = professorId;
    }


    public String getProfessorconvidadocomiteCodigo() {
        return professorconvidadocomiteCodigo;
    }

    public void setProfessorconvidadocomiteCodigo(String professorCodigo) {
        this.professorconvidadocomiteCodigo = professorCodigo;
    }

    public Long getComiteId() {
        return comiteId;
    }

    public void setComiteId(Long comiteId) {
        this.comiteId = comiteId;
    }

    public Long getOrientadorqueconvidouId() {
        return orientadorqueconvidouId;
    }

    public void setOrientadorqueconvidouId(Long professorId) {
        this.orientadorqueconvidouId = professorId;
    }


    public String getOrientadorqueconvidouCodigo() {
        return orientadorqueconvidouCodigo;
    }

    public void setOrientadorqueconvidouCodigo(String professorCodigo) {
        this.orientadorqueconvidouCodigo = professorCodigo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConviteComiteDTO conviteComiteDTO = (ConviteComiteDTO) o;

        if ( ! Objects.equals(id, conviteComiteDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ConviteComiteDTO{" +
            "id=" + id +
            ", status='" + status + "'" +
            ", dataCriacao='" + dataCriacao + "'" +
            '}';
    }
}
