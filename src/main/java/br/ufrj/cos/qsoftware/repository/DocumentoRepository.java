package br.ufrj.cos.qsoftware.repository;

import br.ufrj.cos.qsoftware.domain.Documento;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Documento entity.
 */
@SuppressWarnings("unused")
public interface DocumentoRepository extends JpaRepository<Documento,Long> {

}
