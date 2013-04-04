package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.avaje.ebean.Page;

import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class Conta extends Model {

	@Id
	public Long id;

	@Constraints.Required
	public String nome;

	public Double saldo;
	
    @ManyToOne
    public Usuario usuario;

	// -- Queries

	public static Model.Finder<String, Conta> find = new Finder<String, Conta>(String.class, Conta.class);
	
	/**
     * Consulta as contas do usuario.
     */
    public static List<Conta> consultarContasDoUsuario(String email) {
        return find.where()
            .eq("usuario.email", email)
            .findList();
    }
    
    /**
     * Return a page of contas
     *
     * @param page Page to display
     * @param pageSize Number of contas per page
     * @param sortBy Conta property used for sorting
     * @param order Sort order (either or asc or desc)
     * @param filter Filter applied on the name column
     */
    public static Page<Conta> listPage(int page, int pageSize, String sortBy, String order, String filter, String emailUsuario) {
        return 
            find.where()
                .ilike("nome", "%" + filter + "%")
                .eq("usuario.email", emailUsuario)
                .orderBy(sortBy + " " + order)
                .fetch("usuario")
                .findPagingList(pageSize)
                .getPage(page);
    }
}
