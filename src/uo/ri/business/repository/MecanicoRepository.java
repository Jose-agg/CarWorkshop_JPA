package uo.ri.business.repository;

import uo.ri.model.Mecanico;

/**
 * Interfaz que declara los métodos que serán implementados por la clase de
 * persistencia asociada a la entidad Mecanico (MecanicoJpaRepository)
 * 
 * @author José Antonio García García
 */
public interface MecanicoRepository extends Repository<Mecanico> {

	public Mecanico findByDni(String dni);
}
