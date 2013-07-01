package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class Cidade extends Model {

	@Id
	public Long id;
	
	public String nome;

	@ManyToOne
	public Estado estado;

	// -- Queries

	public static Model.Finder<Long, Cidade> find = new Finder<Long, Cidade>(Long.class, Cidade.class);
	
	/**
     * Consulta as cidades do estado.
     */
    public static List<Cidade> listarPorEstado(Long estado) {
        return find.where()
            .eq("estado.id", estado)
            .orderBy("nome asc")
            .findList();
    }
}
