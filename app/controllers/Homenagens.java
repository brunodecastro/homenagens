package controllers;

import static play.data.Form.form;
import models.Homenagem;
import models.HomenagemFilter;
import models.Usuario;
import play.data.DynamicForm;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import views.html.homenagem.*;

@Security.Authenticated(Secured.class)
public class Homenagens extends Controller {
    
	/**
     * This result directly redirect to application home.
     */
    public static Result GO_HOME = redirect(
        routes.Homenagens.list(0, "descricao", "asc")
    );
    
    /**
     * Handle default path requests, redirect to homenagems list
     */
    public static Result index() {
        return GO_HOME;
    }
    
    public static Result list(int page, String sortBy, String order) {
    	Form<HomenagemFilter> homenagemFilterForm = form(HomenagemFilter.class).bindFromRequest();
        if(homenagemFilterForm.hasErrors()) {
            return badRequest(list.render(Usuario.consultarPorEmail(request().username()), 
            		Homenagem.page(page, 10, sortBy, order, homenagemFilterForm.get()),
                    sortBy, order, homenagemFilterForm
                ));
        }
        return ok(
            list.render(Usuario.consultarPorEmail(request().username()), 
            		Homenagem.page(page, 10, sortBy, order, homenagemFilterForm.get()),
                sortBy, order, homenagemFilterForm
            )
        );
    }
    
    /**
     * Display the 'edit form' of a existing Homenagem.
     *
     * @param id Id of the homenagem to edit
     */
    public static Result edit(Long id) {
    	Homenagem homenagem = Homenagem.find.byId(id);
        Form<Homenagem> homenagemForm = form(Homenagem.class).fill(
        		homenagem
        );
        return ok(
            editForm.render(Usuario.consultarPorEmail(request().username()), id, homenagemForm)
        );
    }
    
    /**
     * Handle the 'edit form' submission 
     *
     * @param id Id of the homenagem to edit
     */
    public static Result update(Long id) {
        Form<Homenagem> homenagemForm = form(Homenagem.class).bindFromRequest();
        if(homenagemForm.hasErrors()) {
        	Homenagem homenagem = Homenagem.find.byId(id);
            return badRequest(editForm.render(Usuario.consultarPorEmail(request().username()), id, homenagemForm));
        }
        homenagemForm.get().update(id);
        flash("success", "O Tipo de Homenagem \"" + homenagemForm.get().descricao + "\" foi atualizado");
        return GO_HOME;
    }
    
    /**
     * Display the 'new homenagem form'.
     */
    public static Result create() {
        Form<Homenagem> homenagemForm = form(Homenagem.class);
        return ok(
            createForm.render(Usuario.consultarPorEmail(request().username()), homenagemForm)
        );
    }
    
    /**
     * Handle the 'new homenagem form' submission 
     */
    public static Result save() {
        Form<Homenagem> homenagemForm = form(Homenagem.class).bindFromRequest();
        if(homenagemForm.hasErrors()) {
            return badRequest(createForm.render(Usuario.consultarPorEmail(request().username()), homenagemForm));
        }
        homenagemForm.get().save();
        flash("success", Messages.get("homenagem.create.success", homenagemForm.get().descricao));
        return GO_HOME;
    }
    
    /**
     * Handle homenagem deletion
     */
    public static Result delete(Long id) {
    	Homenagem homenagem = Homenagem.find.ref(id);
    	String deletedName = homenagem.descricao;
    	homenagem.delete();
        flash("success", "O Tipo de Homenagem \"" + deletedName + "\" foi excluído");
        return GO_HOME;
    }

    

}