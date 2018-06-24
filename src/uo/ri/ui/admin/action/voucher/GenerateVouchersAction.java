package uo.ri.ui.admin.action.voucher;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.AdminService;
import uo.ri.conf.Factory;

/**
 * Representa la acción en la UI a realizar para generar automáticamente bonos
 * para los clientes del taller y mostrar por pantalla el número generado
 * 
 * @author Guille
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
