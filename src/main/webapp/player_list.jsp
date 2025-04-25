<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Player List</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.1.3/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2 class="text-center">Player List</h2>
    <a href="addPlayer.jsp" class="btn btn-primary mb-3">Add New Player</a>

    <!-- Display players in a table -->
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Full Name</th>
            <th>Age</th>
            <th>Index ID</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <!-- Loop through the players and display them -->
        <c:forEach var="player" items="${players}">
            <tr>
                <td>${player.playerId}</td>
                <td>${player.name}</td>
                <td>${player.fullName}</td>
                <td>${player.age}</td>
                <td>${player.indexId}</td>
                <td>
                    <!-- Edit and Delete Actions -->
                    <a href="editPlayer.jsp?id=${player.playerId}" class="btn btn-warning btn-sm">Edit</a>
                    <a href="deletePlayer?id=${player.playerId}" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete this player?')">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
</body>
</html>
