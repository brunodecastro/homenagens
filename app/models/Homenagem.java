package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Page;
import com.avaje.ebean.Query;

@Entity
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
	
	@Required
	public Homenageado homenageado;
	
	public String descricao;

	@Required
	@ManyToOne
	public TipoHomenagem tipoHomenagem;
	
	@Required
	@ManyToOne
	public Cidade cidade;
	
	public String resumo;
	
	public String local;
	
	public String localizacao;
	
	public String precedencia;
	
	public String objeto;
	
	public String prateleira;
	
	public String material;
	
	public String altura;
	
	public String largura;
	
	public String comprimento;
	
	public String profundidade;
	
	public String quemEntregou;
	
	public Date dataRecebimento;
	
//	@OneToMany
//	public List<DbImage> pictures;
	
	
	// -- Queries

	public static Model.Finder<Long, Homenagem> find = new Finder<Long, Homenagem>(Long.class, Homenagem.class);
	
	public static Page<Homenagem> page(int page, int pageSize, String sortBy, String order, String filter) {
		return find.where().ilike("descricao", "%" + filter + "%").orderBy(sortBy + " " + order).findPagingList(pageSize)
				.getPage(page);
	}
	
	public static Page<Homenagem> page(int page, int pageSize, String sortBy, String order, HomenagemFilter homenagemFilter) {
		Query<Homenagem> homenagemQuery = findFiltered(sortBy, order, homenagemFilter);
		
		return homenagemQuery.findPagingList(pageSize).getPage(page);
	}
	
	public static List<Homenagem> list(String sortBy, String order, HomenagemFilter homenagemFilter) {
		Query<Homenagem> homenagemQuery = findFiltered(sortBy, order, homenagemFilter);
		
		return homenagemQuery.findList();
	}

	public static Query<Homenagem> findFiltered(String sortBy, String order, HomenagemFilter homenagemFilter) {
		ExpressionList<Homenagem> queryEl = find.where();
		
		if(homenagemFilter != null) {
			if(homenagemFilter.numeroRegistro != null && !homenagemFilter.numeroRegistro.isEmpty()) {
				queryEl.ilike("numeroRegistro", "%"+ homenagemFilter.numeroRegistro + "%");
			}
			
			if(homenagemFilter.descricao != null && !homenagemFilter.descricao.isEmpty()) {
				queryEl.ilike("descricao", "%"+ homenagemFilter.descricao + "%");
			}
			
			if(homenagemFilter.homenageado != null) {
				queryEl.eq("homenageado", homenagemFilter.homenageado);
			} else {
				queryEl.eq("homenageado", Homenageado.MARCONI);
			}
			
			if(homenagemFilter.tipoHomenagem != null) {
				queryEl.eq("tipoHomenagem", homenagemFilter.tipoHomenagem);
			}
			
			if(homenagemFilter.dataRecebimentoInicio != null && homenagemFilter.dataRecebimentoFim != null) {
				queryEl.between("dataRecebimento", homenagemFilter.dataRecebimentoInicio, homenagemFilter.dataRecebimentoFim);
			} else if(homenagemFilter.dataRecebimentoInicio != null) {
				queryEl.ge("dataRecebimento", homenagemFilter.dataRecebimentoInicio);
			} else if(homenagemFilter.dataRecebimentoFim != null) {
				queryEl.le("dataRecebimento", homenagemFilter.dataRecebimentoFim);
			}
		}
		
		return queryEl.orderBy("homenageado asc, "+ sortBy + " " + order);
	}
}
