package uo.ri.business.impl.cash;

import uo.ri.business.impl.Command;
import uo.ri.business.repository.MedioPagoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.MedioPago;
import uo.ri.model.Metalico;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.exception.Check;

/**
 * Clase encargada de eliminar un medio de pago del sistema
 * 
 * Se basa en el patron Transaction Script.
 * 
 * @author José Antonio García García
 *
 */
public class DeletePaymentMethod implements Command<Void> {

	private Long idMedio;
	private MedioPagoRepository rmp;

	public DeletePaymentMethod(Long idMedio) {
		this.idMedio = idMedio;
	}

	@Override
	public Void execute() throws BusinessException {
		rmp = Factory.repository.forMedioPago();
		MedioPago m = rmp.findById(idMedio);
		Check.isNotNull(m, "Este medio de pago no existe");
		Check.isFalse(m instanceof Metalico,
				"Se está intentando eliminar un tipo metálico");
		Check.isTrue(m.getCargos().size() == 0,
				"El medio de pago tiene cargos asociados");
		rmp.remove(m);
		return null;
	}

}
