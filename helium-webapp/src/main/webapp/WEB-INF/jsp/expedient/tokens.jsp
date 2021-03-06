<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://displaytag.sf.net/el" prefix="display"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<html>
<head>
	<title>Expedient: ${expedient.identificadorLimitat}</title>
	<meta name="titolcmp" content="<fmt:message key='comuns.consultes' />" />
	<link href="<c:url value="/css/tabs.css"/>" rel="stylesheet" type="text/css"/>
	<link href="<c:url value="/css/displaytag.css"/>" rel="stylesheet" type="text/css"/>
	<script type="text/javascript">
		//<![CDATA[
			function confirmarActivar(e) {
				var e = e || window.event;
				e.cancelBubble = true;
				if (e.stopPropagation) e.stopPropagation();
				return confirm("<fmt:message key='info.token.activar.confirmacio' />");
			}
			function confirmarDesactivar(e) {
				var e = e || window.event;
				e.cancelBubble = true;
				if (e.stopPropagation) e.stopPropagation();
				return confirm("<fmt:message key='info.token.desactivar.confirmacio' />");
			}
		//]]>
	</script>
</head>
<body>

	<c:import url="../common/tabsExpedient.jsp">
		<c:param name="tabActiu" value="tokens"/>
	</c:import>

	<h3 class="titol-tab titol-tokens">
		Tokens del procés
	</h3>
	<display:table name="tokens" id="registre" class="displaytag">
		<display:column property="id" title="Id" sortable="false"/>
		<display:column property="fullName" title="Nom" sortable="false"/>
		<display:column property="parentFullName" title="Pare" sortable="false"/>
		<display:column title="root" sortable="false">
			<c:choose><c:when test="${registre.root}">Si</c:when><c:otherwise>No</c:otherwise></c:choose>
		</display:column>
		<display:column property="nodeName" title="Node" sortable="false"/>
		<display:column property="nodeEnter" title="Entrada" format="{0,date,dd/MM/yyyy HH:mm}" sortable="false"/>
		<display:column property="start" title="Creat el" format="{0,date,dd/MM/yyyy HH:mm}" sortable="false"/>
		<display:column property="end" title="Finalitzat el" format="{0,date,dd/MM/yyyy HH:mm}" sortable="false"/>
		<display:column title="Flags" sortable="false">
			<c:if test="${registre.ableToReactivateParent}">R</c:if>
			<c:if test="${registre.terminationImplicit}">F</c:if>
			<c:if test="${registre.suspended}">S</c:if>
		</display:column>
		<display:column sortable="false">
			<c:choose> 
				<c:when test="${empty registre.end}">
					<a href="<c:url value="/expedient/tokenRetrocedir.html"><c:param name="id" value="${param.id}"/><c:param name="tokenId" value="${registre.id}"/></c:url>"><img src="<c:url value="/img/arrow_undo.png"/>" alt="Retrocedir" title="Retrocedir" border="0"/></a>
					<a href="<c:url value="/expedient/tokenActivar.html"><c:param name="activar" value="false"/><c:param name="id" value="${param.id}"/><c:param name="tokenId" value="${registre.id}"/></c:url>" onclick="return confirmarDesactivar(event)"><img src="<c:url value="/img/control_pause_blue.png"/>" alt="Desactivar token" title="Desactivar token" border="0"/></a>
				</c:when>
				<c:otherwise>
					<a href="<c:url value="/expedient/tokenActivar.html"><c:param name="activar" value="true"/><c:param name="id" value="${param.id}"/><c:param name="tokenId" value="${registre.id}"/></c:url>" onclick="return confirmarActivar(event)"><img src="<c:url value="/img/control_play_blue.png"/>" alt="Activar token" title="Activar token" border="0"/></a>
				</c:otherwise>
			</c:choose>
		</display:column>
	</display:table>

	<p align="right" class="aclaracio">Llegenda dels flags: [S] Token suspès, [R] Pot reactivar el pare, [F] Finalització implícita</p>

</body>
</html>
