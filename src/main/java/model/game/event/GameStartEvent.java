package model.game.event;

import model.game.utils.*;

import java.util.List;

public class GameStartEvent {
    public PlayerStatus you;
    public PlayerStatus opponent;
    public List<EnemyStatus> enemies;
}
