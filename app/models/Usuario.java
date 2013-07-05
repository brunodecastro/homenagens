package models;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Lob;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import com.avaje.ebean.Page;

@Entity
public class Usuario extends Model {

	@Id
	public Long id;

	@Constraints.Required
	@Lob
	public String email;

	@Constraints.Required
	@Lob
	public String nome;

	@Constraints.Required
	@Lob
	public String senha;
	
	@Enumerated
	@Constraints.Required
	public TipoUsuario tipoUsuario;
		
	
	// -- Queries

	public static Finder<Long, Usuario> find = new Finder<Long, Usuario>(Long.class, Usuario.class);
	
	public static Usuario consultarPorEmail(String email) {
		return find.where().eq("email", email).findUnique();
	}

	public static Usuario autenticar(String email, String senha) {
		return find.where().eq("email", email).eq("senha", senha).findUnique();
	}
	
	   /**
     * Return a page of usuarios
     *
     * @param page Page to display
     * @param pageSize Number of usuarios per page
     * @param sortBy Usuario property used for sorting
     * @param order Sort order (either or asc or desc)
     * @param filter Filter applied on the name column
     */
    public static Page<Usuario> listPage(int page, int pageSize, String sortBy, String order, String filter) {
        return 
            find.where()
                .ilike("nome", "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .findPagingList(pageSize)
                .getPage(page);
    }
}
