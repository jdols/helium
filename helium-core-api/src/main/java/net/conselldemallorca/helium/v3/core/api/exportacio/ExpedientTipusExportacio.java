/**
 * 
 */
package net.conselldemallorca.helium.v3.core.api.exportacio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.conselldemallorca.helium.v3.core.api.dto.SequenciaAnyDto;
import net.conselldemallorca.helium.v3.core.api.dto.SequenciaDefaultAnyDto;


/**
 * DTO amb informació d'un tipus d'expedient per a la exportació.
 * 
 * @author Limit Tecnologies <limit@limit.es>
 */
public class ExpedientTipusExportacio implements Serializable {

	private Long id;
	private String codi;
	private String nom;
	private String jbpmProcessDefinitionKey;
	private boolean teNumero;
	private boolean teTitol;
	private boolean demanaNumero;
	private boolean demanaTitol;
	private String expressioNumero;
	private long sequencia = 1;
	private long sequenciaDefault = 1;
	private boolean reiniciarCadaAny;
	private int anyActual = 0;
	private String responsableDefecteCodi;
	private boolean restringirPerGrup;
	private boolean tramitacioMassiva;
	private boolean seleccionarAny;
	private boolean ambRetroaccio;
	private boolean reindexacioAsincrona;
	private boolean ambInfoPropia;

	private Map<Integer, SequenciaAnyDto> sequenciaAny = new TreeMap<Integer, SequenciaAnyDto>();
	private Map<Integer, SequenciaDefaultAnyDto> sequenciaDefaultAny = new TreeMap<Integer, SequenciaDefaultAnyDto>();

	// integració amb forms
	private String formextUrl;
	private String formextUsuari;
	private String formextContrasenya;
	// Integració amb tràmits Sistra
	private String sistraTramitCodi;
	private List<MapeigSistraExportacio> sistraMapejos = new ArrayList<MapeigSistraExportacio>();
	
	
	private List<EstatExportacio> estats = new ArrayList<EstatExportacio>();
	private List<CampExportacio> camps = new ArrayList<CampExportacio>();
	private List<AgrupacioExportacio> agrupacions = new ArrayList<AgrupacioExportacio>();
	private List<DefinicioProcesExportacio> definicions = new ArrayList<DefinicioProcesExportacio>();
	private List<EnumeracioExportacio> enumeracions = new ArrayList<EnumeracioExportacio>();
	private List<DocumentExportacio> documents = new ArrayList<DocumentExportacio>();
	private List<TerminiExportacio> terminis = new ArrayList<TerminiExportacio>();
	private List<AccioExportacio> accions = new ArrayList<AccioExportacio>();
	private List<DominiExportacio> dominis = new ArrayList<DominiExportacio>();
	private List<ConsultaExportacio> consultes = new ArrayList<ConsultaExportacio>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public String getJbpmProcessDefinitionKey() {
		return jbpmProcessDefinitionKey;
	}
	public void setJbpmProcessDefinitionKey(String jbpmProcessDefinitionKey) {
		this.jbpmProcessDefinitionKey = jbpmProcessDefinitionKey;
	}
	public boolean isTeNumero() {
		return teNumero;
	}
	public void setTeNumero(boolean teNumero) {
		this.teNumero = teNumero;
	}
	public boolean isTeTitol() {
		return teTitol;
	}
	public void setTeTitol(boolean teTitol) {
		this.teTitol = teTitol;
	}
	public boolean isDemanaNumero() {
		return demanaNumero;
	}
	public void setDemanaNumero(boolean demanaNumero) {
		this.demanaNumero = demanaNumero;
	}
	public boolean isDemanaTitol() {
		return demanaTitol;
	}
	public void setDemanaTitol(boolean demanaTitol) {
		this.demanaTitol = demanaTitol;
	}
	public String getExpressioNumero() {
		return expressioNumero;
	}
	public void setExpressioNumero(String expressioNumero) {
		this.expressioNumero = expressioNumero;
	}
	public long getSequencia() {
		return sequencia;
	}
	public void setSequencia(long sequencia) {
		this.sequencia = sequencia;
	}
	public long getSequenciaDefault() {
		return sequenciaDefault;
	}
	public void setSequenciaDefault(long sequenciaDefault) {
		this.sequenciaDefault = sequenciaDefault;
	}
	public boolean isReiniciarCadaAny() {
		return reiniciarCadaAny;
	}
	public void setReiniciarCadaAny(boolean reiniciarCadaAny) {
		this.reiniciarCadaAny = reiniciarCadaAny;
	}
	public int getAnyActual() {
		return anyActual;
	}
	public void setAnyActual(int anyActual) {
		this.anyActual = anyActual;
	}
	public String getResponsableDefecteCodi() {
		return responsableDefecteCodi;
	}
	public void setResponsableDefecteCodi(String responsableDefecteCodi) {
		this.responsableDefecteCodi = responsableDefecteCodi;
	}
	public boolean isRestringirPerGrup() {
		return restringirPerGrup;
	}
	public void setRestringirPerGrup(boolean restringirPerGrup) {
		this.restringirPerGrup = restringirPerGrup;
	}
	public boolean isTramitacioMassiva() {
		return tramitacioMassiva;
	}
	public void setTramitacioMassiva(boolean tramitacioMassiva) {
		this.tramitacioMassiva = tramitacioMassiva;
	}
	public boolean isSeleccionarAny() {
		return seleccionarAny;
	}
	public void setSeleccionarAny(boolean seleccionarAny) {
		this.seleccionarAny = seleccionarAny;
	}
	public boolean isAmbRetroaccio() {
		return ambRetroaccio;
	}
	public void setAmbRetroaccio(boolean ambRetroaccio) {
		this.ambRetroaccio = ambRetroaccio;
	}
	public boolean isReindexacioAsincrona() {
		return reindexacioAsincrona;
	}
	public void setReindexacioAsincrona(boolean reindexacioAsincrona) {
		this.reindexacioAsincrona = reindexacioAsincrona;
	}
	public boolean isAmbInfoPropia() {
		return ambInfoPropia;
	}
	public void setAmbInfoPropia(boolean ambInfoPropia) {
		this.ambInfoPropia = ambInfoPropia;
	}
	public Map<Integer, SequenciaAnyDto> getSequenciaAny() {
		return sequenciaAny;
	}
	public void setSequenciaAny(Map<Integer, SequenciaAnyDto> sequenciaAny) {
		this.sequenciaAny = sequenciaAny;
	}
	public Map<Integer, SequenciaDefaultAnyDto> getSequenciaDefaultAny() {
		return sequenciaDefaultAny;
	}
	public void setSequenciaDefaultAny(Map<Integer, SequenciaDefaultAnyDto> sequenciaDefaultAny) {
		this.sequenciaDefaultAny = sequenciaDefaultAny;
	}
	public String getFormextUrl() {
		return formextUrl;
	}
	public void setFormextUrl(String formextUrl) {
		this.formextUrl = formextUrl;
	}
	public String getFormextUsuari() {
		return formextUsuari;
	}
	public void setFormextUsuari(String formextUsuari) {
		this.formextUsuari = formextUsuari;
	}
	public String getFormextContrasenya() {
		return formextContrasenya;
	}
	public void setFormextContrasenya(String formextContrasenya) {
		this.formextContrasenya = formextContrasenya;
	}
	public String getSistraTramitCodi() {
		return sistraTramitCodi;
	}
	public void setSistraTramitCodi(String sistraTramitCodi) {
		this.sistraTramitCodi = sistraTramitCodi;
	}

