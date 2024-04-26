package fr._42.numbers;
public class NumberWorker {
    public boolean isPrime(int number) throws IllegalNumberException {
        if (number <= 1)
            throw new IllegalNumberException("number is less than 1");
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0)
                return false;
        }
        return true;
    }

    public int digitsSum(int number) {
        int sum = 0;
        while (number != 0) {
            sum += number % 10;
            number /= 10;
        }
        return  sum;
    }
}

class IllegalNumberException extends RuntimeException {
    public IllegalNumberException(String message) {
        super(message);
    }
}