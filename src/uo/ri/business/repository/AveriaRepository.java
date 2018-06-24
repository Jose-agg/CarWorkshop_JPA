package uo.ri.business.repository;

import java.util.List;

import uo.ri.model.Averia;

/**
 * Interfaz que declara los métodos que serán implementados por la clase de
 * persistencia asociada a la entidad Averia (AveriaJpaRepository)
 * 
 * @author José Antonio García García
 */
public interface AveriaRepository extends Repository<Averia> {

	List<Averia> findByIds(List<Long> idsAveria);

	List<Averia> findNoFacturadasByDni(String dni);

	List<Averia> findWithUnusedBono3ByClienteId(Long id);
}