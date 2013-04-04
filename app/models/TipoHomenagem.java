package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class TipoHomenagem extends Model {

	@Id
	public Long id;

	@Constraints.Required
	public String descricao;
	
    @ManyToOne
    public TipoHomenagem subTipoHomenagem;
	
	
	// -- Queries

	public static Finder<String, TipoHomenagem> find = new Finder<String, TipoHomenagem>(String.class, TipoHomenagem.class);

}
