package br.ufrj.cos.qsoftware.repository;

import br.ufrj.cos.qsoftware.domain.Aluno;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Aluno entity.
 */
@SuppressWarnings("unused")
public interface AlunoRepository extends JpaRepository<Aluno,Long> {

    @Query("select distinct aluno from Aluno aluno left join fetch aluno.publicacaos")
    List<Aluno> findAllWithEagerRelationships();

    @Query("select aluno from Aluno aluno left join fetch aluno.publicacaos where aluno.id =:id")
    Aluno findOneWithEagerRelationships(@Param("id") Long id);

}
