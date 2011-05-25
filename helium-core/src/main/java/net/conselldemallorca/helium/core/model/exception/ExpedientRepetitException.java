/**
 * 
 */
package net.conselldemallorca.helium.core.model.exception;

import org.springframework.core.NestedRuntimeException;

/**
 * Excepció que indica que l'expedient que s'està intentant iniciar ja existeix
 * 
 * @author Limit Tecnologies <limit@limit.es>
 */
public class ExpedientRepetitException extends NestedRuntimeException {

	private static final long serialVersionUID = 1L;

	public ExpedientRepetitException(String msg) {
        super(msg);
    }

    public ExpedientRepetitException(String msg, Throwable t) {
        super(msg, t);
    }

}
