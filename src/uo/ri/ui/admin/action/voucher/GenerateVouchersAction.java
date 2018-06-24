package uo.ri.ui.admin.action.voucher;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.AdminService;
import uo.ri.conf.Factory;

/**
 * Interacción con el usuario para la generacion de bonos si se cumplen alguna
 * de estas tres reglas:
 *  - Bonos por cada 3 recomendaciones.
 *  - Bonos por cada 3 averias.
 *  - Bonos por facturas superiores a 500€
 * 
 * @author José Antonio García García
 *
 */
public class GenerateVouchersAction implements Action {

	@Override
	public void execute() throws Exception {
		Console.println("Generando bonos...");
		AdminService as = Factory.service.forAdmin();
		int bonosGenerados = as.generateVouchers();
		Console.println("\nBonos Generados: " + bonosGenerados + "\n");
	}

}
