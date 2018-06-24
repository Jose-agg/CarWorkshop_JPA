package uo.ri.ui.foreman.action.client;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.ForemanService;
import uo.ri.business.dto.ClientDto;
import uo.ri.conf.Factory;
import uo.ri.util.exception.BusinessException;

public class AddClientAction implements Action {

	@Override
	public void execute() throws BusinessException {

		Long recomenderId = getRecomendador();

		ClientDto c = new ClientDto();
		c.dni = Console.readString("Dni");
		c.name = Console.readString("Nombre");
		c.surname = Console.readString("Apellidos");
		c.addressCity = Console.readString("Ciudad");
		c.addressStreet = Console.readString("Calle");
		c.addressZipcode = Console.readString("Código postal");
		c.email = Console.readString("Email");
		c.phone = Console.readString("Teléfono");

		ForemanService fs = Factory.service.forForeman();
		fs.addClient(c, recomenderId);

		Console.println("Nuevo cliente añadido");

	}

	private Long getRecomendador() {
		boolean respuesta = Console
				.readString("¿Viene recomendado por alguien? (s/n) ")
				.equalsIgnoreCase("s");
		if (respuesta) {
			return Console
					.readLong("Escriba el identificador de su recomendador");
		}
		return null;
	}
}
