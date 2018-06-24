package uo.ri.business.impl;

import uo.ri.util.exception.BusinessException;

/**
 * Interfaz que permite la ejecucion de comandos
 * 
 * @author José Antonio García García
 *
 */
public interface CommandExecutor {

	<T> T execute(Command<T> cmd) throws BusinessException;

}