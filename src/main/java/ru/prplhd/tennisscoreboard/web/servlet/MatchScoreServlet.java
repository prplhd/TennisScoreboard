package ru.prplhd.tennisscoreboard.web.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.prplhd.tennisscoreboard.domain.snapshot.MatchSnapshot;
import ru.prplhd.tennisscoreboard.web.dto.match.MatchPlayerIdsDto;
import ru.prplhd.tennisscoreboard.web.dto.match.MatchScoreboardDto;
import ru.prplhd.tennisscoreboard.exception.NotFoundException;
import ru.prplhd.tennisscoreboard.mapper.MatchScoreboardMapper;
import ru.prplhd.tennisscoreboard.service.FinishedMatchesPersistenceService;
import ru.prplhd.tennisscoreboard.service.OngoingMatchService;
import ru.prplhd.tennisscoreboard.web.ServletContextKeys;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {

    private OngoingMatchService ongoingMatchService;
    private FinishedMatchesPersistenceService finishedMatchesPersistenceService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ongoingMatchService = (OngoingMatchService) getServletContext().getAttribute(ServletContextKeys.ONGOING_MATCH_SERVICE);
        finishedMatchesPersistenceService = (FinishedMatchesPersistenceService) getServletContext().getAttribute(ServletContextKeys.FINISHED_MATCHES_PERSISTENCE_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UUID matchUUID = UUID.fromString(req.getParameter("uuid"));
            MatchSnapshot matchSnapshot = ongoingMatchService.getMatchSnapshot(matchUUID);
            MatchScoreboardDto matchScoreboardDto = MatchScoreboardMapper.toDto(matchSnapshot);

            MatchPlayerIdsDto matchPlayerIdsDto = ongoingMatchService.getPlayerIds(matchScoreboardDto);

            req.setAttribute("matchScoreboardDto", matchScoreboardDto);
            req.setAttribute("matchPlayerIdsDto", matchPlayerIdsDto);
            req.setAttribute("matchUUID", matchUUID);
            req.getRequestDispatcher("/WEB-INF/jsp/match-score.jsp").forward(req, resp);

        } catch (NotFoundException | IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            req.setAttribute("message", "This match has finished or does not exist");
            req.getRequestDispatcher("/WEB-INF/jsp/404.jsp").forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID matchUUID = UUID.fromString(req.getParameter("uuid"));
        Integer scorerId = Integer.valueOf(req.getParameter("scorerId"));

        try {
            MatchSnapshot matchSnapshot = ongoingMatchService.applyPoint(matchUUID, scorerId);
            MatchScoreboardDto matchScoreboardDto = MatchScoreboardMapper.toDto(matchSnapshot);

            if (matchScoreboardDto.winner() == null) {
                resp.sendRedirect("/match-score?uuid=" + matchUUID, HttpServletResponse.SC_SEE_OTHER);

            } else {
                ongoingMatchService.deleteMatch(matchUUID);
                finishedMatchesPersistenceService.saveMatch(matchScoreboardDto);

                req.setAttribute("matchScoreboardDto", matchScoreboardDto);
                req.setAttribute("matchUUID", matchUUID);
                req.getRequestDispatcher("/WEB-INF/jsp/match-score.jsp").forward(req, resp);
            }

        } catch (NotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            req.setAttribute("message", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/jsp/404.jsp").forward(req, resp);
        }
    }
}
