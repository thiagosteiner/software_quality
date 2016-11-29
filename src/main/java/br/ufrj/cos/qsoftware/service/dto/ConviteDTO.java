package br.ufrj.cos.qsoftware.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import br.ufrj.cos.qsoftware.domain.enumeration.SituacaoConvite;
import br.ufrj.cos.qsoftware.domain.enumeration.TipoConvite;

/**
 * A DTO for the Convite entity.
 */
public class ConviteDTO implements Serializable {

    private Long id;

    private SituacaoConvite status;

    private LocalDate dataCriacao;

    private TipoConvite tipoConvite;


    private Long alunoId;
    
    private Long professorId;
    
    private Long conviteprofessorId;
    
    private Long conviteComiteId;
    
    private Long conviteDocumentoId;
    
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
    public TipoConvite getTipoConvite() {
        return tipoConvite;
    }

    public void setTipoConvite(TipoConvite tipoConvite) {
        this.tipoConvite = tipoConvite;
    }

    public Long getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Long alunoId) {
        this.alunoId = alunoId;
    }

    public Long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Long professorId) {
        this.professorId = professorId;
    }

    public Long getConviteprofessorId() {
        return conviteprofessorId;
    }

    public void setConviteprofessorId(Long professorId) {
        this.conviteprofessorId = professorId;
    }

    public Long getConviteComiteId() {
        return conviteComiteId;
    }

    public void setConviteComiteId(Long comiteId) {
        this.conviteComiteId = comiteId;
    }

    public Long getConviteDocumentoId() {
        return conviteDocumentoId;
    }

    public void setConviteDocumentoId(Long documentoId) {
        this.conviteDocumentoId = documentoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConviteDTO conviteDTO = (ConviteDTO) o;

        if ( ! Objects.equals(id, conviteDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ConviteDTO{" +
            "id=" + id +
            ", status='" + status + "'" +
            ", dataCriacao='" + dataCriacao + "'" +
            ", tipoConvite='" + tipoConvite + "'" +
            '}';
    }
}
