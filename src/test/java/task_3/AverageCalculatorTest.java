package task_3;

import org.example.task_3.AverageCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class AverageCalculatorTest {

    public static final double DELTA = 0.0001;

    @Test
    public void testCalculateAverageWithPositiveNumbers() {
        int[] numbers = {2, 4, 6, 8};
        double result = AverageCalculator.calculateAverage(numbers);
        assertEquals(5.0, result, DELTA);
    }

    @Test
    public void testCalculateAverageWithNegativeNumbers() {
        int[] numbers = {-2, -4, -6, -8};
        double result = AverageCalculator.calculateAverage(numbers);
        assertEquals(-5.0, result, DELTA);
    }

    @Test
    public void testCalculateAverageWithMixedNumbers() {
        int[] numbers = {-3, 0, 3};
        double result = AverageCalculator.calculateAverage(numbers);
        assertEquals(0.0, result, DELTA);
    }

    @Test
    public void testCalculateAverageWithEmptyArray() {
        int[] numbers = {};
        assertThrowsExactly(IllegalArgumentException.class, () -> AverageCalculator.calculateAverage(numbers));
    }

    @Test
    public void testCalculateAverageWithNullArray() {
        assertThrowsExactly(IllegalArgumentException.class, () -> AverageCalculator.calculateAverage(null));
    }

    /*
        В текущей реализации для подсчета суммы элементов массива используется переменная sum типа int. Потенциально
        сумма элементов может превысить максимальное значение типа int, что приведет к переполнению и некорректному
        результату. Этот тест должен проверить, что среднее двух Integer.MAX_VALUE (максимальных для int) вычисляется
        корректно. Тест, однако, провалится. Если пропустить такой сценарий, то мы можем столкнуться с ошибками в рантайме.
     */
    @Test
    public void testCalculateAverageWithLargeNumbers() {
        int[] numbers = {Integer.MAX_VALUE, Integer.MAX_VALUE};
        double result = AverageCalculator.calculateAverage(numbers);
        assertEquals(Integer.MAX_VALUE, result, DELTA);
    }
}