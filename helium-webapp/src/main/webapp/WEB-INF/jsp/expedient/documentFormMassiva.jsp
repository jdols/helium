<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
	<title>Modificació de documents</title>
	<meta name="titolcmp" content="<fmt:message key='comuns.consultes' />" />
	<link href="<c:url value="/css/tabs.css"/>" rel="stylesheet" type="text/css"/>
	<link href="<c:url value="/css/displaytag.css"/>" rel="stylesheet" type="text/css"/>
	<c:import url="../common/formIncludes.jsp"/>
</head>
<body>

	<h3 class="titol-tab titol-dades-tasca"><fmt:message key='expedient.document.modificar' /></h3>

	<c:url value="/document/arxiuMostrar.html" var="downloadUrl"><c:param name="token" value="${document.tokenSignatura}"/></c:url>

	<div class="missatgesDocumentGris">
		<h4 class="titol-missatge">
			<c:choose><c:when test="${not document.adjunt}">${command.nom}</c:when><c:otherwise><fmt:message key='expedient.document.adjunt' /></c:otherwise></c:choose>
			<c:if test="${documentDisseny.plantilla}">
				<a href="<c:url value="/expedient/documentGenerar.html"><c:param name="procesInstanceId" value="${param.procesInstanceId}"/><c:param name="docId" value="${command.docId}"/></c:url>"><img src="<c:url value="/img/page_white_star.png"/>" alt="Generar" title="Generar" border="0"/></a>
			</c:if>
		</h4>
		<form:form action="/helium/expedient/documentModificarMas.html" cssClass="uniForm"  commandName="documentCommandMas" enctype="multipart/form-data">
		<div class="inlineLabels">
			<input type="hidden" name="procesInstanceId" value="${param.procesInstanceId}"/>
			<form:hidden path="docId"/>
			<c:choose>
				<c:when test="${document.adjunt}">
					<c:import url="../common/formElement.jsp">
						<c:param name="property" value="nom"/>
						<c:param name="required" value="true"/>
						<c:param name="label"><fmt:message key='expedient.document.titol' /></c:param>
					</c:import>
				</c:when>
				<c:otherwise>
					<form:hidden path="nom"/>
				</c:otherwise>
			</c:choose>
			<c:import url="../common/formElement.jsp">
				<c:param name="property" value="contingut"/>
				<c:param name="required" value="true"/>
				<c:param name="type" value="file"/>
				<c:param name="fileUrl">${downloadUrl}</c:param>
				<c:param name="fileExists" value="${not empty command.data}"/>
				<c:param name="label"><fmt:message key='expedient.document.document' /></c:param>
			</c:import>
			<c:import url="../common/formElement.jsp">
				<c:param name="property" value="data"/>
				<c:param name="required" value="true"/>
				<c:param name="type" value="date"/>
				<c:param name="label"><fmt:message key='expedient.document.data' /></c:param>
			</c:import>
		</div>
		<c:import url="../common/formElement.jsp">
			<c:param name="type" value="buttons"/>
			<c:param name="values">submit,cancel</c:param>
			<c:param name="titles"><fmt:message key='comuns.modificar' />,<fmt:message key='comuns.cancelar' /></c:param>
		</c:import>
	</form:form>
	</div>


</body>
</html>