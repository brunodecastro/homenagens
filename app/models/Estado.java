package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Estado extends Model {

	@Id
	public Long id;
	
	public String nome;
	
	public String abreviacao;

	public Pais pais;

	// -- Queries

	public static Finder<String, Estado> find = new Finder<String, Estado>(String.class, Estado.class);

}
