package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class Estado extends Model {

	@Id
	public Long id;
	
	public String nome;
	
	public String abreviacao;

	@ManyToOne
	public Pais pais;

	// -- Queries

	public static Model.Finder<Long, Estado> find = new Finder<Long, Estado>(Long.class, Estado.class);
	

	/**
     * Consulta os paises do estado.
     */
    public static List<Estado> listarPorPais(Long pais) {
        return find.where()
            .eq("pais.id", pais)
            .orderBy("nome asc")
            .findList();
    }
    
    /**
     * Consulta os paises do estado.
     */
    public static List<Estado> listarPorPais(String pais) {
    	
    	if(pais == null || pais.equals("")) {
    		return new ArrayList<Estado>();
    	} else {
    		return listarPorPais(Long.valueOf(pais));
    	}
    }
}
