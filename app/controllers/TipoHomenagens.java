package controllers;

import static play.data.Form.form;

import java.util.List;

import javax.persistence.PersistenceException;

import models.TipoHomenagem;
import models.Usuario;
import play.data.Form;
import play.db.ebean.Transactional;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.tipoHomenagem.createForm;
import views.html.tipoHomenagem.createSubForm;
import views.html.tipoHomenagem.editForm;
import views.html.tipoHomenagem.editSubForm;
import views.html.tipoHomenagem.list;

@Security.Authenticated(Secured.class)
public class TipoHomenagens extends Controller {

	/**
	 * This result directly redirect to application home.
	 */
	public static Result GO_HOME = redirect(routes.TipoHomenagens.list(0, "name", "asc", ""));

	/**
	 * Handle default path requests, redirect to tipoHomenagems list
	 */
	public static Result index() {
		return GO_HOME;
	}

	public static Result list(int page, String sortBy, String order, String filter) {
		return ok(list.render(Usuario.consultarPorEmail(request().username()), TipoHomenagem.page(page, 10, sortBy, order, filter),
				sortBy, order, filter));
	}

	/**
	 * Display the 'edit form' of a existing TipoHomenagem.
	 * 
	 * @param id
	 *            Id of the tipoHomenagem to edit
	 */
	public static Result edit(Long id) {
		TipoHomenagem tipoHomenagem = TipoHomenagem.find.byId(id);
		Form<TipoHomenagem> tipoHomenagemForm = form(TipoHomenagem.class).fill(tipoHomenagem);
		return ok(editForm.render(Usuario.consultarPorEmail(request().username()), id, tipoHomenagemForm,
				TipoHomenagem.getSubs(tipoHomenagem)));
	}

	/**
	 * Handle the 'edit form' submission
	 * 
	 * @param id
	 *            Id of the tipoHomenagem to edit
	 */
	public static Result update(Long id) {
		Form<TipoHomenagem> tipoHomenagemForm = form(TipoHomenagem.class).bindFromRequest();
		if (tipoHomenagemForm.hasErrors()) {
			tipoHomenagemForm.reject(Messages.get("formErrorMessage"));
			TipoHomenagem tipoHomenagem = TipoHomenagem.find.byId(id);
			return badRequest(editForm.render(Usuario.consultarPorEmail(request().username()), id, tipoHomenagemForm,
					TipoHomenagem.getSubs(tipoHomenagem)));
		}
		tipoHomenagemForm.get().update(id);
		flash("success", "O Tipo de Homenagem \"" + tipoHomenagemForm.get().name + "\" foi atualizado");
		return GO_HOME;
	}

	/**
	 * Display the 'new tipoHomenagem form'.
	 */
	public static Result create() {
		Form<TipoHomenagem> tipoHomenagemForm = form(TipoHomenagem.class);
		return ok(createForm.render(Usuario.consultarPorEmail(request().username()), tipoHomenagemForm));
	}

	/**
	 * Handle the 'new tipoHomenagem form' submission
	 */
	public static Result save() {
		Form<TipoHomenagem> tipoHomenagemForm = form(TipoHomenagem.class).bindFromRequest();
		if (tipoHomenagemForm.hasErrors()) {
			tipoHomenagemForm.reject(Messages.get("formErrorMessage"));
			return badRequest(createForm.render(Usuario.consultarPorEmail(request().username()), tipoHomenagemForm));
		}
		tipoHomenagemForm.get().save();
		flash("success", Messages.get("tipoHomenagem.create.success", tipoHomenagemForm.get().name));
		return GO_HOME;
	}

