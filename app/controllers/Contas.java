package controllers;

import static play.data.Form.form;
import models.Conta;
import models.Usuario;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.contas;
import views.html.conta.criarConta;
import views.html.conta.list;

@Security.Authenticated(Secured.class)
public class Contas extends Controller {
	
	 /**
     * This result directly redirect to application home.
     */
    public static Result GO_HOME = redirect(
        routes.Contas.listPage(0, "nome", "asc", "")
    );
	
	
	public static Result index() {
				return GO_HOME;
//			contas.render(
//					Conta.consultarContasDoUsuario(request().username()), 
//					Usuario.consultarPorEmail(request().username())
//			)
	}
	
	/**
     * Display the 'new computer form'.
     */
    public static Result criar() {
    	Form<Conta> contaForm = form(Conta.class);
        return ok(
            criarConta.render(contaForm, Usuario.consultarPorEmail(request().username()))
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
        Form<Conta> contaForm = form(Conta.class).bindFromRequest();
        
        if(contaForm.hasErrors()) {
            return badRequest(criarConta.render(contaForm, Usuario.consultarPorEmail(request().username())));
        }
        
        contaForm.get().save();
        flash("success", "Conta " + contaForm.get().nome + " foi criado com sucesso.");
        return GO_HOME;
    }
    
    /**
     * Display the paginated list of contas.
     *
     * @param page Current page number (starts from 0)
     * @param sortBy Column to be sorted
     * @param order Sort order (either asc or desc)
     * @param filter Filter applied on conta names
     */
    public static Result listPage(int page, String sortBy, String order, String filter) {
        return ok(
            list.render(Usuario.consultarPorEmail(request().username()),
                Conta.listPage(page, 10, sortBy, order, filter, request().username()),
                sortBy, order, filter
            )
        );
    }
	
	

}
