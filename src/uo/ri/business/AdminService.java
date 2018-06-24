package uo.ri.business;

import java.util.List;

import uo.ri.business.dto.MechanicDto;
import uo.ri.business.dto.VoucherDto;
import uo.ri.business.dto.VoucherSummary;
import uo.ri.util.exception.BusinessException;

/**
 * Interfaz que declara los métodos que serán implementados por la clase de
 * logica asociada al administrador (AdminServiceImpl)
 * 
 * @author José Antonio García García
 */
public interface AdminService {

	/**
	 * Metodo que añade un nuevo mecanico al sistema
	 * 
	 * @param mecanico Datos del nuevo mecanico a añadir
	 * @throws BusinessException
	 */
	public void newMechanic(MechanicDto mecanico) throws BusinessException;

	/**
	 * Metodo que borra un mecanico del sistema
	 * 
	 * @param idMecanico Identificador del mecanico a borrar
	 * @throws BusinessException
	 */
	public void deleteMechanic(Long idMecanico) throws BusinessException;

	/**
	 * Metodo que actualiza los datos basicos de un mecanico del sistema
	 * 
	 * @param mecanico Datos a modificar de un mecanico del sistema
	 * @throws BusinessException
	 */
	public void updateMechanic(MechanicDto mecanico) throws BusinessException;

	/**
	 * Metodo que devuelve un mecanico del sistema a traves de su identificador
	 * 
	 * @param id Identificador del mecanico
	 * @return objeto con los datos del mecanico
	 * @throws BusinessException
	 */
	public MechanicDto findMechanicById(Long id) throws BusinessException;

	/**
	 * Metodo que devuelve la lista de mecanicos que hay actualmente en el 
	 * sistema
	 * 
	 * @return lista con todos los mecanicos del sistema
	 * @throws BusinessException
	 */
	public List<MechanicDto> findAllMechanics() throws BusinessException;

	/**
	 * Metodo que devuelve un resumen de los bonos de cada cliente así como 
	 * informacion agregada
	 * 
	 * @return lista de bonos por cliente
	 * @throws BusinessException
	 */
	public List<VoucherSummary> getVoucherSummary() throws BusinessException;

	/**
	 * Metodo que devuelve todos los bonos que tiene un cliente
	 * 
	 * @param idCliente Identificador del cliente a buscar
	 * @return lista de bonos del cliente
	 * @throws BusinessException
	 */
	public List<VoucherDto> findVouchersByClientId(Long idCliente)
			throws BusinessException;

	/**
	 * Metodo que genera bonos a partir de las siguientes 3 reglas:
	 *  - Bonos por cada 3 recomendaciones.
	 *  - Bonos por cada 3 averias.
	 *  - Bonos por facturas superiores a 500€
	 * 
	 * @return numero total de bonos generados.
	 * @throws BusinessException
	 */
	public int generateVouchers() throws BusinessException;

}
