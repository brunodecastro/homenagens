package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class Cidade extends Model {

	@Id
	public Long id;
	
	public String nome;

	@ManyToOne
	public Estado estado;

	// -- Queries

	public static Model.Finder<String, Cidade> find = new Finder<String, Cidade>(String.class, Cidade.class);
	

}
