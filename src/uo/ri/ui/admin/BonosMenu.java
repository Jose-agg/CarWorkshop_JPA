package uo.ri.ui.admin;

import alb.util.menu.BaseMenu;
import uo.ri.ui.admin.action.voucher.FindAggregatedVouchersAction;
import uo.ri.ui.admin.action.voucher.FindVouchersAction;
import uo.ri.ui.admin.action.voucher.GenerateVouchersAction;

public class BonosMenu extends BaseMenu {

	public BonosMenu() {
		menuOptions = new Object[][] {
				{ "Administrador > Gestión de bonos", 	null },
				{ "Generar bonos", 						GenerateVouchersAction.class },
				{ "Listado de bonos de cliente", 		FindVouchersAction.class }, 
				{ "Listado de informacion agregada de bonos de cada cliente", 	
														FindAggregatedVouchersAction.class }, 
		};
	}
}
