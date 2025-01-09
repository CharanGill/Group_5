package qnaApp;

public class Timer implements Runnable {
    private int timeLimit; // Time limit in seconds
    private boolean timeUp = false;

    public Timer(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public void run() {
        try {
            Thread.sleep(timeLimit * 1000); // Wait for the specified time limit
            timeUp = true; // Mark the timer as expired
        } catch (InterruptedException e) {
            // Timer interrupted if user answers within time
        }
    }

    public boolean isTimeUp() {
        return timeUp;
    }
}

