package uo.ri.business.impl.admin.mechanic;

import uo.ri.business.dto.MechanicDto;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.MecanicoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Mecanico;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.exception.Check;

public class AddMechanic implements Command<Void> {

	private MechanicDto dto;
	private MecanicoRepository r = Factory.repository.forMechanic();

	public AddMechanic(MechanicDto mecanico) {
		this.dto = mecanico;
	}

	@Override
	public Void execute() throws BusinessException {
		Mecanico m = DtoAssembler.toEntity(dto);
		assertNotRepeatDni(dto.dni);
		r.add(m);
		return null;
	}

	private void assertNotRepeatDni(String dni) throws BusinessException {
		Mecanico m = r.findByDni(dni);
		Check.isNull(m, "Ese dni ya existe");
	}

}
