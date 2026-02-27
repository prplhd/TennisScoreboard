package ru.prplhd.tennisscoreboard.web.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.prplhd.tennisscoreboard.dto.match.FinishedMatchesDto;
import ru.prplhd.tennisscoreboard.service.FinishedMatchesPersistenceService;
import ru.prplhd.tennisscoreboard.web.ServletContextKeys;

import java.io.IOException;
import java.util.List;

@WebServlet("/matches")
public class FinishedMatchesServlet extends HttpServlet {

    private FinishedMatchesPersistenceService finishedMatchesPersistenceService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        finishedMatchesPersistenceService = (FinishedMatchesPersistenceService) getServletContext().getAttribute(ServletContextKeys.FINISHED_MATCHES_PERSISTENCE_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<FinishedMatchesDto> finishedMatchesDtos = finishedMatchesPersistenceService.findAllMatches();

        req.setAttribute("finishedMatchesDtos", finishedMatchesDtos);
        req.getRequestDispatcher("/WEB-INF/jsp/matches.jsp").forward(req, resp);
    }
}
