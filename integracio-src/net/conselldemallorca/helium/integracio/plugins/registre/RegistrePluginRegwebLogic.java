package net.conselldemallorca.helium.integracio.plugins.registre;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import net.conselldemallorca.helium.util.GlobalProperties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.regweb.logic.helper.ParametrosRegistroEntrada;
import es.caib.regweb.logic.helper.ParametrosRegistroSalida;
import es.caib.regweb.logic.interfaces.RegistroEntradaFacade;
import es.caib.regweb.logic.interfaces.RegistroEntradaFacadeHome;
import es.caib.regweb.logic.interfaces.RegistroSalidaFacade;
import es.caib.regweb.logic.interfaces.RegistroSalidaFacadeHome;


/**
 * Implementació del plugin de registre per a la interficie logic del
 * registre de la CAIB.
 * 
 * @author Josep Gayà <josepg@limit.es>
 */

public class RegistrePluginRegwebLogic implements RegistrePlugin {

	private static final String SEPARADOR_ENTITAT = "-";
	private static final String SEPARADOR_NUMERO = "/";

	@SuppressWarnings("unchecked")
	public RespostaAnotacioRegistre registrarEntrada(
			RegistreEntrada registreEntrada) throws RegistrePluginException {
		try {
			ParametrosRegistroEntrada params = new ParametrosRegistroEntrada();
			params.fijaUsuario(GlobalProperties.getInstance().getProperty("app.registre.plugin.security.principal"));
			Date ara = new Date();
			params.setdataentrada(new SimpleDateFormat("dd/MM/yyyy").format(ara));
			params.sethora(new SimpleDateFormat("HH:mm").format(ara));
			if (registreEntrada.getDadesOficina() != null) {
				String oficinaCodi = registreEntrada.getDadesOficina().getOficinaCodi();
				if (oficinaCodi != null) {
					int indexBarra = oficinaCodi.indexOf(SEPARADOR_ENTITAT);
					if (indexBarra != -1) {
						params.setoficina(oficinaCodi.substring(0, indexBarra));
						params.setoficinafisica(oficinaCodi.substring(indexBarra + 1));
					}
				}
				params.setdestinatari(
						registreEntrada.getDadesOficina().getOrganCodi());
			}
			if (registreEntrada.getDadesInteressat() != null) {
				String entitatCodi = registreEntrada.getDadesInteressat().getEntitatCodi();
				if (entitatCodi != null) {
					int indexBarra = entitatCodi.indexOf(SEPARADOR_ENTITAT);
					if (indexBarra != -1) {
						params.setentidad1(entitatCodi.substring(0, indexBarra));
						params.setentidad2(entitatCodi.substring(indexBarra + 1));
					}
				}
				params.setaltres(
						registreEntrada.getDadesInteressat().getNomAmbCognoms());
				params.setbalears(
						registreEntrada.getDadesInteressat().getMunicipiCodi());
				params.setfora(
						registreEntrada.getDadesInteressat().getMunicipiNom());
				
			}
			if (registreEntrada.getDadesAssumpte() != null) {
				params.settipo(
						registreEntrada.getDadesAssumpte().getTipus());
				params.setidioex(
						convertirIdioma(registreEntrada.getDadesAssumpte().getIdiomaCodi()));
				params.setcomentario(
						registreEntrada.getDadesAssumpte().getAssumpte());
			}
			if (registreEntrada.getDocuments() != null && registreEntrada.getDocuments().size() > 0) {
				if (registreEntrada.getDocuments().size() == 1) {
					DocumentRegistre document = registreEntrada.getDocuments().get(0);
					params.setdata(
							new SimpleDateFormat("dd/MM/yyyy").format(document.getData()));
					params.setidioma(
							convertirIdioma(document.getIdiomaCodi()));
				} else {
					throw new RegistrePluginException("Nomes es pot registrar un document alhora");
				}
			} else {
				throw new RegistrePluginException("S'ha d'especificar algun document per registrar");
			}
			RegistroEntradaFacade registroEntrada = getRegistreEntradaService();
			ParametrosRegistroEntrada respostaValidacio = registroEntrada.validar(params);
			if (respostaValidacio.getValidado()) {
				ParametrosRegistroEntrada respostaGrabacio = registroEntrada.grabar(params);
				RespostaAnotacioRegistre resposta = new RespostaAnotacioRegistre();
				if (respostaGrabacio.getGrabado()) {
					resposta.setErrorCodi(RespostaAnotacioRegistre.ERROR_CODI_OK);
					resposta.setNumero(
							respostaGrabacio.getNumeroEntrada() +
							SEPARADOR_NUMERO +
							respostaGrabacio.getAnoEntrada());
					resposta.setData(ara);
					return resposta;
				} else {
					throw new RegistrePluginException("No s'ha pogut guardar l'entrada");
				}
			} else {
				StringBuilder sb = new StringBuilder();
				sb.append("Errors de validació:\n");
				Map<String, String> errors = respostaValidacio.getErrores();
				for (String camp: errors.keySet()) {
					sb.append(" | " + errors.get(camp));
				}
				throw new RegistrePluginException("S'han produit errors de validació de l'entrada: " + sb.toString());
			}
		} catch (Exception ex) {
			logger.error("Error al registrar l'entrada", ex);
			throw new RegistrePluginException("Error al registrar l'entrada", ex);
		}
	}
	public RespostaConsulta consultarEntrada(
			String organCodi,
			String oficinaCodi,
			String registreNumero) throws RegistrePluginException {
		try {
			ParametrosRegistroEntrada params = new ParametrosRegistroEntrada();
			params.fijaUsuario(GlobalProperties.getInstance().getProperty("app.registre.plugin.security.principal"));
			params.setoficina(organCodi);
			params.setoficinafisica(oficinaCodi);
			int index = registreNumero.indexOf(SEPARADOR_NUMERO);
			if (index == -1)
				throw new RegistrePluginException("El número de registre a consultar (" + registreNumero + ") no té el format correcte");
			params.setNumeroEntrada(registreNumero.substring(0, index));
			params.setAnoEntrada(registreNumero.substring(index + 1));
			RegistroEntradaFacade registroEntrada = getRegistreEntradaService();
			ParametrosRegistroEntrada llegit = registroEntrada.leer(params);
			RespostaConsulta resposta = new RespostaConsulta();
			resposta.setRegistreNumero(registreNumero);
			resposta.setRegistreData(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(llegit.getDataEntrada() + " " + llegit.getHora()));
			DadesOficina dadesOficina = new DadesOficina();
			dadesOficina.setOrganCodi(llegit.getDestinatari());
			dadesOficina.setOficinaCodi(llegit.getOficina() + SEPARADOR_ENTITAT + llegit.getOficinafisica());
			resposta.setDadesOficina(dadesOficina);
			DadesInteressat dadesInteressat = new DadesInteressat();
			if (llegit.getEntidad1() != null && !"".equals(llegit.getEntidad1()))
				dadesInteressat.setEntitatCodi(
						llegit.getEntidad1() + SEPARADOR_ENTITAT + llegit.getEntidad2());
			dadesInteressat.setNomAmbCognoms(llegit.getAltres());
			dadesInteressat.setMunicipiCodi(llegit.getBalears());
			dadesInteressat.setMunicipiNom(llegit.getFora());
			resposta.setDadesInteressat(dadesInteressat);
			DadesAssumpte dadesAssumpte = new DadesAssumpte();
			dadesAssumpte.setIdiomaCodi(llegit.getIdioex());
			dadesAssumpte.setTipus(llegit.getTipo());
			dadesAssumpte.setAssumpte(llegit.getComentario());
			resposta.setDadesAssumpte(dadesAssumpte);
			List<DocumentRegistre> documents = new ArrayList<DocumentRegistre>();
			DocumentRegistre document = new DocumentRegistre();
			document.setIdiomaCodi(llegit.getIdioma());
			if (llegit.getData() != null)
				document.setData(new SimpleDateFormat("dd/MM/yyyy").parse(llegit.getData()));
			documents.add(document);
			resposta.setDocuments(documents);
			return resposta;
		} catch (Exception ex) {
			logger.error("Error al consultar l'entrada", ex);
			throw new RegistrePluginException("Error al consultar l'entrada", ex);
		}
	}

