package uo.ri.business;

/**
 * Clase resultante de aplicar el patron factoría que permite 
 * crear instancias de los servicios que serán solicitados por la 
 * capa de presentación
 * 
 * @author José Antonio García García
 *
 */
public interface ServiceFactory {

	/**
	 * Devuelve una instancia del servicio de administrador
	 * 
	 * @return Instancia del servicio de administrador
	 */
	AdminService forAdmin();

	/**
	 * Devuelve una instancia del servicio de la caja
	 * 
	 * @return Instancia del servicio de la caja
	 */
	CashService forCash();

	/**
	 * Devuelve un instancia del servicio de jefe de taller
	 * 
	 * @return Instancia del servicio de jefe de taller
	 */
	ForemanService forForeman();

	MechanicService forMechanic();

}
