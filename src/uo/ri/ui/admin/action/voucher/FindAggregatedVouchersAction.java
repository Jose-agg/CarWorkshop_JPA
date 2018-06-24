package uo.ri.ui.admin.action.voucher;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.AdminService;
import uo.ri.business.dto.VoucherSummary;
import uo.ri.conf.Factory;
import uo.ri.ui.util.Printer;

/**
 * Interacción con el usuario que muestra un listado de todos los clientes,
 * con la información agregada de los bonos de cada uno
 * @author yeahb
 *
 */
public class FindAggregatedVouchersAction implements Action {

	@Override
	public void execute() throws Exception {
		AdminService as = Factory.service.forAdmin();
		List<VoucherSummary> vouchers = as.getVoucherSummary();

		Console.println(
				"\nInformación agregada de los bonos de cada cliente\n");
		Printer.printVoucherSummary(vouchers);
	}

}
