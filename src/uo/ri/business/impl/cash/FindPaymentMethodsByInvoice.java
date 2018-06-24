package uo.ri.business.impl.cash;

import java.util.List;
import java.util.stream.Collectors;

import uo.ri.business.dto.PaymentMeanDto;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.MedioPagoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Bono;
import uo.ri.model.MedioPago;
import uo.ri.model.TarjetaCredito;
import uo.ri.util.exception.BusinessException;

/**
 * Clase que busca los medios de pago que tiene disponibles un cliente a partir
 * de una factura
 * 
 * Se basa en el patron Transaction Script.
 * 
 * @author José Antonio García García
 *
 */
public class FindPaymentMethodsByInvoice
		implements Command<List<PaymentMeanDto>> {

	private Long idFactura;
	private MedioPagoRepository rmp;

	public FindPaymentMethodsByInvoice(Long idFactura) {
		this.idFactura = idFactura;
	}

	@Override
	public List<PaymentMeanDto> execute() throws BusinessException {
		rmp = Factory.repository.forMedioPago();
		List<MedioPago> mps = rmp.findPaymentMeansByInvoiceId(idFactura);
		mps = mps.stream().filter(x -> {
			if (x instanceof Bono) {
				Bono b = (Bono) x;
				return b.getDisponible() > 0.0;
			} else if (x instanceof TarjetaCredito) {
				TarjetaCredito t = (TarjetaCredito) x;
				return t.isValidNow();
			}
			return true;
		}).collect(Collectors.toList());
		return DtoAssembler.toPaymentMeanDtoList(mps);
	}

}
