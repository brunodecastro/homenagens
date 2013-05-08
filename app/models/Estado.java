package models;

import play.db.ebean.Model;

public class Estado extends Model {

	public String nome;

	public Pais pais;

	// -- Queries

	public static Finder<String, Estado> find = new Finder<String, Estado>(String.class, Estado.class);

}
