package uo.ri.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import alb.util.math.Round;
import uo.ri.model.types.AveriaStatus;
import uo.ri.model.types.FacturaStatus;
import uo.ri.util.exception.BusinessException;

@Entity
@Table(name = "TFacturas")
public class Factura {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(unique = true)
	private Long numero;
	private Date fecha;
	private double importe;
	private double iva;
	@Enumerated(EnumType.STRING)
	private FacturaStatus status = FacturaStatus.SIN_ABONAR;

	@OneToMany(mappedBy = "factura")
	private Set<Averia> averias = new HashSet<>();
	@OneToMany(mappedBy = "factura")
	private Set<Cargo> cargos = new HashSet<>();

	private boolean usada_bono = false;

	Factura() {
	};

	public Long getId() {
		return id;
	}

	public Factura(Long numero) {
		super();
		this.fecha = new Date();
		this.numero = numero;
	}

	public Factura(long l, Date today) {
		this(l);
		this.fecha = today;
	}

	public Factura(long l, List<Averia> averias2)
			throws uo.ri.util.exception.BusinessException {
		this(l);
		for (Averia a : averias2) {
			addAveria(a);
		}
	}

	public Factura(long l, Date jUNE_6_2012, List<Averia> averias2)
			throws uo.ri.util.exception.BusinessException {
		this(l, jUNE_6_2012);
		for (Averia a : averias2) {
			addAveria(a);
		}
	}

	/**
	 * Añade  la averia a la factura
	 * @param averia
	 * @throws BusinessException 
	 */
	public void addAveria(Averia averia)
			throws uo.ri.util.exception.BusinessException {
		if (getStatus() == FacturaStatus.SIN_ABONAR
				&& averia.getStatus() == AveriaStatus.TERMINADA) {
			Association.Facturar.link(this, averia);
			averia.markAsInvoiced();
			calcularImporte();
		} else {
			throw new uo.ri.util.exception.BusinessException(
					"Averia no terminada");
		}
	}

	/**
	 * Calcula el importe de la factura y su IVA, teniendo en cuenta la fecha de 
	 * factura
	 */
	void calcularImporte() {
		importe = 0.0;
		for (Averia averia : averias) {
			importe += averia.getImporte();
		}
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
		Date fechaAux = null;
		try {
			fechaAux = formatoDelTexto.parse("2012-07-01");
		} catch (ParseException ex) {
		}
		if (fecha.after(fechaAux)) {
			setIva(0.21);
		} else {
			setIva(0.18);
		}

		importe += iva;
		importe = Round.twoCents(importe);
	}

	/**
	 * Elimina una averia de la factura, solo si está SIN_ABONAR y recalcula 
	 * el importe
	 * @param averia
	 */
	public void removeAveria(Averia averia) {
		if (status == FacturaStatus.SIN_ABONAR) {
			Association.Facturar.unlink(this, averia);
			averia.markBackToFinished();
			calcularImporte();
		}
	}

	public double getIva() {
		return iva;
	}

	public void setIva(double iva) {
		this.iva = importe * iva;
	}

	public Long getNumero() {
		return numero;
	}

	public Date getFecha() {
		return (Date) fecha.clone();
	}

	public double getImporte() {
		return importe;
	}

	public FacturaStatus getStatus() {
		return status;
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
		Factura other = (Factura) obj;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Factura [numero=" + numero + ", fecha=" + fecha + ", importe="
				+ importe + ", iva=" + iva + ", status=" + status + ", averias="
				+ averias + "]";
	}

	Set<Averia> _getAverias() {
		return averias;
	}

	public Set<Averia> getAverias() {
		return new HashSet<>(averias);
	}

	public Set<Cargo> getCargos() {
		return new HashSet<>(cargos);
	}

	Set<Cargo> _getCargos() {
		return cargos;
	}

	public void setFecha(Date today) {
		this.fecha = today;

	}

	public void settle() throws BusinessException {
		if (averias.isEmpty())
			throw new BusinessException(
					"No se puede liquidar una factura sin averias");
		else if (importeCargos() > getImporte() + 0.01)
			throw new BusinessException("Los cargos no igualan el importe");
		else if (importeCargos() < getImporte() - 0.01)
			throw new BusinessException("Los cargos no igualan el importe");

		this.status = FacturaStatus.ABONADA;
	}

	private double importeCargos() {
		double importeCargos = 0.0;
		for (Cargo cargo : cargos) {
			importeCargos += cargo.getImporte();
		}
		return importeCargos;
	}

	public boolean isSettled() {
		if (getStatus() == FacturaStatus.ABONADA) {
			return true;
		} else {
			return false;
		}
	}

	public boolean puedeGenerarBono500() {
		if (importe > 500 && status == FacturaStatus.ABONADA
				&& !getUsadaParaBono()) {
			return true;
		}
		return false;
	}

	public void markAsBono500Used() throws BusinessException {
		if (puedeGenerarBono500()) {
			usada_bono = true;
		} else {
			throw new BusinessException(
					"No se puede marcar la factura como usada para bono");
		}
	}

	public boolean getUsadaParaBono() {
		return usada_bono;
	}

	public boolean isBono500Used() {
		return usada_bono;
	}

}
