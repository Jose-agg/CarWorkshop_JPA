package uo.ri.business.impl.admin.voucher;

import java.util.List;

import uo.ri.business.dto.VoucherDto;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.MedioPagoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Bono;
import uo.ri.util.exception.BusinessException;

/**
 * Clase encargada de buscar la informacion de los bonos que hay en el sistema
 * de un determinado cliente. Se basa en el patron Transaction Script.
 * 
 * @author José Antonio García García
 *
 */
public class FindVouchersByClient implements Command<List<VoucherDto>> {

	private Long id;

	public FindVouchersByClient(Long id) {
		this.id = id;
	}

	@Override
	public List<VoucherDto> execute() throws BusinessException {
		MedioPagoRepository r = Factory.repository.forMedioPago();
		List<Bono> lista = r.findVouchersByClientId(id);
		return DtoAssembler.toVoucherDtoList(lista);
	}

}
