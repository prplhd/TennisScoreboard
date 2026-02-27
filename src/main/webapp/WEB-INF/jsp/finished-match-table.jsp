<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table class="table">
    <thead class="result">
    <tr>
        <th class="table-text">Player</th>
        <th class="table-text">First Set</th>
        <th class="table-text">Second Set</th>
        <th class="table-text">Third Set</th>
    </tr>
    </thead>
    <tbody>
    <tr class="player1">
        <td class="table-text">${requestScope.matchDto.firstPlayer.name}</td>
        <td class="table-text">${requestScope.matchDto.scoreDto.finishedSetsScore[0].firstPlayerGames}</td>
        <td class="table-text">${requestScope.matchDto.scoreDto.finishedSetsScore[1].firstPlayerGames}</td>
        <td class="table-text">
            <c:choose>
                <c:when test="${requestScope.matchDto.scoreDto.finishedSetsScore[2] != null}">
                    ${requestScope.matchDto.scoreDto.finishedSetsScore[2].firstPlayerGames}
                </c:when>
                <c:otherwise>
                    —
                </c:otherwise>
            </c:choose>
        </td>
        <td class="table-text">
            <c:if test="${requestScope.matchDto.firstPlayer == requestScope.matchDto.winner}">
                <img class="winner-cup"
                     src="../../images/cup.png" alt="winner"
                     style="height:1.5em; width:auto; display:inline-block; vertical-align:middle;">
            </c:if>
        </td>
    </tr>
    <tr class="player2">
        <td class="table-text">${requestScope.matchDto.secondPlayer.name}</td>
        <td class="table-text">${requestScope.matchDto.scoreDto.finishedSetsScore[0].secondPlayerGames}</td>
        <td class="table-text">${requestScope.matchDto.scoreDto.finishedSetsScore[1].secondPlayerGames}</td>
        <td class="table-text">
            <c:choose>
                <c:when test="${requestScope.matchDto.scoreDto.finishedSetsScore[2] != null}">
                    ${requestScope.matchDto.scoreDto.finishedSetsScore[2].secondPlayerGames}
                </c:when>
                <c:otherwise>
                    —
                </c:otherwise>
            </c:choose>
        </td>
        <td class="table-text">
            <c:if test="${requestScope.matchDto.secondPlayer == requestScope.matchDto.winner}">
                <img class="winner-cup"
                     src="../../images/cup.png" alt="winner"
                     style="height:1.5em; width:auto; display:inline-block; vertical-align:middle;">
            </c:if>
        </td>
    </tr>
    </tbody>
</table>
