package uo.ri.ui.foreman.action.client;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.ForemanService;
import uo.ri.conf.Factory;
import uo.ri.util.exception.BusinessException;

/**
 * Interacción con el usuario para eliminar los datos de un cliente del 
 * sistema
 * 
 * @author José Antonio García García
 *
 */
public class DeleteClientAction implements Action {

	@Override
	public void execute() throws BusinessException {

		Long idCliente = Console.readLong("Id de cliente");

		ForemanService fs = Factory.service.forForeman();
		fs.deleteClient(idCliente);

		Console.println("Se ha eliminado el cliente");
	}

}
