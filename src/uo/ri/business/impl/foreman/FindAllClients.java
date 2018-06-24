package uo.ri.business.impl.foreman;

import java.util.List;

import uo.ri.business.dto.ClientDto;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.ClienteRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Cliente;
import uo.ri.util.exception.BusinessException;

/**
 * Clase que busca todos los clientes del sistema
 * 
 * Se basa en el patron Transaction Script.
 * 
 * @author José Antonio García García
 *
 */
public class FindAllClients implements Command<List<ClientDto>> {

	ClienteRepository rc = Factory.repository.forCliente();

	@Override
	public List<ClientDto> execute() throws BusinessException {
		List<Cliente> clientes = rc.findAll();
		return DtoAssembler.toClientDtoList(clientes);
	}

}
