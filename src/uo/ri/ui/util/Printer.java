package uo.ri.ui.util;

import java.text.SimpleDateFormat;
import java.util.List;

import alb.util.console.Console;
import uo.ri.business.dto.BreakdownDto;
import uo.ri.business.dto.CardDto;
import uo.ri.business.dto.ClientDto;
import uo.ri.business.dto.InvoiceDto;
import uo.ri.business.dto.MechanicDto;
import uo.ri.business.dto.PaymentMeanDto;
import uo.ri.business.dto.VoucherDto;
import uo.ri.business.dto.VoucherSummary;

public class Printer {

	public static void printInvoice(InvoiceDto invoice) {

		double importeConIVa = invoice.total;
		double iva = invoice.vat;
		double importeSinIva = importeConIVa / (1 + iva / 100);

		Console.printf("Factura nº: %d\n", invoice.number);
		Console.printf("\tFecha: %1$td/%1$tm/%1$tY\n", invoice.date);
		Console.printf("\tTotal: %.2f €\n", importeSinIva);
		Console.printf("\tIva: %.1f %% \n", invoice.vat);
		Console.printf("\tTotal con IVA: %.2f €\n", invoice.total);
		Console.printf("\tEstado: %s\n", invoice.status);
	}

	public static void printPaymentMeans(List<PaymentMeanDto> medios) {
		Console.println();
		Console.println("Medios de pago disponibles");

		Console.printf("\t%s \t%-8.8s \t%s \n", "ID", "Tipo", "Acumulado");
		for (PaymentMeanDto medio : medios) {
			printPaymentMean(medio);
		}
	}

	private static void printPaymentMean(PaymentMeanDto medio) {
		Console.printf("\t%s \t%-8.8s \t%s \n", medio.id,
				medio.getClass().getName(), // not the best...
				medio.accumulated);
	}

	public static void printRepairing(BreakdownDto rep) {

		Console.printf("\t%d \t%-40.40s \t%td/%<tm/%<tY \t%-12.12s \t%.2f\n",
				rep.id, rep.description, rep.date, rep.status, rep.total);
	}

	public static void printMechanic(MechanicDto m) {

		Console.printf("\t%d %-10.10s %-25.25s %-25.25s\n", m.id, m.dni, m.name,
				m.surname);
	}

	public static void printVoucherSummary(List<VoucherSummary> vouchers) {

		for (VoucherSummary v : vouchers) {
			Console.printf(
					"\tDNI: %s, \tNOMBRE: %s, \tBONOS EMITIDOS: %d, "
							+ "\tIMPORTE TOTAL: %.2f, \tTOTAL CONSUMIDO: %.2f, "
							+ "\tTOTAL DISPONIBLE: %.2f \n",
					v.dni, v.name, v.emitted, v.totalAmount, v.consumed,
					v.available);
		}
	}

	public static void printVouchers(List<VoucherDto> bonos) {
		int numBonos = 0;
		double consumido = 0.0, disponible = 0.0;
		for (VoucherDto v : bonos) {
			Console.printf(
					"\tCÓDIGO: %s \tDESCRIPCIÓN: %s \tDISPONIBLE: %.2f"
							+ "\tACUMULADO: %.2f\n",
					v.code, v.description, v.available, v.accumulated);
			numBonos++;
			consumido += v.accumulated;
			disponible += v.available;
		}
		Console.printf(
				"\n\tTOTAL BONOS: %d \tTOTAL IMPORTE: %.2f "
						+ "\tTOTAL ACUMULADO: %.2f \tTOTAL DISPONIBLE: %.2f\n",
				numBonos, consumido + disponible, consumido, disponible);
	}

	public static void printPaymentMeansBetter(
			List<PaymentMeanDto> mediosPago) {
		for (PaymentMeanDto dto : mediosPago) {
			if (dto instanceof VoucherDto) {
				Console.printf(
						"\tID: %s \t%s \tCÓDIGO: %s \tDESCRIPCIÓN: %s "
								+ "\tDISPONIBLE: %.2f \tACUMULADO: %.2f\n",
						dto.id, "BONO", ((VoucherDto) dto).code,
						((VoucherDto) dto).description,
						((VoucherDto) dto).available, dto.accumulated);
			} else if (dto instanceof CardDto) {
				SimpleDateFormat formatoDelTexto = new SimpleDateFormat(
						"yyyy-MM-dd");
				Console.printf(
						"\tID: %s \t%s \tNÚMERO: %s \tVALIDEZ: %s "
								+ "\tTIPO: %s \tACUMULADO: %.2f\n",
						dto.id, "TARJETA CRÉDITO", ((CardDto) dto).cardNumber,
						formatoDelTexto.format(((CardDto) dto).cardExpiration),
						((CardDto) dto).cardType, dto.accumulated);
			} else {
				Console.printf("\tID: %s \t%s \tACUMULADO: %.2f\n", dto.id,
						"METÁLICO", dto.accumulated);
			}

		}
	}

	public static void printClient(ClientDto c) {
		Console.println("Formato: id, dni, nombre, apellidos, ciudad, calle, "
				+ "codigo postal, email, telefono");
		Console.printf(
				"\t%d, %-10.10s, %-25.25s, %-25.25s, %-25.25s, %-25.25s,"
						+ " %-25.25s, %-25.25s, %-25.25s\n",
				c.id, c.dni, c.name, c.surname, c.addressCity, c.addressStreet,
				c.addressZipcode, c.email, c.phone);
	}

}
