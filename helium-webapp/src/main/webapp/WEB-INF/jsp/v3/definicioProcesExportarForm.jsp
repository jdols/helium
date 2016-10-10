<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib tagdir="/WEB-INF/tags/helium" prefix="hel"%>
<c:set var="idioma"><%=org.springframework.web.servlet.support.RequestContextUtils.getLocale(request).getLanguage()%></c:set>

<c:set var="titol"><spring:message code="definicio.proces.exportar.form.titol"/></c:set>

<html>
<head>
	<title>${titol}</title>
	<hel:modalHead/>
	<script src="<c:url value="/webjars/datatables.net/1.10.10/js/jquery.dataTables.min.js"/>"></script>
	<script src="<c:url value="/webjars/datatables.net-bs/1.10.10/js/dataTables.bootstrap.min.js"/>"></script>
	<link href="<c:url value="/webjars/datatables.net-bs/1.10.10/css/dataTables.bootstrap.min.css"/>" rel="stylesheet"></link>
	<link href="<c:url value="/css/select2.css"/>" rel="stylesheet"/>
	<link href="<c:url value="/css/select2-bootstrap.css"/>" rel="stylesheet"/>
	<script src="<c:url value="/js/select2.min.js"/>"></script>
	<script src="<c:url value="/js/select2-locales/select2_locale_${idioma}.js"/>"></script>	
	<script src="<c:url value="/js/jsrender.min.js"/>"></script>
	<script src="<c:url value="/js/webutil.common.js"/>"></script>
	<script src="<c:url value="/js/webutil.datatable.js"/>"></script>
	<script src="<c:url value="/js/webutil.modal.js"/>"></script>
	<style type="text/css">
		.panel {
			margin-bottom: 0px;
		}
		.panel-heading {
			padding : 5px 5px;
		}
	</style>
