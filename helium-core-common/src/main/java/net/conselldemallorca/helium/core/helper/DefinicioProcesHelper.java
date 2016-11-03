/**
 * 
 */
package net.conselldemallorca.helium.core.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import net.conselldemallorca.helium.core.api.Deployment;
import net.conselldemallorca.helium.core.model.hibernate.Accio;
import net.conselldemallorca.helium.core.model.hibernate.Camp;
import net.conselldemallorca.helium.core.model.hibernate.Camp.TipusCamp;
import net.conselldemallorca.helium.core.model.hibernate.CampAgrupacio;
import net.conselldemallorca.helium.core.model.hibernate.CampRegistre;
import net.conselldemallorca.helium.core.model.hibernate.CampTasca;
import net.conselldemallorca.helium.core.model.hibernate.DefinicioProces;
import net.conselldemallorca.helium.core.model.hibernate.Document;
import net.conselldemallorca.helium.core.model.hibernate.DocumentTasca;
import net.conselldemallorca.helium.core.model.hibernate.Domini;
import net.conselldemallorca.helium.core.model.hibernate.Entorn;
import net.conselldemallorca.helium.core.model.hibernate.Enumeracio;
import net.conselldemallorca.helium.core.model.hibernate.ExpedientTipus;
import net.conselldemallorca.helium.core.model.hibernate.FirmaTasca;
import net.conselldemallorca.helium.core.model.hibernate.Tasca;
import net.conselldemallorca.helium.core.model.hibernate.Tasca.TipusTasca;
import net.conselldemallorca.helium.core.model.hibernate.Termini;
import net.conselldemallorca.helium.core.model.hibernate.Validacio;
import net.conselldemallorca.helium.jbpm3.api.WorkflowEngineApi;
import net.conselldemallorca.helium.v3.core.api.dto.CampTipusDto;
import net.conselldemallorca.helium.v3.core.api.dto.DefinicioProcesDto;
import net.conselldemallorca.helium.v3.core.api.dto.TascaDto;
import net.conselldemallorca.helium.v3.core.api.exception.DeploymentException;
import net.conselldemallorca.helium.v3.core.api.exception.ExportException;
import net.conselldemallorca.helium.v3.core.api.exportacio.AccioExportacio;
import net.conselldemallorca.helium.v3.core.api.exportacio.AgrupacioExportacio;
import net.conselldemallorca.helium.v3.core.api.exportacio.CampExportacio;
import net.conselldemallorca.helium.v3.core.api.exportacio.CampTascaExportacio;
import net.conselldemallorca.helium.v3.core.api.exportacio.DefinicioProcesExportacio;
import net.conselldemallorca.helium.v3.core.api.exportacio.DefinicioProcesExportacioCommandDto;
import net.conselldemallorca.helium.v3.core.api.exportacio.DocumentExportacio;
import net.conselldemallorca.helium.v3.core.api.exportacio.DocumentTascaExportacio;
import net.conselldemallorca.helium.v3.core.api.exportacio.FirmaTascaExportacio;
import net.conselldemallorca.helium.v3.core.api.exportacio.RegistreMembreExportacio;
import net.conselldemallorca.helium.v3.core.api.exportacio.TascaExportacio;
import net.conselldemallorca.helium.v3.core.api.exportacio.TerminiExportacio;
import net.conselldemallorca.helium.v3.core.api.exportacio.ValidacioExportacio;
import net.conselldemallorca.helium.v3.core.repository.AccioRepository;
import net.conselldemallorca.helium.v3.core.repository.CampAgrupacioRepository;
import net.conselldemallorca.helium.v3.core.repository.CampRegistreRepository;
import net.conselldemallorca.helium.v3.core.repository.CampRepository;
import net.conselldemallorca.helium.v3.core.repository.CampTascaRepository;
import net.conselldemallorca.helium.v3.core.repository.CampValidacioRepository;
import net.conselldemallorca.helium.v3.core.repository.DefinicioProcesRepository;
import net.conselldemallorca.helium.v3.core.repository.DocumentRepository;
import net.conselldemallorca.helium.v3.core.repository.DocumentTascaRepository;
import net.conselldemallorca.helium.v3.core.repository.DominiRepository;
import net.conselldemallorca.helium.v3.core.repository.EnumeracioRepository;
import net.conselldemallorca.helium.v3.core.repository.ExpedientTipusRepository;
import net.conselldemallorca.helium.v3.core.repository.FirmaTascaRepository;
import net.conselldemallorca.helium.v3.core.repository.TascaRepository;
import net.conselldemallorca.helium.v3.core.repository.TerminiRepository;

/**
 * Helper per a les definicions de procés.
 * 
 * @author Limit Tecnologies <limit@limit.es>
 */
@Component
public class DefinicioProcesHelper {
	
