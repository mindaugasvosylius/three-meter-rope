<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Three meter rope</title>
    <script type="text/javascript" src="js/jquery-1.12.3.js"></script>
    <script type="text/javascript" src="js/jquery.color-2.1.2.min.js"></script>
    <script type="text/javascript" src="js/tmr.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            tmr.init('${gameId}');
        });
    </script>
    <link rel="stylesheet" type="text/css" href="css/tmr.css"/>
</head>
<body>
<div class="top">
    <div id="topContent" class="topContent">
        <button id="startGame">start</button>
        <div id="blackCardContainer" class="blackCardContainer">
            <%--<div class="blackCard">--%>
                <%--<div class="cardText">--%>
                    <%--${blackCard.text}--%>
                <%--</div>--%>
            <%--</div>--%>
        </div>

        <div id="playedWhiteCards" class="playedWhiteCards">
        </div>
    </div>
</div>
<div class="bottom">
    <div class="bottomContent">
        <div id="whiteCards" class="whiteCards">
            <%--<c:forEach var="card" items="${player.cards}">--%>
                <%--<div class="whiteCard">--%>
                    <%--<div class="cardText">--%>
                            <%--${card.text}--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</c:forEach>--%>
        </div>
    </div>
</div>
</body>
</html>
