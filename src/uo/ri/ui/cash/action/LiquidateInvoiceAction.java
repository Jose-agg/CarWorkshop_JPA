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
 * Representa en la UI la acción a realizar para liquidar una factura cuyo ID se
 * pide por consola. Posteriormente se mostrarán todos los medios de pago
 * asociados al cliente de esa factura por pantalla, y se iran eligiendo cargos
 * a realizar en dichos medios de pago, hasta cubrir el total de la factura.
 * Entonces se procederá a su liquidación y a realizar los cargos
 * 
 * @author Guille
 *
 */
public class LiquidateInvoiceAction implements Action {

	private CashService cs = Factory.service.forCash();
	private List<PaymentMeanDto> mediosPago;

	@Override
	public void execute() throws Exception {
		InvoiceDto factura = cs.findInvoiceByNumber(
				Console.readLong("Introduce el nº de la factura: "));
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
	 * Muestra por pantalla los medios de pago pasados por parámetro No se
	 * recurrer a Printer porque es diferente la información que aqui queremos
	 * mostrar a la que tendría sentido mostrar allí
	 * 
	 * @param mediosPago:
	 *            los medios de pago a mostrar
	 */
	private void mostrarMediosPago(List<PaymentMeanDto> mediosPago) {
		for (int i = 1; i <= mediosPago.size(); i++) {
			Console.printf("%d) -> %s", i,
					getInfoMedioPago(mediosPago.get(i - 1)));
		}
	}

	/**
	 * Devuelve información específica de un medio de pago
	 * 
	 * @param medioPago:
	 *            el medio de pago
	 * @return la información específica
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
	 * Entra en un bucle en el que va pidiendo al usuario realizar cargos sobre
	 * sus medios de pago disponibles hasta completar el importe total de la
	 * factura a liquidar
	 * 
	 * @param factura:
	 *            la factura que queremos liquidar
	 * @return un mapa con los cargos realizados
	 */
	private Map<Long, Double> bucleMediosPago(InvoiceDto factura) {
		Map<Long, Double> cargos = new HashMap<>();
		double restante = factura.total;
		while (restante > 0.0) {
			Console.printf("Importe de la factura: %.2f€\n", factura.total);
			Console.printf("Falta por pagar: %.2f€\n", restante);
			int medio = Console.readInt("-> Selecciona un método de pago");
			while (medio <= 0 || medio > mediosPago.size()) {
				Console.println(
						"¡VAYA! Debes seleccionar un medio de pago válido");
				medio = Console.readInt("-> Selecciona un método de pago ");
			}
			double cantidad = Round
					.twoCents(Console.readDouble("-> Importe a cargar"));
			while (!validar(medio, cantidad, restante, cargos)) {
				Console.println(
						"¡VAYA! El importe introducido no es válido. Por favor, introducelo de nuevo");
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
	 * Comprueba si es valido realizar cierto cargo a un medio de pago
	 * 
	 * @param medio:
	 *            el medio de pago
	 * @param cantidadIntento:
	 *            lo que le queremos cargar
	 * @param cantidadRestante:
	 *            lo que queda por pagar de factura
	 * @param cargos:
	 *            los cargos ya realizados
	 * @return la respuesta
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
