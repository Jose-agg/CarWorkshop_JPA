package uo.ri.business.repository;

import java.util.List;

import uo.ri.model.Factura;

/**
 * Interfaz que declara los métodos que serán implementados por la clase de
 * persistencia asociada a la entidad Factura (FacturaJpaRepository)
 * 
 * @author José Antonio García García
 */
public interface FacturaRepository extends Repository<Factura> {

	Factura findByNumber(Long numero);

	Long getNextInvoiceNumber();

	List<Factura> findUnusedWithBono500();
}
