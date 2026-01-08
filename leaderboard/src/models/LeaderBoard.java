package models;

import java.util.*;


public class LeaderBoard {
    String leaderBoardId;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    String gameId;
    long startTime;
    long endTime;
    Map<String,Integer> userScores;
    TreeMap<Integer, Set<String>> scoreBoard ;

    public LeaderBoard(String gameId, long startTime, long endTime){
        this.leaderBoardId = UUID.randomUUID().toString();
        this.gameId = gameId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.userScores = new HashMap<>();
        this.scoreBoard = new TreeMap<>(Collections.reverseOrder());
    }


    public String getLeaderBoardId() {
        return leaderBoardId;
    }

    public void setLeaderBoardId(String leaderBoardId) {
        this.leaderBoardId = leaderBoardId;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public Map<String, Integer> getUserScores() {
        return userScores;
    }

    public void setUserScores(Map<String, Integer> userScores) {
        this.userScores = userScores;
    }

    public TreeMap<Integer, Set<String>> getScoreBoard() {
        return scoreBoard;
    }

    public void setScoreBoard(TreeMap<Integer, Set<String>> scoreBoard) {
        this.scoreBoard = scoreBoard;
    }
}
