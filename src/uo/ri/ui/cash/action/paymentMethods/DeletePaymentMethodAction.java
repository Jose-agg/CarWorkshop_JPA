package uo.ri.ui.cash.action.paymentMethods;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.CashService;
import uo.ri.conf.Factory;

/**
 * Interacción con el usuario para la acción de eliminar un medio de pago
 * de la base de datos
 * @author yeahb
 *
 */
public class DeletePaymentMethodAction implements Action {

	@Override
	public void execute() throws Exception {
		Long idMedio = Console.readLong("Id del medio de pago");

		CashService as = Factory.service.forCash();
		as.deletePaymentMethod(idMedio);

		Console.println("Se ha eliminado el medio de pago");

	}

}
