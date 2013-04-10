package controllers;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import models.DbImage;
import models.RawImage;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import play.Configuration;
import play.Logger;
import play.Play;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import play.mvc.Results;

public class ImageController {

	public static Result getAllImages() {
		ObjectNode result = Json.newObject();
		ArrayNode array = result.putArray("files");

		List<DbImage> bdImages = DbImage.find.all();
		for (DbImage image : bdImages) {
			array.add(dbImageToJson(image));
		}

		return Results.ok(result);
	}

	public static Result getImage(Long id) {
		DbImage dbImage = DbImage.find.byId(id);
		if (dbImage == null) {
			return Results.status(404);
		}
		RawImage rawImage = dbImage.getImage();
		return Results.ok(rawImage.getImage()).as(rawImage.getMimetype());
	}

	public static Result getThumbnail(Long id) {
		DbImage dbImage = DbImage.find.byId(id);
		if (dbImage == null) {
			return Results.status(404);
		}
		RawImage rawThumbnailImage = dbImage.getThumbnail();
		return Results.ok(rawThumbnailImage.getImage()).as(rawThumbnailImage.getMimetype());
	}

	public static Result deleteImage(Long id) {
		DbImage dbImage = DbImage.find.byId(id);
		dbImage.delete();

		return Results.ok();
	}

	/**
	 * @return json with all infos of the newly created or updated dbImage
	 */
	public static Result uploadImage() {
		ObjectNode result = Json.newObject();
		ArrayNode array = result.putArray("files");

		try {
			MultipartFormData body = Controller.request().body().asMultipartFormData();

			List<FilePart> fileParts = body.getFiles();

			for (FilePart filePart : fileParts) {
				DbImage image = new DbImage();
				// image.setId(id);
				image.setFilename(filePart.getFilename());
				File imgFile = filePart.getFile();
				byte[] bytes = FileUtils.readFileToByteArray(imgFile);
				setRawImage(image, bytes);
				if (!isAllowedMimetype(image)) {
					return Results.status(400, "mimetype not allowed");
				}
				setRawThumbnail(image);
				image.save();
				array.add(dbImageToJson(image));
			}

			return Results.ok(result);
		} catch (Exception e) {
			Logger.warn(e.getMessage(), e);
			return Results.status(400, e.getMessage());
		}
	}

	private static boolean isAllowedMimetype(DbImage image) {
		Configuration config = Play.application().configuration();
		int index = Arrays.binarySearch(config.getString("image.mimetypes.allowed").split(","), image.getImage().getMimetype());
		return index >= 0;
	}

	private static void setRawImage(DbImage image, byte[] bytes) {
		RawImage rawImage = image.getImage();
		if (rawImage == null) {
			rawImage = new RawImage();
			image.setImage(rawImage);
		}
		rawImage.setImage(bytes);
	}

	private static void setRawThumbnail(DbImage image) {
		Configuration config = Play.application().configuration();
		int thumbnailWidth = config.getInt("thumbnails.width");
		int thumbnailHeight = config.getInt("thumbnails.height");
		RawImage thumbnail = image.getThumbnail();
		if (thumbnail == null) {
			thumbnail = new RawImage();
			image.setThumbnail(thumbnail);
		}
		byte[] thumbnailBytes = image.getImage().createThumbnail(thumbnailWidth, thumbnailHeight);
		thumbnail.setImage(thumbnailBytes);
	}

	private static ObjectNode dbImageToJson(DbImage image) {
		ObjectNode result = Json.newObject();
		result.put("id", image.getId());
		result.put("name", image.getFilename());
		result.put("size", image.getImage().getImage().length);
		result.put("url", "/image/" + image.getId());
		result.put("thumbnail_url", "/thumbnail/" + image.getId());
		result.put("delete_url", "/image/" + image.getId());
		result.put("delete_type", "DELETE");
		return result;
	}

}
