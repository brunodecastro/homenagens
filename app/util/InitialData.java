package util;


import java.util.List;
import java.util.Map;

import models.Usuario;
import play.libs.Yaml;

import com.avaje.ebean.Ebean;

public class InitialData {
	
	@SuppressWarnings("unchecked")
	public static void execute() {
        
		if(Ebean.find(Usuario.class).findRowCount() == 0) {
            
            Map<String,List<Object>> all = (Map<String,List<Object>>) Yaml.load("initial-data.yml");

            // Inseri os usuarios
            Ebean.save(all.get("usuarios"));

            // Insert contas
            Ebean.save(all.get("contas"));
            
            for(Object conta: all.get("contas")) {
                // Insert the conta/usuario relation
//                Ebean.saveManyToManyAssociations(conta, "usuario");
            }
            
        }
    }

}
