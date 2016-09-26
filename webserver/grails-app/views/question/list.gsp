<!DOCTYPE html>
<html>
<head>

<title>Questions</title>
</head>
<style>
table {
	border-collapse: collapse;
	width: 80%;
}
table,thead,td {
	border: 1px solid black;
}
thead {
	height: 20px;
}
td {
	height: 10px;
}
form, button {
        text-decoration: none; 
        font: menu; 
        color: ButtonText;
        display: inline-block; 
        padding: 2px 8px;
    }
</style>

<body>
<div id= optionsMenu>
<g:form controller= "home" action="configuration">
    <button type="submit">Configuration</button>
</g:form>
<g:form controller= "product" action="list">
    <button type="submit">My Products</button>
</g:form>
<g:form controller= "order" action="list">
    <button type="submit">My Orders</button>
</g:form>
<g:form controller= "question" action="updateList">
    <button type="submit">Refresh Questions</button>
</g:form>

</div>
	<br>
	<br>
	<div>
	<table id="questionTable">
		<thead>
				<tr>
					
					<g:sortableColumn property="item.itemId" title="Meli Item" />
					<g:sortableColumn property="item.product.title" title="Item Title" />
					<g:sortableColumn property="item.product.image1" title="Item Image" />
					<g:sortableColumn property="questionId" title="Meli Question" />
					<g:sortableColumn property="text" title="Text" />
					<g:sortableColumn property="status" title="Status" />
					<td>Action</td>					
					
				</tr>
		</thead>
		<tbody>
			<g:each in="${listQuestions}" status="i" var="question">
			<tr>
			<g:form controller="question" action="answerQuestion">
			<g:hiddenField name="questionId" value="${question.id}" />
					<td>${fieldValue(bean: question, field: "item.itemId")}</td>
					<td>${fieldValue(bean: question, field: "item.product.title")}</td>
					<td><img src="${fieldValue(bean: question, field: "item.product.image1")}"/></td>
					<td>${fieldValue(bean: question, field: "questionId")}</td>
					<td>${fieldValue(bean: question, field: "text")}</td>
					<td>${fieldValue(bean: question, field: "status")}</td>
					<g:if test="${fieldValue(bean: question, field: "status") == 'UNANSWERED'}">
					<td><g:textArea name="answer" id="answer" value="" rows="3"/>
					<br>
					<g:submitButton name="save" value="Answer" /></td>
					</g:if>
			</g:form>
			<tr>
			</g:each>
		
		</tbody>
	
	</table>
	
	
	</div>

</body>

</html>