	/**
	 * Handle tipoHomenagem deletion
	 */
	@Transactional
	public static Result delete(Long id) {
		TipoHomenagem tipoHomenagem = TipoHomenagem.find.byId(id);
		
		Usuario usuario = Usuario.consultarPorEmail(request().username());
		List<TipoHomenagem> subs = TipoHomenagem.getSubs(tipoHomenagem);
		
		try {
			tipoHomenagem.delete();
		} catch (PersistenceException e) {
			Form<TipoHomenagem> tipoHomenagemForm = form(TipoHomenagem.class).fill(tipoHomenagem);
			tipoHomenagemForm.reject("Não foi possível excluir. Esse tipo de homenagem ou um de seus subtipos está sendo utilizado por uma homenagem.");
			return badRequest(editForm.render(usuario, id, tipoHomenagemForm, subs));
		}
		flash("success", "O Tipo de Homenagem \"" + tipoHomenagem.name + "\" foi excluído");
		return GO_HOME;
	}

	public static Result createSub(Long parentId) {
		TipoHomenagem tipoHomenagemParent = TipoHomenagem.find.byId(parentId);
		TipoHomenagem tipoHomenagem = new TipoHomenagem();
		tipoHomenagem.parent = tipoHomenagemParent;
		Form<TipoHomenagem> tipoHomenagemForm = form(TipoHomenagem.class).fill(tipoHomenagem);
		return ok(createSubForm.render(Usuario.consultarPorEmail(request().username()), parentId, tipoHomenagemForm));
	}

	public static Result saveSub(Long parentId) {
		Form<TipoHomenagem> tipoHomenagemForm = form(TipoHomenagem.class).bindFromRequest();
		if (tipoHomenagemForm.hasErrors()) {
			tipoHomenagemForm.reject(Messages.get("formErrorMessage"));
			return badRequest(createSubForm.render(Usuario.consultarPorEmail(request().username()), parentId, tipoHomenagemForm));
		}
		TipoHomenagem sub = tipoHomenagemForm.get();
		sub.save();
		flash("success", Messages.get("subtipoHomenagem.create.success", sub.name));

		return redirect(routes.TipoHomenagens.edit(parentId));
	}

	public static Result editSub(Long id) {
		TipoHomenagem tipoHomenagem = TipoHomenagem.find.byId(id);
		Form<TipoHomenagem> tipoHomenagemForm = form(TipoHomenagem.class).fill(tipoHomenagem);
		return ok(editSubForm.render(Usuario.consultarPorEmail(request().username()), tipoHomenagem.parent.id, id,
				tipoHomenagemForm));
	}

	public static Result updateSub(Long parentId, Long id) {
		Form<TipoHomenagem> tipoHomenagemForm = form(TipoHomenagem.class).bindFromRequest();
		if (tipoHomenagemForm.hasErrors()) {
			tipoHomenagemForm.reject(Messages.get("formErrorMessage"));
			return badRequest(editSubForm.render(Usuario.consultarPorEmail(request().username()), parentId, id, tipoHomenagemForm));
		}

		TipoHomenagem sub = tipoHomenagemForm.get();
		sub.update(id);
		flash("success", "O subTipo de Homenagem \"" + tipoHomenagemForm.get().name + "\" foi atualizado");
		return redirect(routes.TipoHomenagens.edit(sub.parent.id));
	}

	public static Result deleteSub(Long id) {
		TipoHomenagem tipoHomenagem = TipoHomenagem.find.byId(id);
		Long tipoHomenagemParentId = tipoHomenagem.parent.id;
		
		Usuario usuario = Usuario.consultarPorEmail(request().username());
		
		try {
			tipoHomenagem.delete();
		} catch (PersistenceException e) {
			Form<TipoHomenagem> tipoHomenagemForm = form(TipoHomenagem.class).fill(tipoHomenagem);
			tipoHomenagemForm.reject("Não foi possível excluir. Esse tipo de homenagem está sendo utilizado por uma homenagem.");
			return badRequest(editSubForm.render(usuario, tipoHomenagemParentId, id, tipoHomenagemForm));
		}
		
		flash("success", "O subTipo de Homenagem \"" + tipoHomenagem.name + "\" foi excluído");
		return redirect(routes.TipoHomenagens.edit(tipoHomenagemParentId));
	}
}