package ru.prplhd.tennisscoreboard.web.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.prplhd.tennisscoreboard.dto.request.NewMatchRequestDto;
import ru.prplhd.tennisscoreboard.service.OngoingMatchService;
import ru.prplhd.tennisscoreboard.web.ServletContextKeys;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {

    private OngoingMatchService ongoingMatchService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ongoingMatchService = (OngoingMatchService) getServletContext().getAttribute(ServletContextKeys.ONGOING_MATCH_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/new-match.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstPlayerName = req.getParameter("firstPlayerName");
        String secondPlayerName = req.getParameter("secondPlayerName");

        NewMatchRequestDto newMatchRequestDto = new NewMatchRequestDto(firstPlayerName, secondPlayerName);

        UUID matchUUID = ongoingMatchService.createNewMatch(newMatchRequestDto);

        resp.sendRedirect("/match-score?matchUUID=" + matchUUID, HttpServletResponse.SC_SEE_OTHER);
    }
}
