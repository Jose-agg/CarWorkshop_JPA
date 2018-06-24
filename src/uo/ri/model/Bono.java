package uo.ri.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import uo.ri.util.exception.BusinessException;

@Entity
@Table(name = "TBonos")
@DiscriminatorValue("TBonos")
public class Bono extends MedioPago {

	protected double disponible = 0.0;
	private String descripcion = "";
	@Column(unique = true)
	private String codigo;

	Bono() {
	};

	public Bono(String codigo) {
		super();
		this.codigo = codigo;
	}

	public Bono(String string, double d) {
		this(string);
		this.disponible = d;
	}

	public Bono(String code, String descripcion, double i) {
		this(code);
		this.descripcion = descripcion;
		this.disponible = i;
	}

	public Double getDisponible() {
		return disponible;
	}

	public void setDisponible(double disponible) {
		this.disponible = disponible;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCodigo() {
		return codigo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		Bono other = (Bono) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Bono [disponible=" + disponible + ", descripcion=" + descripcion
				+ ", codigo=" + codigo + "]";
	}

	@Override
	public void pagar(double cantidad) throws BusinessException {
		if (cantidad > disponible)
			throw new BusinessException("No hay suficiente dinero disponible");
		disponible -= cantidad;
		acumulado += cantidad;

	}

	/**
	 * Método que genera un código para bono. Recorre los bonos de la base de datos
	 * y devuelve un código, sumándole +10 al último código existente.
	 * Ejemplo: B1990 -> B2000
	 * @param medios La lista de medios de pago de la base de datos
	 * @return El nuevo código generado
	 */
	public static String generarCodigo(List<MedioPago> medios) {
		String cod = "B1000";
		for (MedioPago mp : medios) {
			if (mp instanceof Bono) {
				if (((Bono) mp).getCodigo().compareTo(cod) > 0) {
					cod = ((Bono) mp).getCodigo();
				}
			}
		}
		String[] parts = cod.split("B");
		int parte = Integer.parseInt(parts[1]);
		return "B" + (parte + 10);
	}

}
