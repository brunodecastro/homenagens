package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class Usuario extends Model {

	@Id
	public Long id;

	@Constraints.Required
	@Formats.NonEmpty
	public String email;

	@Constraints.Required
	public String nome;

	@Constraints.Required
	public String senha;
	
	
	// -- Queries

	public static Finder<String, Usuario> find = new Finder<String, Usuario>(String.class, Usuario.class);
	
	public static Usuario consultarPorEmail(String email) {
		return find.where().eq("email", email).findUnique();
	}

	public static Usuario autenticar(String email, String senha) {
		return find.where().eq("email", email).eq("senha", senha).findUnique();
	}
}
