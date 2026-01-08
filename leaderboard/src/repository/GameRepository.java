package repository;

import models.Game;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class GameRepository {
    Map<String, Game> games = new HashMap<>();

    public Collection<String> findAllGameIds(){
        return games.keySet();
    }

    public Game findById(String gameId){
        return games.get(gameId);
    }
    public void save(Game game){
        games.put(game.getGameId(), game);
    }

}
