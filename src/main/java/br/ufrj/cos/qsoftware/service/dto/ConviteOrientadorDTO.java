package br.ufrj.cos.qsoftware.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import br.ufrj.cos.qsoftware.domain.enumeration.SituacaoConvite;

/**
 * A DTO for the ConviteOrientador entity.
 */
public class ConviteOrientadorDTO implements Serializable {

    private Long id;

    private SituacaoConvite status;

    private LocalDate dataCriacao;


    private Long orientadorconvidadoId;
    

    private String orientadorconvidadoCodigo;

    private Long documentoId;
    

    private String documentoTitulo;

    private Long alunoqueconvidouId;
    

    private String alunoqueconvidouDre;

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

    public Long getOrientadorconvidadoId() {
        return orientadorconvidadoId;
    }

    public void setOrientadorconvidadoId(Long professorId) {
        this.orientadorconvidadoId = professorId;
    }


    public String getOrientadorconvidadoCodigo() {
        return orientadorconvidadoCodigo;
    }

    public void setOrientadorconvidadoCodigo(String professorCodigo) {
        this.orientadorconvidadoCodigo = professorCodigo;
    }

    public Long getDocumentoId() {
        return documentoId;
    }

    public void setDocumentoId(Long documentoId) {
        this.documentoId = documentoId;
    }


    public String getDocumentoTitulo() {
        return documentoTitulo;
    }

    public void setDocumentoTitulo(String documentoTitulo) {
        this.documentoTitulo = documentoTitulo;
    }

    public Long getAlunoqueconvidouId() {
        return alunoqueconvidouId;
    }

    public void setAlunoqueconvidouId(Long alunoId) {
        this.alunoqueconvidouId = alunoId;
    }


    public String getAlunoqueconvidouDre() {
        return alunoqueconvidouDre;
    }

    public void setAlunoqueconvidouDre(String alunoDre) {
        this.alunoqueconvidouDre = alunoDre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConviteOrientadorDTO conviteOrientadorDTO = (ConviteOrientadorDTO) o;

        if ( ! Objects.equals(id, conviteOrientadorDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ConviteOrientadorDTO{" +
            "id=" + id +
            ", status='" + status + "'" +
            ", dataCriacao='" + dataCriacao + "'" +
            '}';
    }
}
