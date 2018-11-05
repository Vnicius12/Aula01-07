<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cat&aacute;logo de Filmes</title>
</head>
<body>
	<form action="manterfilmes.do" method="get">
		<h3>Clique para inserir um filme e ver o resultado</h3>
		<input type="submit" name="acao" value="inserir"/>
		<h3>Clique para listar todos os gêneros</h3>
		<input type="submit" name="acao" value="generos"/>
		<h3>Digite o id do filme, clique e veja o resultado</h3>
		<input type="text" name="id">
		<input type="submit" name="acao" value="buscar"/>
	</form>
</body>
</html>