</head>
<body>		
	<form:form id="exportar-form" cssClass="form-horizontal" enctype="multipart/form-data" method="post" commandName="command" style="min-height: 500px;">
		
		<div class="inlineLabels">
			<script type="text/javascript">
				// <![CDATA[
				            
				// Opcions comunes per totes les taules del document
				$.extend( true, $.fn.dataTable.defaults, {
				    columnDefs: [ {
				    	targets: 0,
				    	orderable: false
				    } ],
					order: [[ 1, "asc" ]],
					paging : false,
				    searching: false,
				    oLanguage: {
					      sInfo: ""
				    }
				});				

				$(document).ready( function() {					
					// Neteja els errors en fer submit
					$('#exportar-form').submit(function() {
						$('.has-error').removeClass('has-error');
						$('p.help-block').remove();
					});
					// Lliga els events ajax als diferents elements de la pàgina
					actualitzarOpcions();
					// Quan canvia la versió escollida de la definició de procés carrega les opcions d'exportació
					$('#id').change(function(){
						recarregaOpcionsExportacio();
					});
				}); 		
				
				/* Al principi o quan es carrega el contingut de les opcions es lliguen els events ajax. */
				function actualitzarOpcions() {					
					$('table').DataTable();
					// Event per seleccionar o des seleccionar totes les entrades
					$('.checkAll').change(function(){
						$(this).closest('.dataTables_wrapper').find('.check').prop('checked', $(this).is(':checked')).change();
						updateMarcador($(this).closest('.agrupacio'));
					});
					$('.tots').click(function(e) {
						$(this).closest('.agrupacio').find('.checkAll').prop('checked', false).change();
						e.stopPropagation();
					});
					$('.algun, .cap').click(function(e) {
						$(this).closest('.agrupacio').find('.checkAll').prop('checked', true).change();
						e.stopPropagation();
					});
					// marca el checkbox si es clica sobre la línia de la taula
					$('.row_checkbox').click(function(e) {
						if (e.target.type != 'checkbox'){
							var $checkbox =$('.check', this);
							$checkbox.prop('checked', !$checkbox.is(':checked')).change();
						  }						
					});
					// si canvia un check comprova què fer amb el checkAll
					$('.check').change(function() {
						updateMarcador($(this).closest('.agrupacio'))
					});					
					// Per mostrar o ocultar el contingut de les taules
					$('.agrupacio').on('show.bs.collapse', function () {
						$(this).find('.fa-chevron-down').hide();
						$(this).find('.fa-chevron-up').show();
					});
					$('.agrupacio').on('hidden.bs.collapse', function () {
						$(this).find('.fa-chevron-up').hide();
						$(this).find('.fa-chevron-down').show();
					});
					// actualitza els comptadors
					$('.agrupacio').each(function(){
						if ($(this).find('.taula').length > 0)
							updateMarcador($(this));
					});		
					// Expandeix els panells que continguin errors
					$('.has-error').closest('.agrupacio').find('.clicable').click();
					// Aplica la funció select2 als controls select
					$('select.form-control').select2({
					    minimumResultsForSearch: -1
					});
				} 		
				
				// Actualitza la icona del marcador de selecció del costat del títol de la agrupació
				function updateMarcador( $agrupacio ) {
					// amaga els marcadors
					$agrupacio.find('.marcador').hide();
					var total = $agrupacio.find('.check').length;
					var marcats = $agrupacio.find('.check:checked').length;
					
					if (total == marcats && marcats > 0)
						$agrupacio.find('.tots').show();
					else if(marcats == 0)
						$agrupacio.find('.cap').show();
					else
						$agrupacio.find('.algun').show();
					$agrupacio.find('.marcats').text(marcats);
					$agrupacio.find('.total').text(total);
					// botó de checkall					
					$agrupacio.find('.checkAll').prop('checked', total == marcats);
				}
				
				// Quan canvia la versió recarrega les opcions d'exportació
				function recarregaOpcionsExportacio() {
						window.parent.$('button[type="submit"]').attr('disabled', 'disabled');
						$('#exportarOpcions').empty();						
					    $.ajax({
					        url: '<c:url value="/nodeco/v3/definicioProces/${definicioProces.jbpmKey}/exportar/"/>' + $('#id').val() + '/opcions' ,
					        type: 'GET',
					        //Ajax events
					        beforeSend: function(){
								$('#carregant').show();
					        },
					        success: function(data){
					        	$('#exportarOpcions').html(data);
								window.parent.$('button[type="submit"]').removeAttr('disabled');
								actualitzarOpcions();
					        },
					        error: function(err){
					        	console.log('Error ' + err.status + ': ' + err.statusText)
					        },
					        complete: function() {
					        	$('#carregant').hide();
								webutilRefreshMissatges();
					        },
					        //Options to tell jQuery not to process data or worry about content-type.
					        cache: false,
					        contentType: false,
					        processData: false
					    });
					 }
				// ]]>
			</script>			
		</div>
		<hel:inputSelect required="false" emptyOption="false" name="id" textKey="definicio.proces.exportar.form.versio" optionItems="${versions}" optionValueAttribute="codi" optionTextAttribute="valor"/>
		
		<div id="exportarOpcions">
			<%@include file="definicioProcesExportarOpcions.jsp"%>
		</div>
		<div id="carregant" style="display: none; width: 10%; text-align: center;">
			<span class="fa fa-spinner fa-pulse fa-2x fa-fw"></span>
			<span class="sr-only"><spring:message code="comu.processant"/>...</span>
		</div>


		<div id="modal-botons" class="well">
			<button type="button" class="btn btn-default" data-modal-cancel="true">
				<spring:message code="comu.boto.cancelar"/>
			</button>
			<button type="submit" class="btn btn-success right">
				<span class="fa fa-sign-out"></span> <spring:message code="comu.filtre.exportar"/>
			</button>
		</div>
	</form:form>
</body>
</html>