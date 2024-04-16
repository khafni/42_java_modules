package fr._42.numbers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class NumberWorkerTest {

    @ParameterizedTest
    @ValueSource(ints = {23, 29, 31, 41, 43, 47})
    void isPrimeForPrimes(int num) {
        NumberWorker numberWorker = new NumberWorker();
        assertTrue(() -> {
            try {
                return numberWorker.isPrime(num);
            } catch (IllegalNumberException e) {
                return false;
            }
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {60, 74, 92, 78, 44})
    void isPrimeForNotPrimes(int num) {
        NumberWorker numberWorker = new NumberWorker();
        assertTrue(() -> {
            try {
                return !numberWorker.isPrime(num);
            } catch (IllegalNumberException e) {
                return false;
            }
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1})
    void isPrimeForIncorrectNumbers(int num) {
        NumberWorker numberWorker = new NumberWorker();
        assertTrue(() -> {
            try {
                numberWorker.isPrime(num);
            } catch (IllegalNumberException e) {
                return true;
            }
            return false;
        });
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv")
    void isDigitsSumCorrect(int num, int sum) {
        NumberWorker numberWorker = new NumberWorker();
        assertTrue(() -> {
            return numberWorker.digitsSum(num) == sum;
        });
    }

}