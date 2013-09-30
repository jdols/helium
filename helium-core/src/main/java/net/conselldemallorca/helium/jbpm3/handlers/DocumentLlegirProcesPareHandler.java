/**
 * 
 */
package net.conselldemallorca.helium.jbpm3.handlers;

import net.conselldemallorca.helium.core.model.service.DocumentHelper;

import org.jbpm.JbpmException;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;

/**
 * Handler per lligar un document del procés pare cap al
 * procés actual.
 * 
 * @author Limit Tecnologies <limit@limit.es>
 */
@SuppressWarnings("serial")
public class DocumentLlegirProcesPareHandler extends AbstractHeliumActionHandler implements DocumentLlegirProcesPareHandlerInterface {

	private String documentCodi;
	private String varDocumentCodi;



	public void execute(ExecutionContext executionContext) throws Exception {
		Token tokenPare = executionContext.getProcessInstance().getSuperProcessToken();
		if (tokenPare != null) {
			String dc = (String)getValorOVariable(executionContext, documentCodi, varDocumentCodi);
			String varDocument = DocumentHelper.PREFIX_VAR_DOCUMENT + dc;
			Object valor = tokenPare.getProcessInstance().getContextInstance().getVariable(varDocument);
			if (valor != null) {
				executionContext.setVariable(varDocument, valor);
			}
		} else {
			throw new JbpmException("Aquest procés(" + executionContext.getProcessInstance().getId() + ") no té cap procés pare");
		}
	}

	public void setDocumentCodi(String documentCodi) {
		this.documentCodi = documentCodi;
	}
	public void setVarDocumentCodi(String varDocumentCodi) {
		this.varDocumentCodi = varDocumentCodi;
	}

}