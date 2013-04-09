package models;



import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Picture extends Model {

	@Id
	public Long id;

    public String url;

    public static Finder<Long, Picture> find = new Finder<Long, Picture>(Long.class, Picture.class);
}
