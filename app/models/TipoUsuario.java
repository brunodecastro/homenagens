package models;


public enum TipoUsuario {

	ADMINISTRADOR("Administrador"),
	NORMAL("Normal");
	
	private String label;

	/**
	 * Instancia um novo TipoUsuario
	 * 
	 * @param label
	 */
	private TipoUsuario(final String label) {
		this.label = label;
	}

	/**
	 * Retorna o valor do atributo label
	 * 
	 * @return label
	 */
	public String getLabel() {
		return this.label;
	}
	
}
