package uo.ri.ui.foreman.action.client;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.ForemanService;
import uo.ri.business.dto.ClientDto;
import uo.ri.conf.Factory;
import uo.ri.ui.util.Printer;
import uo.ri.util.exception.BusinessException;

/**
 * Interacción con el usuario para listar los datos de todos los cliente del 
 * sistema
 * 
 * @author José Antonio García García
 *
 */
public class FindAllClientsAction implements Action {

	@Override
	public void execute() throws BusinessException {

		ForemanService fs = Factory.service.forForeman();
		List<ClientDto> clients = fs.findAllClients();

		Console.println("\nListado de clientes\n");
		Console.println("Formato: id, dni, nombre, apellidos, ciudad, calle, "
				+ "codigo postal, email, telefono");
		for (ClientDto c : clients) {
			Printer.printClient(c);
		}

	}

}
