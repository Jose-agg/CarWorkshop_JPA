package uo.ri.ui.foreman;

import alb.util.menu.BaseMenu;
import uo.ri.ui.foreman.action.client.AddClientAction;
import uo.ri.ui.foreman.action.client.DeleteClientAction;
import uo.ri.ui.foreman.action.client.FindAllClientsAction;
import uo.ri.ui.foreman.action.client.FindClientByIdAction;
import uo.ri.ui.foreman.action.client.FindRecomendedClientsByClientIdAction;
import uo.ri.ui.foreman.action.client.UpdateClientAction;

public class ClientesMenu extends BaseMenu {

	public ClientesMenu() {
		menuOptions = new Object[][] { 
				{ "Jefe de Taller > Gestión de Clientes", null },
				{ "Añadir cliente", 			AddClientAction.class }, 
				{ "Eliminar cliente", 			DeleteClientAction.class }, 
				{ "Listar clientes", 			FindAllClientsAction.class },
				{ "Mostrar datos cliente", 		FindClientByIdAction.class },
				{ "Listar clientes recomendados por un cliente", 
								FindRecomendedClientsByClientIdAction.class },
				{ "Modificar datos de cliente", UpdateClientAction.class }, 
			};
	}

}
