package uo.ri.ui.cash.action.paymentMethods;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.CashService;
import uo.ri.business.dto.PaymentMeanDto;
import uo.ri.conf.Factory;
import uo.ri.ui.util.Printer;

/**
 * Interacción con el usuario para la acción de consultar todos los medios de pago disponibles
 * de un cliente en la base de datos
 * @author yeahb
 *
 */
public class FindMediosPagoByClientAction implements Action {

	@Override
	public void execute() throws Exception {

		Long id = Console.readLong("Id del cliente");

		CashService cs = Factory.service.forCash();
		List<PaymentMeanDto> mediosPago = cs.findPaymentMethodsByClientId(id);

		Printer.printPaymentMeansBetter(mediosPago);

	}

}
