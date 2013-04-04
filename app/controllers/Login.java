package controllers;

import models.Usuario;

public class Login {
	
	 public String email;
     public String senha;
     
     public String validate() {
         if(Usuario.autenticar(email, senha) == null) {
             return "Usuário ou senha inválido.";
         }
         return null;
     }

}
