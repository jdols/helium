/**
 * 
 */
package net.conselldemallorca.helium.webapp.mvc;

/**
 * Command per iniciar un expedient
 * 
 * @author Limit Tecnologies <limit@limit.es>
 */
public class ExpedientTipusSistraCommand {

	private Long expedientTipusId;
	private boolean actiu;
	private String codiTramit;
	private String infoMapeigCamps;
	private String infoMapeigDocuments;
	private String infoMapeigAdjunts;



	public ExpedientTipusSistraCommand() {}

	public Long getExpedientTipusId() {
		return expedientTipusId;
	}
	public void setExpedientTipusId(Long expedientTipusId) {
		this.expedientTipusId = expedientTipusId;
	}

	public boolean isActiu() {
		return actiu;
	}
	public void setActiu(boolean actiu) {
		this.actiu = actiu;
	}

	public String getCodiTramit() {
		return codiTramit;
	}
	public void setCodiTramit(String codiTramit) {
		this.codiTramit = codiTramit;
	}

	public String getInfoMapeigCamps() {
		return infoMapeigCamps;
	}
	public void setInfoMapeigCamps(String infoMapeigCamps) {
		this.infoMapeigCamps = infoMapeigCamps;
	}

	public String getInfoMapeigDocuments() {
		return infoMapeigDocuments;
	}
	public void setInfoMapeigDocuments(String infoMapeigDocuments) {
		this.infoMapeigDocuments = infoMapeigDocuments;
	}

	public String getInfoMapeigAdjunts() {
		return infoMapeigAdjunts;
	}
	public void setInfoMapeigAdjunts(String infoMapeigAdjunts) {
		this.infoMapeigAdjunts = infoMapeigAdjunts;
	}

}