	@Resource
	private DefinicioProcesRepository definicioProcesRepository;
	@Resource
	private ExpedientTipusRepository expedientTipusRepository;
	@Resource
	private TascaRepository tascaRepository;
	@Resource
	private CampRepository campRepository;
	@Resource
	private CampAgrupacioRepository campAgrupacioRepository;
	@Resource
	private CampValidacioRepository campValidacioRepository;
	@Resource
	private CampRegistreRepository campRegistreRepository;
	@Resource
	private DocumentRepository documentRepository;
	@Resource
	private EnumeracioRepository enumeracioRepository;
	@Resource
	private DominiRepository dominiRepository;
	@Resource
	private TerminiRepository terminiRepository;
	@Resource
	private AccioRepository accioRepository;
	@Resource
	private CampTascaRepository campTascaRepository;
	@Resource
	private DocumentTascaRepository documentTascaRepository;
	@Resource
	private FirmaTascaRepository firmaTascaRepository;

	@Resource
	private ConversioTipusHelper conversioTipusHelper;
	@Resource
	private MessageHelper messageHelper;
	@Resource
	private WorkflowEngineApi workflowEngineApi;
	@Resource
	private EntornHelper entornHelper;
	
	/**
	 * Mètode per importar la informació d'una definició de procés. Si s'efectua sobre una definicó de procés
	 * existent llavors es realitza la importació de la seva informació. Si es realitza sobre un entorn
	 * o tipus d'expedient llavors es fa el desplegament de l'arxiu i la importació de la informació sobre una 
	 * nova versió de la definició de procés.
	 * 
	 * @param entornId
	 * @param expedientTipusId
	 * @param importacio
	 * @param command
	 * @param sobreEscriure Indica si actualitzar o no les entitats amb el mateix codi
	 * @return
	 */
	@Transactional
	public DefinicioProces importar(
			Long entornId, 
			Long expedientTipusId, 
			DefinicioProcesExportacio importacio,
			DefinicioProcesExportacioCommandDto command,
			boolean sobreEscriure) {
		
		Entorn entorn = entornHelper.getEntornComprovantPermisos(
				entornId, 
				true);
		ExpedientTipus expedientTipus = expedientTipusId != null ? 
						expedientTipusRepository.findOne(expedientTipusId) : null;
		
		// Importació

		// si el command és null s'importa tot
		boolean importAll = command == null;
		boolean definicioProcesExisteix = command != null && command.getId() != null;
		DefinicioProces definicio;

		if ( ! definicioProcesExisteix) {
			// Nova definició de procés
			
			// Comprova que no existeixi ja una definició de procés per a un altre tipus d'expedient diferent o pera l'entorn
			definicio = definicioProcesRepository.findDarreraVersioAmbEntornIJbpmKey(
					entornId, 
					importacio.getDefinicioProcesDto().getJbpmKey());
			if (definicio != null)
				if ((definicio.getExpedientTipus() != null // definició de procés lligada a un expedient
					&& 
						(expedientTipusId == null 				// es vol importar a un nou tipus d'expedient
						|| !definicio.getExpedientTipus().getId().equals(expedientTipusId)))) {	// es vol importar a un expedient diferent
					throw new DeploymentException(
							messageHelper.getMessage(
									"exportar.validacio.definicio.desplegada.tipus.expedient", 
									new Object[]{
											definicio.getJbpmKey(),
											definicio.getExpedientTipus().getCodi(),
											definicio.getExpedientTipus().getNom()}));
				} else if (definicio.getExpedientTipus() == null && expedientTipusId != null) { // es vol importar una definició de procés de l'entorn
					throw new DeploymentException(
							messageHelper.getMessage(
									"exportar.validacio.definicio.desplegada.entorn", 
									new Object[]{definicio.getJbpmKey()}));
				}
			Deployment dpd = workflowEngineApi.desplegar(
					importacio.getNomDeploy(), 
					importacio.getContingutDeploy());
			if (dpd != null) {
				// Crea la nova definició de procés
				definicio = new DefinicioProces(
						dpd.getId(),
						dpd.getKey(),
						dpd.getVersion(),
						entorn);
				definicio.setExpedientTipus(expedientTipus);
				definicio = definicioProcesRepository.saveAndFlush(definicio);
				// Crea les tasques publicades
				for (String nomTasca: workflowEngineApi.getTaskNamesFromDeployedProcessDefinition(dpd)) {
					Tasca tasca = new Tasca(
							definicio,
							nomTasca,
							nomTasca,
							TipusTasca.ESTAT);
					String prefixRecursBo = "forms/" + nomTasca;
					for (String resourceName: workflowEngineApi.getResourceNames(dpd.getId())) {
						if (resourceName.startsWith(prefixRecursBo)) {
							tasca.setTipus(TipusTasca.FORM);
							tasca.setRecursForm(nomTasca);
							break;
						}
					}
					tascaRepository.save(tasca);
					definicio.getTasques().add(tasca);
				}				
			} else
				throw new DeploymentException(
						messageHelper.getMessage("exportar.validacio.definicio.deploy.error"));
		} else {
			definicio = definicioProcesRepository.findById(command.getId());
		}

		// Copia la informació importada

		// Agrupacions
		Map<String, CampAgrupacio> agrupacions = new HashMap<String, CampAgrupacio>();
		CampAgrupacio agrupacio;
		if (importAll || command.getAgrupacions().size() > 0)
			for(AgrupacioExportacio agrupacioExportat : importacio.getAgrupacions() )
				if (importAll || command.getAgrupacions().contains(agrupacioExportat.getCodi())){
					agrupacio = null;
					if (definicioProcesExisteix) {
						agrupacio = campAgrupacioRepository.findAmbDefinicioProcesICodi(definicio.getId(), agrupacioExportat.getCodi());
					}
					if (agrupacio == null || sobreEscriure) {
						if (agrupacio == null) {
							agrupacio = new CampAgrupacio(
									definicio, 
									agrupacioExportat.getCodi(), 
									agrupacioExportat.getNom(),
									agrupacioExportat.getOrdre());
							definicio.getAgrupacions().add(agrupacio);
							campAgrupacioRepository.save(agrupacio);
						} else {
							agrupacio.setNom(agrupacioExportat.getNom());
							agrupacio.setOrdre(agrupacioExportat.getOrdre());
						}
						agrupacio.setDescripcio(agrupacioExportat.getDescripcio());
					}
					agrupacions.put(agrupacio.getCodi(), agrupacio);
				}		
		// Camps
		Map<String, Camp> camps = new HashMap<String, Camp>();
		Map<Camp, CampExportacio> registres = new HashMap<Camp, CampExportacio>();
		Map<Camp, CampExportacio> campsTipusConsulta = new HashMap<Camp, CampExportacio>();
		Camp camp;
		if (importAll || command.getVariables().size() > 0) {
			for(CampExportacio campExportat : importacio.getCamps() )
				if (importAll || command.getVariables().contains(campExportat.getCodi())){
					camp = null;
					if (definicioProcesExisteix) {
						camp = campRepository.findByDefinicioProcesAndCodi(definicio, campExportat.getCodi());
					}
					if (camp == null || sobreEscriure) {
						if (camp == null) {
							camp = new Camp(
									definicio, 
									campExportat.getCodi(),
									TipusCamp.valueOf(campExportat.getTipus().toString()),
									campExportat.getEtiqueta());
							definicio.getCamps().add(camp);
							camp = campRepository.saveAndFlush(camp);
						} else {
							camp.setTipus(TipusCamp.valueOf(campExportat.getTipus().toString()));
							camp.setEtiqueta(campExportat.getEtiqueta());
						}
						camp.setIgnored(campExportat.isIgnored());
						camp.setObservacions(campExportat.getObservacions());
						camp.setDominiId(campExportat.getDominiId());
						camp.setDominiParams(campExportat.getDominiParams());
						camp.setDominiCampText(campExportat.getDominiCampText());
						camp.setDominiCampValor(campExportat.getDominiCampValor());
						camp.setDominiCacheText(campExportat.isDominiCacheText());
						camp.setConsultaParams(campExportat.getConsultaParams());
						camp.setConsultaCampText(campExportat.getConsultaCampText());
						camp.setConsultaCampValor(campExportat.getConsultaCampValor());
						camp.setMultiple(campExportat.isMultiple());
						camp.setOcult(campExportat.isOcult());
						camp.setDominiIntern(campExportat.isDominiIntern());
						camp.setDefprocJbpmKey(campExportat.getDefprocJbpmKey());
						camp.setJbpmAction(campExportat.getJbpmAction());
						camp.setOrdre(campExportat.getOrdre());
						
						// Esborra les validacions existents
						for (Validacio validacio : camp.getValidacions())
							campValidacioRepository.delete(validacio);
						camp.getValidacions().clear();
						campValidacioRepository.flush();
						// Afegeix les noves validacions
						for (ValidacioExportacio validacioExportat : campExportat.getValidacions()) {
							Validacio validacio = new Validacio(
									camp, 
									validacioExportat.getExpressio(), 
									validacioExportat.getMissatge());
							validacio.setNom(validacioExportat.getNom());
							validacio.setOrdre(validacioExportat.getOrdre());
							campValidacioRepository.save(validacio);
							camp.getValidacions().add(validacio);
						}
						// Agrupació del camp
						if (campExportat.getAgrupacioCodi() != null && agrupacions.containsKey(campExportat.getAgrupacioCodi()))
							camp.setAgrupacio(agrupacions.get(campExportat.getAgrupacioCodi()));
						// Enumeració del camp
						if (campExportat.getCodiEnumeracio() != null)
							this.relacionarCampEnumeracio(camp, campExportat.getCodiEnumeracio(), entorn, expedientTipus);
						// Domini del camp
						if (campExportat.getCodiDomini() != null)
							this.relacionarCampDomini(camp, campExportat.getCodiDomini(), entorn, expedientTipus);
						// Guarda els camps de tipus consulta per processar-los després de les consultes
						if (campExportat.getCodiConsulta() != null)
							campsTipusConsulta.put(camp, campExportat);
						// Guarda els registres per processar-los després de tots els camps
						if (camp.getTipus() == TipusCamp.REGISTRE) {
							registres.put(camp, campExportat);
						}						
					}
					camps.put(camp.getCodi(), camp);
				}		
			// Tracta els registres
			CampExportacio campExportat;
			for (Camp registre : registres.keySet()) {
				// Esborra la relació amb els membres existents
				if (!registre.getRegistreMembres().isEmpty()) {
					for (CampRegistre campRegistre : registre.getRegistreMembres()) {
						campRegistre.getMembre().getRegistrePares().remove(campRegistre);
						campRegistre.setMembre(null);
						campRegistre.setRegistre(null);
						campRegistreRepository.delete(campRegistre);
					}
					registre.getRegistreMembres().clear();
					campRegistreRepository.flush();
				}
				// afegeix la informació dels registres
				campExportat = registres.get(registre);
				for (RegistreMembreExportacio registreMembre : campExportat.getRegistreMembres()) {
					CampRegistre campRegistre = new CampRegistre(
							camps.get(registre.getCodi()),
							camps.get(registreMembre.getCodi()),
							registreMembre.getOrdre());
					campRegistre.setLlistar(registreMembre.isLlistar());
					campRegistre.setObligatori(registreMembre.isObligatori());
					registre.getRegistreMembres().add(campRegistre);
					campRegistreRepository.save(campRegistre);
				}
			}
		}		
		// Documents
		Map<String, Document> documents = new HashMap<String, Document>();
		Document document;
		if (importAll || command.getDocuments().size() > 0)
			for(DocumentExportacio documentExportat : importacio.getDocuments() )
				if (importAll || command.getDocuments().contains(documentExportat.getCodi())){
					document = null;
					if (definicioProcesExisteix) {
						document = documentRepository.findByDefinicioProcesAndCodi(definicio, documentExportat.getCodi());
					}
					if (document == null || sobreEscriure) {
						if (document == null) {
							document = new Document(
									definicio, 
									documentExportat.getCodi(), 
									documentExportat.getNom());
							definicio.getDocuments().add(document);
							documentRepository.saveAndFlush(document);
						} else {
							document.setNom(documentExportat.getNom());
						}
						document.setDescripcio(documentExportat.getDescripcio());
						document.setArxiuNom(documentExportat.getArxiuNom());
						document.setArxiuContingut(documentExportat.getArxiuContingut());
						document.setPlantilla(documentExportat.isPlantilla());
						document.setCustodiaCodi(documentExportat.getCustodiaCodi());
						document.setContentType(documentExportat.getContentType());
						document.setTipusDocPortasignatures(documentExportat.getTipusDocPortasignatures());
						document.setAdjuntarAuto(documentExportat.isAdjuntarAuto());
						if (documentExportat.getCodiCampData() != null)
							document.setCampData(camps.get(documentExportat.getCodiCampData()));
						document.setConvertirExtensio(documentExportat.getConvertirExtensio());
						document.setExtensionsPermeses(documentExportat.getExtensionsPermeses());
					}
					documents.put(documentExportat.getCodi(), document);
				}	
		
		// Terminis
		Termini termini;
		if (importAll || command.getTerminis().size() > 0)
			for(TerminiExportacio terminiExportat : importacio.getTerminis() )
				if (importAll || command.getTerminis().contains(terminiExportat.getCodi())){
					termini = null;
					if (definicioProcesExisteix) {
						termini = terminiRepository.findByDefinicioProcesAndCodi(definicio, terminiExportat.getCodi());
					}
					if (termini == null || sobreEscriure) {
						if (termini == null) {
							termini = new Termini(
									definicio,
									terminiExportat.getCodi(),
									terminiExportat.getNom(),
									terminiExportat.getAnys(),
									terminiExportat.getMesos(),
									terminiExportat.getDies(),
									terminiExportat.isLaborable());
							definicio.getTerminis().add(termini);
							terminiRepository.save(termini);
						} else {
							termini.setNom(terminiExportat.getNom());
							termini.setNom(terminiExportat.getNom());
							termini.setAnys(terminiExportat.getAnys());
							termini.setMesos(terminiExportat.getMesos());
							termini.setDies(terminiExportat.getDies());
							termini.setLaborable(terminiExportat.isLaborable());
						}
						termini.setDuradaPredefinida(terminiExportat.isDuradaPredefinida());
						termini.setDescripcio(terminiExportat.getDescripcio());
						termini.setDiesPrevisAvis(terminiExportat.getDiesPrevisAvis());
						termini.setAlertaPrevia(terminiExportat.isAlertaPrevia());
						termini.setAlertaFinal(terminiExportat.isAlertaFinal());
						termini.setAlertaCompletat(terminiExportat.isAlertaCompletat());
						termini.setManual(terminiExportat.isManual());
					}
				}
		
		// Accions
		Accio accio;
		if (importAll || command.getAccions().size() > 0)
			for(AccioExportacio accioExportat : importacio.getAccions() )
				if (importAll || command.getAccions().contains(accioExportat.getCodi())){
					accio = null;
					if (definicioProcesExisteix) {
						accio = accioRepository.findByDefinicioProcesIdAndCodi(definicio.getId(), accioExportat.getCodi());
					}
					if (accio == null || sobreEscriure) {
						if (accio == null) {
							accio = new Accio(
									definicio, 
									accioExportat.getCodi(), 
									accioExportat.getNom(),
									accioExportat.getJbpmAction());
							definicio.getAccions().add(accio);
							accioRepository.save(accio);
						} else {
							accio.setNom(accioExportat.getNom());
							accio.setJbpmAction(accioExportat.getJbpmAction());
						}
						accio.setDescripcio(accioExportat.getDescripcio());
						accio.setPublica(accioExportat.isPublica());
						accio.setOculta(accioExportat.isOculta());
						accio.setRols(accioExportat.getRols());
					}
				}		
		
		// Tasques
		Tasca tasca;
		if (importAll || command.getTasques().size() > 0)
			for(TascaExportacio tascaExportat : importacio.getTasques() )
				if (importAll || command.getTasques().contains(tascaExportat.getJbpmName())){
					tasca = tascaRepository.findByJbpmNameAndDefinicioProces(tascaExportat.getJbpmName(), definicio);
					if (!definicioProcesExisteix || sobreEscriure) {
						tasca.setNom(tascaExportat.getNom());
						tasca.setTipus(TipusTasca.valueOf(tascaExportat.getTipus().toString()));
						tasca.setMissatgeInfo(tascaExportat.getMissatgeInfo());
						tasca.setMissatgeWarn(tascaExportat.getMissatgeWarn());
						tasca.setNomScript(tascaExportat.getNomScript());
						tasca.setExpressioDelegacio(tascaExportat.getExpressioDelegacio());
						tasca.setRecursForm(tascaExportat.getRecursForm());
						tasca.setFormExtern(tascaExportat.getFormExtern());
						tasca.setTramitacioMassiva(tascaExportat.isTramitacioMassiva());
						tasca.setFinalitzacioSegonPla(tascaExportat.isFinalitzacioSegonPla());
						tascaRepository.save(tasca);
						
						// Si la tasca ja existia llavors esborra els camps, documents i firmes
						if (definicioProcesExisteix) {
							for(CampTasca c : tasca.getCamps())
								campTascaRepository.delete(c);
							tasca.getCamps().clear();
							documentTascaRepository.flush();
							
							for(DocumentTasca d : tasca.getDocuments())
								documentTascaRepository.delete(d);
							tasca.getDocuments().clear();
							documentTascaRepository.flush();
							
							for(FirmaTasca f : tasca.getFirmes())
								firmaTascaRepository.delete(f);
							tasca.getFirmes().clear();
							firmaTascaRepository.flush();
						}
						// Camps de la tasca
						for (CampTascaExportacio campExportat : tascaExportat.getCamps()) {
							CampTasca campTasca = new CampTasca();
							campTasca.setOrder(campExportat.getOrder());		
							campTasca.setReadFrom(campExportat.isReadFrom());
							campTasca.setWriteTo(campExportat.isWriteTo());
							campTasca.setRequired(campExportat.isRequired());
							campTasca.setReadOnly(campExportat.isReadOnly());
							campTasca.setTasca(tasca);
							this.relacionarCampTasca(
									campTasca, 
									campExportat.getCampCodi(), 
									campExportat.isTipusExpedient(),
									expedientTipus, 
									definicio);
							tasca.getCamps().add(campTasca);	
							campTascaRepository.save(campTasca);
						}
						// Documents de la tasca
						for (DocumentTascaExportacio documentExportat : tascaExportat.getDocuments()) {
							DocumentTasca documentTasca = new DocumentTasca();
							documentTasca.setRequired(documentExportat.isRequired());
							documentTasca.setReadOnly(documentExportat.isReadOnly());
							documentTasca.setOrder(documentExportat.getOrder());
							documentTasca.setTasca(tasca);
							this.relacionarDocumentTasca(
									documentTasca, 
									documentExportat.getDocumentCodi(), 
									documentExportat.isTipusExpedient(),
									expedientTipus, 
									definicio);
							tasca.getDocuments().add(documentTasca);	
							documentTascaRepository.save(documentTasca);
						}
						// Firmes de la tasca
						for (FirmaTascaExportacio firmaExportat : tascaExportat.getFirmes()) {
							FirmaTasca firmaTasca = new FirmaTasca();
							firmaTasca.setRequired(firmaExportat.isRequired());
							firmaTasca.setOrder(firmaExportat.getOrder());
							this.relacionarFirmaTasca(
									firmaTasca, 
									firmaExportat.getDocumentCodi(), 
									firmaExportat.isTipusExpedient(),
									expedientTipus, 
									definicio);
							firmaTasca.setTasca(tasca);
							tasca.getFirmes().add(firmaTasca);	
							firmaTascaRepository.save(firmaTasca);
						}
					}
				}
		return definicio;
	}
	
