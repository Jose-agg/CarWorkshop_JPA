package uo.ri.business.repository;

import java.util.List;

import uo.ri.model.Bono;
import uo.ri.model.MedioPago;
import uo.ri.model.TarjetaCredito;

/**
 * Interfaz que declara los métodos que serán implementados por la clase de
 * persistencia asociada a la entidad Medio Pago (MedioPagoJpaRepository)
 * 
 * @author José Antonio García García
 */
public interface MedioPagoRepository extends Repository<MedioPago> {

	/**
	 * Metodo que devuelve los metodos de pago de los que dispone un cliente a
	 * partir de id
	 * 
	 * @param id Identificador del cliente a buscar
	 * @return lista de medios de pago
	 */
	List<MedioPago> findPaymentMeansByClientId(Long id);

	/**
	 * Metodo que devuelve los metodos de pago de los que dispone un cliente a
	 * partir de una factura a su nombre
	 * 
	 * @param idFactura Identificador de una factura
	 * @return lista de medios de pago
	 */
	List<MedioPago> findPaymentMeansByInvoiceId(Long idFactura);

	/**
	 * Metodo que devuelve la lista de medios de pago de un cliente
	 * 
	 * @param id Identificador del cliente a buscar
	 * @return lista de medios de pago
	 */
	List<MedioPago> findByClientId(Long id);

	/**
	 * Metodo que busca informacion basica y agregada de los bonos de un cliente
	 * Devuelve un array Object[] con tres elementos
	 * 	- Object[0] un enetero con el número de bonos que tiene el cliente
	 * 	- Object[1] un double con la cantidad disponible total de todos los 
	 * 		bonos del cliente
	 *  - Object[2] un doble con la cantidad acumulada total
	 * @param id Identificador del cliente a buscar
	 * @return array de datos encontrados
	 */
	Object[] findAggregateVoucherDataByClientId(Long id);

	/**
	 * Metodo que devuelve la tarjeta de credito a la cual pertenezca el numero
	 * pasado como parametro
	 * 
	 * @param numero Numero de la tarjeta de credito
	 * @return tarjeta de credito encontrada
	 */
	TarjetaCredito findCreditCardByNumber(String numero);

	/**
	 * Metodo que devuelve los bonos de un cliente
	 * 
	 * @param id Identificador del cliente a buscar
	 * @return lista de bonos encontrados
	 */
	List<Bono> findVouchersByClientId(Long id);

	/**
	 * Metodo que devuelve el bono al cual corresponda el codigo pasado como
	 * parametro
	 * 
	 * @param code Codigo del bono a buscar
	 * @return bono encontrado
	 */
	Bono findVoucherByCode(String code);

}
