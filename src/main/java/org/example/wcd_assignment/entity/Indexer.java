package org.example.wcd_assignment.entity;

public class Indexer {
    private int indexId;
    private String name;
    private float valueMin;
    private float valueMax;

    // Constructors
    public Indexer() {}

    public Indexer(int indexId, String name, float valueMin, float valueMax) {
        this.indexId = indexId;
        this.name = name;
        this.valueMin = valueMin;
        this.valueMax = valueMax;
    }

    // Getters and Setters
    public int getIndexId() {
        return indexId;
    }

    public void setIndexId(int indexId) {
        this.indexId = indexId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValueMin() {
        return valueMin;
    }

    public void setValueMin(float valueMin) {
        this.valueMin = valueMin;
    }

    public float getValueMax() {
        return valueMax;
    }

    public void setValueMax(float valueMax) {
        this.valueMax = valueMax;
    }

    @Override
    public String toString() {
        return "Index [indexId=" + indexId + ", name=" + name + ", valueMin=" + valueMin + ", valueMax=" + valueMax + "]";
    }
}

