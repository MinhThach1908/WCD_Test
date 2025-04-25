package org.example.wcd_assignment.repository;

import org.example.wcd_assignment.entity.Indexer;

import java.util.List;

public interface IndexerRepository {
    Indexer findById(int indexId);
    List<Indexer> findAll();
    Indexer save(Indexer indexer);
    Indexer update(int indexId, Indexer indexer);
    boolean deleteById(int indexId);
}
