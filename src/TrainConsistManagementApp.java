import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
public class TrainConsistManagementApp {

    // Inner Bogie class
    static class Bogie {
        String name;
        int capacity;

        Bogie(String name, int capacity) {
            this.name = name;
            this.capacity = capacity;
        }
    }

    public static void main(String[] args) {

        System.out.println("==================================================");
        System.out.println(" UC13 - Performance Comparison (Loops vs Streams) ");
        System.out.println("==================================================\n");

        // Create a large list of bogies for benchmarking
        List<Bogie> bogies = new ArrayList<>();
        String[] types = {"Sleeper", "AC Chair", "First Class", "Second Sitting", "Third AC", "General"};
        int[] capacities = {72, 60, 24, 90, 64, 100};

        for (int i = 0; i < 100000; i++) {
            int idx = i % types.length;
            bogies.add(new Bogie(types[idx] + "-" + i, capacities[idx]));
        }

        System.out.println("Dataset size: " + bogies.size() + " bogies\n");
        System.out.println("Task: Filter bogies with capacity > 60\n");

        // ── Method 1: Traditional For-Loop ──
        long startLoop = System.nanoTime();

        List<Bogie> filteredLoop = new ArrayList<>();
        for (Bogie b : bogies) {
            if (b.capacity > 60) {
                filteredLoop.add(b);
            }
        }

        long endLoop = System.nanoTime();
        long loopTime = endLoop - startLoop;

        // ── Method 2: Stream API ──
        long startStream = System.nanoTime();

        List<Bogie> filteredStream = bogies.stream()
                .filter(b -> b.capacity > 60)
                .collect(Collectors.toList());

        long endStream = System.nanoTime();
        long streamTime = endStream - startStream;

        // ── Display Results ──
        System.out.println("── Performance Results ──\n");

        System.out.println("Traditional For-Loop:");
        System.out.println("  Filtered count: " + filteredLoop.size());
        System.out.println("  Time taken: " + loopTime + " ns (" + (loopTime / 1_000_000.0) + " ms)");

        System.out.println("\nStream API:");
        System.out.println("  Filtered count: " + filteredStream.size());
        System.out.println("  Time taken: " + streamTime + " ns (" + (streamTime / 1_000_000.0) + " ms)");

        System.out.println("\n── Comparison ──");
        String faster = loopTime < streamTime ? "For-Loop" : "Stream API";
        double ratio = loopTime < streamTime
                ? (double) streamTime / loopTime
                : (double) loopTime / streamTime;
        System.out.println("  " + faster + " was faster by " + String.format("%.2f", ratio) + "x");

        System.out.println("\nProgram continues...");
    }
}