	/** Troba el domini per codi dins del tius d'expedient o l'entorn i el relaciona amb el camp. Si 
	 * no el troba llença una excepció de no trobat.
	 * @param camp
	 * @param codiDomini
	 * @param entorn
	 * @param expedientTipus
	 */
	private void relacionarCampDomini(
			Camp camp, 
			String codiDomini, 
			Entorn entorn, 
			ExpedientTipus expedientTipus) throws DeploymentException {
		
		Domini domini = null;
		if (expedientTipus != null)
			for (Domini d : expedientTipus.getDominis())
				if (d.getCodi().equals(codiDomini)) {
					domini = d;
					break;
				}
		if (domini == null) {
			// cerca a l'entorn
			domini = dominiRepository.findByEntornAndCodi(entorn, codiDomini);	
		}
		if (domini != null)
			camp.setDomini(domini);
		else
			throw new DeploymentException(
					messageHelper.getMessage(
					"exportar.validacio.variable.seleccio.domini", 
					new Object[]{
							camp.getCodi(),
							codiDomini}));
	}

	/** Troba la enumeració per codi dins del tius d'expedient o l'entorn i el relaciona amb el camp. Si 
	 * no el troba llença una excepció de no trobat.
	 * @param camp
	 * @param codiEnumeracio
	 * @param entorn
	 * @param expedientTipus
	 */
	private void relacionarCampEnumeracio(
			Camp camp, 
			String codiEnumeracio, 
			Entorn entorn,
			ExpedientTipus expedientTipus) throws DeploymentException {

		Enumeracio enumeracio = null;
		if (expedientTipus != null)
			for (Enumeracio e : expedientTipus.getEnumeracions())
				if (e.getCodi().equals(codiEnumeracio)) {
					enumeracio = e;
					break;
				}
		if (enumeracio == null) {
			// cerca a l'entorn
			enumeracio = enumeracioRepository.findByEntornAndCodi(entorn, codiEnumeracio);	
		}
		if (enumeracio != null)
			camp.setEnumeracio(enumeracio);
		else
			throw new DeploymentException(
					messageHelper.getMessage(
					"exportar.validacio.variable.seleccio.enumeracio", 
					new Object[]{
							camp.getCodi(),
							codiEnumeracio}));
	}
	
