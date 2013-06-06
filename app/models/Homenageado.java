package models;


public enum Homenageado  {

	MARCONI("Marconi Perillo"),
	VALERIA("Valéria Perillo");
	
	private String label;

	/**
	 * Instancia um novo Homenageado
	 * 
	 * @param label
	 */
	private Homenageado(final String label) {
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
