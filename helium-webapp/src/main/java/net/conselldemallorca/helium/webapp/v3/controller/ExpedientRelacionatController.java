/**
 * 
 */
package net.conselldemallorca.helium.webapp.v3.controller;

import javax.servlet.http.HttpServletRequest;

import net.conselldemallorca.helium.v3.core.api.dto.EntornDto;
import net.conselldemallorca.helium.v3.core.api.dto.ExpedientDto;
import net.conselldemallorca.helium.v3.core.api.service.ExpedientService;
import net.conselldemallorca.helium.webapp.v3.command.ExpedientRelacionarCommand;
import net.conselldemallorca.helium.webapp.v3.helper.MissatgesHelper;
import net.conselldemallorca.helium.webapp.v3.helper.NoDecorarHelper;
import net.conselldemallorca.helium.webapp.v3.helper.SessionHelper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

/**
 * Controlador per a la relació d'expedients
 * 
 * @author Limit Tecnologies <limit@limit.es>
 */
@Controller
@RequestMapping("/v3/expedient")
public class ExpedientRelacionatController extends BaseExpedientController {

	@Autowired
	private ExpedientService expedientService;

	@RequestMapping(value = "/{expedientId}/relacionats", method = RequestMethod.GET)
	public String relacionarForm(HttpServletRequest request, @PathVariable Long expedientId, Model model) {
		NoDecorarHelper.marcarNoCapsaleraNiPeu(request);
		model.addAttribute("expedientId", expedientId);		
		model.addAttribute("relacionats",expedientService.getExpedientsRelacionats(expedientId));
		return "v3/expedient/relacionar";
	}

	@ModelAttribute("relacionarCommand")
	public ExpedientRelacionarCommand populateRelacionarCommand() {
		return new ExpedientRelacionarCommand();
	}
	
	@RequestMapping(value = "/{expedientId}/relacionarExpediente", method = RequestMethod.POST)
	public String relacionarExpediente(
			HttpServletRequest request, 
			@PathVariable Long expedientId, 
			Model model, 
			@ModelAttribute("relacionarCommand") 
			ExpedientRelacionarCommand command, 
			BindingResult result, 
			SessionStatus status) {
		EntornDto entorn = SessionHelper.getSessionManager(request).getEntornActual();
		if (entorn != null) {
			ExpedientDto expedientOrig = expedientService.findById(expedientId);
			if (potModificarExpedient(expedientOrig)) {
				try {
					ExpedientDto expedientDest = expedientService.findById(command.getExpedientIdDesti());
					expedientService.createRelacioExpedient(
							expedientId,
							expedientDest.getId());
					MissatgesHelper.info(request, getMessage(request, "expedient.relacionar.ok"));
				} catch (Exception ex) {
				  	MissatgesHelper.error(request, getMessage(request, "error.expedient.relacionar"));
				  	logger.error("No s'ha pogut relacionar l'expedient " + expedientOrig.getIdentificador(), ex);
				}
			} else {
				MissatgesHelper.error(request, getMessage(request, "error.permisos.modificar.expedient"));
			}
		} else {
			MissatgesHelper.error(request, getMessage(request, "error.no.entorn.selec"));
		}
		return "/v3/utils/modalTancar";
	}

	@RequestMapping(value = "/{expedientId}/relacioDelete", method = RequestMethod.POST)
	public String relacioDelete(
			HttpServletRequest request, 
			@PathVariable Long expedientId,
			@RequestParam(value = "expedientIdOrigen", required = true) Long expedientIdOrigen,
			@RequestParam(value = "expedientIdDesti", required = true) Long expedientIdDesti,
			Model model) {
	EntornDto entorn = SessionHelper.getSessionManager(request).getEntornActual();
	if (entorn != null) {
		ExpedientDto expedient = expedientService.findById(expedientId);
		if (potModificarExpedient(expedient)) {
				try {
					expedientService.deleteRelacioExpedient(
							expedientIdOrigen,
							expedientIdDesti);
					MissatgesHelper.info(request, getMessage(request, "expedient.relacio.esborrar.ok"));
				} catch (Exception ex) {
					MissatgesHelper.error(request, getMessage(request, "error.expedient.relacio.esborrar"));
		        	logger.error("No s'ha pogut relacionar l'expedient " + expedient.getIdentificador(), ex);
				}
			} else {
				MissatgesHelper.error(request, getMessage(request, "error.permisos.modificar.expedient"));
			}
		} else {
			MissatgesHelper.error(request, getMessage(request, "error.no.entorn.selec"));
		}
		return "redirect:/v3/expedient/" + expedientId;
	}
	
	@RequestMapping(value = "/{expedientId}/expedient/suggest", method = RequestMethod.GET)
	public String suggestAction(
			HttpServletRequest request,
			@RequestParam(value = "q", required = true) String text,
			ModelMap model) {
		EntornDto entorn = SessionHelper.getSessionManager(request).getEntornActual();
		if (entorn != null) {
			model.addAttribute(
					"expedients",
					expedientService.findAmbEntornLikeIdentificador(
							entorn.getId(),
							text));
		}
		return "expedient/suggest";
	}

	private static final Log logger = LogFactory.getLog(ExpedientExecucionsController.class);

}
