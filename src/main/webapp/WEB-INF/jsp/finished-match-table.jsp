<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table class="table">
    <thead class="result">
    <tr>
        <th class="table-text">Player</th>
        <th class="table-text"></th>
        <th class="table-text">Sets Won</th>
        <th class="table-text"></th>
    </tr>
    </thead>
    <tbody>
    <tr class="player1">
        <td class="table-text">${requestScope.matchScoreboardDto.firstPlayer}</td>
        <td class="table-text"></td>
        <td class="table-text">${requestScope.matchScoreboardDto.firstPlayerSets}</td>
        <td class="table-text"></td>
        <td class="table-text">
            <c:if test="${requestScope.matchScoreboardDto.firstPlayer == requestScope.matchScoreboardDto.winner}">
                <img class="winner-cup" src="../../images/cup.png" alt="winner">
            </c:if>
        </td>
    </tr>
    <tr class="player2">
        <td class="table-text">${requestScope.matchScoreboardDto.secondPlayer}</td>
        <td class="table-text"></td>
        <td class="table-text">${requestScope.matchScoreboardDto.secondPlayerSets}</td>
        <td class="table-text"></td>
        <td class="table-text">
            <c:if test="${requestScope.matchScoreboardDto.secondPlayer == requestScope.matchScoreboardDto.winner}">
                <img class="winner-cup" src="../../images/cup.png" alt="winner">
            </c:if>
        </td>
    </tr>
    </tbody>
</table>
