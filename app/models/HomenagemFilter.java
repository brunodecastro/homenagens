package models;

import java.util.Date;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import com.avaje.ebean.Page;

public class HomenagemFilter extends Model {

	public String numeroRegistro;
	
	public String descricao;

	public TipoHomenagem tipoHomenagem;
	
	public String resumo;
	
	public Pais pais;
	
	public Estado estado;
	
	public Cidade cidade;
	
	public String quemEntregou;
	
	public Date dataRecebimentoInicio;
	
	public Date dataRecebimentoFim;
	
}
