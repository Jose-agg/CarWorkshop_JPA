package uo.ri.ui.cash.action.paymentMethods;

import java.text.SimpleDateFormat;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.CashService;
import uo.ri.business.dto.CardDto;
import uo.ri.conf.Factory;

/**
 * Interacción con el usuario para la creacion de una nueva tarjeta
 * 
 * @author José Antonio García García
 *
 */
public class AddCardAction implements Action {

	@Override
	public void execute() throws Exception {
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
		CardDto c = new CardDto();
		c.clientId = Console.readLong("Id del cliente");
		c.cardType = Console.readString("Tipo de la tarjeta");
		c.cardNumber = Console.readString("Numero de la tarjeta");
		c.cardExpiration = formatoDelTexto
				.parse(Console.readString("Fecha de validez (yyyy-mm-dd)"));

		CashService cs = Factory.service.forCash();
		cs.addCardPaymentMethod(c);

		Console.println("Aañadida tarjeta de crédito ");

	}

}
