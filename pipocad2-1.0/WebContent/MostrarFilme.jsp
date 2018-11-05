<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Filme Selecionado</title>
</head>
<body>
	<c:if test="${filme.id < 0}">
		<h3>Filme não encontrado</h3>
	</c:if>
	<c:if test="${filme.id >= 0}">
		<h3>${filme}</h3>
		<c:if test="${filme.posterPath != null}">
			<h3>Data de Lançamento: <fmt:formatDate value="${filme.dataLancamento}" pattern="dd/MM/yyyy"/></h3>
			<img alt="poster" src="${filme.posterPath}" height="480" width="320">
		</c:if>
	</c:if>
</body>
</html>