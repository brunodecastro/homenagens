package models;

import static play.data.Form.form;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import play.data.Form;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import com.avaje.ebean.Page;

@Entity
public class TipoHomenagem extends Model {

	@Id
	public Long id;

	@Required
	public String name;
	
	@ManyToOne(fetch=FetchType.LAZY)
	public TipoHomenagem parent;
	
	@Valid
	@OneToMany(mappedBy="parent")
	public List<TipoHomenagem> subs;

	public TipoHomenagem() {
	}

	public TipoHomenagem(String name) {
		this.name = name;
	}
	
	public static Model.Finder<Long, TipoHomenagem> find = new Finder<Long, TipoHomenagem>(Long.class, TipoHomenagem.class);

	public static Page<TipoHomenagem> page(int page, int pageSize, String sortBy, String order, String filter) {
		return find.where().ilike("name", "%" + filter + "%").isNull("parent").orderBy(sortBy + " " + order).findPagingList(pageSize)
				.getPage(page);
	}
	
	public static List<TipoHomenagem> getSubs(TipoHomenagem parent) {
		return find.where().eq("parent.id", parent.id).orderBy("name asc").findList();
	}
	
	public static Map<String,String> options(Long exceptId) {
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(TipoHomenagem c: TipoHomenagem.find.where().ne("id", exceptId).orderBy("name").findList()) {
            options.put(c.id.toString(), c.name);
        }
        return options;
    }
	
	public Form<TipoHomenagem> getForm() {
		Form<TipoHomenagem> tipoHomenagemForm = form(TipoHomenagem.class).fill(this);
		return tipoHomenagemForm;
	}
}