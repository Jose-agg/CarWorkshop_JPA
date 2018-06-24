package uo.ri.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import uo.ri.model.types.FacturaStatus;
import uo.ri.util.exception.BusinessException;

@Entity
@Table(name="TCargos",
uniqueConstraints= {
		@UniqueConstraint(columnNames="FACTURA_ID, MEDIOPAGO_ID")
})
public class Cargo {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long id;

	@ManyToOne Factura factura;
	@ManyToOne MedioPago medioPago;
	private double importe = 0.0;

	Cargo(){};

	public Long getId() {
		return id;
	}


	public Cargo(Factura factura, MedioPago medioPago) {
		super();
		Association.Cargar.link(factura,this,medioPago);
	}

	public Cargo(Factura factura, MedioPago medioPago, double importe) throws uo.ri.util.exception.BusinessException {	
		if(medioPago instanceof Bono && ((Bono) medioPago).getDisponible()<importe) {
			throw new BusinessException("Bono con disponible insuficiente");
		}
		if(medioPago instanceof TarjetaCredito && ((TarjetaCredito) medioPago).getValidez().getTime() < 
				new Date().getTime()) {
			throw new BusinessException("Tarjeta de crédito con fecha caducada");
		}
		medioPago.acumulado+=importe;
		if(medioPago instanceof Bono) {
			((Bono) medioPago).disponible-=importe;
		}
		this.importe=importe;
		Association.Cargar.link(factura, this, medioPago);

	}



	public Factura getFactura() {
		return factura;
	}



	void _setFactura(Factura factura) {
		this.factura = factura;
	}



	public MedioPago getMedioPago() {
		return medioPago;
	}



	void _setMedioPago(MedioPago medioPago) {
		this.medioPago = medioPago;
	}



	public double getImporte() {
		return importe;
	}



	public void setImporte(double importe) {
		this.importe = importe;
	}


	/**
	 * Anula (retrocede) este cargo de la factura y el medio de pago
	 * Solo se puede hacer si la factura no está abonada
	 * Decrementar el acumulado del medio de pago
	 * Desenlazar el cargo de la factura y el medio de pago 
	 * @throws BusinessException
	 */
	public void rewind() throws uo.ri.util.exception.BusinessException {
		if(factura.getStatus()!=FacturaStatus.ABONADA) {
			medioPago.acumulado-=importe;
			Association.Cargar.unlink(this);
		}
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((factura == null) ? 0 : factura.hashCode());
		result = prime * result
				+ ((medioPago == null) ? 0 : medioPago.hashCode());
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
		Cargo other = (Cargo) obj;
		if (factura == null) {
			if (other.factura != null)
				return false;
		} else if (!factura.equals(other.factura))
			return false;
		if (medioPago == null) {
			if (other.medioPago != null)
				return false;
		} else if (!medioPago.equals(other.medioPago))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "Cargo [factura=" + factura + ", medioPago=" + medioPago
				+ ", importe=" + importe + "]";
	}

}