	public List<MapeigSistraExportacio> getSistraMapejos() {
		return sistraMapejos;
	}
	public void setSistraMapejos(List<MapeigSistraExportacio> sistraMapejos) {
		this.sistraMapejos = sistraMapejos;
	}
	public List<EstatExportacio> getEstats() {
		return estats;
	}
	public void setEstats(List<EstatExportacio> estats) {
		this.estats = estats;
	}

	public List<CampExportacio> getCamps() {
		return camps;
	}
	public void setCamps(List<CampExportacio> camps) {
		this.camps = camps;
	}
	public List<AgrupacioExportacio> getAgrupacions() {
		return agrupacions;
	}
	public void setAgrupacions(List<AgrupacioExportacio> agrupacions) {
		this.agrupacions = agrupacions;
	}
	public List<DefinicioProcesExportacio> getDefinicions() {
		return definicions;
	}
	public void setDefinicions(List<DefinicioProcesExportacio> definicions) {
		this.definicions = definicions;
	}
	public List<EnumeracioExportacio> getEnumeracions() {
		return enumeracions;
	}
	public void setEnumeracions(List<EnumeracioExportacio> enumeracions) {
		this.enumeracions = enumeracions;
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
	public List<AccioExportacio> getAccions() {
		return accions;
	}
	public void setAccions(List<AccioExportacio> accions) {
		this.accions = accions;
	}
	public List<DominiExportacio> getDominis() {
		return dominis;
	}
	public void setDominis(List<DominiExportacio> dominis) {
		this.dominis = dominis;
	}
	public List<ConsultaExportacio> getConsultes() {
		return consultes;
	}
	public void setConsultes(List<ConsultaExportacio> consultes) {
		this.consultes = consultes;
	}

	private static final long serialVersionUID = 4423574853273245620L;
}