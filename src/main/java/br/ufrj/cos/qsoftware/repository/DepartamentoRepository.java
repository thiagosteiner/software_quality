package br.ufrj.cos.qsoftware.repository;

import br.ufrj.cos.qsoftware.domain.Departamento;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Departamento entity.
 */
@SuppressWarnings("unused")
public interface DepartamentoRepository extends JpaRepository<Departamento,Long> {

}
