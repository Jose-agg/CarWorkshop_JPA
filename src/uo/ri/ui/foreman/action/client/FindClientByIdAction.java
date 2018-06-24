package uo.ri.ui.foreman.action.client;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.ForemanService;
import uo.ri.business.dto.ClientDto;
import uo.ri.conf.Factory;
import uo.ri.ui.util.Printer;
import uo.ri.util.exception.BusinessException;

/**
 * Interacción con el usuario para buscar los datos de un cliente del 
 * sistema
 * 
 * @author José Antonio García García
 *
 */
public class FindClientByIdAction implements Action {

	@Override
	public void execute() throws BusinessException {
		Long id = Console.readLong("Id del cliente");

		ForemanService fs = Factory.service.forForeman();

		ClientDto c = fs.findClientById(id);
		if (c == null) {
			throw new BusinessException("No existe el cliente");
		}

		Console.println("\nDetalles del cliente\n");
		Console.println("Formato: id, dni\t, nombre\t\t, apellidos\t\t, "
				+ "ciudad\t\t, calle\t\t, codigo postal\t\t, email\t\t, telefono");
		Printer.printClient(c);
	}

}
