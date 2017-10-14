<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Three meter rope</title>
    <script type="text/javascript" src="js/jquery-1.12.3.js"></script>
    <script type="text/javascript" src="js/jquery.color-2.1.2.min.js"></script>
    <%--<script type="text/javascript" src="js/tmr.js"></script>--%>
    <link rel="stylesheet" type="text/css" href="css/tmr.css"/>
</head>
<body>
<div class="top">
    <form method="post" action="/tmr/createGame.mvc">
        <input type="submit" value="create game" />
    </form>
    <div id="topContent" class="topContent">
        <c:forEach var="game" items="${games}">
            <form method="post" action="/tmr/joinGame.mvc">
                ${game.name}, players: ${game.getPlayers().size()}
                <input type="hidden" value="${game.getId()}" name="gameId"/>
                <input type="submit" value="join game"/>
            </form>
        </c:forEach>
    </div>
</div>
<div class="bottom">
    <div class="bottomContent">
    </div>
</div>
</body>
</html>
