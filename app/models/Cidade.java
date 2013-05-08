package models;

import play.db.ebean.Model;

public class Cidade extends Model {

	public String nome;

	public Estado estado;

	// -- Queries

	public static Finder<String, Cidade> find = new Finder<String, Cidade>(String.class, Cidade.class);

}
