package uo.ri.persistence.jpa;

import java.util.List;

import uo.ri.business.repository.MecanicoRepository;
import uo.ri.model.Mecanico;
import uo.ri.persistence.jpa.util.Jpa;

public class MechanicJpaRepository implements MecanicoRepository {

	@Override
	public void add(Mecanico t) {
		Jpa.getManager().persist(t);
	}

	@Override
	public void remove(Mecanico t) {
		Jpa.getManager().remove(t);
	}

	@Override
	public Mecanico findById(Long id) {
		return Jpa.getManager().find(Mecanico.class, id);
	}

	@Override
	public List<Mecanico> findAll() {
		return Jpa.getManager()
				.createNamedQuery("Mecanico.findAll", Mecanico.class)
				.getResultList();
	}

	@Override
	public Mecanico findByDni(String dni) {
		return Jpa.getManager()
				.createNamedQuery("Mecanico.findByDni", Mecanico.class)
				.setParameter(1, dni).getResultList().stream().findFirst()
				.orElse(null);
	}

}
