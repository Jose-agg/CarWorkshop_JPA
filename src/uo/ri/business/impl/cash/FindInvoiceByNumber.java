package uo.ri.business.impl.cash;

import uo.ri.business.dto.InvoiceDto;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.FacturaRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Factura;
import uo.ri.util.exception.BusinessException;

/**
 * Clase encargada de buscar una factura a partir de su numero
 * 
 * Se basa en el patron Transaction Script.
 * 
 * @author José Antonio García García
 *
 */
public class FindInvoiceByNumber implements Command<InvoiceDto> {

	private Long numFactura;

	public FindInvoiceByNumber(Long numFactura) {
		this.numFactura = numFactura;
	}

	@Override
	public InvoiceDto execute() throws BusinessException {
		FacturaRepository rf = Factory.repository.forFactura();
		Factura factura = rf.findByNumber(numFactura);
		if (factura == null)
			return null;
		return DtoAssembler.toDto(factura);

	}
}
