/**
 * 
 */
package net.conselldemallorca.helium.v3.core.api.exportacio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.conselldemallorca.helium.v3.core.api.dto.DefinicioProcesDto;

/**
 * DTO amb informació per exportar/importar una definició de procés
 * cap a /des de un altre Helium.
 * 
 * @author Limit Tecnologies <limit@limit.es>
 */
public class DefinicioProcesExportacio implements Serializable {

	private DefinicioProcesDto definicioProcesDto;
	private List<TascaExportacio> tasques = new ArrayList<TascaExportacio>();
	private List<CampExportacio> camps = new ArrayList<CampExportacio>();
	private List<DocumentExportacio> documents = new ArrayList<DocumentExportacio>();
	private List<TerminiExportacio> terminis = new ArrayList<TerminiExportacio>();
	private List<AgrupacioExportacio> agrupacions = new ArrayList<AgrupacioExportacio>();
	private List<AccioExportacio> accions = new ArrayList<AccioExportacio>();
	private String nomDeploy;
	private byte[] contingutDeploy;

	public DefinicioProcesDto getDefinicioProcesDto() {
		return definicioProcesDto;
	}
	public void setDefinicioProcesDto(DefinicioProcesDto definicioProcesDto) {
		this.definicioProcesDto = definicioProcesDto;
	}
	public List<TascaExportacio> getTasques() {
		return tasques;
	}
	public void setTasques(List<TascaExportacio> tasques) {
		this.tasques = tasques;
	}
	public List<CampExportacio> getCamps() {
		return camps;
	}
	public void setCamps(List<CampExportacio> camps) {
		this.camps = camps;
	}
	public List<DocumentExportacio> getDocuments() {
		return documents;
	}
	public void setDocuments(List<DocumentExportacio> documents) {
		this.documents = documents;
	}
	public List<TerminiExportacio> getTerminis() {
		return terminis;
	}
	public void setTerminis(List<TerminiExportacio> terminis) {
		this.terminis = terminis;
	}
	public List<AgrupacioExportacio> getAgrupacions() {
		return agrupacions;
	}
	public void setAgrupacions(List<AgrupacioExportacio> agrupacions) {
		this.agrupacions = agrupacions;
	}
	public List<AccioExportacio> getAccions() {
		return accions;
	}
	public void setAccions(List<AccioExportacio> accions) {
		this.accions = accions;
	}
	public String getNomDeploy() {
		return nomDeploy;
	}
	public void setNomDeploy(String nomDeploy) {
		this.nomDeploy = nomDeploy;
	}
	public byte[] getContingutDeploy() {
		return contingutDeploy;
	}
	public void setContingutDeploy(byte[] contingutDeploy) {
		this.contingutDeploy = contingutDeploy;
	}
	private static final long serialVersionUID = 1L;

}
