package uo.ri.business.impl.cash;

import uo.ri.business.dto.VoucherDto;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.ClienteRepository;
import uo.ri.business.repository.MedioPagoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Association;
import uo.ri.model.Bono;
import uo.ri.model.Cliente;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.exception.Check;

/**
 * CLase encargada de añadir un nuevo bono al sistema
 * 
 * Se basa en el patron Transaction Script.
 * 
 * @author José Antonio García García
 *
 */
public class AddVoucher implements Command<Void> {

	private VoucherDto dto;
	private MedioPagoRepository r = Factory.repository.forMedioPago();
	private ClienteRepository cr = Factory.repository.forCliente();

	public AddVoucher(VoucherDto card) {
		this.dto = card;
	}

	@Override
	public Void execute() throws BusinessException {
		Cliente c = cr.findById(dto.clientId);
		Check.isNotNull(c, "Este cliente no existe");

		dto.code = Bono.generarCodigo(r.findAll());
		checkNotRepeatedNumber(dto.code);
		Bono m = DtoAssembler.toEntity(dto);
		Association.Pagar.link(c, m);
		r.add(m);
		return null;
	}

	/**
	 * Metodo que busca si el codigo del bono ya aparece en el sistema
	 * 
	 * @param codigo Codigo del nuevo bono que se quiere crear
	 * @throws BusinessException
	 */
	private void checkNotRepeatedNumber(String codigo)
			throws BusinessException {
		Bono b = r.findVoucherByCode(codigo);
		Check.isNull(b, "Existe otro bono con este codigo");
	}

}
