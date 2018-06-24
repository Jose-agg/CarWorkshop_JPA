package uo.ri.persistence.jpa.executor;

import uo.ri.business.impl.ComandExecutorFactory;
import uo.ri.business.impl.CommandExecutor;

/**
 * Clase que crea una instancia de un ejecutador de comandos de JPA
 * 
 * @author José Antonio García García
 *
 */
public class JpaExecutorFactory implements ComandExecutorFactory {

	@Override
	public CommandExecutor forExecutor() {
		return new JpaCommandExecutor();
	}

}
