/**
 * 
 */
package net.conselldemallorca.helium.webapp.v3.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.conselldemallorca.helium.v3.core.api.service.AdminService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controlador per a la pàgina inicial (index).
 * 
 * @author Limit Tecnologies <limit@limit.es>
 */
@Controller
@RequestMapping("/v3")
public class IndexV3Controller {

	@Resource
	private AdminService adminService;

	@RequestMapping(method = RequestMethod.GET)
	public String get(HttpServletRequest request) {
		return "redirect:/v3/index";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request) {
		return "redirect:/v3/expedient";
	}

//	@RequestMapping(value = "/mesuresTemps", method = RequestMethod.GET)
//	@ResponseBody
//	public List<MesuraTemporalDto> mesuresTemps(HttpServletRequest request) {
//		return adminService.findMesuresTemporals();
//	}

}