/**
 * 
 */
package net.conselldemallorca.helium.webapp.v3.helper;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.conselldemallorca.helium.v3.core.api.dto.EntornDto;
import net.conselldemallorca.helium.v3.core.api.dto.ExpedientTipusDto;
import net.conselldemallorca.helium.webapp.v3.command.ExpedientConsultaCommand;

/**
 * Helper per a gestionar les dades de la sessió d'usuari.
 * 
 * @author Limit Tecnologies <limit@limit.es>
 */
public class SessionHelper {

	public static final String VARIABLE_ENTORN_ACTUAL = "entornActual";
	public static final String VARIABLE_ENTORN_ACTUAL_V3 = "entornActualV3";
	public static final String VARIABLE_EXPTIP_ACTUAL = "expedientTipusActual";
	public static final String VARIABLE_EXPTIP_ACCESSIBLES = "expedientTipusAccessibles";
	public static final String VARIABLE_PERMIS_EXPTIP_DISSENY = "potDissenyarExpedientTipus";
	public static final String VARIABLE_PERMIS_EXPTIP_GESTIO = "potGestionarExpedientTipus";
	public static final String VARIABLE_PERMIS_ENTORN_READ = "potLlegirEntorn";
	public static final String VARIABLE_PERMIS_ENTORN_DESIGN = "potDissenyarEntorn";
	public static final String VARIABLE_PERMIS_ENTORN_ORGANIZATION = "potOrganitzarEntorn";
	public static final String VARIABLE_PERMIS_ENTORN_ADMINISTRATION = "potAdministrarEntorn";
	public static final String VARIABLE_HIHA_TRAMITS_INICIABLES = "hiHaTramitsPerIniciar";

	public static final String VARIABLE_FILTRE_CONSULTA_GENERAL = "filtreConsultaGeneral";
	public static final String VARIABLE_SELECCIO_CONSULTA_GENERAL = "seleccioConsultaGeneral";

	public static Object getAttribute(
			HttpServletRequest request,
			String name) {
		return request.getSession().getAttribute(
				name);
	}
	public static void setAttribute(
			HttpServletRequest request,
			String name,
			Object value) {
		request.getSession().setAttribute(
				name,
				value);
	}
	public static void removeAttribute(
			HttpServletRequest request,
			String name) {
		request.getSession().removeAttribute(
				name);
	}

	public static SessionManager getSessionManager(HttpServletRequest request) {
		return new SessionManager(request);
	}

	public static class SessionManager {

		private HttpServletRequest request;
		public SessionManager(HttpServletRequest request) {
			this.request = request;
		}

		public EntornDto getEntornActual() {
			return (EntornDto)getAttribute(
					request,
					VARIABLE_ENTORN_ACTUAL_V3);
		}
		public void setEntornActual(EntornDto entorn) {
			setAttribute(
					request,
					VARIABLE_ENTORN_ACTUAL_V3,
					entorn);
		}
		public ExpedientTipusDto getExpedientTipusActual() {
			return (ExpedientTipusDto)getAttribute(
					request,
					VARIABLE_EXPTIP_ACTUAL);
		}
		public void setExpedientTipusActual(ExpedientTipusDto expedientTipus) {
			setAttribute(
					request,
					VARIABLE_EXPTIP_ACTUAL,
					expedientTipus);
		}
		@SuppressWarnings("unchecked")
		public List<ExpedientTipusDto> getExpedientTipusAccessibles() {
			return (List<ExpedientTipusDto>)getAttribute(
					request,
					VARIABLE_EXPTIP_ACCESSIBLES);
		}
		public void setExpedientTipusAccessibles(List<ExpedientTipusDto> expedientsTipus) {
			setAttribute(
					request,
					VARIABLE_EXPTIP_ACCESSIBLES,
					expedientsTipus);
		}
		public Boolean getPotDissenyarExpedientTipus() {
			return (Boolean)getAttribute(
					request,
					VARIABLE_PERMIS_EXPTIP_DISSENY);
		}
		public void setPotDissenyarExpedientTipus(Boolean potDissenyarExpedientTipus) {
			setAttribute(
					request,
					VARIABLE_PERMIS_EXPTIP_DISSENY,
					potDissenyarExpedientTipus);
		}
		public Boolean getPotGestionarExpedientTipus() {
			return (Boolean)getAttribute(
					request,
					VARIABLE_PERMIS_EXPTIP_GESTIO);
		}
		public void setPotGestionarExpedientTipus(Boolean potGestionarExpedientTipus) {
			setAttribute(
					request,
					VARIABLE_PERMIS_EXPTIP_GESTIO,
					potGestionarExpedientTipus);
		}
		public Boolean getPotLlegirEntorn() {
			return (Boolean)getAttribute(
					request,
					VARIABLE_PERMIS_ENTORN_READ);
		}
		public void setPotLlegirEntorn(Boolean potLlegirEntorn) {
			setAttribute(
					request,
					VARIABLE_PERMIS_ENTORN_READ,
					potLlegirEntorn);
		}
		public Boolean getPotDissenyarEntorn() {
			return (Boolean)getAttribute(
					request,
					VARIABLE_PERMIS_ENTORN_DESIGN);
		}
		public void setPotDissenyarEntorn(Boolean potDissenyarEntorn) {
			setAttribute(
					request,
					VARIABLE_PERMIS_ENTORN_DESIGN,
					potDissenyarEntorn);
		}
		public Boolean getPotOrganitzarEntorn() {
			return (Boolean)getAttribute(
					request,
					VARIABLE_PERMIS_ENTORN_ORGANIZATION);
		}
		public void setPotOrganitzarEntorn(Boolean potOrganitzarEntorn) {
			setAttribute(
					request,
					VARIABLE_PERMIS_ENTORN_ORGANIZATION,
					potOrganitzarEntorn);
		}
		public Boolean getPotAdministrarEntorn() {
			return (Boolean)getAttribute(
					request,
					VARIABLE_PERMIS_ENTORN_ADMINISTRATION);
		}
		public void setPotAdministrarEntorn(Boolean potAdministrarEntorn) {
			setAttribute(
					request,
					VARIABLE_PERMIS_ENTORN_ADMINISTRATION,
					potAdministrarEntorn);
		}
		public Boolean getHiHaTramitsPerIniciar() {
			return (Boolean)getAttribute(
					request,
					VARIABLE_HIHA_TRAMITS_INICIABLES);
		}
		public void setHiHaTramitsPerIniciar(Boolean hiHaTramitsPerIniciar) {
			setAttribute(
					request,
					VARIABLE_HIHA_TRAMITS_INICIABLES,
					hiHaTramitsPerIniciar);
		}
		public ExpedientConsultaCommand getFiltreConsultaGeneral() {
			return (ExpedientConsultaCommand)getAttribute(
					request,
					VARIABLE_FILTRE_CONSULTA_GENERAL);
		}
		public void setFiltreConsultaGeneral(ExpedientConsultaCommand filtreConsultaGeneral) {
			setAttribute(
					request,
					VARIABLE_FILTRE_CONSULTA_GENERAL,
					filtreConsultaGeneral);
		}
		@SuppressWarnings("unchecked")
		public Set<Long> getSeleccioConsultaGeneral() {
			return (Set<Long>)getAttribute(
					request,
					VARIABLE_SELECCIO_CONSULTA_GENERAL);
		}
		public void setSeleccioConsultaGeneral(Set<Long> seleccioConsultaGeneral) {
			setAttribute(
					request,
					VARIABLE_SELECCIO_CONSULTA_GENERAL,
					seleccioConsultaGeneral);
		}

	}

}