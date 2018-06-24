package uo.ri.business.impl.cash;

import uo.ri.business.dto.CardDto;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.ClienteRepository;
import uo.ri.business.repository.MedioPagoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Association;
import uo.ri.model.Cliente;
import uo.ri.model.TarjetaCredito;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.exception.Check;

/**
 * Clase que se encarga de añadir una tarjeta de credito al sistema
 * 
 * Se basa en el patron Transaction Script.
 * 
 * @author José Antonio García García
 *
 */
public class AddCard implements Command<Void> {

	private CardDto dto;
	private MedioPagoRepository r = Factory.repository.forMedioPago();
	private ClienteRepository cr = Factory.repository.forCliente();

	public AddCard(CardDto card) {
		this.dto = card;
	}

	@Override
	public Void execute() throws BusinessException {
		Cliente c = cr.findById(dto.clientId);
		Check.isNotNull(c, "Este cliente no existe");
		checkNotRepeatedNumber(dto.cardNumber);

		TarjetaCredito m = DtoAssembler.toEntity(dto);
		m.comprobarFecha();
		Association.Pagar.link(c, m);
		r.add(m);
		return null;
	}

	/**
	 * Metodo que busca si el numero de tarjeta ya aparece en el sistema
	 * 
	 * @param numero Numero de la nueva tarjeta que se quiere crear
	 * @throws BusinessException
	 */
	private void checkNotRepeatedNumber(String numero)
			throws BusinessException {
		TarjetaCredito t = r.findCreditCardByNumber(numero);
		Check.isNull(t, "Existe otra tarjeta con este numero");
	}

}
