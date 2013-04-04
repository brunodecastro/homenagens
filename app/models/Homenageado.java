package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class Homenageado extends Model {

	@Id
	public Long id;

	@Constraints.Required
	public String nome;

	
	// -- Queries

	public static Finder<String, Homenageado> find = new Finder<String, Homenageado>(String.class, Homenageado.class);

}
