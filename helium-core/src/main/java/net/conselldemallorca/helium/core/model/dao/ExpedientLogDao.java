/**
 * 
 */
package net.conselldemallorca.helium.core.model.dao;

import java.util.List;

import net.conselldemallorca.helium.core.model.hibernate.ExpedientLog;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Dao pels objectes de tipus log d'expedient
 * 
 * @author Limit Tecnologies <limit@limit.es>
 */
@Repository
public class ExpedientLogDao extends HibernateGenericDao<ExpedientLog, Long> {

	public ExpedientLogDao() {
		super(ExpedientLog.class);
	}

	public List<ExpedientLog> findAmbExpedientIdOrdenatsPerData(Long expedientId) {
		return findOrderedByCriteria(
				new String[] {"data"},
				true,
				Restrictions.eq("expedient.id", expedientId));
	}

	public ExpedientLog findAmbJbpmLogId(Long jbpmLogId) {
		List<ExpedientLog> logs = findByCriteria(
				Restrictions.eq("jbpmLogId", jbpmLogId));
		if (logs.size() > 0)
			return logs.get(0);
		else
			return null;
	}

}