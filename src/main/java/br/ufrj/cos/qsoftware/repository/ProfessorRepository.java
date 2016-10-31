package br.ufrj.cos.qsoftware.repository;

import br.ufrj.cos.qsoftware.domain.Professor;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Professor entity.
 */
@SuppressWarnings("unused")
public interface ProfessorRepository extends JpaRepository<Professor,Long> {

    @Query("select distinct professor from Professor professor left join fetch professor.orientadorpublicacaos")
    List<Professor> findAllWithEagerRelationships();

    @Query("select professor from Professor professor left join fetch professor.orientadorpublicacaos where professor.id =:id")
    Professor findOneWithEagerRelationships(@Param("id") Long id);

}
