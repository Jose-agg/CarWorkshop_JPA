package uo.ri.model;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import uo.ri.util.exception.BusinessException;
import uo.ri.util.exception.Check;

@Entity
@Table(name = "TTarjetascredito")
@DiscriminatorValue("TTarjetasCredito")
public class TarjetaCredito extends MedioPago {

	@Column(unique = true)
	private String numero;
	private String tipo;
	private Date validez;

	TarjetaCredito() {
	};

	public TarjetaCredito(String numero) {
		super();
		this.numero = numero;
		Instant t0 = Instant.now();
		Date in24HoursTime = Date.from(t0.plus(1, ChronoUnit.DAYS));
		this.validez = in24HoursTime;
		this.tipo = "UNKNOWN";
	}

	public TarjetaCredito(String string, String string2, Date yesterday) {
		this(string);
		this.tipo = string2;
		this.validez = yesterday;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Date getValidez() {
		return (Date) validez.clone();
	}

	public void setValidez(Date validez) {
		this.validez = validez;
	}

	public String getNumero() {
		return numero;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TarjetaCredito other = (TarjetaCredito) obj;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TarjetaCredito [numero=" + numero + ", tipo=" + tipo
				+ ", validez=" + validez + "]";
	}

	@Override
	public void pagar(double cantidad) throws BusinessException {
		if (!isValidNow()) {
			throw new BusinessException("La tarjeta está caducada");
		} else {
			acumulado += cantidad;
		}
	}

	public boolean isValidNow() {
		if (new Date().getTime() < getValidez().getTime()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Método que comprueba si la fecha de caducidad de la tarjeta es válida
	 * @throws BusinessException
	 */
	public void comprobarFecha() throws BusinessException {
		boolean comprobar = new Date().after(getValidez());
		Check.isFalse(comprobar,
				"La fecha de validez de la tarjeta ha caducado");
	}
}
