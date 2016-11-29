package br.ufrj.cos.qsoftware.repository;

import br.ufrj.cos.qsoftware.domain.Convite;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Convite entity.
 */
@SuppressWarnings("unused")
public interface ConviteRepository extends JpaRepository<Convite,Long> {

}
