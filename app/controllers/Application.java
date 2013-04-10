package controllers;

import static play.data.Form.form;

import java.io.File;
import java.sql.Blob;
import java.util.List;

import models.Picture;

import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

import com.google.common.io.Files;

import play.Logger;
import play.data.Form;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import views.html.login;
import views.html.upload;

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
			return redirect(routes.Contas.index());
		} else {
			return badRequest(login.render(loginForm));
		}
	}

	public static Result startUpload() {
		return ok(upload.render());
	}
	
	public static Result getPicture(long id) {
		Picture picture = Picture.find.byId(id);
		return ok("");
//		response().setContentType(picture.image)
//		Ok(picture.image.getBinaryStream().
	}
	
	public static Result upload() {
		ObjectNode result = Json.newObject();
		ArrayNode array = result.putArray("files");

		MultipartFormData body = request().body().asMultipartFormData();
		
		List<FilePart> files = body.getFiles();
		for(FilePart filePart : files) {
//			Files.move(from, to)
			
			ObjectNode fileJson = new ObjectNode(JsonNodeFactory.instance);
			fileJson.put("name", filePart.getFilename());
			fileJson.put("size", filePart.getFile().length());
			try {
				fileJson.put("url", filePart.getFile().getCanonicalPath());
			} catch (Exception e) { Logger.debug("", e);}
//			fileJson.put("thumbnail_url", filePart.getFile().getName());
//			fileJson.put("delete_url", filePart.getFile().getName());
//			fileJson.put("delete_type", "DELETE");
			Logger.info(filePart.toString());
			array.add(fileJson);
		}
		
		return ok(result);
	}

}
