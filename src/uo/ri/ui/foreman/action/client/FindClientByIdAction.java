package uo.ri.ui.foreman.action.client;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.ForemanService;
import uo.ri.business.dto.ClientDto;
import uo.ri.conf.Factory;
import uo.ri.ui.util.Printer;
import uo.ri.util.exception.BusinessException;

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
		Printer.printClient(c);
	}

}
