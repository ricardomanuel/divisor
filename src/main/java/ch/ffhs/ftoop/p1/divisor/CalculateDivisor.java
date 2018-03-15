package ch.ffhs.ftoop.p1.divisor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Das folgende Programm soll aus einem vorgegebene Interval von Long-Zahlen die
 * Zahl zurückgeben, die die meisten Divisoren hat. Sollte es mehrere solche
 * Zahlen geben, so soll die kleinste dieser Zahlen ausgegeben werden.
 * <p>
 * Die Berechnung soll in n Threads stattfinden, die via Executor Framework
 * gesteuert werden, und sich das Problem aufteilen - jeder Thread soll eine
 * Teilmenge des Problems lösen. Verwenden Sie bitte einen FixedThreadPool und
 * implementieren Sie die Worker als Callable.
 *
 * @author ble
 */
public class CalculateDivisor {

    private long von, bis;
    private int threadCount;

    /**
     * @param von         untere Intervallgrenze
     * @param bis         obere Intervallgrenze
     * @param threadCount Abzahl der Threads, auf die das Problem aufgeteilt werden soll
     */
    public CalculateDivisor(long von, long bis, int threadCount) {
        this.von = von;
        this.bis = bis;
        this.threadCount = threadCount;

    }

    /**
     * Berechnungsroutine
     *
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    DivisorResult calculate() throws InterruptedException, ExecutionException {
        List<Future<DivisorResult>> futures = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        for (long dividend = von; dividend <= bis; dividend++) {
            futures.add(executor.submit(new DivisorTask(dividend)));
        }

        while (futures.stream().filter(f -> !f.isDone()).count() > 0) {
            Thread.sleep(500);
        }

        return futures.stream() //
                .map(f -> toDivisorResult(f)) //
                .sorted((o1, o2) -> {
                    if (o1.countDiv > o2.countDiv) {
                        return -1;
                    }

                    if (o1.countDiv < o2.countDiv) {
                        return 1;
                    }

                    return 0;

                }) //
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Not found"));
    }

    private DivisorResult toDivisorResult(Future<DivisorResult> future) {
        try {
            return future.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws InterruptedException,
            ExecutionException {
        if (args.length != 3) {
            System.out
                    .println("Usage: CalculateDivisor <intervalStart> <intervalEnd> <threadCount>");
            System.exit(1);
        }
        long von = Long.parseLong(args[0]);
        long bis = Long.parseLong(args[1]);
        int threads = Integer.parseInt(args[2]);

        CalculateDivisor cp = new CalculateDivisor(von, bis, threads);
        System.out.println("Ergebnis: " + cp.calculate());
        System.exit(0);
    }

}

class DivisorTask implements Callable<DivisorResult> {

    final private long dividend;
    private long divisorCount = 0;

    public DivisorTask(long dividend) {
        this.dividend = dividend;
    }

    @Override
    public DivisorResult call() throws Exception {
        for (long i = 1; i <= dividend; i++) {
            if (dividend % i == 0) {
                divisorCount++;
            }
        }

        return new DivisorResult(dividend, divisorCount);
    }
}

/**
 * Hält das Ergebnis einer Berechnung
 *
 * @author bele
 */
class DivisorResult {
    // das eigentlich ergebnis - die Zahl mit der max. Anzahl von Divisoren
    long result;

    // Anzahl der Divisoren von Result
    long countDiv;

    public DivisorResult(long r, long c) {
        result = r;
        countDiv = c;
    }

    public long getResult() {
        return result;
    }

    public long getCountDiv() {
        return countDiv;
    }

    @Override
    public String toString() {
        return "Zahl mit maximaler Anzahl Divisoren: " + result + " ("
                + countDiv + " Divisoren)";
    }

}
