import java.util.concurrent.Callable;

public class ToSumCallable implements Callable<Integer> {
    private int start;
    private int end;
    private  int[] array;
    public ToSumCallable(int start, int end, int[] array) {
        this.start = start;
        this.end = end;
        this.array = array;
    }

    @Override
    public Integer call() {
        int sum = 0;
        for (int i = 0; i < end; i++) {
            sum += array[i];
        }
        return  sum;
    }

}
