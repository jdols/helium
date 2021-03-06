/**
 * 
 */
package net.conselldemallorca.helium.webapp.mvc;


/**
 * Command per fer una consulta de persones
 * 
 * @author Limit Tecnologies <limit@limit.es>
 */
public class PersonaConsultaCommand {

	private String codi;
	private String nom;
	private String email;



	public PersonaConsultaCommand() {}



	public String getCodi() {
		return codi;
	}
	public void setCodi(String codi) {
		this.codi = codi;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}
