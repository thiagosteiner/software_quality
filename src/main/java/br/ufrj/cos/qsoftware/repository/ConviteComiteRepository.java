package br.ufrj.cos.qsoftware.repository;

import br.ufrj.cos.qsoftware.domain.ConviteComite;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ConviteComite entity.
 */
@SuppressWarnings("unused")
public interface ConviteComiteRepository extends JpaRepository<ConviteComite,Long> {

}
