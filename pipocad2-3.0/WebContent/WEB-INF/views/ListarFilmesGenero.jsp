<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="pt-br">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Filmes por Gênero</title>

<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/car.css" rel="stylesheet">
<link rel="stylesheet" href="css/carouseller.css">

</head>

<body>
	<!-- Barra superior com os menus de navegação -->
	<c:import url="Menu.jsp" />
	<!-- Container Principal -->
	<div id="main" class="container">
	<c:forEach var="genero" items="${glista}">
				<div class="row">
					<div class="titulo_div">
						<h1 class="titulo_genero">${genero.nome}</h1>
					</div>
					<div class="col-sm-12">
						<div class="carouseller">
							<a href="javascript:void(0)" class="carouseller__left">‹</a>
								<div class="carouseller__wrap">
									<div class="carouseller__list">
										<c:forEach var="filme" items="${flista}">
											<c:if test="${ filme.genero.id == genero.id}">
												<div class="car__6">
													<div class="wrapper">
														<div class="movie-img">
															<a href="visualizar_filme?id=${filme.id}">
																<img src="${filme.posterPath}" height="200" width="140">
															</a>
														</div>
														<div class="movie-info text-center">
															<div class="movie-text">
																<h1>${filme.titulo}</h1>
																<h2><fmt:formatDate value="${filme.dataLancamento}" pattern="dd/MM/yyyy"/></h2>
																<p>${filme.descricao}</p>
															</div>
															<div class="movie-rating">
																<p class="rating"><fmt:parseNumber type="number" value="${filme.popularidade}"  /></p>
															</div>
														</div>
													</div>
												</div>
											</c:if>
										</c:forEach>
									</div>
								</div>
							<a href="javascript:void(0)" class="carouseller__right">›</a>
						</div>
					</div>
				</div>
	</c:forEach>			
	</div>
	<!-- /#main -->
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/carouseller.min.js"></script>
	<script src="js/generoscar.js"></script>

</body>

</html>