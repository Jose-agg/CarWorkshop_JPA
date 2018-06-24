package uo.ri.persistence.jpa;

import java.util.List;

import uo.ri.business.repository.FacturaRepository;
import uo.ri.model.Factura;
import uo.ri.persistence.jpa.util.BaseRepository;
import uo.ri.persistence.jpa.util.Jpa;

public class FacturaJpaRepository extends BaseRepository<Factura>
		implements FacturaRepository {

	@Override
	public Factura findByNumber(Long numero) {
		return Jpa.getManager()
				.createNamedQuery("Factura.findByNumber", Factura.class)
				.setParameter(1, numero).getResultList().stream().findFirst()
				.orElse(null);
	}

	@Override
	public Long getNextInvoiceNumber() {
		return Jpa.getManager()
				.createNamedQuery("Factura.getNextInvoiceNumber", Long.class)
				.getSingleResult();
	}

	@Override
	public List<Factura> findUnusedWithBono500() {
		return Jpa.getManager().createNamedQuery(
				"Factura.findUnusedWithBono500", Factura.class).getResultList();
	}

}
