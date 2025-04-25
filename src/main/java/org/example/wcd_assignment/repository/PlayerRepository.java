package org.example.wcd_assignment.repository;

import org.example.wcd_assignment.entity.Player;

import java.sql.SQLException;
import java.util.List;

public interface PlayerRepository {
    Player findById(long id);
    List<Player> findAll(int limit, int page);
    List<Player> findAll(int limit, int page, String keyword, String sortColumn, String sortDirection);
    Player save(Player player);
    Player update(long id, Player player);
    boolean deleteById(long id);
    int getTotalRecords(String keyword);
}
