package uo.ri.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import uo.ri.model.types.Address;

@Entity
@Table(name = "TClientes")
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(unique = true)
	private String dni;
	private String nombre;
	private String apellidos;
	private Address address;
	private String email;
	private String phone;

	@OneToMany(mappedBy = "cliente")
	private Set<Vehiculo> vehiculos = new HashSet<>();
	@OneToMany(mappedBy = "cliente")
	private Set<MedioPago> mediosPago = new HashSet<>();

	@OneToOne(mappedBy = "recomendado")
	private Recomendacion recomendador;
	@OneToMany(mappedBy = "recomendador")
	private Set<Recomendacion> recomendaciones = new HashSet<>();

	Cliente() {
	};

	public Cliente(String dni) {
		this.dni = dni;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String telefono) {
		this.phone = telefono;
	}

	public Cliente(String dni, String nombre, String apellidos) {
		this(dni);
		this.nombre = nombre;
		this.apellidos = apellidos;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getDni() {
		return dni;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
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
		Cliente other = (Cliente) obj;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cliente [dni=" + dni + ", nombre=" + nombre + ", apellidos="
				+ apellidos + ", address=" + address + "]";
	}

	public Set<Vehiculo> getVehiculos() {
		return new HashSet<>(vehiculos);
	}

	Set<Vehiculo> _getVehiculos() {
		return vehiculos;
	}

	public Set<MedioPago> getMediosPago() {
		return new HashSet<>(mediosPago);
	}

	Set<MedioPago> _getMediospago() {
		return mediosPago;
	}

	public List<Averia> getAveriasBono3NoUsadas() {
		List<Averia> averiasParaUsar = new ArrayList<Averia>();
		for (Vehiculo vehiculo : vehiculos) {
			for (Averia averia : vehiculo.getAverias()) {
				if (averia.esElegibleParaBono3()) {
					averiasParaUsar.add(averia);
				}
			}
		}
		return averiasParaUsar;
	}

	Set<Recomendacion> _getRecomendacionesHechas() {
		return recomendaciones;
	}

	public Set<Recomendacion> getRecomendacionesHechas() {
		return new HashSet<>(recomendaciones);
	}

	public Recomendacion getRecomendacionRecibida() {
		return recomendador;
	}

	void _setRecomendacionRecibida(Recomendacion reco) {
		this.recomendador = reco;
	}

	/**
	 * Método que devuelve una lista con las recomendaciones válidas del cliente,
	 * que podrán ser usadas para generar un bono.
	 * @return la lista de recomendaciones válidas
	 */
	public List<Recomendacion> recomendacionesValidas() {
		List<Recomendacion> listaRecos = new ArrayList<>();
		for (Recomendacion recomendacion : recomendaciones) {
			if (!recomendacion.getUsadaParaBono()) {
				for (Vehiculo vehiculo : recomendacion.getRecomendado()
						.getVehiculos()) {
					if (vehiculo.getAverias().size() > 0) {
						listaRecos.add(recomendacion);
						break;
					}

				}
			}
		}
		return listaRecos;
	}

	/**
	 * Método que comprueba si el cliente puede recibir bono por 3 recomendaciones.
	 * Comprueba que tenga 3 o más recomendaciones, que cada una de ellas sea vádida,(el cliente recomendado
	 * tenga averías en el taller), y que el propio cliente tenga alguna avería en el taller.
	 * @return Valor booleano indicando si puede recibir bono o no.
	 */
	public boolean elegibleBonoPorRecomendaciones() {
		boolean recos = recomendaciones.size() >= 3;
		boolean reparacionPropia = false;
		for (Vehiculo vehiculo : vehiculos) {
			if (vehiculo.getAverias().size() > 0) {
				reparacionPropia = true;
				break;
			}
		}

		return recos && reparacionPropia
				&& recomendacionesValidas().size() >= 3;
	}

	/**
	 * Método que devuelve el número de bonos que se generan para el cliente 
	 * por recomendaciones. Comprueba que el cliente es válido y va recorriendo las recomendaciones
	 * válidas, marcándolas como usadas.
	 * @return El número de bonos para el cliente
	 */
	public int generarBonos() {
		if (elegibleBonoPorRecomendaciones()) {
			int contador = recomendacionesValidas().size() / 3;
			int finaL = recomendacionesValidas().size() / 3;
			while (contador > 0) {
				int cont = 0;
				for (Recomendacion reco : recomendacionesValidas()) {
					reco.markAsUsadaBono();
					cont++;
					if (cont == 3)
						break;
				}
				contador--;
			}
			return finaL;
		}
		return 0;
	}

	public Metalico getMetalico() {
		return (Metalico) mediosPago.stream().filter(x -> x instanceof Metalico)
				.findFirst().orElse(null);
	}

}
