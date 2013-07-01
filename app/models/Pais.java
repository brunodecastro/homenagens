package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Pais extends Model {
	
	@Id
	public Long id;
	
	public String nome;

	public String abreviacao;

	// -- Queries

	public static Model.Finder<Long, Pais> find = new Finder<Long, Pais>(Long.class, Pais.class);
	
	

}
