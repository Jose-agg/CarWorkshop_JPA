package uo.ri.business.repository;

import uo.ri.model.Mecanico;

/**
 * Interfaz que declara los métodos que serán implementados por la clase de
 * persistencia asociada a la entidad Mecanico (MecanicoJpaRepository)
 * 
 * @author José Antonio García García
 */
public interface MecanicoRepository extends Repository<Mecanico> {

	/**
	 * Metodo que devuelve un mecanico a partir de su dni
	 * 
	 * @param dni DNI del mecanico a buscar
	 * @return mecanico encontrado
	 */
	Mecanico findByDni(String dni);
}
