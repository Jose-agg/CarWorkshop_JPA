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

	/**
	 * Metodo que devuelve una factura a partir de su numero
	 * 
	 * @param numero Numero de la factura a buscar
	 * @return factura encontrada
	 */
	Factura findByNumber(Long numero);

	/**
	 * Metodo que devuelve el numero que corresponderia a la siguiente factura
	 * a crear
	 * 
	 * @return numero que corresponde a la siguiente factura a 
	 */
	Long getNextInvoiceNumber();

	/**
	 * Metodo que devuelve la lista de facturas con importe superior a 500€ que
	 * no hayan sido utilizadas previamente en la creacion de un bono
	 * 
	 * @return lista de facturas encontradas
	 */
	List<Factura> findUnusedWithBono500();
}
