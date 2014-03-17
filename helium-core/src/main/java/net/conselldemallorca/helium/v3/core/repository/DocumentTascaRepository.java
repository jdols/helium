/**
 * 
 */
package net.conselldemallorca.helium.v3.core.repository;

import java.util.List;

import net.conselldemallorca.helium.core.model.hibernate.DocumentTasca;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Dao pels objectes de tipus plantilla de tasca
 * 
 * @author Limit Tecnologies <limit@limit.es>
 */
public interface DocumentTascaRepository extends JpaRepository<DocumentTasca, Long> {
	@Query("select " +
			"	 max(dt.order) " +
			"from " +
			"    DocumentTasca dt " +
			"where " +
			"    dt.tasca.id=:tascaId")
	public int getNextOrder(@Param("tascaId") Long tascaId);

	@Query("select dt from " +
			"    DocumentTasca dt " +
			"where " +
			"    dt.tasca.id=:tascaId " +
			"and dt.order=:order")
	public DocumentTasca getAmbOrdre(@Param("tascaId") Long tascaId, @Param("order") int order);

	@Query("select dt from " +
			"    DocumentTasca dt " +
			"where " +
			"    dt.tasca.id=:tascaId " +
			"order by " +
			"    dt.order")
	public List<DocumentTasca> findAmbTascaOrdenats(@Param("tascaId") Long tascaId);

	@Query("select dt from " +
			"    DocumentTasca dt " +
			"where " +
			"    dt.document.id=:documentId " +
			"and dt.tasca.id=:tascaId")
	public DocumentTasca findAmbDocumentTasca(@Param("documentId") Long documentId, @Param("tascaId") Long tascaId);
}