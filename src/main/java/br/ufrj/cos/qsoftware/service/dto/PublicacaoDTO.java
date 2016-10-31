package br.ufrj.cos.qsoftware.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Publicacao entity.
 */
public class PublicacaoDTO implements Serializable {

    private Long id;

    private LocalDate dataPublicacao;

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

    public void setDataPublicacao(LocalDate dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }
    public LocalDate getDataApresentacao() {
        return dataApresentacao;
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

        PublicacaoDTO publicacaoDTO = (PublicacaoDTO) o;

        if ( ! Objects.equals(id, publicacaoDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PublicacaoDTO{" +
            "id=" + id +
            ", dataPublicacao='" + dataPublicacao + "'" +
            ", dataApresentacao='" + dataApresentacao + "'" +
            '}';
    }
}
