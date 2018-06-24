package uo.ri.business.impl.foreman;

import uo.ri.business.dto.ClientDto;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.ClienteRepository;
import uo.ri.business.repository.MedioPagoRepository;
import uo.ri.business.repository.RecomendacionRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Cliente;
import uo.ri.model.MedioPago;
import uo.ri.model.Metalico;
import uo.ri.model.Recomendacion;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.exception.Check;

/**
 * Clase encargada de añadir un nuevo cliente al sistema
 * 
 * Se basa en el patron Transaction Script.
 * 
 * @author José Antonio García García
 *
 */
public class AddClient implements Command<Void> {

	private ClientDto dto;
	private Long recomenderId;
	private Recomendacion recomendacion;
	private MedioPago medioPago;

	private ClienteRepository rc;
	private RecomendacionRepository rr;
	private MedioPagoRepository rm;

	public AddClient(ClientDto c, Long recomenderId) {
		this.dto = c;
		this.recomenderId = recomenderId;
	}

	@Override
	public Void execute() throws BusinessException {
		rc = Factory.repository.forCliente();
		rr = Factory.repository.forRecomendacion();
		rm = Factory.repository.forMedioPago();
		checkNotRepeated(dto.dni);
		Cliente c = DtoAssembler.toEntity(dto);

		if (recomenderId != null) {
			Cliente recom = rc.findById(recomenderId);
			Check.isNotNull(recom, "No existe el cliente recomendador");
			recomendacion = new Recomendacion(rc.findById(recomenderId), c);
			rr.add(recomendacion);
		}

		medioPago = new Metalico(c);
		rm.add(medioPago);
		rc.add(c);
		return null;
	}

	/**
	 * Metodo que comprueba que el DNI del nuevo cliente no exista ya en el 
	 * sistema.
	 * 
	 * @param dni DNI a buscar
	 * @throws BusinessException
	 */
	private void checkNotRepeated(String dni) throws BusinessException {
		Cliente c = rc.findByDni(dni);
		Check.isNull(c, "Ya existe un cliente con ese dni");
	}
}
