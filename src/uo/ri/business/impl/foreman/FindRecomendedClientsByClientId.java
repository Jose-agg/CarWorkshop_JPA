package uo.ri.business.impl.foreman;

import java.util.ArrayList;
import java.util.List;

import uo.ri.business.dto.ClientDto;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.ClienteRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Cliente;
import uo.ri.model.Recomendacion;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.exception.Check;

/**
 * Clase que busca todos los usuarios que hayan sido determinados por un
 * determinado cliente
 * 
 * Se basa en el patron Transaction Script.
 * 
 * @author José Antonio García García
 *
 */
public class FindRecomendedClientsByClientId
		implements Command<List<ClientDto>> {

	private Long id;

	private ClienteRepository rc;

	public FindRecomendedClientsByClientId(Long id) {
		this.id = id;
	}

	@Override
	public List<ClientDto> execute() throws BusinessException {
		rc = Factory.repository.forCliente();
		Cliente c = rc.findById(id);

		Check.isNotNull(c, "No existe un cliente con este id");
		Check.isTrue(listClientesRecomendados(c).isEmpty(),
				"Este cliente no ha recomendado a nadie");

		return DtoAssembler.toClientDtoList(listClientesRecomendados(c));
	}

	/**
	 * Metodo que busca la lista de clientes que hayan sido recomendados por 
	 * un determinado cliente
	 * 
	 * @param cliente Cliente a buscar
	 * @return lista de clientes recomendados
	 */
	private List<Cliente> listClientesRecomendados(Cliente cliente) {
		List<Cliente> clientesRecomendados = new ArrayList<Cliente>();

		for (Recomendacion r : cliente.getRecomendacionesHechas()) {
			clientesRecomendados.add(r.getRecomendado());
		}

		return clientesRecomendados;

	}

}
