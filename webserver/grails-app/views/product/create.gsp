<html>
<head>
<title>New Product</title>
</head>
<body>
	<g:form action="save"  enctype="multipart/form-data" >
		<br/>
		New Product		
		<br/>
		SKU: <g:textField name="sku" id="sku"/>
		<br/>
		Title: <g:textField name="title" id="title"/>
		<br/>
		Price: <g:textField name="price" id="price"/>
		<br/>
		Stock: <g:textField name="stock" id="stock"/>
		<br/>
		Description: <g:textField name="description" id="description"/>
		<br/>		
<%--		Currency: --%>
<%--		<br/>--%>
<%--		<input type="checkbox" id="dolares">Dólares</>--%>
<%--		<input type="checkbox" id="pesos">Pesos</>--%>
<%--		<br/>--%>
<%--		Categoría:--%>
<%--		<g:select id="categProperty" name="categProperty.id" --%>
<%--		from="${sellapropiedades.PublishCategories.list()}" optionKey="id" optionValue="description"/>--%>
<%--		<br/>--%>
<%--		Condición:<select name="condition" id="condition"></select>--%>
<%--		<br/>--%>

		<input class="save" type="submit" value="Save Product">
		<br/>
<%--		Imagen:--%>
<%--		<br/>--%>
<%--		<input type="file" name="imagenFile" id="imagenFile"/>--%>
		
			
	</g:form>
</body>
</html>