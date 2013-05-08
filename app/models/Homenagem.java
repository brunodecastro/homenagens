package models;

import java.util.Date;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

public class Homenagem extends Model {

	@Id
	public Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	public Homenagem homenagemPai;
	
	@Valid
	@OneToMany(mappedBy="homenagemPai")
	public List<Homenagem> homenagensFilhas;
	
	@Required
	public String numeroRegistro;
	
	public Homenageado homenageado;
	
	public String descricao;

	public TipoHomenagem tipoHomenagem;
	
	public String resumo;
	
	public Cidade cidade;
	
	public String local;
	
	public String localizacao;
	
	public String precedencia;
	
	public String objeto;
	
	public String prateleira;
	
	public String material;
	
	public Double altura;
	
	public Double largura;
	
	public Double comprimento;
	
	public Double profundidade;
	
	public String quemEntregou;
	
	public Date dataRecebimento;
	
	@OneToMany
	public List<DbImage> pictures;
	
	
	// -- Queries

	public static Finder<String, Homenagem> find = new Finder<String, Homenagem>(String.class, Homenagem.class);

}
