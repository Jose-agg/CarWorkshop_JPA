package uo.ri.ui.admin.action.voucher;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.AdminService;
import uo.ri.business.dto.VoucherDto;
import uo.ri.conf.Factory;
import uo.ri.ui.util.Printer;

/**
 * Interacción con el usuario para mostrar los bonos disponibles de un cliente
 * 
 * @author José Antonio García García
 *
 */
public class FindVouchersAction implements Action {

	@Override
	public void execute() throws Exception {

		Long idCliente = Console.readLong("Id del cliente");
		AdminService as = Factory.service.forAdmin();
		List<VoucherDto> bonos = as.findVouchersByClientId(idCliente);

		Console.println("\nListado de bonos del cliente: " + idCliente + "\n");
		Printer.printVouchers(bonos);
	}

}
