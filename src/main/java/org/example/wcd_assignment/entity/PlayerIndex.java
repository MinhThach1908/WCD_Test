package org.example.wcd_assignment.entity;

public class PlayerIndex {
    private int id;
    private int playerId;
    private int indexId;
    private float value;

    // Constructors
    public PlayerIndex() {}

    public PlayerIndex(int id, int playerId, int indexId, float value) {
        this.id = id;
        this.playerId = playerId;
        this.indexId = indexId;
        this.value = value;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getIndexId() {
        return indexId;
    }

    public void setIndexId(int indexId) {
        this.indexId = indexId;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "PlayerIndex [id=" + id + ", playerId=" + playerId + ", indexId=" + indexId + ", value=" + value + "]";
    }
}

