package controllers;

import static play.data.Form.form;
import models.Usuario;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.usuario.criarUsuario;
import views.html.usuario.list;

import com.avaje.ebean.Page;

@Security.Authenticated(Secured.class)
public class Usuarios extends Controller {

	/**
	 * This result directly redirect to application home.
	 */
	public static Result GO_HOME = redirect(routes.Usuarios.listar(0, "nome", "asc", ""));

	public static Result index() {
		return GO_HOME;
	}
	
	/**
     * Display the 'new computer form'.
     */
    public static Result criar() {
    	Form<Usuario> usuarioForm = form(Usuario.class);
        return ok(
            criarUsuario.render(usuarioForm, Usuario.consultarPorEmail(request().username()))
        );
    }
    
    /**
     * Display the 'edit form' of a existing Computer.
     *
     * @param id Id of the computer to edit
     */
    public static Result editar(Long id) {
    	return ok();
    }
    
    /**
     * Handle the 'new computer form' submission 
     */
    public static Result salvar() {
        Form<Usuario> contaForm = form(Usuario.class).bindFromRequest();
        
        if(contaForm.hasErrors()) {
            return badRequest(criarUsuario.render(contaForm, Usuario.consultarPorEmail(request().username())));
        }
        
        contaForm.get().save();
        flash("success", "Usuario " + contaForm.get().nome + " foi criado com sucesso.");
        return GO_HOME;
    }
	
	/**
     * Display the paginated list of usuarios.
     *
     * @param page Current page number (starts from 0)
     * @param sortBy Column to be sorted
     * @param order Sort order (either asc or desc)
     * @param filter Filter applied on conta names
     */
    public static Result listar(int page, String sortBy, String order, String filter) {

    	Usuario usuario = Usuario.consultarPorEmail(request().username());
    	Page<Usuario> usuarioPage = Usuario.listPage(page, 10, sortBy, order, filter);

    	return ok(
            list.render(usuario, usuarioPage, sortBy, order, filter
            )
        );
    }

}

