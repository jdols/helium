/**
 * 
 */
package net.conselldemallorca.helium.core.model.service;

import net.conselldemallorca.helium.core.model.dto.ArxiuDto;
import net.conselldemallorca.helium.core.model.dto.DocumentDto;
import net.conselldemallorca.helium.core.model.exception.IllegalArgumentsException;
import net.conselldemallorca.helium.core.util.DocumentTokenUtils;
import net.conselldemallorca.helium.core.util.GlobalProperties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;


/**
 * Servei per a gestionar les descàrregues d'arxius
 * 
 * @author Limit Tecnologies <limit@limit.es>
 */
@Service
public class DocumentService {

	private DtoConverter dtoConverter;

	private MessageSource messageSource;

	private DocumentTokenUtils documentTokenUtils;



	public DocumentDto arxiuDocumentInfo(Long documentStoreId) {
		return dtoConverter.toDocumentDto(documentStoreId, false, false, false, false);
	}

	public ArxiuDto arxiuDocumentOriginal(Long documentStoreId) {
		DocumentDto document = dtoConverter.toDocumentDto(documentStoreId, true, false, false, false);
		if (document == null)
			return null;
		return new ArxiuDto(
				document.getArxiuNom(),
				document.getArxiuContingut());
	}

	public ArxiuDto arxiuDocumentVista(Long documentStoreId) {
		DocumentDto document = dtoConverter.toDocumentDto(documentStoreId, true, true, false, false);
		if (document == null)
			return null;
		return new ArxiuDto(
				document.getVistaNom(),
				document.getVistaContingut());
	}

	public ArxiuDto arxiuDocumentPerMostrar(Long documentStoreId) {
		DocumentDto document = arxiuDocumentInfo(documentStoreId);
		if (document == null)
			return null;
		if (document.isSignat() || document.isRegistrat()) {
			return arxiuDocumentVista(documentStoreId);
		} else {
			return arxiuDocumentOriginal(documentStoreId);
		}
	}

	public ArxiuDto arxiuDocumentPerSignar(String token, boolean estampar) {
		try {
			String[] tokenDesxifrat = getDocumentTokenUtils().desxifrarTokenMultiple(token);
			Long documentStoreId = Long.parseLong(tokenDesxifrat[0]);
			if (tokenDesxifrat.length == 2) {
				documentStoreId = Long.parseLong(tokenDesxifrat[1]);
				DocumentDto document = dtoConverter.toDocumentDto(
						documentStoreId,
						true,
						true,
						true,
						estampar);
				if (document == null)
					return null;
				return new ArxiuDto(
						document.getVistaNom(),
						document.getVistaContingut());
			} else {
				throw new IllegalArgumentsException( getMessage("error.documentService.formatIncorrecte") );
			}
		} catch (Exception ex) {
			logger.error("Error al obtenir el document amb token " + token, ex);
			throw new IllegalArgumentsException( getMessage("error.documentService.formatIncorrecte") );
		}
	}



	@Autowired
	public void setDtoConverter(DtoConverter dtoConverter) {
		this.dtoConverter = dtoConverter;
	}
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}



	private DocumentTokenUtils getDocumentTokenUtils() {
		if (documentTokenUtils == null)
			documentTokenUtils = new DocumentTokenUtils(
					(String)GlobalProperties.getInstance().get("app.encriptacio.clau"));
		return documentTokenUtils;
	}

	protected String getMessage(String key, Object[] vars) {
		try {
			return messageSource.getMessage(
					key,
					vars,
					null);
		} catch (NoSuchMessageException ex) {
			return "???" + key + "???";
		}
	}

	protected String getMessage(String key) {
		return getMessage(key, null);
	}

	private static final Log logger = LogFactory.getLog(DocumentService.class);

}
