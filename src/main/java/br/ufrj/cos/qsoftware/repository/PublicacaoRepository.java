package br.ufrj.cos.qsoftware.repository;

import br.ufrj.cos.qsoftware.domain.Publicacao;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Publicacao entity.
 */
@SuppressWarnings("unused")
public interface PublicacaoRepository extends JpaRepository<Publicacao,Long> {

}