	/** Troba el camp per al camp tasca segons si el tipus d'expedient està configurat amb info pròpia.
	 * @param campTasca
	 * @param codiCamp
	 * @param expedientTipus
	 * @param definicio
	 */
	private void relacionarCampTasca(
			CampTasca campTasca, 
			String codiCamp, 
			boolean isTipusExpedient,
			ExpedientTipus expedientTipus,
			DefinicioProces definicio) throws DeploymentException {
		
		Camp camp = null;
		if (expedientTipus != null && isTipusExpedient) {
			// Camp del tipus d'expedient
			camp = campRepository.findByExpedientTipusAndCodi(expedientTipus, codiCamp);
		} else {
			// Camp de la definició de procés
			camp = campRepository.findByDefinicioProcesAndCodi(definicio, codiCamp);
		}
		if (camp != null)
			campTasca.setCamp(camp);
		else
			throw new DeploymentException(
					messageHelper.getMessage(
					"exportar.validacio.tasca.variable" + (isTipusExpedient ? ".expedientTipus" : ""), 
					new Object[]{
							campTasca.getTasca().getNom(),
							codiCamp}));
	}	
	
	/** Troba el document per al document tasca segons si el tipus d'expedient està configurat amb info pròpia.
	 * @param documentTasca
	 * @param codiDocument
	 * @param expedientTipus
	 * @param definicio
	 */
	private void relacionarDocumentTasca(
			DocumentTasca documentTasca, 
			String codiDocument, 
			boolean isTipusExpedient,
			ExpedientTipus expedientTipus,
			DefinicioProces definicio) throws DeploymentException {
		
		Document document = null;
		if (expedientTipus != null && isTipusExpedient) {
			// Camp del tipus d'expedient
			document = documentRepository.findByExpedientTipusAndCodi(expedientTipus, codiDocument);
		} else {
			// Camp de la definició de procés
			document = documentRepository.findByDefinicioProcesAndCodi(definicio, codiDocument);
		}
		if (document != null)
			documentTasca.setDocument(document);
		else
			throw new DeploymentException(
					messageHelper.getMessage(
					"exportar.validacio.tasca.document" + (isTipusExpedient ? ".expedientTipus" : ""), 
					new Object[]{
							documentTasca.getTasca().getNom(),
							codiDocument}));
	}	

