package uo.ri.ui.cash.action.paymentMethods;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.CashService;
import uo.ri.business.dto.VoucherDto;
import uo.ri.conf.Factory;

/**
 * Interacción con el usuario para la creacion de un nuevo bono
 * 
 * @author José Antonio García García
 *
 */
public class AddVoucherAction implements Action {

	@Override
	public void execute() throws Exception {
		VoucherDto c = new VoucherDto();
		c.clientId = Console.readLong("Id del cliente");
		c.description = Console.readString("Descripción del bono");
		c.available = Console.readDouble("Cantidad disponible");

		CashService cs = Factory.service.forCash();
		cs.addVoucherPaymentMethod(c);

		Console.println("Bono añadido con éxito");
	}
}
