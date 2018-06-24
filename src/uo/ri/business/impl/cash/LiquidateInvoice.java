package uo.ri.business.impl.cash;

import java.util.Map;

import uo.ri.business.dto.InvoiceDto;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.CargoRepository;
import uo.ri.business.repository.FacturaRepository;
import uo.ri.business.repository.MedioPagoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Cargo;
import uo.ri.model.Factura;
import uo.ri.model.MedioPago;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.exception.Check;

/**
 * Clase que liquida una factura con los medios de pago introducidos por el
 * cliente
 * 
 * Se basa en el patron Transaction Script.
 * 
 * @author José Antonio García García
 *
 */
public class LiquidateInvoice implements Command<InvoiceDto> {

	private Long idFactura;
	private Map<Long, Double> cargos;
	private Factura factura;

	private FacturaRepository rf;

	public LiquidateInvoice(Long idFactura, Map<Long, Double> cargos) {
		this.idFactura = idFactura;
		this.cargos = cargos;
	}

	@Override
	public InvoiceDto execute() throws BusinessException {
		rf = Factory.repository.forFactura();
		this.factura = rf.findById(idFactura);

		Check.isNull(factura, "No existe un factura con este id");
		Check.isTrue(factura.isSettled(), "Esta factura ya ha sido abonada");

		realizarCargos();
		factura.settle();
		return DtoAssembler.toDto(factura);
	}

	/**
	 * Metodo que se encarga de cobrar la cantidad indicada por el cliente a 
	 * cada medio de pago que haya seleccionado
	 * 
	 * @throws BusinessException
	 */
	private void realizarCargos() throws BusinessException {
		CargoRepository rc = Factory.repository.forCargo();
		MedioPagoRepository rmp = Factory.repository.forMedioPago();
		for (Long idMedioPago : cargos.keySet()) {
			MedioPago mp = rmp.findById(idMedioPago);
			Cargo cargo = new Cargo(factura, mp, cargos.get(idMedioPago));
			rc.add(cargo);
		}
	}
}
