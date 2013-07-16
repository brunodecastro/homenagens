package models;


public enum Homenageado  {

	MARCONI("Marconi Perillo", "Sr."),
	VALERIA("Valéria Perillo", "Sra.");
	
	private String label;
	
	private String tratamento;

	/**
	 * Instancia um novo Homenageado
	 * 
	 * @param label
	 */
	private Homenageado(final String label, final String tratamento) {
		this.label = label;
		this.tratamento = tratamento;
	}

	/**
	 * Retorna o valor do atributo label
	 * 
	 * @return label
	 */
	public String getLabel() {
		return this.label;
	}
	
	public String getTratamento() {
		return this.tratamento;
	}
	
	public String getNomeComTratamento() {
		return this.getTratamento() + " " + this.getLabel();
	}

}
