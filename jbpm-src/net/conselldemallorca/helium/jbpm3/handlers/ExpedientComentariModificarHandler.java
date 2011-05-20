/**
 * 
 */
package net.conselldemallorca.helium.jbpm3.handlers;

import net.conselldemallorca.helium.model.dto.ExpedientDto;
import net.conselldemallorca.helium.model.hibernate.Expedient;
import net.conselldemallorca.helium.util.ExpedientIniciant;

import org.jbpm.JbpmException;
import org.jbpm.graph.exe.ExecutionContext;

/**
 * Handler per modificar el comentari d'un expedient.
 * 
 * @author Josep Gayà <josepg@limit.es>
 */
@SuppressWarnings("serial")
public class ExpedientComentariModificarHandler extends AbstractHeliumActionHandler {

	private String comentari;
	private String varComentari;



	public void execute(ExecutionContext executionContext) throws Exception {
		Expedient ex = ExpedientIniciant.getExpedient();
		String c = (String)getValorOVariable(executionContext, comentari, varComentari);
		if (ex != null) {
			ex.setComentari(c);
		} else {
			ExpedientDto expedient = getExpedient(executionContext);
			if (expedient != null) {
				getExpedientService().editar(
						expedient.getEntorn().getId(),
						expedient.getId(),
						expedient.getNumero(),
						expedient.getTitol(),
						expedient.getIniciadorCodi(),
						expedient.getResponsableCodi(),
						expedient.getDataInici(),
						c,
						expedient.getEstat().getId(),
						expedient.getGeoPosX(),
						expedient.getGeoPosY(),
						expedient.getGeoReferencia());
			} else {
				throw new JbpmException("No s'ha trobat l'expedient per canviar el comentari");
			}
		}
	}

	public void setComentari(String comentari) {
		this.comentari = comentari;
	}
	public void setVarComentari(String varComentari) {
		this.varComentari = varComentari;
	}

}
