package controller.game;

//import controller.networking.GameGateway;

import gateway.game.GameGateway;
import model.*;

import java.util.*;
import java.io.*;

import static model.GameModel.*;
import static model.Player.Teban.*;


import model.game.event.*;
import model.util.User;


public class GameController1 implements IGameController {
    private final Random random = new Random();
    private List<Enemy> enemyList;
    public Player me, opponent;
    GameGateway.IGameGateway gateway;

    public GameController1() throws Exception {
        gateway = new GameGateway.GameGatewayImpl(this);
    }

    @Override
    public void onGameStart(GameStartEvent event) {

    }

    @Override
    public void onPlayerAction(PlayerActionEvent event) {

    }

    @Override
    public void onEnemyAction(EnemyActionEvent event) {

    }

    @Override
    public void onPlayerDead(PlayerDeadEvent event) {

    }

    @Override
    public void onEnemyDead(EnemyDeadEvent event) {

    }

    @Override
    public void onEnemySwarm(EnemySwarmEvent event) {

    }

    @Override
    public void onChangeTurn(ChangeTurnEvent event) {

    }

    @Override
    public void onGameEnd(GameEndEvent event) {

    }
}