	@SuppressWarnings("unchecked")
	public RespostaAnotacioRegistre registrarSortida(
			RegistreSortida registreSortida) throws RegistrePluginException {
		try {
			ParametrosRegistroSalida params = new ParametrosRegistroSalida();
			params.fijaUsuario(GlobalProperties.getInstance().getProperty("app.registre.plugin.security.principal"));
			Date ara = new Date();
			params.setdatasalida(new SimpleDateFormat("dd/MM/yyyy").format(ara));
			params.sethora(new SimpleDateFormat("HH:mm").format(ara));
			if (registreSortida.getDadesOficina() != null) {
				String oficinaCodi = registreSortida.getDadesOficina().getOficinaCodi();
				if (oficinaCodi != null) {
					int indexBarra = oficinaCodi.indexOf(SEPARADOR_ENTITAT);
					if (indexBarra != -1) {
						params.setoficina(oficinaCodi.substring(0, indexBarra));
						params.setoficinafisica(oficinaCodi.substring(indexBarra + 1));
					}
				}
				params.setremitent(
						registreSortida.getDadesOficina().getOrganCodi());
			}
			if (registreSortida.getDadesInteressat() != null) {
				String entitatCodi = registreSortida.getDadesInteressat().getEntitatCodi();
				int indexBarra = entitatCodi.indexOf(SEPARADOR_ENTITAT);
				if (entitatCodi != null && indexBarra != -1) {
					params.setentidad1(entitatCodi.substring(0, indexBarra));
					params.setentidad2(entitatCodi.substring(indexBarra + 1));
				}
				params.setaltres(
						registreSortida.getDadesInteressat().getNomAmbCognoms());
				params.setbalears(
						registreSortida.getDadesInteressat().getMunicipiCodi());
				params.setfora(
						registreSortida.getDadesInteressat().getMunicipiNom());
				
			}
			if (registreSortida.getDadesAssumpte() != null) {
				params.settipo(
						registreSortida.getDadesAssumpte().getTipus());
				params.setidioex(
						registreSortida.getDadesAssumpte().getIdiomaCodi());
				params.setcomentario(
						registreSortida.getDadesAssumpte().getAssumpte());
			}
			if (registreSortida.getDocuments() != null && registreSortida.getDocuments().size() > 0) {
				if (registreSortida.getDocuments().size() == 1) {
					DocumentRegistre document = registreSortida.getDocuments().get(0);
					params.setdata(
							new SimpleDateFormat("dd/MM/yyyy").format(document.getData()));
					params.setidioma(
							document.getIdiomaCodi());
				} else {
					throw new RegistrePluginException("Nomes es pot registrar un document alhora");
				}
			} else {
				throw new RegistrePluginException("S'ha d'especificar algun document per registrar");
			}
			RegistroSalidaFacade registroSalida = getRegistreSortidaService();
			ParametrosRegistroSalida respostaValidacio = registroSalida.validar(params);
			if (respostaValidacio.getValidado()) {
				ParametrosRegistroSalida respostaGrabacio = registroSalida.grabar(params);
				RespostaAnotacioRegistre resposta = new RespostaAnotacioRegistre();
				if (respostaGrabacio.getGrabado()) {
					resposta.setErrorCodi(RespostaAnotacioRegistre.ERROR_CODI_OK);
					resposta.setNumero(
							respostaGrabacio.getNumeroSalida() +
							SEPARADOR_NUMERO +
							respostaGrabacio.getAnoSalida());
					resposta.setData(ara);
					return resposta;
				} else {
					throw new RegistrePluginException("No s'ha pogut guardar la sortida");
				}
			} else {
				StringBuilder sb = new StringBuilder();
				sb.append("Errors de validació:\n");
				Map<String, String> errors = respostaValidacio.getErrores();
				for (String camp: errors.keySet()) {
					sb.append(" | " + errors.get(camp));
				}
				throw new RegistrePluginException("S'han produit errors de validació de l'entrada: " + sb.toString());
			}
		} catch (Exception ex) {
			logger.error("Error al registrar la sortida", ex);
			throw new RegistrePluginException("Error al registrar la sortida", ex);
		}
	}

