package uo.ri.business.impl.admin.mechanic;

import uo.ri.business.dto.MechanicDto;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.MecanicoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Mecanico;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.exception.Check;

public class UpdateMechanic implements Command<Void> {

	private MechanicDto dto;

	public UpdateMechanic(MechanicDto dto) {
		this.dto = dto;
	}

	@Override
	public Void execute() throws BusinessException {
		MecanicoRepository r = Factory.repository.forMechanic();
		Mecanico m = r.findById(dto.id);
		Check.isNotNull(m, "El mecanico no existe");
		m.setApellidos(dto.surname);
		m.setNombre(dto.name);
		return null;
	}

}
