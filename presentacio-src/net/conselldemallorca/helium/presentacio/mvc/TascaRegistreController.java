/**
 * 
 */
package net.conselldemallorca.helium.presentacio.mvc;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.conselldemallorca.helium.model.dto.TascaDto;
import net.conselldemallorca.helium.model.hibernate.Camp;
import net.conselldemallorca.helium.model.hibernate.CampTasca;
import net.conselldemallorca.helium.model.hibernate.Entorn;
import net.conselldemallorca.helium.model.service.DissenyService;
import net.conselldemallorca.helium.model.service.TascaService;
import net.conselldemallorca.helium.presentacio.mvc.util.TascaFormUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;



/**
 * Controlador per la gestió dels formularis dels camps de tipus registre
 * a dins les tasques
 * 
 * @author Josep Gayà <josepg@limit.es>
 */
@Controller
public class TascaRegistreController extends CommonRegistreController {

	private TascaService tascaService;



	@Autowired
	public TascaRegistreController(
			TascaService tascaService,
			DissenyService dissenyService) {
		super(dissenyService);
		this.tascaService = tascaService;
	}

	@Override
	public void populateOthers(
			HttpServletRequest request,
			String id,
			Object command,
			ModelMap model) {
		Entorn entorn = getEntornActiu(request);
		if (entorn != null) {
			TascaDto tasca = tascaService.getById(entorn.getId(), id);
			List<Camp> camps = new ArrayList<Camp>();
    		for (CampTasca campTasca: tasca.getCamps())
    			camps.add(campTasca.getCamp());
			model.addAttribute(
					"tasca",
					tascaService.getById(
							entorn.getId(),
							id,
							TascaFormUtil.getValorsFromCommand(
		        					camps,
		        					command,
		        					true,
		    						false)));
		}
	}

	@RequestMapping(value = "/tasca/registre", method = RequestMethod.GET)
	public String registreGet(HttpServletRequest request) {
		return super.registreGet(request);
	}
	@RequestMapping(value = "/tasca/registre", method = RequestMethod.POST)
	public String registrePost(
			HttpServletRequest request,
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "registreId", required = true) Long registreId,
			@RequestParam(value = "index", required = false) Integer index,
			@RequestParam(value = "submit", required = true) String submit,
			@ModelAttribute("command") Object command,
			BindingResult result,
			SessionStatus status,
			ModelMap model) {
		return super.registrePost(request, id, registreId, index, submit, command, result, status, model);
	}

	@Override
	public void esborrarRegistre(HttpServletRequest request, String id, String campCodi, int index) {
		tascaService.esborrarRegistre(id, campCodi, index);
	}
	@Override
	public Object[] getValorRegistre(HttpServletRequest request, Long entornId, String id, String campCodi) {
		return (Object[])tascaService.getVariable(entornId, id, campCodi);
	}
	@Override
	public void guardarRegistre(HttpServletRequest request, String id, String campCodi, Object[] valors,
			int index) {
		tascaService.guardarRegistre(id, campCodi, valors, index);
	}
	@Override
	public void guardarRegistre(HttpServletRequest request, String id, String campCodi, Object[] valors) {
		tascaService.guardarRegistre(id, campCodi, valors);
	}
	@Override
	public String redirectUrl(String id, String campCodi) {
		return "redirect:/tasca/form.html?id=" + id;
	}
	@Override
	public String registreUrl() {
		return "tasca/registre";
	}

}
