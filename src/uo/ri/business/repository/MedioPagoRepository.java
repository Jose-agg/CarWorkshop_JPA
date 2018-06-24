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

	List<MedioPago> findPaymentMeansByClientId(Long id);

	List<MedioPago> findPaymentMeansByInvoiceId(Long idFactura);

	List<MedioPago> findByClientId(Long id);

	/**
	 * Devuelve un array Object[] con tres elementos
	 * 	- Object[0] un enetero con el número de bonos que tiene el cliente
	 * 	- Object[1] un double con la cantidad disponible total de todos los 
	 * 		bonos del cliente
	 *  - Object[2] un doble con la cantidad acumulada total
	 * @param id del cliente del que se consultan los bonos
	 * @return el array de Object
	 */
	Object[] findAggregateVoucherDataByClientId(Long id);

	TarjetaCredito findCreditCardByNumber(String pan);

	List<Bono> findVouchersByClientId(Long id);

	Bono findVoucherByCode(String code);

}
