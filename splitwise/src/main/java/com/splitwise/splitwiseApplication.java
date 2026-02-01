package com.splitwise;

import com.splitwise.dao.Group;
import com.splitwise.dao.User;
import com.splitwise.enums.SplitType;
import com.splitwise.repository.ExpenseRepository;
import com.splitwise.repository.GroupRepository;
import com.splitwise.repository.UserRepository;
import com.splitwise.service.BalanceService;
import com.splitwise.service.ExpenseService;
import com.splitwise.service.GroupService;
import com.splitwise.service.UserService;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.ArrayList;
import java.util.List;

public class splitwiseApplication extends Application<splitwiseConfiguration> {

    public static void main(final String[] args) throws Exception {
        new splitwiseApplication().run(args);
    }


    @Override
    public void run(final splitwiseConfiguration configuration,
                    final Environment environment) throws Exception {

        UserRepository userRepository = new UserRepository();
        GroupRepository groupRepository = new GroupRepository();
        ExpenseRepository expenseRepository = new ExpenseRepository();
        // ====== SERVICES ======
        UserService userService = new UserService(userRepository);
        GroupService groupService = new GroupService(groupRepository, userService);
        BalanceService balanceService = new BalanceService(groupService);
        ExpenseService expenseService = new ExpenseService(balanceService, groupService, expenseRepository);

        // ====== USERS ======
        User u1 = userService.createUser("Alice");
        User u2 = userService.createUser( "Bob");
        User u3 = userService.createUser("Charlie");

        // ====== GROUP ======
        Group trip = groupService.createGroup("GoaTrip", List.of(u1.getUserName(), u2.getUserName(), u3.getUserName()), u1.getUserName());

        System.out.println("Group created: " + trip.getGroupName());

        // ====== CONCURRENT WRITERS ======
        Runnable addExpenseTask = () -> {
            expenseService.addExpense(
                    "Lunch",
                    trip.getGroupName(),
                    u1.getUserName(),
                    300,
                    List.of(u1.getUserName(), u2.getUserName(), u3.getUserName()),
                    SplitType.EQUAL,
                    null
            );
        };

        Thread writer1 = new Thread(addExpenseTask);
        Thread writer2 = new Thread(addExpenseTask);

        // ====== CONCURRENT READERS ======
        Runnable readBalancesTask = () -> {
            balanceService.showGroupBalances(trip.getGroupName());
        };

        Thread reader1 = new Thread(readBalancesTask);
        Thread reader2 = new Thread(readBalancesTask);

        // ====== START THREADS ======
        writer1.start();
        writer2.start();
        reader1.start();
        reader2.start();

        // ====== WAIT ======
        writer1.join();
        writer2.join();
        reader1.join();
        reader2.join();

        // ====== FINAL STATE ======
        System.out.println("\n==== FINAL GROUP BALANCES ====");
        balanceService.showGroupBalances(trip.getGroupName());

        System.out.println("\n==== USER VIEW ====");
        balanceService.showUserBalance(trip.getGroupName(), u2.getUserName());

    }

}
