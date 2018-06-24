package uo.ri.business.impl.foreman;

import uo.ri.business.impl.Command;
import uo.ri.business.repository.ClienteRepository;
import uo.ri.business.repository.MedioPagoRepository;
import uo.ri.business.repository.RecomendacionRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Cliente;
import uo.ri.model.MedioPago;
import uo.ri.model.Recomendacion;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.exception.Check;

/**
 * Case encargada de borrar un cliente del sistema.
 * 
 * Se basa en el patron Transaction Script.
 * 
 * @author José Antonio García García
 *
 */
public class DeleteClient implements Command<Void> {

	private Long idCliente;

	private ClienteRepository rc;
	private RecomendacionRepository rr;
	private MedioPagoRepository rm;

	public DeleteClient(Long id) {
		this.idCliente = id;
	}

	@Override
	public Void execute() throws BusinessException {
		rc = Factory.repository.forCliente();
		rr = Factory.repository.forRecomendacion();
		rm = Factory.repository.forMedioPago();

		Cliente c = rc.findById(idCliente);
		Check.isNotNull(c, "Este cliente no existe");
		Check.isTrue(c.getVehiculos().size() == 0, "El cliente no puede ser"
				+ " eliminado porque tiene vehículos registrados");

		eleminarMediosPago(c);
		eliminarRecomendacionesHechas(c);
		eliminarRecomendacionRecibida(c);
		rc.remove(c);

		return null;
	}

	/**
	 * Metodo que elimina los medios de pago del cliente
	 * 
	 * @param cliente Cliente a buscar
	 */
	private void eleminarMediosPago(Cliente cliente) {
		for (MedioPago m : cliente.getMediosPago()) {
			rm.remove(m);
			m.unlink();
		}
	}

	/**
	 * Metodo que elimina las recomendaciones que haya hecho el cliente.
	 * 
	 * @param cliente Cliente a buscar
	 */
	private void eliminarRecomendacionesHechas(Cliente cliente) {
		for (Recomendacion r : cliente.getRecomendacionesHechas()) {
			rr.remove(r);
			r.unlink();
		}
	}

	/**
	 * Metodo que elimina la recomendacion que haya podido recibir el cliente.
	 * 
	 * @param cliente Cliente a buscar
	 */
	private void eliminarRecomendacionRecibida(Cliente cliente) {
		if (cliente.getRecomendacionRecibida() != null) {
			rr.remove(cliente.getRecomendacionRecibida());
			cliente.getRecomendacionRecibida().unlink();
		}
	}

}
