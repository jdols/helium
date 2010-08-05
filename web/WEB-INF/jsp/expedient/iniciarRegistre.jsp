<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
	<title>${registre.etiqueta}</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
 
	<link href="<c:url value="/css/reset.css"/>" rel="stylesheet" type="text/css"/>
	<link href="<c:url value="/css/common.css"/>" rel="stylesheet" type="text/css"/>
	<script type="text/javascript" src="<c:url value="/js/jquery/jquery.js"/>"></script>

	<script type="text/javascript" src="<c:url value="/js/selectable.js"/>"></script>
	<link href="<c:url value="/css/displaytag.css"/>" rel="stylesheet" type="text/css"/>
	<c:import url="../common/formIncludes.jsp"/>
<script type="text/javascript">
// <![CDATA[
function canviTermini(input) {
	var campId = input.id.substring(0, input.id.indexOf("_"));
	var anys = document.getElementById(campId + "_anys").value;
	var mesos = document.getElementById(campId + "_mesos").value;
	var dies = document.getElementById(campId + "_dies").value;
	document.getElementById(campId).value = anys + "/" + mesos + "/" + dies;
}
function refrescarPare(accio) {
	if (accio == 'submit')
		parent.refresh();
	else
		window.close();
}
// ]]>
</script>
</head>
<body>
	<c:choose>
		<c:when test="${not tancarRegistre}">
			<form:form action="iniciarRegistre.html" cssClass="uniForm tascaForm zebraForm">
				<form:hidden path="id"/>
				<form:hidden path="registreId"/>
				<form:hidden path="entornId"/>
				<form:hidden path="index"/>
				<div class="inlineLabels">
					<c:forEach var="membre" items="${registre.registreMembres}">
						<c:set var="campRegistreActual" value="${membre}" scope="request"/>
						<c:import url="../common/campTasca.jsp"/>
					</c:forEach>
				</div>
				<c:import url="../common/formElement.jsp">
					<c:param name="type" value="buttons"/>
					<c:param name="values">submit,cancel</c:param>
					<c:param name="titles">Guardar,Cancel·lar</c:param>
				</c:import>
			</form:form>
			<p class="aclaracio">Els camps marcats amb <img src="<c:url value="/img/bullet_red.png"/>" alt="Camp obligatori" title="Camp obligatori" border="0"/> són obligatoris</p>
		</c:when>
		<c:otherwise><script type="text/javascript">refrescarPare('${param.submit}');</script></c:otherwise>
	</c:choose>

</body>
</html>
