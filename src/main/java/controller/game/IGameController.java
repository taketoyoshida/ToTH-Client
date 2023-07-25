package controller.game;

import model.game.event.*;

public interface IGameController {
    void onGameStart(GameStartEvent event);

    void onPlayerAction(PlayerActionEvent event);

    void onEnemyAction(EnemyActionEvent event);

    void onPlayerDead(PlayerDeadEvent event);

    void onEnemyDead(EnemyDeadEvent event);

    void onEnemySwarm(EnemySwarmEvent event);

    void onChangeTurn(ChangeTurnEvent event);

    void onGameEnd(GameEndEvent event);
}
