package models;

import javax.persistence.Id;

import play.data.validation.Constraints;
import play.db.ebean.Model;

public class Homenagem extends Model {

	@Id
	public Long id;

	@Constraints.Required
	public String nome;
	
	
	// -- Queries

	public static Finder<String, Homenagem> find = new Finder<String, Homenagem>(String.class, Homenagem.class);

}
