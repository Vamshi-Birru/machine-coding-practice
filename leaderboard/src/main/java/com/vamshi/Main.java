import models.Game;
import service.LeaderBoardService;

public class Main {
    public static void main(String[] args) {
        LeaderBoardService service = new LeaderBoardService();

//        System.out.println("=== Create Game ===");
//        Game g1 = new Game("g1");
//        service.createGame(g1);
//
//        System.out.println("=== Create Leaderboard ===");
//        String lb1 = service.createLeaderboard("g1", 0, 999999);
//        System.out.println("Leaderboard 1 id: " + lb1);
//
//        System.out.println("\n=== Submit Scores ===");
//        service.submitScore("g1", "u1", 100);
//        service.submitScore("g1", "u2", 150);
//        service.submitScore("g1", "u3", 250);
//        service.submitScore("g1", "u1", 200); // update u1
//
//        System.out.println("\n=== Full Leaderboard ===");
//        System.out.println(service.getLeaderboard(lb1 + "-g1"));
//
//        System.out.println("\n=== listPlayersPrev(u1, 2) ===");
//        System.out.println(service.listPlayersPrev("g1", lb1, "u1", 2));
//
//        System.out.println("\n=== listPlayersNext(u1, 2) ===");
//        System.out.println(service.listPlayersNext("g1", lb1, "u1", 2));
//
//        System.out.println("\n=== Submit Lower Score (Ignored) ===");
//        service.submitScore("g1", "u1", 20);
//        System.out.println(service.getLeaderboard(lb1 + "-g1"));
//
//        System.out.println("\n=== Add New Leaderboard & Validate Multi-board Submit ===");
//        String lb2 = service.createLeaderboard("g1", 0, 999999);
//        service.submitScore("g1", "u4", 300);
//
//        System.out.println("LB1:");
//        System.out.println(service.getLeaderboard(lb1 + "-g1"));
//        System.out.println("LB2:");
//        System.out.println(service.getLeaderboard(lb2 + "-g1"));


        System.out.println("\n=== Concurrency Test ===");

// Create fresh leaderboard
        String lbC = service.createLeaderboard("g1", 0, 999999);

// Task that keeps increasing score
        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                service.submitScore("g1", "uX", i);
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        Thread t3 = new Thread(task);

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(service.getLeaderboard(lbC + "-g1"));

    }
}
