<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<table class="table">
    <thead class="result">
    <tr>
        <th class="table-text">Player</th>
        <th class="table-text">Sets</th>
        <th class="table-text">Games</th>
        <th class="table-text">Points</th>
    </tr>
    </thead>
    <tbody>
    <tr class="player1">
        <td class="table-text">${requestScope.matchScoreboardDto.firstPlayer}</td>
        <td class="table-text">${requestScope.matchScoreboardDto.firstPlayerSets}</td>
        <td class="table-text">${requestScope.matchScoreboardDto.firstPlayerGames}</td>
        <td class="table-text">${requestScope.matchScoreboardDto.firstPlayerPoints}</td>
        <td class="table-text">
            <form method="post" action="${pageContext.request.contextPath}/match-score?uuid=${requestScope.matchUUID}">
                <button class="score-btn" name="scorerId" value="${requestScope.matchPlayerIdsDto.firstPlayerId}">Score</button>
            </form>
        </td>
    </tr>
    <tr class="player2">
        <td class="table-text">${requestScope.matchScoreboardDto.secondPlayer}</td>
        <td class="table-text">${requestScope.matchScoreboardDto.secondPlayerSets}</td>
        <td class="table-text">${requestScope.matchScoreboardDto.secondPlayerGames}</td>
        <td class="table-text">${requestScope.matchScoreboardDto.secondPlayerPoints}</td>
        <td class="table-text">
            <form method="post" action="${pageContext.request.contextPath}/match-score?uuid=${requestScope.matchUUID}">
                <button class="score-btn" name="scorerId" value="${requestScope.matchPlayerIdsDto.secondPlayerId}">Score</button>
            </form>
        </td>
    </tr>
    </tbody>
</table>
