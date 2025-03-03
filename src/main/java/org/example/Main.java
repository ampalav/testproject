package org.example;

import java.util.*;
import java.util.concurrent.*;

public class Main {

}

// Elevator directions
//enum Direction {
//    UP, DOWN, IDLE
//}
//
//// Represents a request from a floor
//class Request {
//    int floor;
//    Direction direction;
//
//    public Request(int floor, Direction direction) {
//        this.floor = floor;
//        this.direction = direction;
//    }
//}
//
//// Elevator class
//class Elevator {
//    private int id;
//    private int currentFloor;
//    private Direction direction;
//    private Queue<Request> requests;
//
//    public Elevator(int id) {
//        this.id = id;
//        this.currentFloor = 1;
//        this.direction = Direction.IDLE;
//        this.requests = new LinkedList<>();
//    }
//
//    public int getCurrentFloor() { return currentFloor; }
//    public Direction getDirection() { return direction; }
//    public int getId() { return id; }
//
//    public void addRequest(Request request) {
//        requests.offer(request);
//        updateDirection();
//    }
//
//    private void updateDirection() {
//        if (requests.isEmpty()) {
//            direction = Direction.IDLE;
//        } else if (requests.peek().floor > currentFloor) {
//            direction = Direction.UP;
//        } else {
//            direction = Direction.DOWN;
//        }
//    }
//
//    public void move() {
//        while (!requests.isEmpty()) {
//            Request request = requests.peek();
//
//            if (request.floor > currentFloor) {
//                direction = Direction.UP;
//                currentFloor++;
//            } else if (request.floor < currentFloor) {
//                direction = Direction.DOWN;
//                currentFloor--;
//            }
//
//            System.out.println("Elevator " + id + " moved to floor " + currentFloor);
//
//            if (request.floor == currentFloor) {
//                requests.poll();
//                System.out.println("Elevator " + id + " stopped at floor " + currentFloor);
//            }
//            updateDirection();
//        }
//        direction = Direction.IDLE;
//    }
//}
//
//// Elevator System
//class ElevatorSystem {
//    private List<Elevator> elevators;
//    private ExecutorService executor;
//
//    public ElevatorSystem(int numElevators) {
//        elevators = new ArrayList<>();
//        for (int i = 0; i < numElevators; i++) {
//            elevators.add(new Elevator(i + 1));
//        }
//        executor = Executors.newFixedThreadPool(numElevators);
//        scheduleElevatorMovements();
//    }
//
//    private void scheduleElevatorMovements() {
//        for (Elevator elevator : elevators) {
//            executor.submit(() -> {
//                while (true) {
//                    elevator.move();
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        Thread.currentThread().interrupt();
//                    }
//                }
//            });
//        }
//    }
//
//    public void requestElevator(int floor, Direction direction) {
//        Elevator bestElevator = findBestElevator(floor, direction);
//        bestElevator.addRequest(new Request(floor, direction));
//        System.out.println("Request for floor " + floor + " assigned to Elevator " + bestElevator.getId());
//    }
//
//    private Elevator findBestElevator(int floor, Direction direction) {
//        return elevators.stream()
//                .min(Comparator.comparingInt(e -> Math.abs(e.getCurrentFloor() - floor)))
//                .orElse(elevators.get(0));
//    }
//}
//
//// Main Class to test the Elevator System
//public class Main {
//    public static void main(String[] args) {
//        ElevatorSystem system = new ElevatorSystem(4);
//
//        // Simulating requests
//        system.requestElevator(10, Direction.UP);
//        system.requestElevator(25, Direction.DOWN);
//        system.requestElevator(40, Direction.UP);
//        system.requestElevator(5, Direction.DOWN);
//    }

