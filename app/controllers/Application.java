package controllers;

import static play.data.Form.form;
import play.Routes;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.login;

public class Application extends Controller {

	public static Result login() {
		return ok(login.render(form(Login.class)));
	}

	public static Result logout() {

		session().clear();

		flash("success", "Você foi desconectado.");

		return redirect(routes.Application.login());
	}

	public static Result autenticar() {
		Form<Login> loginForm = form(Login.class).bindFromRequest();

		if (!loginForm.hasErrors()) {
			session("email", loginForm.get().email);
			return redirect(routes.Homenagens.index());
		} else {
			return badRequest(login.render(loginForm));
		}
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
