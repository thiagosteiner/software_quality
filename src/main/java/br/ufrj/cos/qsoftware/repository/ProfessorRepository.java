package br.ufrj.cos.qsoftware.repository;

import br.ufrj.cos.qsoftware.domain.Professor;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Professor entity.
 */
@SuppressWarnings("unused")
public interface ProfessorRepository extends JpaRepository<Professor,Long> {

}
