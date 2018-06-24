package uo.ri.ui.cash;

import alb.util.menu.BaseMenu;
import uo.ri.ui.cash.action.paymentMethods.AddCardAction;
import uo.ri.ui.cash.action.paymentMethods.AddVoucherAction;
import uo.ri.ui.cash.action.paymentMethods.DeletePaymentMethodAction;
import uo.ri.ui.cash.action.paymentMethods.FindMediosPagoByClientAction;

public class PaymentMethodsMenu extends BaseMenu{
	
	public PaymentMethodsMenu() {
		menuOptions = new Object[][] { 
			{"Caja > Gestión de medios de pago", null},
			
			{ "Añadir tarjeta de crédito", 	AddCardAction.class }, 
			{ "Añadir bono",				AddVoucherAction.class }, 
			{ "Eliminar medio de pago", 	DeletePaymentMethodAction.class }, 
			{ "Listar medios de pago",		FindMediosPagoByClientAction.class },
		};
	}

}