	public RespostaConsulta consultarSortida(
			String organCodi,
			String oficinaCodi,
			String registreNumero) throws RegistrePluginException {
		try {
			ParametrosRegistroSalida params = new ParametrosRegistroSalida();
			params.fijaUsuario(GlobalProperties.getInstance().getProperty("app.registre.plugin.security.principal"));
			params.setoficina(organCodi);
			params.setoficinafisica(oficinaCodi);
			int index = registreNumero.indexOf(SEPARADOR_NUMERO);
			if (index == -1)
				throw new RegistrePluginException("El número de registre a consultar (" + registreNumero + ") no té el format correcte");
			params.setNumeroSalida(registreNumero.substring(0, index));
			params.setAnoSalida(registreNumero.substring(index + 1));
			RegistroSalidaFacade registroSalida = getRegistreSortidaService();
			ParametrosRegistroSalida llegit = registroSalida.leer(params);
			RespostaConsulta resposta = new RespostaConsulta();
			resposta.setRegistreNumero(registreNumero);
			resposta.setRegistreData(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(llegit.getDataSalida() + " " + llegit.getHora()));
			DadesOficina dadesOficina = new DadesOficina();
			dadesOficina.setOrganCodi(llegit.getRemitent());
			dadesOficina.setOficinaCodi(llegit.getOficina() + SEPARADOR_ENTITAT + llegit.getOficinafisica());
			resposta.setDadesOficina(dadesOficina);
			DadesInteressat dadesInteressat = new DadesInteressat();
			if (llegit.getEntidad1() != null && !"".equals(llegit.getEntidad1()))
				dadesInteressat.setEntitatCodi(
						llegit.getEntidad1() + SEPARADOR_ENTITAT + llegit.getEntidad2());
			dadesInteressat.setNomAmbCognoms(llegit.getAltres());
			dadesInteressat.setMunicipiCodi(llegit.getBalears());
			dadesInteressat.setMunicipiNom(llegit.getFora());
			resposta.setDadesInteressat(dadesInteressat);
			DadesAssumpte dadesAssumpte = new DadesAssumpte();
			dadesAssumpte.setUnitatAdministrativa(llegit.getRemitent());
			dadesAssumpte.setIdiomaCodi(llegit.getIdioex());
			dadesAssumpte.setTipus(llegit.getTipo());
			dadesAssumpte.setAssumpte(llegit.getComentario());
			resposta.setDadesAssumpte(dadesAssumpte);
			List<DocumentRegistre> documents = new ArrayList<DocumentRegistre>();
			DocumentRegistre document = new DocumentRegistre();
			document.setIdiomaCodi(llegit.getIdioma());
			if (llegit.getData() != null)
				document.setData(new SimpleDateFormat("dd/MM/yyyy").parse(llegit.getData()));
			documents.add(document);
			resposta.setDocuments(documents);
			return resposta;
		} catch (Exception ex) {
			logger.error("Error al consultar la sortida", ex);
			throw new RegistrePluginException("Error al consultar la sortida", ex);
		}
	}

