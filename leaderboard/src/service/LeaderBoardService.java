package service;

import models.Game;
import models.LeaderBoard;
import models.LeaderBoardEntry;
import repository.GameRepository;
import repository.LeaderBoardRepository;

import java.util.*;

public class LeaderBoardService {
    private   LeaderBoardRepository leaderBoardRepository ;
    private GameRepository gameRepository;
    public  LeaderBoardService(){
        this.leaderBoardRepository = new LeaderBoardRepository();
        this.gameRepository = new GameRepository();
    }
    public String createLeaderboard(String gameId, long startTime, long endTime){
        LeaderBoard leaderBoard = new LeaderBoard(gameId,startTime,endTime);

        leaderBoardRepository.save(leaderBoard);
        Game game = gameRepository.findById(gameId);
        if(game==null){
            game = new Game(gameId);
            gameRepository.save(game);
        }
        String fullKey = leaderBoard.getLeaderBoardId()+"-"+gameId;
        game.getLeaderBoardIds().add(fullKey);
        return leaderBoard.getLeaderBoardId();
    }

    public  Collection<LeaderBoardEntry> getLeaderboard(String fullKey){
        LeaderBoard lb = leaderBoardRepository.findById(fullKey);
        if(lb == null) return List.of();

        List<LeaderBoardEntry> results = new ArrayList<>();
        int rank = 1;

        for(int score : lb.getScoreBoard().keySet()){
            for(String uid : lb.getScoreBoard().get(score)){
                results.add(new LeaderBoardEntry(uid, score, rank++));
            }
        }
        return results;
    }


    public  List<String> listPlayersNext(String gameId, String leaderBoardId,String userId,int nPlayers){
        LeaderBoard leaderBoard = leaderBoardRepository.findById(leaderBoardId+"-"+gameId);
        Integer userScoreObj = leaderBoard.getUserScores().get(userId);
        if (userScoreObj == null) return List.of(); // or throw
        int UserScore = userScoreObj;
        List<String> result = new ArrayList<>();
        for(int score :leaderBoard.getScoreBoard().keySet()){
            if(result.size()==nPlayers) break;
            if(score>UserScore) continue;
            boolean gotiT = false;
            for(String s:leaderBoard.getScoreBoard().get(score)){
                if(result.size()==nPlayers||userId.equals(s)) break;
                if(score==UserScore){
                    if(gotiT){
                        result.add(s);
                    }
                    else{
                        if(userId.equals(s)) gotiT=true;
                    }
                }
                else result.add(s);
            }
        }
        return result;
    }

    public  List<String> listPlayersPrev(String gameId, String leaderBoardId,String userId,int nPlayers){
        LeaderBoard leaderBoard = leaderBoardRepository.findById(leaderBoardId+"-"+gameId);
        Integer userScoreObj = leaderBoard.getUserScores().get(userId);
        if (userScoreObj == null) return List.of(); // or throw
        int UserScore = userScoreObj;
        List<String> result = new ArrayList<>();
        for(int score :leaderBoard.getScoreBoard().keySet()){
            if(result.size()==nPlayers) break;
            if(score<UserScore) break;
            boolean gotiT = false;
            for(String s:leaderBoard.getScoreBoard().get(score)){
                if(result.size()==nPlayers||userId.equals(s)) break;
                if(score==UserScore){
                    if(!gotiT){
                        result.add(s);
                    }
                    else{
                        if(userId.equals(s)) break;
                    }
                }
                else result.add(s);
            }
        }
        return result;
    }
    public Collection<String> getSupportedGames(){
        return gameRepository.findAllGameIds();
    }
    public  String createGame(Game game){

        gameRepository.save(game);

        return game.getGameId();
    }

    public synchronized void submitScore(String gameID, String userID, int score){
        Game game = gameRepository.findById(gameID);

        for(String fullKey : game.getLeaderBoardIds()){
            LeaderBoard lb = leaderBoardRepository.findById(fullKey);
            if(lb == null) continue;

            Map<String,Integer> userScores = lb.getUserScores();
            TreeMap<Integer,Set<String>> scoreBoard = lb.getScoreBoard();

            // Remove previous score if exists
            if(!userScores.containsKey(userID)){
                userScores.put(userID, score);
                scoreBoard.computeIfAbsent(score, k -> new HashSet<>()).add(userID);
                leaderBoardRepository.save(lb);
                continue;

            }

            // Insert new score
            int oldScore = userScores.get(userID);
            if(score <= oldScore) continue;
            scoreBoard.get(oldScore).remove(userID);
            if(scoreBoard.get(oldScore).isEmpty()){
                scoreBoard.remove(oldScore);
            }
            userScores.put(userID, score);
            scoreBoard.computeIfAbsent(score, k -> new HashSet<>()).add(userID);
            // Save back (optional — map is same object)
            leaderBoardRepository.save(lb);
        }
    }
}
