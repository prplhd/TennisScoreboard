package ru.prplhd.tennisscoreboard.web.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.prplhd.tennisscoreboard.dto.match.MatchDto;
import ru.prplhd.tennisscoreboard.service.OngoingMatchService;
import ru.prplhd.tennisscoreboard.web.ServletContextKeys;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {

    private OngoingMatchService ongoingMatchService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ongoingMatchService = (OngoingMatchService) getServletContext().getAttribute(ServletContextKeys.ONGOING_MATCH_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID matchUUID = UUID.fromString(req.getParameter("uuid"));
        MatchDto matchDto = ongoingMatchService.getMatchScoreboard(matchUUID);

        req.setAttribute("matchDto", matchDto);
        req.setAttribute("matchUUID", matchUUID);
        req.getRequestDispatcher("/WEB-INF/jsp/match-score.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID matchUUID = UUID.fromString(req.getParameter("uuid"));
        Integer scorerId = Integer.valueOf(req.getParameter("scorerId"));

        MatchDto matchDto = ongoingMatchService.applyPoint(matchUUID, scorerId);

        if (matchDto.winner() == null) {
            resp.sendRedirect("/match-score?uuid=" + matchUUID, HttpServletResponse.SC_SEE_OTHER);
        } else {
            req.setAttribute("matchDto", matchDto);
            req.setAttribute("matchUUID", matchUUID);
            req.getRequestDispatcher("/WEB-INF/jsp/match-score.jsp").forward(req, resp);
        }
    }
}