	public RespostaAnotacioRegistre registrarNotificacio(
			RegistreNotificacio registreNotificacio) throws RegistrePluginException {
		throw new RegistrePluginException("Mètode no implementat en aquest plugin");
	}
	public RespostaJustificantRecepcio obtenirJustificantRecepcio(
			String numeroRegistre) throws RegistrePluginException {
		throw new RegistrePluginException("Mètode no implementat en aquest plugin");
	}



	private RegistroEntradaFacade getRegistreEntradaService() throws Exception {
		Context ctx = getInitialContext();
		Object objRef = ctx.lookup("es.caib.regweb.logic.RegistroEntradaFacade");
		RegistroEntradaFacadeHome home = (RegistroEntradaFacadeHome)javax.rmi.PortableRemoteObject.narrow(
				objRef,
				RegistroEntradaFacadeHome.class);
		ctx.close();
		return home.create();
	}
	private RegistroSalidaFacade getRegistreSortidaService() throws Exception {
		Context ctx = getInitialContext();
		Object objRef = ctx.lookup("es.caib.regweb.logic.RegistroSalidaFacade");
		RegistroSalidaFacadeHome home = (RegistroSalidaFacadeHome)javax.rmi.PortableRemoteObject.narrow(
				objRef,
				RegistroSalidaFacadeHome.class);
		ctx.close();
		return home.create();
	}
	private Context getInitialContext() throws Exception {
		Properties props = new Properties();
		props.put(
				Context.INITIAL_CONTEXT_FACTORY,
				GlobalProperties.getInstance().getProperty("app.registre.plugin.initial.context.factory"));
		props.put(
				Context.URL_PKG_PREFIXES,
				GlobalProperties.getInstance().getProperty("app.registre.plugin.url.pkg.prefixes"));
		props.put(
				Context.PROVIDER_URL,
				GlobalProperties.getInstance().getProperty("app.registre.plugin.provider.url"));
		String principal = GlobalProperties.getInstance().getProperty("app.registre.plugin.security.principal");
		if (principal != null && principal.length() > 0)
			props.put(
					Context.SECURITY_PRINCIPAL,
					principal);
		String credentials = GlobalProperties.getInstance().getProperty("app.registre.plugin.security.credentials");
		if (credentials != null && credentials.length() > 0)
			props.put(
					Context.SECURITY_CREDENTIALS,
					credentials);
		return new InitialContext(props);
	}

	private String convertirIdioma(String iso6391) {
		if ("es".equalsIgnoreCase(iso6391)) {
			return "1";
		} else if ("ca".equalsIgnoreCase(iso6391)) {
			return "2";
		} else if ("eu".equalsIgnoreCase(iso6391)) {
			return "4";
		} else if ("gl".equalsIgnoreCase(iso6391)) {
			return "5";
		} else if ("as".equalsIgnoreCase(iso6391)) {
			return "6";
		} else if ("de".equalsIgnoreCase(iso6391)) {
			return "C";
		} else if ("en".equalsIgnoreCase(iso6391)) {
			return "A";
		} else if ("fr".equalsIgnoreCase(iso6391)) {
			return "B";
		} else if ("it".equalsIgnoreCase(iso6391)) {
			return "E";
		} else if ("pt".equalsIgnoreCase(iso6391)) {
			return "F";
		}
		return "1";
	}

	private static final Log logger = LogFactory.getLog(RegistrePluginRegwebLogic.class);

}
