package br.ufrj.cos.qsoftware.repository;

import br.ufrj.cos.qsoftware.domain.Monografia;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Monografia entity.
 */
@SuppressWarnings("unused")
public interface MonografiaRepository extends JpaRepository<Monografia,Long> {

}
