package uo.ri.persistence.jpa;

import java.util.List;

import uo.ri.business.repository.MedioPagoRepository;
import uo.ri.model.Bono;
import uo.ri.model.MedioPago;
import uo.ri.model.TarjetaCredito;
import uo.ri.persistence.jpa.util.BaseRepository;
import uo.ri.persistence.jpa.util.Jpa;

/**
 * Repositorio que realiza la persistencia con los elementos de tipo MedioPago
 * de la base de datos. Se resuelven métodos de consulta utilizando el mapeador JPA
 * @author yeahb
 *
 */
public class MedioPagoJpaRepository extends BaseRepository<MedioPago>
		implements MedioPagoRepository {

	@Override
	public List<MedioPago> findPaymentMeansByClientId(Long id) {
		return Jpa.getManager().createNamedQuery("MedioPago.findAllByClientId",
				MedioPago.class).setParameter(1, id).getResultList();
	}

	@Override
	public List<MedioPago> findPaymentMeansByInvoiceId(Long idFactura) {
		return Jpa.getManager()
				.createNamedQuery("MedioPago.findByInvoiceId", MedioPago.class)
				.setParameter(1, idFactura).getResultList();
	}

	@Override
	public List<MedioPago> findByClientId(Long id) {
		return Jpa.getManager().createNamedQuery("MedioPago.findAllByClientId",
				MedioPago.class).setParameter(1, id).getResultList();
	}

	@Override
	public Object[] findAggregateVoucherDataByClientId(Long id) {
		return Jpa.getManager()
				.createNamedQuery(
						"MedioPago.findAggregateVoucherDataByClientId",
						Object[].class)
				.setParameter(1, id).getResultList().stream().findFirst()
				.orElse(null);
	}

	@Override
	public TarjetaCredito findCreditCardByNumber(String pan) {
		return Jpa.getManager()
				.createNamedQuery("MedioPago.findCreditCardByNumber",
						TarjetaCredito.class)
				.setParameter(1, pan).getResultList().stream().findFirst()
				.orElse(null);
	}

	@Override
	public List<Bono> findVouchersByClientId(Long id) {
		return Jpa.getManager()
				.createNamedQuery("MedioPago.findVouchersByClientId",
						Bono.class)
				.setParameter(1, id).getResultList();
	}

	@Override
	public Bono findVoucherByCode(String code) {
		return Jpa.getManager()
				.createNamedQuery("MedioPago.findVoucherByCode", Bono.class)
				.setParameter(1, code).getResultList().stream().findFirst()
				.orElse(null);
	}

}
