package org.example.wcd_assignment.controller;

import jakarta.servlet.annotation.WebServlet;
import org.example.wcd_assignment.entity.Indexer;
import org.example.wcd_assignment.entity.Player;
import org.example.wcd_assignment.repository.IndexerRepository;
import org.example.wcd_assignment.repository.MySQLIndexerRepository;
import org.example.wcd_assignment.repository.MySQLPlayerRepository;
import org.example.wcd_assignment.repository.PlayerRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/player/create")
public class PlayerCreateController extends HttpServlet {
    PlayerRepository playerRepository = new MySQLPlayerRepository();
    IndexerRepository indexerRepository = new MySQLIndexerRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("player_create.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String fullName = request.getParameter("full_name");
        String age = request.getParameter("age");
        String indexIdStr = request.getParameter("index_id");

        // Validate input data
        if (name == null || name.trim().isEmpty() || fullName == null || fullName.trim().isEmpty()) {
            request.setAttribute("error", "Name and Full Name are required.");
            request.getRequestDispatcher("player_create.jsp").forward(request, response);
            return;
        }

        if (age == null || !age.matches("\\d+")) {
            request.setAttribute("error", "Age must be a valid number.");
            request.getRequestDispatcher("player_create.jsp").forward(request, response);
            return;
        }

        if (indexIdStr == null || !indexIdStr.matches("\\d+")) {
            request.setAttribute("error", "Index ID must be a valid number.");
            request.getRequestDispatcher("player_create.jsp").forward(request, response);
            return;
        }

        int indexId = Integer.parseInt(indexIdStr);

        // Check if the index exists
        Indexer indexer = indexerRepository.findById(indexId);
        if (indexer == null) {
            request.setAttribute("error", "Invalid Index ID.");
            request.getRequestDispatcher("player_create.jsp").forward(request, response);
            return;
        }

        Player player = new Player(playerId, name, fullName, age, indexId);
        Player savedPlayer = playerRepository.save(player);

        if (savedPlayer != null) {
            response.sendRedirect("player/list");
        } else {
            request.setAttribute("error", "Failed to save player.");
            request.getRequestDispatcher("player_create.jsp").forward(request, response);
        }
    }
}