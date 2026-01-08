package models;

public class LeaderBoardEntry {
    private final String userId;
    private final int score;
    private final int rank;
    public LeaderBoardEntry(String userId, int score, int rank){
        this.userId = userId;
        this.score = score;
        this.rank = rank;
    }
    public String toString() {
        return "{" +
                "userId='" + userId + '\'' +
                ", score=" + score +
                ", rank=" + rank +
                '}';
    }
}
