package net.conselldemallorca.helium.webapp.v3.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Validador per al manteniment de consultes del tipus d'expedient: 
 * - Comprova que el codi no estigui duplicat
 */
@Documented
@Constraint(validatedBy = ExpedientTipusConsultaValidator.class)
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExpedientTipusConsulta {

	String message() default "expedient.tipus.consulta.validacio";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}