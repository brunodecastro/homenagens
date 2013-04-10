package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import com.avaje.ebean.annotation.PrivateOwned;

@SuppressWarnings("serial")
@Entity
public class DbImage extends Model {

	@Id
	public Long id;

	@Required
	private String filename;

	@Required
	@PrivateOwned
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private RawImage image;

	@Required
	@PrivateOwned
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private RawImage thumbnail;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public RawImage getImage() {
		return image;
	}

	public void setImage(RawImage image) {
		this.image = image;
	}

	public RawImage getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(RawImage thumbnail) {
		this.thumbnail = thumbnail;
	}

	// -- Queries
	public static Finder<Long, DbImage> find = new Finder<Long, DbImage>(Long.class, DbImage.class);

}
