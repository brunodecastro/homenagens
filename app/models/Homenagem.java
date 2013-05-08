package models;

import java.util.Date;

import javax.persistence.Id;

import play.db.ebean.Model;

public class Homenagem extends Model {

	@Id
	public Long id;
	
	public Homenagem homenagem;
	
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
	
	
	// -- Queries

	public static Finder<String, Homenagem> find = new Finder<String, Homenagem>(String.class, Homenagem.class);

}
