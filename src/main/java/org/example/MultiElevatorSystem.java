package org.example;

import java.util.*;

enum Direction {
    UP, DOWN, IDLE;
}

class Elevator {
    private int id;
    private int currentFloor;
    private Direction direction;
    private PriorityQueue<Integer> upQueue;
    private PriorityQueue<Integer> downQueue;

    public Elevator(int id) {
        this.id = id;
        this.currentFloor = 0;
        this.direction = Direction.IDLE;
        this.upQueue = new PriorityQueue<>();
        this.downQueue = new PriorityQueue<>(Collections.reverseOrder());
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getId() {
        return id;
    }

    public boolean isIdle() {
        return direction == Direction.IDLE;
    }

    public void requestFloor(int floor) {
        if (floor > currentFloor) {
            upQueue.add(floor);
            direction = Direction.UP;
        } else if (floor < currentFloor) {
            downQueue.add(floor);
            direction = Direction.DOWN;
        }
    }

    public void move() {
        if (direction == Direction.UP && !upQueue.isEmpty()) {
            currentFloor = upQueue.poll();
        } else if (direction == Direction.DOWN && !downQueue.isEmpty()) {
            currentFloor = downQueue.poll();
        }
        if (upQueue.isEmpty() && downQueue.isEmpty()) {
            direction = Direction.IDLE;
        }
    }

    @Override
    public String toString() {
        return "Elevator " + id + " at floor " + currentFloor + " moving " + direction;
    }
}

class ElevatorSystem {
    private List<Elevator> elevators;

    public ElevatorSystem(int numElevators) {
        elevators = new ArrayList<>();
        for (int i = 0; i < numElevators; i++) {
            elevators.add(new Elevator(i));
        }
    }

    public void requestElevator(int currentFloor, int destinationFloor) {
        Elevator bestElevator = findBestElevator(currentFloor);
      //  bestElevator.requestFloor(currentFloor);
        bestElevator.requestFloor(destinationFloor);

        System.out.println("Assigned Elevator " + bestElevator.getId() + " to pick up from floor " + currentFloor + " and go to floor " + destinationFloor);
    }

    private Elevator findBestElevator(int requestFloor) {
        Elevator best = null;
        int minDistance = Integer.MAX_VALUE;

        for (Elevator e : elevators) {
            int distance = Math.abs(e.getCurrentFloor() - requestFloor);
            if (e.isIdle() || distance < minDistance) {
                minDistance = distance;
                best = e;
            } else if (e.getDirection() == Direction.UP && requestFloor >= e.getCurrentFloor()) {
                best = e;
            } else if (e.getDirection() == Direction.DOWN && requestFloor <= e.getCurrentFloor()) {
                best = e;
            }
        }
        return best;
    }

    public void step() {
        for (Elevator e : elevators) {
            e.move();
        }
    }

    public void status() {
        for (Elevator e : elevators) {
            System.out.println(e);
        }
    }
}

public class MultiElevatorSystem {
    public static void main(String[] args) throws Exception {
        ElevatorSystem system = new ElevatorSystem(3);
        system.requestElevator(1, 5);
        system.requestElevator(2, 8);
        Thread.sleep(5000);
        system.requestElevator(1, 7);

        system.requestElevator(0, 7);
        Thread.sleep(10000);
        system.requestElevator(0, 10);
        system.requestElevator(2, 11);

        for (int i = 0; i < 5; i++) {
            system.step();
            system.status();
        }
    }
}

