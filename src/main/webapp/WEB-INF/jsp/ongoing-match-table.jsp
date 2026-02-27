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
        <td class="table-text">${requestScope.matchDto.firstPlayer.name}</td>
        <td class="table-text">${requestScope.matchDto.scoreDto.firstPlayerSets}</td>
        <td class="table-text">${requestScope.matchDto.scoreDto.firstPlayerGames}</td>
        <td class="table-text">${requestScope.matchDto.scoreDto.firstPlayerPoints}</td>
        <td class="table-text">
            <form method="post" action="${pageContext.request.contextPath}/match-score?uuid=${requestScope.matchUUID}">
                <button class="score-btn" name="scorerId" value="${requestScope.matchDto.firstPlayer.id}">Score</button>
            </form>
        </td>
    </tr>
    <tr class="player2">
        <td class="table-text">${requestScope.matchDto.secondPlayer.name}</td>
        <td class="table-text">${requestScope.matchDto.scoreDto.secondPlayerSets}</td>
        <td class="table-text">${requestScope.matchDto.scoreDto.secondPlayerGames}</td>
        <td class="table-text">${requestScope.matchDto.scoreDto.secondPlayerPoints}</td>
        <td class="table-text">
            <form method="post" action="${pageContext.request.contextPath}/match-score?uuid=${requestScope.matchUUID}">
                <button class="score-btn" name="scorerId" value="${requestScope.matchDto.secondPlayer.id}">Score</button>
            </form>
        </td>
    </tr>
    </tbody>
</table>
