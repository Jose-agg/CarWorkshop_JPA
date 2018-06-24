package uo.ri.business.impl.admin.voucher;

import java.util.List;
import java.util.stream.Collectors;

import alb.util.random.Random;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.ClienteRepository;
import uo.ri.business.repository.FacturaRepository;
import uo.ri.business.repository.MedioPagoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Association;
import uo.ri.model.Averia;
import uo.ri.model.Bono;
import uo.ri.model.Cliente;
import uo.ri.model.Factura;
import uo.ri.model.Recomendacion;
import uo.ri.util.exception.BusinessException;

/**
 * Clase encargada de generar bonos a partir de las siguientes 3 reglas:
 *  - Bonos por cada 3 recomendaciones.
 *  - Bonos por cada 3 averias.
 *  - Bonos por facturas superiores a 500€
 *  
 *  Se basa en el patron Transaction Script.
 * 
 * @author José Antonio García García
 */
public class GenerateVouchers implements Command<Integer> {

	private MedioPagoRepository rmp = Factory.repository.forMedioPago();
	private ClienteRepository rc = Factory.repository.forCliente();
	private FacturaRepository fr = Factory.repository.forFactura();
	private int numBonos;

	public static final double CANTIDAD_AVERIAS = 20.0;
	public static final double CANTIDAD_FACTURAS = 30.0;
	public static final double CANTIDAD_RECOMENDACIONES = 25.0;

	@Override
	public Integer execute() throws BusinessException {
		numBonos = 0;
		List<Cliente> clientes = rc.findAll();
		for (Cliente cliente : clientes) {
			generarBonosPorTresAverias(cliente);
			if (cliente.elegibleBonoPorRecomendaciones())
				generarBonosPorTresRecomendaciones(cliente);
		}
		generarBonosPorFacturas();
		return numBonos;
	}

	/**
	 * Metodo que genera los bonos por cada tres averias no utilizadas
	 * previamente
	 * 
	 * @param cliente Datos del cliente a buscar
	 */
	private void generarBonosPorTresAverias(Cliente cliente) {
		int numAverias, numBonos;

		List<Averia> averias = cliente.getAveriasBono3NoUsadas();
		numAverias = averias.size() - averias.size() % 3;
		numBonos = averias.size() / 3;
		for (int i = 0; i < numAverias; i++) {
			averias.get(i).markAsBono3Used();
		}

		for (int j = 0; j < numBonos; j++) {
			crearBono(cliente, CANTIDAD_AVERIAS, "Por tres averías");
			this.numBonos++;
		}
	}

	/**
	 * Metodo que genera los bonos por cada factura superior a 500€ no utilizada
	 * previamente
	 * 
	 */
	private void generarBonosPorFacturas() throws BusinessException {
		List<Factura> facturas = fr.findUnusedWithBono500();
		for (Factura factura : facturas) {
			if (factura.puedeGenerarBono500()) {
				factura.markAsBono500Used();
				Cliente c = factura.getAverias().stream().findFirst().get()
						.getVehiculo().getCliente();
				crearBono(c, CANTIDAD_FACTURAS, "Por factura superior a 500€");
				this.numBonos++;
			}
		}

	}

	/**
	 * Metodo que genera los bonos por cada tres recomendaciones no utilizadas
	 * previamente
	 * 
	 * @param cliente Datos del cliente a buscar
	 */
	private void generarBonosPorTresRecomendaciones(Cliente cliente) {
		List<Recomendacion> recomendaciones = cliente.getRecomendacionesHechas()
				.stream().collect(Collectors.toList());
		int numRec = recomendaciones.size() - recomendaciones.size() % 3;
		int numBonos = recomendaciones.size() / 3;

		for (int i = 0; i < numRec; i++) {
			recomendaciones.get(i).markAsUsadaBono();
		}

		for (int i = 0; i < numBonos; i++) {
			crearBono(cliente, CANTIDAD_RECOMENDACIONES, "Por recomendación");
			this.numBonos++;
		}
	}

	/**
	 * Metodo que crea un nuevo bono
	 * 
	 * @param cliente Datos del cliente al que se asignará el bono
	 * @param disponible Cantidad de la que dispondrá el bono
	 * @param descripcion Descripcion del bono
	 */
	private void crearBono(Cliente cliente, double disponible,
			String descripcion) {
		Bono bono = new Bono(Random.string(5), disponible);
		bono.setDescripcion(descripcion);
		Association.Pagar.link(cliente, bono);

		rmp.add(bono);
	}
}
