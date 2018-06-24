package uo.ri.business.impl.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import uo.ri.business.dto.CardDto;
import uo.ri.business.dto.ClientDto;
import uo.ri.business.dto.InvoiceDto;
import uo.ri.business.dto.MechanicDto;
import uo.ri.business.dto.PaymentMeanDto;
import uo.ri.business.dto.VoucherDto;
import uo.ri.business.dto.VoucherSummary;
import uo.ri.model.Bono;
import uo.ri.model.Cliente;
import uo.ri.model.Factura;
import uo.ri.model.Mecanico;
import uo.ri.model.MedioPago;
import uo.ri.model.Metalico;
import uo.ri.model.TarjetaCredito;
import uo.ri.model.types.Address;

/**
 * Clase encargada de parsear objetos DTO a entidades del sistema y viceversa.
 * 
 * @author José Antonio García García
 *
 */
public class DtoAssembler {

	public static Mecanico toEntity(MechanicDto dto) {
		return new Mecanico(dto.dni, dto.name, dto.surname);
	}

	public static List<MechanicDto> toMechanicDtoList(List<Mecanico> list) {
		List<MechanicDto> res = new ArrayList<>();
		for (Mecanico m : list) {
			res.add(toDto(m));
		}
		return res;
	}

	public static MechanicDto toDto(Mecanico m) {
		MechanicDto dto = new MechanicDto();
		dto.id = m.getId();
		dto.dni = m.getDni();
		dto.name = m.getNombre();
		dto.surname = m.getApellidos();
		return dto;
	}

	public static VoucherSummary toDto(Cliente cli, List<Bono> listaBonos) {
		double consumido = 0.0;
		double disponible = 0.0;
		for (Bono bono : listaBonos) {
			consumido += bono.getAcumulado();
			disponible += bono.getDisponible();
		}
		VoucherSummary dto = new VoucherSummary();
		dto.dni = cli.getDni();
		dto.name = cli.getNombre();
		dto.surname = cli.getApellidos();
		dto.emitted = listaBonos.size();
		dto.consumed = consumido;
		dto.available = disponible;
		dto.totalAmount = dto.consumed + dto.available;
		return dto;
	}

	public static List<VoucherDto> toVoucherDtoList(List<Bono> list) {
		List<VoucherDto> res = new ArrayList<>();
		for (Bono b : list) {
			res.add(toDto(b));
		}
		return res;
	}

	public static VoucherDto toDto(Bono b) {
		VoucherDto dto = new VoucherDto();
		dto.id = b.getId();
		dto.clientId = b.getCliente().getId();
		dto.accumulated = b.getAcumulado();
		dto.code = b.getCodigo();
		dto.description = b.getDescripcion();
		dto.available = b.getDisponible();
		return dto;
	}

	public static TarjetaCredito toEntity(CardDto dto) {
		return new TarjetaCredito(dto.cardNumber, dto.cardType,
				dto.cardExpiration);
	}

	public static Bono toEntity(VoucherDto dto) {
		return new Bono(dto.code, dto.description, dto.available);
	}

	public static InvoiceDto toDto(Factura factura) {
		InvoiceDto dto = new InvoiceDto();
		dto.id = factura.getId();
		dto.number = factura.getNumero();
		dto.date = factura.getFecha();
		dto.total = factura.getImporte();
		dto.vat = factura.getIva();
		dto.status = factura.getStatus().toString();
		return dto;
	}

	public static List<PaymentMeanDto> toPaymentMeanDtoList(
			List<MedioPago> list) {
		return list.stream().map(mp -> toDto(mp)).collect(Collectors.toList());
	}

	private static PaymentMeanDto toDto(MedioPago mp) {
		if (mp instanceof Bono) {
			return toDto((Bono) mp);
		} else if (mp instanceof TarjetaCredito) {
			return toDto(mp);
		} else if (mp instanceof Metalico) {
			return toDto(mp);
		} else {
			throw new RuntimeException("Unexpected type of payment mean");
		}
	}

	public static Cliente toEntity(ClientDto dto) {
		Cliente c = new Cliente(dto.dni, dto.name, dto.surname);
		Address addr = new Address(dto.addressStreet, dto.addressCity,
				dto.addressZipcode);
		c.setAddress(addr);
		c.setEmail(dto.email);
		c.setPhone(dto.phone);
		return c;
	}

	public static List<ClientDto> toClientDtoList(List<Cliente> clientes) {
		List<ClientDto> res = new ArrayList<>();
		for (Cliente c : clientes) {
			res.add(toDto(c));
		}
		return res;
	}

	public static ClientDto toDto(Cliente c) {
		ClientDto dto = new ClientDto();

		dto.id = c.getId();
		dto.dni = c.getDni();
		dto.name = c.getNombre();
		dto.surname = c.getApellidos();
		dto.addressCity = c.getAddress().getCity();
		dto.addressStreet = c.getAddress().getStreet();
		dto.addressZipcode = c.getAddress().getZipcode();
		dto.email = c.getEmail();
		dto.phone = c.getPhone();

		return dto;
	}

}
