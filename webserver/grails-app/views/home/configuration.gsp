<html>
<head>
<style type="text/css">
 
.container-fluid {
position:relative;padding-left:20px;padding-right:20px;zoom:1;
}
.container-fluid table {
width:30%;margin-bottom:18px;padding:0;border-collapse:separate;*border-collapse:collapse;font-size:13px;border:1px solid #ddd;-webkit-border-radius:4px;-moz-border-radius:4px;border-radius:4px;}table th,table td{padding:10px 10px 9px;line-height:18px;text-align:left;
}
 th{padding-top:9px;font-weight:bold;vertical-align:middle;border-bottom:1px solid #ddd;}
 td{vertical-align:top;}
table th+th,table td+td{border-left:1px solid #ddd;}
table tr+tr td{border-top:1px solid #ddd;}
}
#menu {background-color: gray ; border: black 1px solid; height:20px; width:100px; float:left;}
#menu li {float:left}
</style>

</head>
<body>

<div id="header"></div>


<div>
<form align="right" action="logout">
User:${session.user.username}
<input class="save" type="submit" value="Logout">
</form>
</div>


<div id="info">
<g:if test="${userLogged}">
		<div class='container-fluid'>
		<table>
		
			<tr>
			<td>Nickname: <output id="nickname" name="nickname">${userLogged.nickname}</output><br/></td>
			</tr>			
			<tr>
			<td>Email: <output id="email" name="email">${userLogged.email}</output><br/></td>
			</tr>			
			<tr>
			<td>Site: <output id="site" name="site">${userLogged.site_id}</output><br/></td>
			</tr>			
			<tr>
			<td>User ID: <output id="userId" name="userId">${userLogged.id}</output><br/></td>
			</tr>			
			<tr>
			<td>User Type: <output id="userType" name="userType">${userLogged.user_type}</output><br/></td>
			</tr>			
			<tr>
			<td>MercadoEnvíos: <output id="mEnvios" name="mEnvios">${listShipping}</output><br/></td>
			</tr>			
			<tr>
			<td>MercadoPago: <output id="mPago" name="mPago">${userLogged.status.mercadopago_tc_accepted}</output><br/></td>
			</tr>		
	
   			</table>
		</div>

		</g:if>	
</div>


<div id="menu">
<g:if test="${userLogged}">
<g:form controller= "product" action="list">
    <button type="submit">My Products</button>
</g:form>
</g:if>

<g:if test="${userLogged}">
<g:form controller= "question" action="list">
    <button type="submit">My Questions</button>
</g:form>
</g:if>


<g:if test="${userLogged}">
<g:form controller= "order" action="list">
    <button type="submit">My Orders</button>
</g:form>
</g:if>

<g:if test="${userLogged}">
<g:form action="/notification/getNotification">
    <button type="submit">Notifications Process</button>
</g:form>
</g:if>

<g:if test="${userLogged}">
<g:form controller= "metrics" action="index">
    <button type="submit">Metrics</button>
</g:form>
</g:if>


<form action="oauth">
<input class="save" type="submit" value="Get Authentication">
</form>


</div>
</body>
</html>