package br.ufrj.cos.qsoftware.repository;

import br.ufrj.cos.qsoftware.domain.Comite;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Comite entity.
 */
@SuppressWarnings("unused")
public interface ComiteRepository extends JpaRepository<Comite,Long> {

    @Query("select distinct comite from Comite comite left join fetch comite.professors")
    List<Comite> findAllWithEagerRelationships();

    @Query("select comite from Comite comite left join fetch comite.professors where comite.id =:id")
    Comite findOneWithEagerRelationships(@Param("id") Long id);

}
