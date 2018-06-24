package uo.ri.business.impl.foreman;

import uo.ri.business.dto.ClientDto;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.ClienteRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Cliente;
import uo.ri.util.exception.BusinessException;

/**
 * Clase encargada de buscar un cliente a partir de su identificador
 * 
 * Se basa en el patron Transaction Script.
 * 
 * @author José Antonio García García
 *
 */
public class FindClientById implements Command<ClientDto> {

	private Long id;

	ClienteRepository rc = Factory.repository.forCliente();

	public FindClientById(Long id) {
		this.id = id;
	}

	@Override
	public ClientDto execute() throws BusinessException {
		Cliente c = rc.findById(id);
		if (c == null)
			return null;
		else
			return DtoAssembler.toDto(c);
	}

}
