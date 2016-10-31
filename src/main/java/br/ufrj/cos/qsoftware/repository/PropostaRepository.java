package br.ufrj.cos.qsoftware.repository;

import br.ufrj.cos.qsoftware.domain.Proposta;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Proposta entity.
 */
@SuppressWarnings("unused")
public interface PropostaRepository extends JpaRepository<Proposta,Long> {

}
