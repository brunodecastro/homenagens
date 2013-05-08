package models;

import play.db.ebean.Model;

public class Pais extends Model {

	public String nome;

	// -- Queries

	public static Finder<String, Pais> find = new Finder<String, Pais>(String.class, Pais.class);

}
