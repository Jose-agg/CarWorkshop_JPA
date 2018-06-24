package uo.ri.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import alb.util.assertion.Assert;
import uo.ri.model.types.AveriaStatus;
import uo.ri.model.types.FacturaStatus;

@Entity
@Table(name = "TAverias", uniqueConstraints = {
		@UniqueConstraint(columnNames = "FECHA, VEHICULO_ID") })
public class Averia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	private String descripcion;
	@Temporal(TemporalType.DATE)
	private Date fecha;
	private double importe = 0.0;
	@Enumerated(EnumType.STRING)
	private AveriaStatus status = AveriaStatus.ABIERTA;
	private boolean usada_bono = false;

	@ManyToOne
	private Vehiculo vehiculo;
	@ManyToOne
	private Mecanico mecanico;
	@OneToMany(mappedBy = "averia")
	private Set<Intervencion> intervenciones = new HashSet<>();
	@ManyToOne
	private Factura factura;

	Averia() {
	}

	public Averia(Vehiculo vehiculo) {
		super();
		this.fecha = new Date();
		Association.Averiar.link(vehiculo, this);
	}

	public Long getId() {
		return id;
	}

	public void desassign() {
		Assert.isTrue(status == AveriaStatus.ASIGNADA);
		Association.Asignar.unlink(mecanico, this);
		status = AveriaStatus.ABIERTA;
	}

	public Averia(Vehiculo vehiculo, String descripcion) {
		this(vehiculo);
		this.descripcion = descripcion;
	}

	/**
	 * Asigna la averia al mecanico
	 * Solo se puede asignar una averia que está ABIERTA
	 * linkado de averia y mecanico
	 * la averia pasa a ASIGNADA	
	 * @param mecanico al que se le asigna la avería
	 */
	public void assignTo(Mecanico mecanico) {
		if (getStatus() == AveriaStatus.ABIERTA) {
			Association.Asignar.link(mecanico, this);
			status = AveriaStatus.ASIGNADA;
		}
	}

	/**
	 * El mecánico da por finalizada esta avería, entonces se calcula el 
	 * importe
	 * 
	 */
	public void markAsFinished() {
		if (getStatus() == AveriaStatus.ASIGNADA) {
			importe = 0.0;
			for (Intervencion inter : intervenciones) {
				importe = importe + inter.getImporte();
			}
			Association.Asignar.unlink(getMecanico(), this);
			status = AveriaStatus.TERMINADA;
		}
	}

	public void markAsInvoiced() throws uo.ri.util.exception.BusinessException {
		if (getStatus() == AveriaStatus.TERMINADA && getFactura() != null) {
			status = AveriaStatus.FACTURADA;
		} else {
			throw new uo.ri.util.exception.BusinessException(
					"No factura asignada");
		}
	}

	/**
	 * Una averia en estado TERMINADA se puede asignar a otro mecánico
	 * (el primero no ha podido terminar la reparación), pero debe ser pasada 
	 * a ABIERTA primero
	 */
	public void reopen() {
		if (getStatus() == AveriaStatus.TERMINADA) {
			status = AveriaStatus.ABIERTA;
		}
	}

	/**
	 * Una avería ya facturada se elimina de la factura
	 */
	public void markBackToFinished() {
		if (getStatus() == AveriaStatus.FACTURADA) {
			status = AveriaStatus.TERMINADA;
		}
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	void _setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public Date getFecha() {
		return (Date) fecha.clone();
	}

	public double getImporte() {
		return importe;
	}

	public AveriaStatus getStatus() {
		return status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result
				+ ((vehiculo == null) ? 0 : vehiculo.hashCode());
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
		Averia other = (Averia) obj;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (vehiculo == null) {
			if (other.vehiculo != null)
				return false;
		} else if (!vehiculo.equals(other.vehiculo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Averia [descripcion=" + descripcion + ", fecha=" + fecha
				+ ", importe=" + importe + ", " + "status=" + status
				+ ", vehiculo=" + vehiculo + "]";
	}

	public Mecanico getMecanico() {
		return mecanico;
	}

	void _setMecanico(Mecanico mecanico) {
		this.mecanico = mecanico;
	}

	public Factura getFactura() {
		return factura;
	}

	void _setFactura(Factura factura) {
		this.factura = factura;
	}

	Set<Intervencion> _getIntervenciones() {
		return intervenciones;
	}

	public Set<Intervencion> getIntervenciones() {
		return new HashSet<>(intervenciones);
	}

	public boolean esElegibleParaBono3() {
		if (getStatus() == AveriaStatus.FACTURADA
				&& getFactura().getStatus() == FacturaStatus.ABONADA
				&& !getUsadaParaBono()) {
			return true;
		}
		return false;
	}

	public void markAsBono3Used() {
		usada_bono = true;
	}

	public boolean getUsadaParaBono() {
		return usada_bono;
	}

	public boolean isInvoiced() {
		return status == AveriaStatus.FACTURADA;
	}

	public boolean isUsadaBono3() {
		return usada_bono;
	}

}
