package repository;

import models.LeaderBoard;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LeaderBoardRepository {
    Map<String, LeaderBoard> leaderBoards = new HashMap<>();
    public void save(LeaderBoard leaderBoard){
        String s = leaderBoard.getLeaderBoardId()+"-"+leaderBoard.getGameId();
        leaderBoards.put(s,leaderBoard);
    }
    public LeaderBoard findById(String s){
        return leaderBoards.get(s);
    }
    public Collection<LeaderBoard> findAll(){
        return leaderBoards.values();
    }

    public void deleteById(String s){
        leaderBoards.remove(s);
    }
}
