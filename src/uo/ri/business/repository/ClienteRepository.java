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

	/**
	 * Metodo que busca un cliente a partir de su dni
	 * 
	 * @param dni DNI del cliente a buscar
	 * @return cliente encontrado
	 */
	Cliente findByDni(String dni);

	/**
	 * Metodo que devuelve la lista de usuarios que han recomendado a otros
	 * usuarios
	 * 
	 * @return lista de clientes recomendadores
	 */
	List<Cliente> findWithRecomendations();

	/**
	 * Metodo que devuelve la lista de clientes que hayan tenido al menos tres
	 * averias que no hayan sido utilizadas para la generacion de bonos
	 * 
	 * @return lista de cliente encontrados
	 */
	List<Cliente> findWithThreeUnusedBreakdowns();

	/**
	 * Metodo que devuelve la lista de usuarios que han sido recomendados por
	 * un cliente en particular
	 * 
	 * @param id Identificador del cliente a buscar
	 * @return lista de clientes que ha recomendado
	 */
	List<Cliente> findRecomendedBy(Long id);
}
