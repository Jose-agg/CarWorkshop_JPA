package uo.ri.business.repository;

import java.util.List;

/**
 * Interfaz general que declara las operaciones basica que todo repositorio
 * ha de implementar.
 * 
 * @author Jose Antonio Garcia Garcia
 *
 * @param <T>
 */
public interface Repository<T> {
	void add(T t);

	void remove(T t);

	T findById(Long id);

	List<T> findAll();
}
