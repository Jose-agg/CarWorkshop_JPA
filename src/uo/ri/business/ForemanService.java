package uo.ri.business;

import java.util.List;

import uo.ri.business.dto.ClientDto;
import uo.ri.util.exception.BusinessException;

/**
 * Interfaz que declara los métodos que serán implementados por la clase de
 * logica asociada al jefe de taller (ForemanServiceImpl)
 * 
 * @author José Antonio García García
 */
public interface ForemanService {

	/**
	 * Metodo que añade un nuevo cliente al sistema
	 * 
	 * @param client Datos del cliente a añadir
	 * @param recomenderId Identificador del cliente que le ha recomendado
	 * @throws BusinessException
	 */
	public void addClient(ClientDto client, Long recomenderId)
			throws BusinessException;

	/**
	 * Metodo que borra un cliente del sistema
	 * 
	 * @param idCliente Identificador del cliente a borrar
	 * @throws BusinessException
	 */
	public void deleteClient(Long idCliente) throws BusinessException;

	/**
	 * Metodo que devuelve todos los clientes del sistema
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public List<ClientDto> findAllClients() throws BusinessException;

	/**
	 * Metodo que devuelve toda la informacion de un cliente
	 * 
	 * @param id Identificador del cliente a buscar
	 * @return datos del cliente
	 * @throws BusinessException
	 */
	public ClientDto findClientById(Long id) throws BusinessException;

	/**
	 * Metodo que devuelve todos los clientes que han sido recomendados por un
	 * cliente dado
	 * 
	 * @param id Identificador del cliente a buscar
	 * @return lista de clientes que han sido recomendados
	 * @throws BusinessException
	 */
	public List<ClientDto> findRecomendedClientsByClienteId(Long id)
			throws BusinessException;

	/**
	 * Metodo que actualiza los datos de un cliente del sistema
	 * 
	 * @param client Nuevos datos del cliente
	 * @throws BusinessException
	 */
	public void updateClient(ClientDto client) throws BusinessException;

}
