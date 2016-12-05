package br.ufrj.cos.qsoftware.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

import br.ufrj.cos.qsoftware.domain.enumeration.SituacaoAprovacao;
import br.ufrj.cos.qsoftware.domain.enumeration.TipoDocumento;

/**
 * A DTO for the Documento entity.
 */
public class DocumentoDTO implements Serializable {

    private Long id;

    private String titulo;

    private String resumo;

    private LocalDate dataCriacao;

    private SituacaoAprovacao status;

    private TipoDocumento tipoDocumento;

    @Lob
    private byte[] arquivo;

    private String arquivoContentType;

    private Long orientadorId;
    

    private String orientadorNome;

    private Long alunoId;
    

    private String alunoNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }
    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    public SituacaoAprovacao getStatus() {
        return status;
    }

    public void setStatus(SituacaoAprovacao status) {
        this.status = status;
    }
    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
    public byte[] getArquivo() {
        return arquivo;
    }

    public void setArquivo(byte[] arquivo) {
        this.arquivo = arquivo;
    }

    public String getArquivoContentType() {
        return arquivoContentType;
    }

    public void setArquivoContentType(String arquivoContentType) {
        this.arquivoContentType = arquivoContentType;
    }

    public Long getOrientadorId() {
        return orientadorId;
    }

    public void setOrientadorId(Long professorId) {
        this.orientadorId = professorId;
    }
    
    public void setOrientadorNull() {
        this.orientadorId = null;
    }


    public String getOrientadorNome() {
        return orientadorNome;
    }

    public void setOrientadorNome(String professorNome) {
        this.orientadorNome = professorNome;
    }

    public Long getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Long alunoId) {
        this.alunoId = alunoId;
    }


    public String getAlunoNome() {
        return alunoNome;
    }

    public void setAlunoNome(String alunoNome) {
        this.alunoNome = alunoNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DocumentoDTO documentoDTO = (DocumentoDTO) o;

        if ( ! Objects.equals(id, documentoDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DocumentoDTO{" +
            "id=" + id +
            ", titulo='" + titulo + "'" +
            ", resumo='" + resumo + "'" +
            ", dataCriacao='" + dataCriacao + "'" +
            ", status='" + status + "'" +
            ", tipoDocumento='" + tipoDocumento + "'" +
            ", arquivo='" + arquivo + "'" +
            '}';
    }
}
