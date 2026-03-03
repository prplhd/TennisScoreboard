package ru.prplhd.tennisscoreboard.web.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.prplhd.tennisscoreboard.dto.FinishedMatchesPageDto;
import ru.prplhd.tennisscoreboard.service.FinishedMatchesPersistenceService;
import ru.prplhd.tennisscoreboard.web.ServletContextKeys;

import java.io.IOException;

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
        String pageParameterValue = req.getParameter("page");

        FinishedMatchesPageDto finishedMatchesPageDto = finishedMatchesPersistenceService.getMatchesPage(pageParameterValue);

        req.setAttribute("finishedMatchesPageDto", finishedMatchesPageDto);
        req.getRequestDispatcher("/WEB-INF/jsp/matches.jsp").forward(req, resp);
    }
}
