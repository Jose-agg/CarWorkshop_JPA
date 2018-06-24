package uo.ri.business;

import java.util.List;
import java.util.Map;

import uo.ri.business.dto.BreakdownDto;
import uo.ri.business.dto.CardDto;
import uo.ri.business.dto.InvoiceDto;
import uo.ri.business.dto.PaymentMeanDto;
import uo.ri.business.dto.VoucherDto;
import uo.ri.util.exception.BusinessException;

/**
 * Interfaz que declara los métodos que serán implementados por la clase de
 * logica asociada a la caja (CashServiceImpl)
 * 
 * @author José Antonio García García
 */
public interface CashService {

	/**
	 * Metodo que crea una factura a partir de una lista de averias
	 * 
	 * @param idsAveria Identificadores de averias
	 * @return datos de la factura creada
	 * @throws BusinessException
	 */
	public InvoiceDto createInvoiceFor(List<Long> idsAveria)
			throws BusinessException;

	/**
	 * Metodo que devuelve los datos de una factua a partir de su numero
	 * 
	 * @param numeroFactura Numero de la factura a buscar
	 * @return datos de la factura buscada
	 * @throws BusinessException
	 */
	public InvoiceDto findInvoiceByNumber(Long numeroFactura)
			throws BusinessException;

	/**
	 * Metodo que devuelve los metodos de pago que tiene disponible el cliente
	 * asociado a una factura
	 * 
	 * @param idFactura Identificador de la factura
	 * @return lista de metodos de pago que tiene disponible el cliente
	 * @throws BusinessException
	 */
	public List<PaymentMeanDto> findPaymentMethodsForInvoice(Long idFactura)
			throws BusinessException;

	/**
	 * Metodo que liquida una factura con los medios de pago seleccionados por
	 * un cliente
	 * 
	 * @param idFactura Identificador de una factura
	 * @param cargos Lista de medios de pago seleccionados por el cliente
	 * @throws BusinessException
	 */
	public void liquidateInvoice(Long idFactura, Map<Long, Double> cargos)
			throws BusinessException;

	/**
	 * Metodo que devuelve la lista de averias de un cliente que aun no han sido
	 *  facturadas
	 * 
	 * @param dni Dni del cliente a buscar
	 * @return lista de averias sin facturar
	 * @throws BusinessException
	 */
	public List<BreakdownDto> findBreakdownByClient(String dni)
			throws BusinessException;

	/**
	 * Metodo que añade una nueva tarjeta de credito al sistema
	 * 
	 * @param card Datos de la nueva tarjeta
	 * @throws BusinessException
	 */
	public void addCardPaymentMethod(CardDto card) throws BusinessException;

	/**
	 * Metodo que añade un nuevo bono al sistema
	 * 
	 * @param voucher Datos del nuevo bono
	 * @throws BusinessException
	 */
	public void addVoucherPaymentMethod(VoucherDto voucher)
			throws BusinessException;

	/**
	 * Metodo que elimina un medio de pago
	 * 
	 * @param idMedioPago Identificador del medio de pago
	 * @throws BusinessException
	 */
	public void deletePaymentMethod(Long idMedioPago) throws BusinessException;

	/**
	 * Metodo que devuelve los metodos de pago de los que dispone un cliente
	 * 
	 * @param id Identificador del cliente a buscar
	 * @return lista de medios de pago del cliente
	 * @throws BusinessException
	 */
	public List<PaymentMeanDto> findPaymentMethodsByClientId(Long id)
			throws BusinessException;

}
