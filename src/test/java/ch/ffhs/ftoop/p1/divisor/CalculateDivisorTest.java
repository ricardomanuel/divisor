package ch.ffhs.ftoop.p1.divisor;

import org.junit.Test;
import student.TestCase;

import java.util.concurrent.ExecutionException;

/**
 * Hinweis: Die Unit Tests haben einen festen Timeout von 10 sekunden - achten
 * Sie daher darauf, dass Sie das Testintervall nicht zu gross gestalten.
 *
 * @author ble
 */

public class CalculateDivisorTest extends TestCase {

    @Test
    public void testCalculate() throws InterruptedException, ExecutionException {
        CalculateDivisor.main(new String[]{"10", "10000", "4"});
        assertFuzzyEquals(
                "Ergebnis: Zahl mit maximaler Anzahl Divisoren: 7560 (64 Divisoren)\n",
                systemOut().getHistory());
    }

    @Test
    public void testCalculateWith1Thread() throws InterruptedException, ExecutionException {
        CalculateDivisor.main(new String[]{"10", "10000", "1"});
        assertFuzzyEquals(
                "Ergebnis: Zahl mit maximaler Anzahl Divisoren: 7560 (64 Divisoren)\n",
                systemOut().getHistory());
    }

    @Test
    public void testCalculateWith10Thread() throws InterruptedException, ExecutionException {
        CalculateDivisor.main(new String[]{"10", "10000", "10"});
        assertFuzzyEquals(
                "Ergebnis: Zahl mit maximaler Anzahl Divisoren: 7560 (64 Divisoren)\n",
                systemOut().getHistory());
    }

    @Test
    public void testCalculateWith100Thread() throws InterruptedException, ExecutionException {
        CalculateDivisor.main(new String[]{"10", "10000", "100"});
        assertFuzzyEquals(
                "Ergebnis: Zahl mit maximaler Anzahl Divisoren: 7560 (64 Divisoren)\n",
                systemOut().getHistory());
    }

    @Test
    public void testCalculateWith10000Thread() throws InterruptedException, ExecutionException {
        CalculateDivisor.main(new String[]{"10", "10000", "10000"});
        assertFuzzyEquals(
                "Ergebnis: Zahl mit maximaler Anzahl Divisoren: 7560 (64 Divisoren)\n",
                systemOut().getHistory());
    }

}
