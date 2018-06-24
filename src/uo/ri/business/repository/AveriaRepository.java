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

	/**
	 * Metodo que devuelve una lista de averias a partir de los identificadores
	 * 
	 * @param idsAveria Lista de identificadoresde averias
	 * @return lista de averias
	 */
	List<Averia> findByIds(List<Long> idsAveria);

	/**
	 * Metodo que devuelve la lista de averias no facturas de un cliente a 
	 * partir de su dni
	 * 
	 * @param dni DNI del cliente a buscar
	 * @return lista de averias no facturadas
	 */
	List<Averia> findNoFacturadasByDni(String dni);

	/**
	 * Metodo que devuelve las averias de un cliente que no hayan sido 
	 * utilizadas para la generacion de un bono
	 * 
	 * @param id Identificador del clientea buscar
	 * @return lista de averias de dicho cliente
	 */
	List<Averia> findWithUnusedBono3ByClienteId(Long id);
}