	/** Troba el document per a la firma tasca segons si el tipus d'expedient està configurat amb info pròpia.
	 * @param firmaTasca
	 * @param codiDocument
	 * @param expedientTipus
	 * @param definicio
	 */
	private void relacionarFirmaTasca(
			FirmaTasca firmaTasca, 
			String codiDocument, 
			boolean isTipusExpedient,
			ExpedientTipus expedientTipus,
			DefinicioProces definicio) throws DeploymentException {
		
		Document document = null;
		if (expedientTipus != null && isTipusExpedient) {
			// Camp del tipus d'expedient
			document = documentRepository.findByExpedientTipusAndCodi(expedientTipus, codiDocument);
		} else {
			// Camp de la definició de procés
			document = documentRepository.findByDefinicioProcesAndCodi(definicio, codiDocument);
		}
		if (document != null)
			firmaTasca.setDocument(document);
		else
			throw new DeploymentException(
					messageHelper.getMessage(
					"exportar.validacio.tasca.firma" + (isTipusExpedient ? ".expedientTipus" : ""), 
					new Object[]{
							firmaTasca.getTasca().getNom(),
							codiDocument}));
	}
	/** Mètode per a construir un objecte d'exportació de la definició de procés.
	 * 
	 * @param definicioProcesId Especifica una definició de procés.
	 * @param command Objecte amb els codis de la informació a incloure en la exportació. Si és null s'inclourà
	 * tota la informació sense filtrar pel command.
	 * @return
	 */
	@Transactional(readOnly = true)
	public DefinicioProcesExportacio getExportacio(
			Long definicioProcesId, 
			DefinicioProcesExportacioCommandDto command) {
		
		// si el command és null s'exporta tot
		boolean exportAll = command == null;
		
		DefinicioProcesExportacio exportacio = new DefinicioProcesExportacio();
		DefinicioProces definicio = 
				definicioProcesRepository.findOne(definicioProcesId);
		exportacio.setDefinicioProcesDto(
				conversioTipusHelper.convertir(
						definicio, 
						DefinicioProcesDto.class));
		exportacio.setNomDeploy("export.par");
		Set<String> resourceNames = workflowEngineApi.getResourceNames(definicio.getJbpmId());
		if (resourceNames != null && resourceNames.size() > 0) {
			try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ZipOutputStream zos = new ZipOutputStream(baos);
				byte[] data = new byte[1024];
				for (String resource: resourceNames) {
					byte[] bytes = workflowEngineApi.getResourceBytes(
							definicio.getJbpmId(), 
							resource);
					if (bytes != null) {
						InputStream is = new ByteArrayInputStream(bytes);
						zos.putNextEntry(new ZipEntry(resource));
						int count;
						while ((count = is.read(data, 0, 1024)) != -1)
							zos.write(data, 0, count);
				        zos.closeEntry();
					}
				}
				zos.close();
				exportacio.setContingutDeploy(baos.toByteArray());
			} catch (Exception ex) {
				String errMsg = messageHelper.getMessage(
						"error.dissenyService.generantContingut.definicioProces", 
						new Object[] {definicio.getJbpmKey() + " v." + definicio.getVersio()});
				logger.error(errMsg, ex);
				throw new ExportException(errMsg, ex);
			}
		}
		// Tasques
		if (exportAll || command.getTasques().size() > 0)
			for (Tasca tasca : definicio.getTasques()) 
				if (exportAll || command.getTasques().contains(tasca.getJbpmName())) {
					TascaExportacio tascaExportacio = new TascaExportacio(
							tasca.getNom(),
							TascaDto.TipusTascaDto.valueOf(tasca.getTipus().toString()),
							tasca.getJbpmName());
					tascaExportacio.setMissatgeInfo(tasca.getMissatgeInfo());
					tascaExportacio.setMissatgeWarn(tasca.getMissatgeWarn());
					tascaExportacio.setNomScript(tasca.getNomScript());
					tascaExportacio.setExpressioDelegacio(tasca.getExpressioDelegacio());
					tascaExportacio.setRecursForm(tasca.getRecursForm());
					tascaExportacio.setFormExtern(tasca.getFormExtern());
					tascaExportacio.setTramitacioMassiva(tasca.isTramitacioMassiva());
					tascaExportacio.setFinalitzacioSegonPla(tasca.isFinalitzacioSegonPla());
					// Afegeix els camps de la tasca
					for (CampTasca camp: tasca.getCamps()) {
						tascaExportacio.addCamp(
								new CampTascaExportacio(
									camp.getCamp().getCodi(),
									camp.isReadFrom(),
									camp.isWriteTo(),
									camp.isRequired(),
									camp.isReadOnly(),
									camp.getOrder(),
									camp.getCamp().getExpedientTipus() != null));
					}
					// Afegeix els documents de la tasca
					for (DocumentTasca document: tasca.getDocuments()) {
						tascaExportacio.addDocument(
								new DocumentTascaExportacio(
										document.getDocument().getCodi(),
										document.isRequired(),
										document.isReadOnly(),
										document.getOrder(),
										document.getDocument().getExpedientTipus() != null));
					}
					// Afegeix les signatures de la tasca
					for (FirmaTasca firma: tasca.getFirmes()) {
						tascaExportacio.addFirma(
								new FirmaTascaExportacio(
										firma.getDocument().getCodi(),
										firma.isRequired(),
										firma.getOrder(),
										firma.getDocument().getExpedientTipus() != null));
					}
					// Afegeix les validacions de la tasca
					for (Validacio validacio: tasca.getValidacions()) {
						tascaExportacio.addValidacio(
								new ValidacioExportacio(
										validacio.getNom(),
										validacio.getExpressio(),
										validacio.getMissatge(),
										validacio.getOrdre()));
					}
					exportacio.getTasques().add(tascaExportacio);
				}	
		// Variables
		if (exportAll || command.getVariables().size() > 0)
			for (Camp camp : definicio.getCamps()) 
				if (exportAll || command.getVariables().contains(camp.getCodi())) {
					boolean necessitaDadesExternes = 
							TipusCamp.SELECCIO.equals(camp.getTipus()) 
							|| TipusCamp.SUGGEST.equals(camp.getTipus());			
					CampExportacio campExportacio = new CampExportacio(
		                    camp.getCodi(),
		                    CampTipusDto.valueOf(camp.getTipus().toString()),
		                    camp.getEtiqueta(),
		                    camp.getObservacions(),
		                    (necessitaDadesExternes) ? camp.getDominiId() : null,
		                    (necessitaDadesExternes) ? camp.getDominiParams() : null,
		                    (necessitaDadesExternes) ? camp.getDominiCampText() : null,
		                    (necessitaDadesExternes) ? camp.getDominiCampValor() : null,
		                    (necessitaDadesExternes) ? camp.getConsultaParams() : null,
		                    (necessitaDadesExternes) ? camp.getConsultaCampText() : null,
		                    (necessitaDadesExternes) ? camp.getConsultaCampValor() : null,
		                    camp.isMultiple(),
		                    camp.isOcult(),
		                    camp.isDominiIntern(),
		                    camp.isDominiCacheText(),
		                    (necessitaDadesExternes && camp.getEnumeracio() != null) ? camp.getEnumeracio().getCodi() : null,
		                    (necessitaDadesExternes && camp.getDomini() != null) ? camp.getDomini().getCodi() : null,
		                    (necessitaDadesExternes && camp.getConsulta() != null) ? camp.getConsulta().getCodi() : null,
		                    (camp.getAgrupacio() != null) ? camp.getAgrupacio().getCodi() : null,
		                    camp.getDefprocJbpmKey(),
		                    camp.getJbpmAction(),
		                    camp.getOrdre(),
		                    camp.isIgnored());
					exportacio.getCamps().add(campExportacio);
					// Afegeix les validacions del camp
					for (Validacio validacio: camp.getValidacions()) {
						campExportacio.addValidacio(new ValidacioExportacio(
								validacio.getNom(),
								validacio.getExpressio(),
								validacio.getMissatge(),
								validacio.getOrdre()));
					}
					// Afegeix els membres dels camps de tipus registre
					for (CampRegistre membre: camp.getRegistreMembres()) {
						campExportacio.addRegistreMembre(new RegistreMembreExportacio(
								membre.getMembre().getCodi(),
								membre.isObligatori(),
								membre.isLlistar(),
								membre.getOrdre()));
					}
				}
		// Agrupacions
		if (exportAll || command.getAgrupacions().size() > 0)
			for (CampAgrupacio agrupacio: definicio.getAgrupacions()) 
				if (exportAll || command.getAgrupacions().contains(agrupacio.getCodi()))
					exportacio.getAgrupacions().add(new AgrupacioExportacio(
							agrupacio.getCodi(),
							agrupacio.getNom(),
							agrupacio.getDescripcio(),
							agrupacio.getOrdre()));		
		// Documents
		if (exportAll || command.getDocuments().size() > 0) {
			DocumentExportacio documentExportacio;
			for (Document document : definicio.getDocuments())
				if (exportAll || command.getDocuments().contains(document.getCodi())) {
					documentExportacio = new DocumentExportacio(
							document.getCodi(),
							document.getNom(),
							document.getDescripcio(),
							document.getArxiuContingut(),
							document.getArxiuNom(),
							document.isPlantilla());
					documentExportacio.setCustodiaCodi(document.getCustodiaCodi());
					documentExportacio.setContentType(document.getContentType());
					documentExportacio.setTipusDocPortasignatures(document.getTipusDocPortasignatures());
					documentExportacio.setAdjuntarAuto(document.isAdjuntarAuto());
					if (document.getCampData() != null)
						documentExportacio.setCodiCampData(document.getCampData().getCodi());
					documentExportacio.setConvertirExtensio(document.getConvertirExtensio());
					documentExportacio.setExtensionsPermeses(document.getExtensionsPermeses());
					exportacio.getDocuments().add(documentExportacio);
				}
		}		
		// Terminis
		if (exportAll || command.getTerminis().size() > 0) {
			TerminiExportacio terminiExportacio;
			for (Termini termini : definicio.getTerminis())
				if (exportAll || command.getTerminis().contains(termini.getCodi())) {
					terminiExportacio = new TerminiExportacio(
							termini.getCodi(),
							termini.getNom(),
							termini.getDescripcio(),
							termini.isDuradaPredefinida(),
							termini.getAnys(),
							termini.getMesos(),
							termini.getDies(),
							termini.isLaborable(),
							termini.getDiesPrevisAvis(),
							termini.isAlertaPrevia(),
							termini.isAlertaFinal(),
							termini.isAlertaCompletat(),
							termini.isManual());
					exportacio.getTerminis().add(terminiExportacio);
				}
		}	
		// Accions
		if (exportAll || command.getAccions().size() > 0) {
			AccioExportacio accioExportacio;
			for (Accio accio : definicio.getAccions())
				if (exportAll || command.getAccions().contains(accio.getCodi())) {
					accioExportacio = new AccioExportacio(
							accio.getCodi(),
							accio.getNom(),
							accio.getDescripcio(),
							accio.getDefprocJbpmKey(),
							accio.getJbpmAction(),
							accio.isPublica(),
							accio.isOculta(),
							accio.getRols());
					exportacio.getAccions().add(accioExportacio);
				}
		}		
		
	return exportacio;
}

	private static final Logger logger = LoggerFactory.getLogger(DefinicioProcesHelper.class);

}
