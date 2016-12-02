package br.ufrj.cos.qsoftware.repository;

import br.ufrj.cos.qsoftware.domain.ConviteOrientador;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ConviteOrientador entity.
 */
@SuppressWarnings("unused")
public interface ConviteOrientadorRepository extends JpaRepository<ConviteOrientador,Long> {

}
