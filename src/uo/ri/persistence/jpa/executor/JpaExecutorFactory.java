package uo.ri.persistence.jpa.executor;

import uo.ri.business.impl.CommandExecutorFactory;
import uo.ri.business.impl.CommandExecutor;

/**
 * Clase que crea una instancia de un ejecutador de comandos de JPA
 * 
 * @author José Antonio García García
 *
 */
public class JpaExecutorFactory implements CommandExecutorFactory {

	@Override
	public CommandExecutor forExecutor() {
		return new JpaCommandExecutor();
	}

}
