package models;

public enum TipoUsuario {

	ADMINISTRADOR(1, "Administrador"),
	NORMAL(2, "Normal");
	
			private Integer value;
	private String label;

	/**
	 * Instancia um novo TipoUsuario
	 * 
	 * @param value
	 * @param label
	 */
	private TipoUsuario(final int value, final String label) {
		this.value = Integer.valueOf(value);
		this.label = label;
	}

	public static TipoUsuario getEnumFromValue(final int value) {
		for (TipoUsuario tipoUsuario : values()) {
			if (tipoUsuario.getValue().equals(Integer.valueOf(value))) {
				return tipoUsuario;
			}
		}
		return null;
	}

	/**
	 * Retorna o valor do atributo value
	 * 
	 * @return value
	 */
	public Integer getValue() {
		return this.value;
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
