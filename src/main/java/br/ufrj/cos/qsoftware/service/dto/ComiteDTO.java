package br.ufrj.cos.qsoftware.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Comite entity.
 */
public class ComiteDTO implements Serializable {

    private Long id;

    private String local;

    private LocalDate dataOcorrencia;

    private String ataComite;


    private Long documentoId;
    

    private String documentoTitulo;

    private Set<ProfessorDTO> professors = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
    public LocalDate getDataOcorrencia() {
        return dataOcorrencia;
    }

    public void setDataOcorrencia(LocalDate dataOcorrencia) {
        this.dataOcorrencia = dataOcorrencia;
    }
    public String getAtaComite() {
        return ataComite;
    }

    public void setAtaComite(String ataComite) {
        this.ataComite = ataComite;
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

    public Set<ProfessorDTO> getProfessors() {
        return professors;
    }

    public void setProfessors(Set<ProfessorDTO> professors) {
        this.professors = professors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ComiteDTO comiteDTO = (ComiteDTO) o;

        if ( ! Objects.equals(id, comiteDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ComiteDTO{" +
            "id=" + id +
            ", local='" + local + "'" +
            ", dataOcorrencia='" + dataOcorrencia + "'" +
            ", ataComite='" + ataComite + "'" +
            '}';
    }
}
