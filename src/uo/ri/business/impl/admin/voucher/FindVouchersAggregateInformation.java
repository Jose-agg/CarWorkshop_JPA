package uo.ri.business.impl.admin.voucher;

import java.util.ArrayList;
import java.util.List;

import uo.ri.business.dto.VoucherSummary;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.ClienteRepository;
import uo.ri.business.repository.MedioPagoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Cliente;
import uo.ri.util.exception.BusinessException;

/**
 * Clase encargada de buscar la informacion de todos los bonos que hay en el 
 * sistema con informacion agregada. Se basa en el patron Transaction Script.
 * 
 * @author José Antonio García García
 *
 */
public class FindVouchersAggregateInformation
		implements Command<List<VoucherSummary>> {

	private ClienteRepository cr = Factory.repository.forCliente();
	private MedioPagoRepository r = Factory.repository.forMedioPago();

	@Override
	public List<VoucherSummary> execute() throws BusinessException {
		List<VoucherSummary> lista = new ArrayList<>();
		List<Cliente> clientes = cr.findAll();
		for (Cliente cli : clientes) {
			lista.add(DtoAssembler.toDto(cli,
					r.findVouchersByClientId(cli.getId())));
		}
		return lista;
	}

}
