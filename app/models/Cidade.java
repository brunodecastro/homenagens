package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Cidade extends Model {

	@Id
	public Long id;
	
	public String nome;

	public Estado estado;

	// -- Queries

	public static Finder<String, Cidade> find = new Finder<String, Cidade>(String.class, Cidade.class);

}
