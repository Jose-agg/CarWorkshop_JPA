package uo.ri.business.impl.foreman;

import uo.ri.business.dto.ClientDto;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.ClienteRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Cliente;
import uo.ri.model.types.Address;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.exception.Check;

/**
 * Clase que actualiza los datos de un cliente del sistema
 * 
 * Se basa en el patron Transaction Script.
 * 
 * @author José Antonio García García
 *
 */
public class UpdateClient implements Command<Void> {

	private ClientDto dto;

	private ClienteRepository rc;

	public UpdateClient(ClientDto c) {
		this.dto = c;
	}

	@Override
	public Void execute() throws BusinessException {
		rc = Factory.repository.forCliente();

		Cliente c = rc.findById(dto.id);
		Check.isNotNull(c, "Cliente no existe");

		c.setNombre(dto.name);
		c.setApellidos(dto.surname);
		Address addr = new Address(dto.addressStreet, dto.addressCity,
				dto.addressZipcode);
		c.setAddress(addr);
		c.setEmail(dto.email);
		c.setPhone(dto.phone);

		return null;
	}

}
