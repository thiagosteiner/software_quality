package br.ufrj.cos.qsoftware.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import br.ufrj.cos.qsoftware.domain.enumeration.TipoAluno;

/**
 * A DTO for the Aluno entity.
 */
public class AlunoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String dre;

    private LocalDate previsaoFormatura;

    private TipoAluno tipo;


    private Long monografiaId;
    
    private Set<PublicacaoDTO> publicacaos = new HashSet<>();

    private Long professorId;
    
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
    public String getDre() {
        return dre;
    }

    public void setDre(String dre) {
        this.dre = dre;
    }
    public LocalDate getPrevisaoFormatura() {
        return previsaoFormatura;
    }

    public void setPrevisaoFormatura(LocalDate previsaoFormatura) {
        this.previsaoFormatura = previsaoFormatura;
    }
    public TipoAluno getTipo() {
        return tipo;
    }

    public void setTipo(TipoAluno tipo) {
        this.tipo = tipo;
    }

    public Long getMonografiaId() {
        return monografiaId;
    }

    public void setMonografiaId(Long monografiaId) {
        this.monografiaId = monografiaId;
    }

    public Set<PublicacaoDTO> getPublicacaos() {
        return publicacaos;
    }

    public void setPublicacaos(Set<PublicacaoDTO> publicacaos) {
        this.publicacaos = publicacaos;
    }

    public Long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Long professorId) {
        this.professorId = professorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AlunoDTO alunoDTO = (AlunoDTO) o;

        if ( ! Objects.equals(id, alunoDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AlunoDTO{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            ", dre='" + dre + "'" +
            ", previsaoFormatura='" + previsaoFormatura + "'" +
            ", tipo='" + tipo + "'" +
            '}';
    }
}
