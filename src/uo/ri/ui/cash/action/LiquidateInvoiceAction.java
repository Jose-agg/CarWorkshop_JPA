package uo.ri.ui.cash.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import alb.util.console.Console;
import alb.util.math.Round;
import alb.util.menu.Action;
import uo.ri.business.CashService;
import uo.ri.business.dto.CardDto;
import uo.ri.business.dto.InvoiceDto;
import uo.ri.business.dto.PaymentMeanDto;
import uo.ri.business.dto.VoucherDto;
import uo.ri.conf.Factory;
import uo.ri.util.exception.BusinessException;

/**
 * Interacción con el usuario para la liquidacion de facturas
 * 
 * @author José Antonio García García
 *
 */
public class LiquidateInvoiceAction implements Action {

	private CashService cs = Factory.service.forCash();
	private List<PaymentMeanDto> mediosPago;

	@Override
	public void execute() throws Exception {
		InvoiceDto factura = cs.findInvoiceByNumber(
				Console.readLong("Numero de la factura: "));
		if (factura == null)
			throw new BusinessException("No existe");
		else if (factura.status.equals("ABONADA"))
			throw new BusinessException("Factura ya abonada");

		mediosPago = cs.findPaymentMethodsForInvoice(factura.id);
		mostrarMediosPago(mediosPago);
		Map<Long, Double> cargos = bucleMediosPago(factura);
		Console.println("Total a deber introducido");
		cs.liquidateInvoice(factura.id, cargos);
		Console.println("Factura liquidada");
	}

	/**
	 * Metodo auxiliar que muestra los metodos de pago disponibles para el 
	 * cliente
	 * 
	 * @param mediosPago Medios de pago disponibles
	 */
	private void mostrarMediosPago(List<PaymentMeanDto> mediosPago) {
		for (int i = 1; i <= mediosPago.size(); i++) {
			Console.printf("%d) -> %s", i,
					getInfoMedioPago(mediosPago.get(i - 1)));
		}
	}

	/**
	 * Metodo auxiliar que muestra la informacion de un medio de pago
	 * 
	 * @param medioPago Medio de pago a mostrar
	 * @return informacion del medio de pago parseada
	 */
	private String getInfoMedioPago(PaymentMeanDto medioPago) {
		if (medioPago instanceof VoucherDto) {
			VoucherDto bono = (VoucherDto) medioPago;
			return String.format(
					"[Tipo: Bono, Codigo: %s, Disponible: %.2f€]\n", bono.code,
					bono.available);
		} else if (medioPago instanceof CardDto) {
			CardDto card = (CardDto) medioPago;
			return String.format(
					"[Tipo: Tarjeta de Crédito, Nº: %s, Tipo: %s, Validez: %s]\n",
					card.cardNumber, card.cardType, card.cardExpiration);
		} else
			return "[Tipo: Metálico]\n";
	}

	/**
	 * Metodo auxiliar que pide al usuario la distribucion del importe de la 
	 * factura en funcion de los medios de pago mostrados previamente
	 * 
	 * @param factura Datos de la factura a liquidar
	 * @return mapa con los medios de pago utilizados y el importe que se 
	 * 			quiere sacar de cada uno de ellos
	 */
	private Map<Long, Double> bucleMediosPago(InvoiceDto factura) {
		Map<Long, Double> cargos = new HashMap<>();
		double restante = factura.total;
		Console.printf("Importe de la factura: %.2f€\n", factura.total);
		while (restante > 0.0) {
			Console.printf("Falta por pagar: %.2f€\n", restante);
			int medio = Console.readInt("-> Selecciona un método de pago");
			while (medio <= 0 || medio > mediosPago.size()) {
				Console.println("Selecciona un medio de pago valido");
				medio = Console.readInt("-> Selecciona un método de pago ");
			}
			double cantidad = Round
					.twoCents(Console.readDouble("-> Importe a cargar"));
			while (!validar(medio, cantidad, restante, cargos)) {
				Console.println("Introduce un importe valido");
				cantidad = Round
						.twoCents(Console.readDouble("-> Importe a cargar"));
			}
			restante -= cantidad;
			restante = Round.twoCents(restante);
			Long key = mediosPago.get(medio - 1).id;
			if (cargos.containsKey(key))
				cargos.replace(key, cargos.get(key) + cantidad);
			else
				cargos.put(key, cantidad);
		}

		return cargos;
	}

	/**
	 * Metodo auxiliar que comprueba si es valido realizar cierto cargo a un 
	 * medio de pago
	 * 
	 * @param medio Medio de pago a comprobar
	 * @param cantidadIntento Cantidad que se desea utilizar
	 * @param cantidadRestante Importe restante de la factura
	 * @param cargos Datos de los cargos que se han utilizado previamente
	 * @return true si son validos los datos, false si no lo son
	 */
	private boolean validar(int medio, double cantidadIntento,
			double cantidadRestante, Map<Long, Double> cargos) {

		if (cantidadIntento > cantidadRestante || cantidadIntento < 0.0)
			return false;
		if (mediosPago.get(medio - 1) instanceof VoucherDto) {
			VoucherDto bono = (VoucherDto) mediosPago.get(medio - 1);
			double lleva = (cargos.containsKey(bono.id)) ? cargos.get(bono.id)
					: 0.0;
			return bono.available - lleva >= cantidadIntento;
		}
		return true;
	}

}
