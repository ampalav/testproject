package org.example;

import java.util.concurrent.atomic.AtomicLong;

public class LeakyBucket {
    private final long bucketsize; // Max requests allowed
    private final long leakRatePerSecond; // Requests leaked per second
    private static final long windowUnit = 1000; // 1 second in milliseconds
    private final AtomicLong water = new AtomicLong(0); // Current water level
    private volatile long lastLeakTimeStamp;

    public LeakyBucket(long threshold, long leakRatePerSecond) {
        this.bucketsize = threshold;
        this.leakRatePerSecond = leakRatePerSecond;
        this.lastLeakTimeStamp = System.currentTimeMillis();
    }

    public boolean tryAcquire() {
        long now = System.currentTimeMillis();

        // Leak requests based on time elapsed
        long elapsedTime = now - lastLeakTimeStamp;
        long leakAmount = (elapsedTime / windowUnit) * leakRatePerSecond;

        if (leakAmount > 0) {
            water.addAndGet(-leakAmount); // Leak the calculated amount
            lastLeakTimeStamp = now; // Update last leak time
        }

        // Ensure water level does not go negative
        if (water.get() < 0) {
            water.set(0);
        }

        // Check if request can be allowed
        if (water.get() < bucketsize) {
            water.incrementAndGet(); // ✅ Correctly increment by 1
            return true;
        }

        return false; // Bucket full, reject request
    }

    public static void main(String[] args) {
        LeakyBucket rateLimiter = new LeakyBucket(5, 2); // Allow 5 max, leak 2/sec

        for (int i = 1; i <= 14; i++) {
            boolean allowed = rateLimiter.tryAcquire();
            System.out.println("Request " + i + ": " + (allowed ? "✅ Allowed" : "❌ Rejected"));

            try {
                Thread.sleep(200); // Simulating requests at different times
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
