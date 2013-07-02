package controllers;

import static play.data.Form.form;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Cidade;
import models.Estado;
import models.Homenageado;
import models.Homenagem;
import models.HomenagemFilter;
import models.Usuario;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import play.Routes;
import play.data.Form;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.homenagem.createForm;
import views.html.homenagem.editForm;
import views.html.homenagem.list;

@Security.Authenticated(Secured.class)
public class Homenagens extends Controller {
    
	/**
     * This result directly redirect to application home.
     */
    private static Result GO_HOME = redirect(
        routes.Homenagens.list(0, "descricao", "asc", "", "", "", "", "", "", "")
    );
    
    private static Result goHome(Homenageado homenageado) {
    	if(homenageado != null) {
    		return redirect(routes.Homenagens.list(0, "descricao", "asc", homenageado.name(), "", "", "", "", "", ""));
    	} else {
    		return GO_HOME;
    	}
    }
    
    /**
     * Handle default path requests, redirect to homenagems list
     */
    public static Result index() {
        return goHome(null);
    }
    
    public static Result listarEstados(Long pais) {
        List<Estado> estados = Estado.listarPorPais(pais);

        return ok(Json.toJson(estados));
    }
    
    public static Result listarCidades(Long estado) {
        List<Cidade> cidades = Cidade.listarPorEstado(estado);

        return ok(Json.toJson(cidades));
    }
    
    public static Result list(int page, String sortBy, String order, String homenageado, String numeroRegistro, String descricao, String resumo, String quemEntregou, String dataRecebimentoInicio, String dataRecebimentoFim) {
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
        flash("success", "A Homenagem \"" + homenagemForm.get().descricao + "\" foi atualizada");
        return goHome(homenagemForm.get() != null ? homenagemForm.get().homenageado : null);
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
        return goHome(homenagemForm.get() != null ? homenagemForm.get().homenageado : null);
    }
    
    /**
     * Handle homenagem deletion
     */
    public static Result delete(Long id) {
    	Homenagem homenagem = Homenagem.find.ref(id);
    	Homenageado homenageado = homenagem.homenageado;
    	String deletedName = homenagem.descricao;
    	homenagem.delete();
        flash("success", "A Homenagem \"" + deletedName + "\" foi excluída");
        return goHome(homenageado);
    }

    public static Result print(String sortBy, String order, String homenageado, String numeroRegistro, String descricao, String resumo, String quemEntregou, String dataRecebimentoInicio, String dataRecebimentoFim) {
    	Form<HomenagemFilter> homenagemFilterForm = form(HomenagemFilter.class).bindFromRequest();
    	List<Homenagem> homenagemList = Homenagem.list(sortBy, order, homenagemFilterForm.get());
    	
    	Map<String, Object> reportParams = new HashMap<String, Object>();
    	reportParams.put("REPORT_TITLE", "titulo");
    	
    	Homenageado homenageadoObj = homenagemFilterForm.get().homenageado;
    	if(homenageadoObj == null) {
    		homenageadoObj = Homenageado.valueOf(homenageado);
    	}
    	reportParams.put("HOMENAGEADO_NOME", homenageadoObj.getLabel());
    	
        return ReportController.jasperDocument("homenagem_list", reportParams, new
    			JRBeanCollectionDataSource(homenagemList));
    }
    
    public static Result javascriptRoutes() {
        response().setContentType("text/javascript");
        return ok(
            Routes.javascriptRouter("myJsRoutes",
                routes.javascript.Homenagens.listarEstados(),
                routes.javascript.Homenagens.listarCidades()
            )
        );
    }

}