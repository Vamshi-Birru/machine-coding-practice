package models;

import java.util.ArrayList;
import java.util.List;

public class Game {
    String gameId;

    public Game() {

    }

    public List<String> getLeaderBoardIds() {
        return leaderBoardIds;
    }

    public void setLeaderBoardIds(List<String> leaderBoardIds) {
        this.leaderBoardIds = leaderBoardIds;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    List<String> leaderBoardIds ;

    public  Game(String gameId){
        this.gameId = gameId;
        this.leaderBoardIds = new ArrayList<>();
    }

}
