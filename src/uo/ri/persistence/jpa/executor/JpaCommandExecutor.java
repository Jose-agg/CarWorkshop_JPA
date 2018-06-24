package uo.ri.persistence.jpa.executor;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import uo.ri.business.impl.Command;
import uo.ri.business.impl.CommandExecutor;
import uo.ri.persistence.jpa.util.Jpa;
import uo.ri.util.exception.BusinessException;

/**
 * Clase que ejecuta los comandos necesarios para el funcionamiento de JPA
 * 
 * @author José Antonio García García
 *
 */
public class JpaCommandExecutor implements CommandExecutor {

	@Override
	public <T> T execute(Command<T> cmd) throws BusinessException {
		EntityManager mapper = Jpa.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();

		T res;
		try {
			res = cmd.execute();
			trx.commit();

		} catch (BusinessException | PersistenceException ex) {
			if (trx.isActive()) {
				trx.rollback();
			}
			throw ex;
		}
		if (mapper.isOpen()) {
			mapper.close();
		}

		return res;
	}
}
