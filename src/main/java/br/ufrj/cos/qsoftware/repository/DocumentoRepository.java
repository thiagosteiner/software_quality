package br.ufrj.cos.qsoftware.repository;

import br.ufrj.cos.qsoftware.domain.Documento;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Documento entity.
 */
@SuppressWarnings("unused")
public interface DocumentoRepository extends JpaRepository<Documento,Long> {

    @Query("select distinct documento from Documento documento left join fetch documento.orientadors")
    List<Documento> findAllWithEagerRelationships();

    @Query("select documento from Documento documento left join fetch documento.orientadors where documento.id =:id")
    Documento findOneWithEagerRelationships(@Param("id") Long id);

}
