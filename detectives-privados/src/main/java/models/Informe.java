package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="informes")
public class Informe {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;
	private String titulo;
	private String descripcion;
	@Column(length=4000)
	private String contenido;
	private String temaColor;
	private Integer autorId;
	
	public Informe() {}
	
	public Informe(String id, String titulo, String descripcion, String contenido, String temaColor, Integer autorId) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.contenido = contenido;
		this.descripcion = descripcion;
		this.autorId = autorId;
		this.temaColor = temaColor;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public String getTemaColor() {
		return temaColor;
	}

	public void setTemaColor(String temaColor) {
		this.temaColor = temaColor;
	}

	@Override
	public String toString() {
		return "Informe [id=" + id + ", titulo=" + titulo + ", contenido=" + contenido + ", descripcion=" + descripcion
				+ ", autorId=" + autorId + ", temaColor=" + temaColor + "]";
	}
	
	

}
