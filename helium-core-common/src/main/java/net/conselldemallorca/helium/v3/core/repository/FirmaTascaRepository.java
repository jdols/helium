/**
 * 
 */
package net.conselldemallorca.helium.v3.core.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.conselldemallorca.helium.core.model.hibernate.FirmaTasca;

/**
 * Dao pels objectes de tipus firma de tasca
 * 
 * @author Limit Tecnologies <limit@limit.es>
 */
public interface FirmaTascaRepository extends JpaRepository<FirmaTasca, Long> {
	
	/** Consulta el següent valor per a ordre de les agrupacions. */
	@Query(	"select coalesce( max( ft.order), -1) + 1 " +
			"from FirmaTasca ft " +
			"where " +
			"    ft.tasca.id = :tascaId " )
	public Integer getNextOrdre(@Param("tascaId") Long tascaId);

	@Query("select ft from " +
			"    FirmaTasca ft " +
			"where " +
			"    ft.tasca.id=:tascaId " +
			"and ft.order=:order")
	FirmaTasca getAmbOrdre(@Param("tascaId") Long tascaId, @Param("order") int order);

	@Query("select ft from " +
			"    FirmaTasca ft " +
			"where " +
			"   ft.tasca.jbpmName=:jbpmName " +
			"	and ft.tasca.definicioProces.jbpmId=:jbpmId " +
			"order by " +
			"    ft.order")
	List<FirmaTasca> findAmbTascaOrdenats(
			@Param("jbpmName") String name,
			@Param("jbpmId") String jbpmId);
	
	@Query("from FirmaTasca ft " +
			"where ft.tasca.id=:tascaId " +
			"order by ft.order")
	public List<FirmaTasca> findAmbTascaIdOrdenats(
			@Param("tascaId") Long tascaId);	

	@Query("select count(ft) from " +
			"    FirmaTasca ft " +
			"where " +
			"   ft.tasca.jbpmName=:jbpmName " +
			"	and ft.tasca.definicioProces.jbpmId=:jbpmId")
	Long countAmbTasca(
			@Param("jbpmName") String name,
			@Param("jbpmId") String jbpmId);

	@Query("select ft from " +
			"    FirmaTasca ft " +
			"where " +
			"    ft.document.id=:documentId " +
			"and ft.tasca.id=:tascaId")
	FirmaTasca findAmbDocumentTasca(@Param("documentId") Long documentId, @Param("tascaId") Long tascaId);
	
	@Query(	"from FirmaTasca ft " +
			"where " +
			"   ft.tasca.id = :tascaId " +
			"	and (:esNullFiltre = true or lower(ft.document.codi) like lower('%'||:filtre||'%') or lower(ft.document.nom) like lower('%'||:filtre||'%')) ")
	public Page<FirmaTasca> findByFiltrePaginat(
			@Param("tascaId") Long tascaId,
			@Param("esNullFiltre") boolean esNullFiltre,
			@Param("filtre") String filtre,
			Pageable pageable);
	
	@Query("select ft " +
			"from FirmaTasca ft " +
			"where ft.tasca.id = :tascaId " +
			"order by ft.order asc ")
	List<FirmaTasca> findFirmesTasca(
			@Param("tascaId") Long tascaId);	
}
