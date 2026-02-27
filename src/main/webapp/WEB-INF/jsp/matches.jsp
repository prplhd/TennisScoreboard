<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tennis Scoreboard | Finished Matches</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}css/style.css">

    <script src="${pageContext.request.contextPath}js/app.js"></script>
</head>

<body>
<header class="header">
    <section class="nav-header">
        <div class="brand">
            <div class="nav-toggle">
                <img src="${pageContext.request.contextPath}images/menu.png" alt="Logo" class="logo">
            </div>
            <span class="logo-text">TennisScoreboard</span>
        </div>
        <div>
            <nav class="nav-links">
                <a class="nav-link" href="${pageContext.request.contextPath}/">Home</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/matches">Matches</a>
            </nav>
        </div>
    </section>
</header>
<main>
    <div class="container">
        <h1>Matches</h1>
        <div class="input-container">
            <input class="input-filter" placeholder="Filter by name" type="text" />
            <div>
                <a href="#">
                    <button class="btn-filter">Reset Filter</button>
                </a>
            </div>
        </div>

        <table class="table-matches">
            <tr>
                <th>Player One</th>
                <th>Player Two</th>
                <th>Winner</th>
            </tr>
            <c:choose>
                <c:when test="${requestScope.finishedMatchesDtos[0] == null}">
                    <td></td>
                    <td>No matches yet</td>
                    <td></td>
                </c:when>
                <c:otherwise>
                    <c:forEach var="finishedMatch" items="${requestScope.finishedMatchesDtos}">
                        <tr>
                            <td>${finishedMatch.firstPlayerName}</td>
                            <td>${finishedMatch.secondPlayerName}</td>
                            <td><span class="winner-name-td">${finishedMatch.winnerName}</span></td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>

        </table>

        <div class="pagination">
            <a class="prev" href="#"> < </a>
            <a class="num-page current" href="#">1</a>
            <a class="num-page" href="#">2</a>
            <a class="num-page" href="#">3</a>
            <a class="next" href="#"> > </a>
        </div>
    </div>
</main>
<footer>
    <div class="footer">
        <p>&copy; Tennis Scoreboard, project from <a href="https://zhukovsd.github.io/java-backend-learning-course/">zhukovsd/java-backend-learning-course</a>
            roadmap.</p>
    </div>
</footer>
</body>
</html>
