package uo.ri.business.impl;

import java.util.List;

import uo.ri.business.AdminService;
import uo.ri.business.dto.MechanicDto;
import uo.ri.business.dto.VoucherDto;
import uo.ri.business.dto.VoucherSummary;
import uo.ri.business.impl.admin.mechanic.AddMechanic;
import uo.ri.business.impl.admin.mechanic.DeleteMechanic;
import uo.ri.business.impl.admin.mechanic.FindAllMechanics;
import uo.ri.business.impl.admin.mechanic.FindMechanicById;
import uo.ri.business.impl.admin.mechanic.UpdateMechanic;
import uo.ri.business.impl.admin.voucher.FindVouchersAggregateInformation;
import uo.ri.business.impl.admin.voucher.FindVouchersByClient;
import uo.ri.business.impl.admin.voucher.GenerateVouchers;
import uo.ri.conf.Factory;
import uo.ri.util.exception.BusinessException;

public class AdminServiceImpl implements AdminService {

	private CommandExecutor executor = Factory.executor.forExecutor();

	@Override
	public void newMechanic(MechanicDto mecanico) throws BusinessException {
		executor.execute(new AddMechanic(mecanico));
	}

	@Override
	public void deleteMechanic(Long idMecanico) throws BusinessException {
		executor.execute(new DeleteMechanic(idMecanico));

	}

	@Override
	public void updateMechanic(MechanicDto mecanico) throws BusinessException {
		executor.execute(new UpdateMechanic(mecanico));
	}

	@Override
	public MechanicDto findMechanicById(Long id) throws BusinessException {
		return executor.execute(new FindMechanicById(id));
	}

	@Override
	public List<MechanicDto> findAllMechanics() throws BusinessException {
		return executor.execute(new FindAllMechanics());
	}

	@Override
	public List<VoucherSummary> getVoucherSummary() throws BusinessException {
		return executor.execute(new FindVouchersAggregateInformation());
	}

	@Override
	public List<VoucherDto> findVouchersByClientId(Long idCliente)
			throws BusinessException {
		return executor.execute(new FindVouchersByClient(idCliente));
	}

	@Override
	public int generateVouchers() throws BusinessException {
		return executor.execute(new GenerateVouchers());
	}

}
