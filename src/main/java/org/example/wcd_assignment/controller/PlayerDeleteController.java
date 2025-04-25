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

@WebServlet("/player/delete")
public class PlayerDeleteController extends HttpServlet {
    PlayerRepository playerRepository = new MySQLPlayerRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long playerId = Long.parseLong(request.getParameter("id"));

        if (playerRepository.deleteById(playerId)) {
            response.sendRedirect("player/list");
        } else {
            response.sendRedirect("player/list");
        }
    }
}

