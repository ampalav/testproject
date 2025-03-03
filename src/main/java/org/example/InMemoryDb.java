package org.example;
import java.util.*;

public class InMemoryDb implements InMemoryDbInterface {
    Map<String, String> databaseNode;
    Map<String, Integer> databaseCount;
    private Deque<Map<String, String>> transactions;
    private Deque<Map<String, Integer>> transactionsCount;

    public InMemoryDb() {
        databaseNode = new HashMap<>();
        databaseCount = new HashMap<>();
        transactions = new LinkedList<>();
        transactionsCount = new LinkedList<>();
    }

    @Override
    public String get(String key) {
        return databaseNode.get(key);
    }

    @Override
    public int getCount(String key) {
        return databaseCount.get(key);
    }

    @Override
    public void set(String key, String value) {
        if (!transactions.isEmpty()) {
            transactions.peek().put(key, value);
            transactionsCount.peek().put(key, databaseCount.getOrDefault(key, 0) + 1);
        } else {
            String oldValue = databaseNode.get(key);
            if (oldValue != null) {
                databaseCount.put(key, databaseCount.getOrDefault(key, 0) + 1);
            }
            databaseNode.put(key, value);
            databaseCount.put(key, databaseCount.getOrDefault(key, 0) + 1);
        }

    }

    @Override
    public void begin() {
        transactions.offer(new HashMap<>());
        transactionsCount.offer(new HashMap<>());
        System.out.println("New transactions are created");
    }

    @Override
    public void commit() {
        if (transactions.isEmpty()) {
            System.out.println("There are no transactions");
            return;
        }
        Map<String, String> currdbNode =  transactions.poll();
        Map<String, Integer> currdbCount = transactionsCount.poll();

        for (Map.Entry<String, String> entry : currdbNode.entrySet()) {
            String oldValue = databaseNode.get(entry.getKey());
            if (oldValue != null) {
                databaseCount.put(entry.getKey(), currdbCount.getOrDefault(entry.getKey(), 0) - 1);
            } else {
                databaseNode.put(entry.getKey(), entry.getValue());
            }
            databaseCount.put(entry.getKey(), currdbCount.getOrDefault(entry.getKey(), 0) + 1);
        }

        System.out.println("New transactions are committed");
    }

    @Override
    public void rollback() {
        if (transactions.isEmpty()) {
            System.out.println("No New transactions");
        } else {
           transactions.poll();
           transactionsCount.poll();
           System.out.println("New transactions are rolled back");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InMemoryDb inMemoryDb = new InMemoryDb();

        System.out.println("In-Memory Database CLI started. Type commands (EXIT to stop).");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            String[] commandArgs = input.split(" ");

            if (commandArgs.length == 0) continue;

            String command = commandArgs[0].toUpperCase();

            switch (command) {
                case "EXIT":
                    System.out.println("Goodbye!");
                    break;

                case "BEGIN":
                    inMemoryDb.begin();
                    break;

                case "GET":
                    if (commandArgs.length < 2) {
                        System.out.println("Invalid command");
                        break;
                    } else {
                        System.out.println(inMemoryDb.get(commandArgs[1]));
                        break;
                    }

                case "GETCOUNT":
                    if (commandArgs.length < 2) {
                        System.out.println("Invalid command");
                        break;
                    } else {
                        System.out.println(inMemoryDb.getCount(commandArgs[1]));
                        break;
                    }

                case "COMMIT":
                    inMemoryDb.commit();
                    System.out.println("Commit all new transactions");
                    break;

                case "SET":
                    if (commandArgs.length < 2) {
                        System.out.println("Invalid command to commit db");
                        break;
                    } else {
                        inMemoryDb.set(commandArgs[1], commandArgs[2]);
                        System.out.println("Set new value");
                        break;
                    }

                case "ROLLBACK":
                    inMemoryDb.rollback();
                    System.out.println("Rollback all new transactions");
                    break;

                default:
                    System.out.println("Unknown command. Valid commands: GET, SET, GETCOUNT_OF_VALUES, BEGIN, COMMIT, ROLLBACK, EXIT.");
                    break;
            }
        }

    }

}
