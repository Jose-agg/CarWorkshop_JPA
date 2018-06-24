package uo.ri.business.repository;

import java.util.List;

import uo.ri.model.Cliente;

/**
 * Interfaz que declara los métodos que serán implementados por la clase de
 * persistencia asociada a la entidad Cliente (ClienteJpaRepository)
 * 
 * @author José Antonio García García
 */
public interface ClienteRepository extends Repository<Cliente> {

	Cliente findByDni(String dni);

	List<Cliente> findWithRecomendations();

	List<Cliente> findWithThreeUnusedBreakdowns();

	List<Cliente> findRecomendedBy(Long id);
}
