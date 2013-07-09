package net.conselldemallorca.helium.jbpm3.integracio;

import net.conselldemallorca.helium.v3.core.api.service.Jbpm3HeliumService;

/**
 * Classe que fa de pont entre jBPM i Helium.
 * 
 * @author Limit Tecnologies <limit@limit.es>
 */
public final class Jbpm3HeliumBridge {

	private Jbpm3HeliumService jbpm3HeliumService;

	private static final Jbpm3HeliumBridge INSTANCE = new Jbpm3HeliumBridge();
	private Jbpm3HeliumBridge() {
		if (INSTANCE != null) {
			throw new IllegalStateException("Ja hi ha una instància creada");
		}
	}
	public static Jbpm3HeliumBridge getInstance() {
		return INSTANCE;
	}
	public static Jbpm3HeliumService getInstanceService() {
		return INSTANCE.getJbpm3HeliumService();
	}



	/*public String getHeliumProperty(String propertyName) {
		return getJbpm3HeliumService().getHeliumProperty(propertyName);
	}
	public List<FestiuDto> findFestiusAll() {
		return getJbpm3HeliumService().findFestiusAll();
	}
	public ExpedientDto getExpedientIniciant() {
		return getJbpm3HeliumService().getExpedientIniciant();
	}
	public EntornDto getEntornActual() {
		return getJbpm3HeliumService().getEntornActual();
	}
	public DefinicioProcesDto getDefinicioProcesAmbJbpmKeyIVersio(
			String jbpmKey,
			int version) {
		return getJbpm3HeliumService().getDefinicioProcesAmbJbpmKeyIVersio(
				jbpmKey,
				version);
	}
	public DefinicioProcesDto getDarreraVersioAmbEntornIJbpmKey(
			Long entornId,
			String jbpmKey) throws EntornNotFoundException {
		return getJbpm3HeliumService().getDarreraVersioAmbEntornIJbpmKey(
				entornId,
				jbpmKey);
	}
	public DefinicioProcesDto getDefinicioProcesPerProcessInstanceId(
			String processInstanceId) {
		return getJbpm3HeliumService().getDefinicioProcesPerProcessInstanceId(
				processInstanceId);
	}
	public boolean isHeliumAssignmentActive() {
		String organigramaActiu = getHeliumProperty("app.jbpm.identity.source");
		return "helium".equalsIgnoreCase(organigramaActiu);
	}
	public String getUsuariCodiActual() {
		return getJbpm3HeliumService().getUsuariCodiActual();
	}
	public PersonaDto getPersonaAmbCodi(String codi) {
		return getJbpm3HeliumService().getPersonaAmbCodi(codi);
	}
	public AreaDto getAreaAmbEntornICodi(
			Long entornId,
			String codi) throws EntornNotFoundException {
		return getJbpm3HeliumService().getAreaAmbEntornICodi(entornId, codi);
	}
	public CarrecDto getCarrecAmbEntornIAreaICodi(
			Long entornId,
			String areaCodi,
			String carrecCodi) throws EntornNotFoundException, AreaNotFoundException {
		return getJbpm3HeliumService().getCarrecAmbEntornIAreaICodi(
				entornId,
				areaCodi,
				carrecCodi);
	}
	public ReassignacioDto findReassignacioActivaPerUsuariOrigen(
			String usuariCodi) throws PersonaNotFoundException {
		return getJbpm3HeliumService().findReassignacioActivaPerUsuariOrigen(usuariCodi);
	}
	public void alertaEsborrarAmbTaskInstanceId(
			long taskInstanceId) throws TaskInstanceNotFoundException {
		getJbpm3HeliumService().alertaEsborrarAmbTaskInstanceId(taskInstanceId);
	}
	public List<CampTascaDto> findCampsPerTaskInstance(
			long taskInstanceId) throws TaskInstanceNotFoundException {
		return getJbpm3HeliumService().findCampsPerTaskInstance(taskInstanceId);
	}
	public List<DocumentTascaDto> findDocumentsPerTaskInstance(
			long taskInstanceId) throws TaskInstanceNotFoundException {
		return getJbpm3HeliumService().findDocumentsPerTaskInstance(taskInstanceId);
	}
	public String getCodiVariablePerDocumentCodi(String documentCodi) {
		return getJbpm3HeliumService().getCodiVariablePerDocumentCodi(documentCodi);
	}
	public void emailSend(
			String fromAddress,
			List<String> recipients,
			List<String> ccRecipients,
			List<String> bccRecipients,
			String subject,
			String text,
			List<ArxiuDto> attachments) throws PluginException {
		getJbpm3HeliumService().emailSend(
				fromAddress,
				recipients,
				ccRecipients,
				bccRecipients,
				subject,
				text,
				attachments);
	}

	//
	// Handlers
	//
	public void alertaCrear(
			Long entornId,
			Long expedientId,
			Date data,
			String usuariCodi,
			String text) throws EntornNotFoundException, ExpedientNotFoundException {
		getJbpm3HeliumService().alertaCrear(
				entornId,
				expedientId,
				data,
				usuariCodi,
				text);
	}
	public void expedientModificarEstat(
			String processInstanceId,
			String estatCodi) throws ExpedientNotFoundException, EstatNotFoundException {
		ExpedientDto expedient = getJbpm3HeliumService().getExpedientArrelAmbProcessInstanceId(processInstanceId);
		getJbpm3HeliumService().expedientModificarEstat(
				expedient.getId(),
				estatCodi);
	}
	public void expedientModificarComentari(
			String processInstanceId,
			String comentari) throws ExpedientNotFoundException {
		ExpedientDto expedient = getJbpm3HeliumService().getExpedientArrelAmbProcessInstanceId(processInstanceId);
		getJbpm3HeliumService().expedientModificarComentari(
				expedient.getId(),
				comentari);
	}
	public void expedientModificarGeoref(
			String processInstanceId,
			Double posx,
			Double posy,
			String referencia) throws ExpedientNotFoundException {
		ExpedientDto expedient = getJbpm3HeliumService().getExpedientArrelAmbProcessInstanceId(processInstanceId);
		getJbpm3HeliumService().expedientModificarGeoref(
				expedient.getId(),
				posx,
				posy,
				referencia);
	}
	public void expedientModificarGrup(
			String processInstanceId,
			String grupCodi) throws ExpedientNotFoundException {
		ExpedientDto expedient = getJbpm3HeliumService().getExpedientArrelAmbProcessInstanceId(processInstanceId);
		getJbpm3HeliumService().expedientModificarGrup(
				expedient.getId(),
				grupCodi);
	}
	public void expedientModificarNumero(
			String processInstanceId,
			String numero) throws ExpedientNotFoundException {
		ExpedientDto expedient = getJbpm3HeliumService().getExpedientArrelAmbProcessInstanceId(processInstanceId);
		getJbpm3HeliumService().expedientModificarNumero(
				expedient.getId(),
				numero);
	}
	public void expedientModificarResponsable(
			String processInstanceId,
			String responsableCodi) throws ExpedientNotFoundException, PersonaNotFoundException {
		ExpedientDto expedient = getJbpm3HeliumService().getExpedientArrelAmbProcessInstanceId(processInstanceId);
		getJbpm3HeliumService().expedientModificarResponsable(
				expedient.getId(),
				responsableCodi);
	}
	public void expedientModificarTitol(
			String processInstanceId,
			String titol) throws ExpedientNotFoundException {
		ExpedientDto expedient = getJbpm3HeliumService().getExpedientArrelAmbProcessInstanceId(processInstanceId);
		getJbpm3HeliumService().expedientModificarTitol(
				expedient.getId(),
				titol);
	}
	public void expedientAturar(
			String processInstanceId,
			String motiu) throws ExpedientNotFoundException {
		ExpedientDto expedient = getJbpm3HeliumService().getExpedientArrelAmbProcessInstanceId(processInstanceId);
		getJbpm3HeliumService().expedientAturar(expedient.getId(), motiu);
	}
	public void expedientReprendre(String processInstanceId) throws ExpedientNotFoundException {
		ExpedientDto expedient = getJbpm3HeliumService().getExpedientArrelAmbProcessInstanceId(processInstanceId);
		getJbpm3HeliumService().expedientReprendre(expedient.getId());
	}
	public void expedientLuceneIndexUpdate(String processInstanceId) throws ExpedientNotFoundException {
		ExpedientDto expedient = getJbpm3HeliumService().getExpedientArrelAmbProcessInstanceId(processInstanceId);
		getJbpm3HeliumService().expedientLuceneIndexUpdate(expedient.getId());
	}
	public void documentGenerarAmbPlantilla(
			String processInstanceId,
			String documentCodi,
			Date dataDocument) {
		getJbpm3HeliumService().generarAmbPlantilla(
				null,
				processInstanceId,
				documentCodi,
				dataDocument,
				true);
	}
	public ExpedientDto getExpedientArrelAmbProcessInstanceId(
			String processInstanceId) {
		return getJbpm3HeliumService().getExpedientArrelAmbProcessInstanceId(processInstanceId);
	}
	public ExpedientDto getExpedientAmbEntornITipusINumero(
			Long entornId,
			String expedientTipusCodi,
			String numero) throws EntornNotFoundException, ExpedientTipusNotFoundException {
		return getJbpm3HeliumService().getExpedientAmbEntornITipusINumero(
				entornId,
				expedientTipusCodi,
				numero);
	}
	public TerminiDto getTerminiAmbDefinicioProcesICodi(
			Long definicioProcesId,
			String terminiCodi) throws TerminiNotFoundException {
		return getJbpm3HeliumService().getTerminiAmbDefinicioProcesICodi(
				definicioProcesId,
				terminiCodi);
	}
	public TerminiIniciatDto getTerminiIniciatAmbProcessInstanceITerminiCodi(
			String processInstanceId,
			String terminiCodi) throws TerminiNotFoundException {
		return getJbpm3HeliumService().getTerminiIniciatAmbCodi(
				processInstanceId,
				terminiCodi);
	}
	public void configurarTerminiIniciatAmbDadesJbpm(
			Long terminiIniciatId,
			String taskInstanceId,
			Long timerId) throws TerminiIniciatNotFoundException {
		getJbpm3HeliumService().configurarTerminiIniciatAmbDadesJbpm(
				terminiIniciatId,
				taskInstanceId,
				timerId);
	}
	public Date terminiCalcularDataInici(
			Date inici,
			int anys,
			int mesos,
			int dies,
			boolean laborable) {
		return getJbpm3HeliumService().terminiCalcularDataInici(
				inici,
				anys,
				mesos, 
				dies,
				laborable);
	}
	public Date terminiCalcularDataFi(
			Date inici,
			int anys,
			int mesos,
			int dies,
			boolean laborable) {
		return getJbpm3HeliumService().terminiCalcularDataFi(
				inici,
				anys,
				mesos, 
				dies,
				laborable);
	}
	public void terminiIniciar(
			Long terminiId,
			String processInstanceId,
			Date inici,
			int anys,
			int mesos,
			int dies,
			boolean esDataFi) {
		getJbpm3HeliumService().terminiIniciar(
				terminiId,
				processInstanceId,
				inici,
				anys,
				mesos,
				dies,
				esDataFi);
	}
	public void terminiIniciar(
			Long terminiId,
			String processInstanceId,
			Date inici,
			boolean esDataFi) {
		getJbpm3HeliumService().terminiIniciar(
				terminiId,
				processInstanceId,
				inici,
				esDataFi);
		
	}
	public void terminiCancelar(
			Long terminiIniciatId,
			Date data) {
		getJbpm3HeliumService().terminiCancelar(
				terminiIniciatId,
				data);
	}
	public void terminiPausar(
			Long terminiIniciatId,
			Date data) {
		getJbpm3HeliumService().terminiPausar(
				terminiIniciatId,
				data);
	}
	public void terminiContinuar(
			Long terminiIniciatId,
			Date data) {
		getJbpm3HeliumService().terminiContinuar(
				terminiIniciatId,
				data);
	}
	public DocumentDto getDocumentInfo(Long documentStoreId) {
		return getJbpm3HeliumService().getDocumentInfo(documentStoreId);
	}
	public ArxiuDto getArxiuPerMostrar(Long documentStoreId) {
		return getJbpm3HeliumService().getArxiuPerMostrar(documentStoreId);
	}
	public Long documentExpedientGuardar(
			String processInstanceId,
			String codi,
			Date data,
			String arxiuNom,
			byte[] arxiuContingut) {
		return getJbpm3HeliumService().documentExpedientGuardar(
				processInstanceId,
				codi,
				data,
				arxiuNom,
				arxiuContingut);
	}
	public Long documentExpedientAdjuntar(
			String processInstanceId,
			String adjuntId,
			String adjuntTitol,
			Date adjuntData,
			String arxiuNom,
			byte[] arxiuContingut) {
		return getJbpm3HeliumService().documentExpedientAdjuntar(
				processInstanceId,
				adjuntId,
				adjuntTitol,
				adjuntData,
				arxiuNom,
				arxiuContingut);
	}
	public void documentExpedientEsborrar(
			String taskInstanceId,
			String processInstanceId,
			String documentCodi) {
		getJbpm3HeliumService().documentExpedientEsborrar(
				taskInstanceId,
				processInstanceId,
				documentCodi);
	}
	public void documentExpedientGuardarDadesRegistre(
			Long documentStoreId,
			String registreNumero,
			Date registreData,
			String registreOficinaCodi,
			String registreOficinaNom,
			boolean registreEntrada) {
		getJbpm3HeliumService().documentExpedientGuardarDadesRegistre(
				documentStoreId,
				registreNumero,
				registreData,
				registreOficinaCodi,
				registreOficinaNom,
				registreEntrada);
	}
	public boolean isRegistreActiu() {
		return getJbpm3HeliumService().isRegistreActiu();
	}
	public RegistreIdDto registreAnotacioEntrada(
			RegistreAnotacioDto anotacio) throws PluginException {
		return getJbpm3HeliumService().registreAnotacioEntrada(anotacio);
	}
	public RegistreIdDto registreAnotacioSortida(
			RegistreAnotacioDto anotacio) throws PluginException {
		return getJbpm3HeliumService().registreAnotacioSortida(anotacio);
	}
	public RegistreIdDto registreNotificacio(
			RegistreNotificacioDto notificacio) throws PluginException {
		return getJbpm3HeliumService().registreNotificacio(notificacio);
	}
	public Date registreNotificacioComprovarRecepcio(
			String registreNumero) throws PluginException {
		return getJbpm3HeliumService().registreNotificacioComprovarRecepcio(registreNumero);
	}
	public String getRegistreOficinaNom(
			String oficinaCodi) throws PluginException {
		return getJbpm3HeliumService().getRegistreOficinaNom(oficinaCodi);
	}
	public void portasignaturesEnviar(
			Long documentId,
			List<Long> annexosId,
			PersonaDto persona,
			List<PersonaDto> personesPas1,
			int minSignatarisPas1,
			List<PersonaDto> personesPas2,
			int minSignatarisPas2,
			List<PersonaDto> personesPas3,
			int minSignatarisPas3,
			Long expedientId,
			String importancia,
			Date dataLimit,
			Long tokenId,
			Long processInstanceId,
			String transicioOK,
			String transicioKO) {
		getJbpm3HeliumService().portasignaturesEnviar(
				documentId,
				annexosId,
				persona,
				personesPas1,
				minSignatarisPas1,
				personesPas2,
				minSignatarisPas2,
				personesPas3,
				minSignatarisPas3,
				expedientId,
				importancia,
				dataLimit,
				tokenId,
				processInstanceId,
				transicioOK,
				transicioKO);
	}
	public void zonaperExpedientCrear(
			String processInstanceId,
			ZonaperExpedientDto dadesExpedient) throws PluginException {
		getJbpm3HeliumService().zonaperExpedientCrear(
				processInstanceId,
				dadesExpedient);
	}
	public void zonaperEventCrear(
			String processInstanceId,
			ZonaperEventDto dadesEvent)
			throws PluginException {
		getJbpm3HeliumService().zonaperEventCrear(
				processInstanceId,
				dadesEvent);
	}

	public List<DominiRespostaFilaDto> dominiConsultar(
			String processInstanceId,
			String dominiCodi,
			String dominiId,
			Map<String, Object> parametres) throws DominiNotFoundException {
		return getJbpm3HeliumService().dominiConsultar(
				processInstanceId,
				dominiCodi,
				dominiId,
				parametres);
	}

	public List<EnumeracioValorDto> enumeracioConsultar(
			String processInstanceId,
			String enumeracioCodi) throws EnumeracioNotFoundException {
		return getJbpm3HeliumService().enumeracioConsultar(
				processInstanceId,
				enumeracioCodi);
	}

	public ExpedientTipusDto findExpedientTipusAmbEntornICodi(
			Long entornId,
			String expedientTipusCodi) {
		return getJbpm3HeliumService().findExpedientTipusAmbEntornICodi(
				entornId,
				expedientTipusCodi);
	}
	public EstatDto findEstatAmbExpedientTipusICodi(
			Long expedientTipusId,
			String estatCodi) {
		return getJbpm3HeliumService().findEstatAmbExpedientTipusICodi(
				expedientTipusId,
				estatCodi);
	}
	public DocumentDissenyDto getDocumentDisseny(
			Long definicioProcesId,
			String documentCodi) {
		return getJbpm3HeliumService().getDocumentDisseny(definicioProcesId, documentCodi);
	}

	public void expedientRelacionar(
			Long expedientIdOrigen,
			Long expedientIdDesti) {
		getJbpm3HeliumService().expedientRelacionar(expedientIdOrigen, expedientIdDesti);
	}

	public void tokenRedirigir(
			long tokenId,
			String nodeName,
			boolean cancelarTasques) {
		getJbpm3HeliumService().tokenRedirigir(tokenId, nodeName, cancelarTasques);
	}

	public ArxiuDto getArxiuGestorDocumental(
			String id) {
		return getJbpm3HeliumService().getArxiuGestorDocumental(id);
	}

	public TramitDto getTramit(
			String numero,
			String clau) {
		return getJbpm3HeliumService().getTramit(numero, clau);
	}

	public ExpedientDadaDto getDadaPerProcessInstance(
			String processInstanceId,
			String varCodi) {
		return getJbpm3HeliumService().getDadaPerProcessInstance(processInstanceId, varCodi);
	}

	public PaginaDto<ExpedientDto> findPerConsultaGeneralPaginat(
			Long entornId,
			Long expedientTipusId,
			String titol,
			String numero,
			Date dataInici1,
			Date dataInici2,
			Date dataFi1,
			Date dataFi2,
			EstatTipusDto estatTipus,
			Long estatId,
			Double geoPosX,
			Double geoPosY,
			String geoReferencia,
			boolean nomesAmbTasquesActives,
			boolean nomesAlertes,
			boolean mostrarAnulats,
			PaginacioParamsDto paginacioParams) throws EntornNotFoundException, ExpedientTipusNotFoundException, EstatNotFoundException {
		return getJbpm3HeliumService().findPerConsultaGeneralPaginat(
				entornId,
				expedientTipusId,
				titol,
				numero,
				dataInici1,
				dataInici2,
				dataFi1,
				dataFi2,
				estatTipus,
				estatId,
				geoPosX,
				geoPosY,
				geoReferencia,
				nomesAmbTasquesActives,
				nomesAlertes,
				mostrarAnulats,
				paginacioParams);
	}*/



