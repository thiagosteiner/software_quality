package br.ufrj.cos.qsoftware.repository;

import br.ufrj.cos.qsoftware.domain.Tese;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Tese entity.
 */
@SuppressWarnings("unused")
public interface TeseRepository extends JpaRepository<Tese,Long> {

}
