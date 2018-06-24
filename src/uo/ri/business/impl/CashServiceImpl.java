package uo.ri.business.impl;

import java.util.List;
import java.util.Map;

import uo.ri.business.CashService;
import uo.ri.business.dto.BreakdownDto;
import uo.ri.business.dto.CardDto;
import uo.ri.business.dto.InvoiceDto;
import uo.ri.business.dto.PaymentMeanDto;
import uo.ri.business.dto.VoucherDto;
import uo.ri.business.impl.cash.AddCard;
import uo.ri.business.impl.cash.AddVoucher;
import uo.ri.business.impl.cash.CreateInvoiceFor;
import uo.ri.business.impl.cash.DeletePaymentMethod;
import uo.ri.business.impl.cash.FindInvoiceByNumber;
import uo.ri.business.impl.cash.FindPaymentMethodsByClientId;
import uo.ri.business.impl.cash.FindPaymentMethodsByInvoice;
import uo.ri.business.impl.cash.LiquidateInvoice;
import uo.ri.conf.Factory;
import uo.ri.util.exception.BusinessException;

public class CashServiceImpl implements CashService {

	CommandExecutor executor = Factory.executor.forExecutor();

	@Override
	public InvoiceDto createInvoiceFor(List<Long> idsAveria)
			throws BusinessException {
		return executor.execute(new CreateInvoiceFor(idsAveria));
	}

	@Override
	public InvoiceDto findInvoiceByNumber(Long numeroFactura)
			throws BusinessException {
		return executor.execute(new FindInvoiceByNumber(numeroFactura));
	}

	@Override
	public List<PaymentMeanDto> findPaymentMethodsForInvoice(Long idFactura)
			throws BusinessException {
		return executor.execute(new FindPaymentMethodsByInvoice(idFactura));
	}

	@Override
	public InvoiceDto liquidateInvoice(Long idFactura, Map<Long, Double> cargos)
			throws BusinessException {
		return executor.execute(new LiquidateInvoice(idFactura, cargos));

	}

	@Override
	public List<BreakdownDto> findBreakdownByClient(String dni)
			throws BusinessException {
		return null;
	}

	@Override
	public void addCardPaymentMethod(CardDto card) throws BusinessException {
		executor.execute(new AddCard(card));
	}

	@Override
	public void addVoucherPaymentMethod(VoucherDto voucher)
			throws BusinessException {
		executor.execute(new AddVoucher(voucher));
	}

	@Override
	public void deletePaymentMethod(Long idMedioPago) throws BusinessException {
		executor.execute(new DeletePaymentMethod(idMedioPago));
	}

	@Override
	public List<PaymentMeanDto> findPaymentMethodsByClientId(Long id)
			throws BusinessException {
		return executor.execute(new FindPaymentMethodsByClientId(id));
	}

	@Override
	public void addCardPaymentMean(CardDto card) throws BusinessException {
		addCardPaymentMethod(card);
	}

	@Override
	public void addVoucherPaymentMean(VoucherDto voucher)
			throws BusinessException {
		addVoucherPaymentMethod(voucher);
	}

	@Override
	public void deletePaymentMean(Long id) throws BusinessException {
		deletePaymentMethod(id);
	}

	@Override
	public InvoiceDto settleInvoice(Long idFactura, Map<Long, Double> mapa)
			throws BusinessException {
		return liquidateInvoice(idFactura, mapa);
	}

}