	public Jbpm3HeliumService getJbpm3HeliumService() {
		return jbpm3HeliumService;
	}



	/*private ExpedientService expedientService;
	private DocumentService documentService;
	private PluginService pluginService;
	private DissenyService dissenyService;
	private TerminiService terminiService;
	private OrganitzacioService organitzacioService;
	private ConfigService configService;
	private TascaService tascaService;

	private boolean configured = false;



	public EntornDto getEntornActual() {
		return getConfigService().getEntornActual();
	}
	public ExpedientDto getExpedientIniciant() {
		return getExpedientService().getExpedientIniciant();
	}
	public String getUsuariCodiActual() {
		return getConfigService().getUsuariCodiActual();
	}

	public ExpedientDto findExpedientAmbEntornITipusINumero(
			Long entornId,
			String expedientTipusCodi,
			String numero) throws EntornNotFoundException, ExpedientTipusNotFoundException {
		return getExpedientService().findAmbEntornITipusINumero(
				entornId,
				expedientTipusCodi,
				numero);
	}
	public DefinicioProcesDto getDefinicioProcesAmbJbpmKeyIVersio(
			String jbpmKey,
			int version) {
		return getDissenyService().getDefinicioProcesAmbJbpmKeyIVersio(
				jbpmKey,
				version);
	}
	public DefinicioProcesDto getDarreraVersioAmbEntornIJbpmKey(
			Long entornId,
			String jbpmKey) throws EntornNotFoundException {
		return getDissenyService().getDarreraVersioAmbEntornIJbpmKey(
				entornId,
				jbpmKey);
	}
	public DefinicioProcesDto getDefinicioProcesPerProcessInstanceId(
			String processInstanceId) {
		return getDissenyService().getDefinicioProcesPerProcessInstanceId(
				processInstanceId);
	}
	public PersonaDto getPersonaAmbCodi(String codi) {
		return getConfigService().getPersonaAmbCodi(codi);
	}
	public AreaDto getAreaAmbEntornICodi(
			Long entornId,
			String codi) throws EntornNotFoundException {
		return getOrganitzacioService().getAreaAmbEntornICodi(entornId, codi);
	}
	public CarrecDto getCarrecAmbEntornIAreaICodi(
			Long entornId,
			String areaCodi,
			String carrecCodi) throws EntornNotFoundException, AreaNotFoundException {
		return getOrganitzacioService().getCarrecAmbEntornIAreaICodi(
				entornId,
				areaCodi,
				carrecCodi);
	}
	public List<FestiuDto> findFestiusAll() {
		return getConfigService().findFestiusAll();
	}
	public ReassignacioDto findReassignacioActivaPerUsuariOrigen(
			String usuariCodi) throws PersonaNotFoundException {
		return getConfigService().findReassignacioActivaPerUsuariOrigen(usuariCodi);
	}
	public void alertaEsborrarAmbTaskInstanceId(
			long taskInstanceId) throws TaskInstanceNotFoundException {
		getExpedientService().alertaEsborrarAmbTaskInstanceId(taskInstanceId);
	}
	public void expedientModificarEstat(
			String processInstanceId,
			String estatCodi) throws ExpedientNotFoundException, EstatNotFoundException {
		ExpedientDto expedient = getExpedientService().findAmbProcessInstanceId(processInstanceId);
		getExpedientService().modificar(
				expedient.getId(),
				expedient.getNumero(),
				expedient.getTitol(),
				expedient.getResponsableCodi(),
				expedient.getDataInici(),
				expedient.getComentari(),
				(expedient.getEstat() != null) ? expedient.getEstat().getCodi() : null,
				expedient.getGeoPosX(),
				expedient.getGeoPosY(),
				expedient.getGeoReferencia(),
				expedient.getGrupCodi(),
				true);
	}
	public void expedientModificarComentari(
			String processInstanceId,
			String comentari) throws ExpedientNotFoundException {
		ExpedientDto expedient = getExpedientService().findAmbProcessInstanceId(processInstanceId);
		getExpedientService().modificar(
				expedient.getId(),
				expedient.getNumero(),
				expedient.getTitol(),
				expedient.getResponsableCodi(),
				expedient.getDataInici(),
				comentari,
				(expedient.getEstat() != null) ? expedient.getEstat().getCodi() : null,
				expedient.getGeoPosX(),
				expedient.getGeoPosY(),
				expedient.getGeoReferencia(),
				expedient.getGrupCodi(),
				true);
	}
	public void expedientModificarGeoref(
			String processInstanceId,
			Double posx,
			Double posy,
			String referencia) throws ExpedientNotFoundException {
		ExpedientDto expedient = getExpedientService().findAmbProcessInstanceId(processInstanceId);
		getExpedientService().modificar(
				expedient.getId(),
				expedient.getNumero(),
				expedient.getTitol(),
				expedient.getResponsableCodi(),
				expedient.getDataInici(),
				expedient.getComentari(),
				(expedient.getEstat() != null) ? expedient.getEstat().getCodi() : null,
				posx,
				posy,
				referencia,
				expedient.getGrupCodi(),
				true);
	}
	public void expedientModificarGrup(
			String processInstanceId,
			String grupCodi) throws ExpedientNotFoundException {
		ExpedientDto expedient = getExpedientService().findAmbProcessInstanceId(processInstanceId);
		getExpedientService().modificar(
				expedient.getId(),
				expedient.getNumero(),
				expedient.getTitol(),
				expedient.getResponsableCodi(),
				expedient.getDataInici(),
				expedient.getComentari(),
				(expedient.getEstat() != null) ? expedient.getEstat().getCodi() : null,
				expedient.getGeoPosX(),
				expedient.getGeoPosY(),
				expedient.getGeoReferencia(),
				grupCodi,
				true);
	}
	public void expedientModificarNumero(
			String processInstanceId,
			String numero) throws ExpedientNotFoundException {
		ExpedientDto expedient = getExpedientService().findAmbProcessInstanceId(processInstanceId);
		getExpedientService().modificar(
				expedient.getId(),
				numero,
				expedient.getTitol(),
				expedient.getResponsableCodi(),
				expedient.getDataInici(),
				expedient.getComentari(),
				(expedient.getEstat() != null) ? expedient.getEstat().getCodi() : null,
				expedient.getGeoPosX(),
				expedient.getGeoPosY(),
				expedient.getGeoReferencia(),
				expedient.getGrupCodi(),
				true);
	}
	public void expedientModificarResponsable(
			String processInstanceId,
			String responsableCodi) throws ExpedientNotFoundException, PersonaNotFoundException {
		ExpedientDto expedient = getExpedientService().findAmbProcessInstanceId(processInstanceId);
		getExpedientService().modificar(
				expedient.getId(),
				expedient.getNumero(),
				expedient.getTitol(),
				responsableCodi,
				expedient.getDataInici(),
				expedient.getComentari(),
				(expedient.getEstat() != null) ? expedient.getEstat().getCodi() : null,
				expedient.getGeoPosX(),
				expedient.getGeoPosY(),
				expedient.getGeoReferencia(),
				expedient.getGrupCodi(),
				true);
	}
	public void expedientModificarTitol(
			String processInstanceId,
			String titol) throws ExpedientNotFoundException {
		ExpedientDto expedient = getExpedientService().findAmbProcessInstanceId(processInstanceId);
		getExpedientService().modificar(
				expedient.getId(),
				expedient.getNumero(),
				titol,
				expedient.getResponsableCodi(),
				expedient.getDataInici(),
				expedient.getComentari(),
				(expedient.getEstat() != null) ? expedient.getEstat().getCodi() : null,
				expedient.getGeoPosX(),
				expedient.getGeoPosY(),
				expedient.getGeoReferencia(),
				expedient.getGrupCodi(),
				true);
	}
	public void expedientAturar(
			Long expedientId,
			String motiu) throws ExpedientNotFoundException {
		getExpedientService().aturar(expedientId, motiu);
	}
	public void expedientReprendre(Long expedientId) throws ExpedientNotFoundException {
		getExpedientService().reprendre(expedientId);
	}
	public void expedientLuceneIndexUpdate(String processInstanceId) throws ExpedientNotFoundException {
		ExpedientDto expedient = getExpedientService().findAmbProcessInstanceId(processInstanceId);
		getExpedientService().luceneIndexUpdate(expedient.getId());
	}

	public void documentGenerarAmbPlantilla(
			String processInstanceId,
			String documentCodi,
			Date dataDocument) {
		getDocumentService().generarAmbPlantilla(
				null,
				processInstanceId,
				documentCodi,
				dataDocument,
				true);
	}

	public TerminiIniciatDto getTerminiIniciat(
			String processInstanceId,
			String terminiCodi) throws TerminiNotFoundException {
		return getTerminiService().findIniciatAmbCodiIProcessInstance(
				terminiCodi,
				processInstanceId);
	}
	public void configurarTerminiIniciatAmbDadesJbpm(
			Long terminiIniciatId,
			String taskInstanceId,
			Long timerId) throws TerminiIniciatNotFoundException {
		getTerminiService().configurarIniciatAmbDadesJbpm(
				terminiIniciatId,
				taskInstanceId,
				timerId);
	}

	public List<DominiRespostaFilaDto> dominiConsultar(
			String processInstanceId,
			String dominiCodi,
			String dominiId,
			Map<String, Object> parametres) throws DominiNotFoundException {
		return getExpedientService().dominiConsultar(
				processInstanceId,
				dominiCodi,
				dominiId,
				parametres);
	}

	public List<EnumeracioValorDto> enumeracioConsultar(
			String processInstanceId,
			String enumeracioCodi) throws EnumeracioNotFoundException {
		return getExpedientService().enumeracioConsultar(
				processInstanceId,
				enumeracioCodi);
	}

	public String getHeliumProperty(String propertyName) {
		return getConfigService().getHeliumProperty(propertyName);
	}

	public boolean isHeliumAssignmentActive() {
		String organigramaActiu = getHeliumProperty("app.jbpm.identity.source");
		return "helium".equalsIgnoreCase(organigramaActiu);
	}

	public void configServices(
			ExpedientService expedientService,
			DocumentService documentService,
			PluginService pluginService,
			DissenyService dissenyService,
			TerminiService terminiService,
			OrganitzacioService organitzacioService,
			ConfigService configService,
			TascaService tascaService) {
		this.expedientService = expedientService;
		this.documentService = documentService;
		this.pluginService = pluginService;
		this.dissenyService = dissenyService;
		this.terminiService = terminiService;
		this.organitzacioService = organitzacioService;
		this.configService = configService;
		this.tascaService = tascaService;
		configured = true;
	}

	public ExpedientService getExpedientService() {
		return expedientService;
	}
	public DocumentService getDocumentService() {
		return documentService;
	}
	public PluginService getPluginService() {
		return pluginService;
	}
	public DissenyService getDissenyService() {
		return dissenyService;
	}
	public TerminiService getTerminiService() {
		return terminiService;
	}
	public OrganitzacioService getOrganitzacioService() {
		return organitzacioService;
	}
	public ConfigService getConfigService() {
		return configService;
	}
	public TascaService getTascaService() {
		return tascaService;
	}

	public boolean isConfigured() {
		return configured;
	}*/

}
