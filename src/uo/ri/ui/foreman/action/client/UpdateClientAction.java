package uo.ri.ui.foreman.action.client;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.ForemanService;
import uo.ri.business.dto.ClientDto;
import uo.ri.conf.Factory;
import uo.ri.util.exception.BusinessException;

/**
 * Interacción con el usuario para actualizar los datos de un cliente del 
 * sistema
 * 
 * @author José Antonio García García
 *
 */
public class UpdateClientAction implements Action {

	@Override
	public void execute() throws BusinessException {

		Long id = Console.readLong("Id del cliente");
		String nombre = Console.readString("Nombre");
		String apellidos = Console.readString("Apellidos");
		String ciudad = Console.readString("Ciudad");
		String calle = Console.readString("Calle");
		String codigoPostal = Console.readString("Código postal");
		String email = Console.readString("Email");
		String telefono = Console.readString("Teléfono");

		ForemanService fs = Factory.service.forForeman();

		ClientDto c = fs.findClientById(id);
		if (c == null) {
			throw new BusinessException("No existe el cliente");
		}
		c.name = nombre;
		c.surname = apellidos;
		c.addressCity = ciudad;
		c.addressStreet = calle;
		c.addressZipcode = codigoPostal;
		c.email = email;
		c.phone = telefono;

		fs.updateClient(c);
		;

		Console.println("Cliente actualizado");

	}

}
