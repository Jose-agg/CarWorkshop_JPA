package uo.ri.business.impl.cash;

import java.util.List;

import uo.ri.business.dto.PaymentMeanDto;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.MedioPagoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.MedioPago;
import uo.ri.util.exception.BusinessException;

/**
 * Clase que busca los medios de pago que tiene disponibles un cliente
 * 
 * Se basa en el patron Transaction Script.
 * 
 * @author José Antonio García García
 *
 */
public class FindPaymentMethodsByClientId
		implements Command<List<PaymentMeanDto>> {

	private Long id;

	public FindPaymentMethodsByClientId(Long id) {
		this.id = id;
	}

	@Override
	public List<PaymentMeanDto> execute() throws BusinessException {
		MedioPagoRepository r = Factory.repository.forMedioPago();
		List<MedioPago> list = r.findPaymentMeansByClientId(id);
		return DtoAssembler.toPaymentMeanDtoList(list);
	}

}
