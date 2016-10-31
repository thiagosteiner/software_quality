package br.ufrj.cos.qsoftware.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Aluno entity.
 */
public class AlunoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    private LocalDate previsaoFormatura;


    private Long teseId;
    
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
    public LocalDate getPrevisaoFormatura() {
        return previsaoFormatura;
    }

    public void setPrevisaoFormatura(LocalDate previsaoFormatura) {
        this.previsaoFormatura = previsaoFormatura;
    }

    public Long getTeseId() {
        return teseId;
    }

    public void setTeseId(Long teseId) {
        this.teseId = teseId;
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
            ", previsaoFormatura='" + previsaoFormatura + "'" +
            '}';
    }
}
