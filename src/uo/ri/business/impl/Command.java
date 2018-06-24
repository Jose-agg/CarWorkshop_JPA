package uo.ri.business.impl;

import uo.ri.util.exception.BusinessException;

/**
 * Interfaz que permite crear comandos que encapsulen comportamientos y 
 * así poder ejecutarlos y, si fuera necesario, deshacerlos en caso de fallo
 * 
 * @author José Antonio García García
 *
 * @param <T> Tipo con el que tratará el objeto
 */
public interface Command<T> {

	T execute() throws BusinessException;
}
