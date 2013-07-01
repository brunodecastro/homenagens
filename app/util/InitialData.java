package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

import models.Usuario;
import play.libs.Yaml;

import com.avaje.ebean.Ebean;

public class InitialData {

	@SuppressWarnings("unchecked")
	public static void execute() {

		if (Ebean.find(Usuario.class).findRowCount() == 0) {

			Map<String, List<Object>> all = (Map<String, List<Object>>) Yaml.load("initial-data.yml");

			// Insere os usuarios
			Ebean.save(all.get("usuarios"));

			Ebean.save(all.get("tiposHomenagem"));

		}

		executeScript();
	}

	private static void executeScript() {
		java.sql.Connection connection = play.db.DB.getConnection();

		ScriptRunner runner = new ScriptRunner(connection, false, false);
		try {
			runner.runScript(new BufferedReader(new FileReader("conf/initializeDB.sql")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
