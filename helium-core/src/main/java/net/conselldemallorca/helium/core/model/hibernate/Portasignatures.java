package net.conselldemallorca.helium.core.model.hibernate;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.UniqueConstraint;

/**
 * Objecte de domini que representa un document del portasignatures.
 * 
 * @author Limit Tecnologies <limit@limit.es>
 */
@Entity
@Table(name="hel_portasignatures",
		uniqueConstraints={@UniqueConstraint(columnNames={"document_id", "token_id"})})
public class Portasignatures implements Serializable, GenericEntity<Long> {

	public enum TipusEstat {
		BLOQUEJAT,
		PENDENT,
		SIGNAT,
		REBUTJAT
	}
	
	public enum Transicio {
		SIGNAT,
		REBUTJAT
	}
	
	private Long id;
	private Integer documentId;
	private Long tokenId;
	private Date dataEnviat;
	private TipusEstat estat;
	private Transicio transition;
	private Long documentStoreId;
	private String motiuRebuig;
	private String transicioOK;
	private String transicioKO;
	
	public Portasignatures() {}
	public Portasignatures(
			Integer documentId,
			Long tokenId,
			Date dataEnviat,
			TipusEstat estat,
			Transicio transition,
			Long documentStoreId,
			String motiuRebuig,
			String transicioOK,
			String transicioKO) {
		this.documentId = documentId;
		this.tokenId = tokenId;
		this.dataEnviat = dataEnviat;
		this.estat = estat;
		this.transition = transition;
		this.documentStoreId = documentStoreId;
		this.motiuRebuig = motiuRebuig;
		this.transicioOK = transicioOK;
		this.transicioKO = transicioKO;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator="gen_portasignatures")
	@TableGenerator(name="gen_portasignatures", table="hel_idgen", pkColumnName="taula", valueColumnName="valor")
	@Column(name="id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="document_id", nullable=false)
	public Integer getDocumentId() {
		return documentId;
	}
	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}
	
	@Column(name="token_id", nullable=false)
	public Long getTokenId() {
		return tokenId;
	}
	public void setTokenId(Long tokenId) {
		this.tokenId = tokenId;
	}
	
	@Column(name="data_enviat")
	public Date getDataEnviat() {
		return dataEnviat;
	}
	public void setDataEnviat(Date dataEnviat) {
		this.dataEnviat = dataEnviat;
	}

	@Column(name="estat")
	public TipusEstat getEstat() {
		return estat;
	}
	public void setEstat(TipusEstat estat) {
		this.estat = estat;
	}
	
	@Column(name="transicio")
	public Transicio getTransition() {
		return transition;
	}
	public void setTransition(Transicio transition) {
		this.transition = transition;
	}
	
	@Column(name="document_store_id")
	public Long getDocumentStoreId() {
		return documentStoreId;
	}
	public void setDocumentStoreId(Long documentStoreId) {
		this.documentStoreId = documentStoreId;
	}
	
	@Column(name="motiu_rebuig")
	public String getMotiuRebuig() {
		return motiuRebuig;
	}
	public void setMotiuRebuig(String motiuRebuig) {
		this.motiuRebuig = motiuRebuig;
	}
	
	@Column(name="transicio_ok")
	public String getTransicioOK() {
		return transicioOK;
	}
	public void setTransicioOK(String transicioOK) {
		this.transicioOK = transicioOK;
	}
	
	@Column(name="transicio_ko")
	public String getTransicioKO() {
		return transicioKO;
	}
	public void setTransicioKO(String transicioKO) {
		this.transicioKO = transicioKO;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataEnviat == null) ? 0 : dataEnviat.hashCode());
		result = prime * result
				+ ((documentId == null) ? 0 : documentId.hashCode());
		result = prime * result + ((estat == null) ? 0 : estat.hashCode());
		result = prime * result + ((tokenId == null) ? 0 : tokenId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Portasignatures other = (Portasignatures) obj;
		if (dataEnviat == null) {
			if (other.dataEnviat != null)
				return false;
		} else if (!dataEnviat.equals(other.dataEnviat))
			return false;
		if (documentId == null) {
			if (other.documentId != null)
				return false;
		} else if (!documentId.equals(other.documentId))
			return false;
		if (estat == null) {
			if (other.estat != null)
				return false;
		} else if (!estat.equals(other.estat))
			return false;
		if (tokenId == null) {
			if (other.tokenId != null)
				return false;
		} else if (!tokenId.equals(other.tokenId))
			return false;
		return true;
	}
	
	private static final long serialVersionUID = 1L;
}
