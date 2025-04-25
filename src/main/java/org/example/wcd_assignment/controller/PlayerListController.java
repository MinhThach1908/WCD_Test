package org.example.wcd_assignment.controller;

import jakarta.servlet.annotation.WebServlet;
import org.example.wcd_assignment.entity.Player;
import org.example.wcd_assignment.repository.MySQLPlayerRepository;
import org.example.wcd_assignment.repository.PlayerRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/player/list")
public class PlayerListController extends HttpServlet {
    PlayerRepository playerRepository = new MySQLPlayerRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = 10;  // Number of players per page
        List<Player> players = playerRepository.findAll(limit, page);
        int totalRecords = playerRepository.getTotalRecords("");

        request.setAttribute("players", players);
        request.setAttribute("totalRecords", totalRecords);
        request.setAttribute("currentPage", page);

        RequestDispatcher dispatcher = request.getRequestDispatcher("player_list.jsp");
        dispatcher.forward(request, response);
    }
}

