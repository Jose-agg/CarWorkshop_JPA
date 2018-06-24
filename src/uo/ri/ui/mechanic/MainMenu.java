package uo.ri.ui.mechanic;

import alb.util.menu.BaseMenu;
import alb.util.menu.NotYetImplementedAction;
import uo.ri.business.impl.BusinessServiceFactory;
import uo.ri.conf.Factory;
import uo.ri.persistence.jpa.JpaRepositoryFactory;
import uo.ri.persistence.jpa.executor.JpaExecutorFactory;

public class MainMenu extends BaseMenu {

	public MainMenu() {
		menuOptions = new Object[][] { { "Mecánico", null },
				{ "Listar reparaciones asignadas",
						NotYetImplementedAction.class },
				{ "Añadir repuestos a reparación",
						NotYetImplementedAction.class },
				{ "Eliminar repuestos a reparación",
						NotYetImplementedAction.class },
				{ "Cerrar una reparación", NotYetImplementedAction.class }, };
	}

	public static void main(String[] args) {
		new MainMenu().config().execute();
	}

	/**
	 * Configures the main components of the application
	 * @return this
	 */
	private MainMenu config() {
		Factory.service = new BusinessServiceFactory();
		Factory.repository = new JpaRepositoryFactory();
		Factory.executor = new JpaExecutorFactory();

		return this;
	}

}
