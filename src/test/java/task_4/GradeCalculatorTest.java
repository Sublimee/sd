package task_4;

import org.example.task_4.GradeCalculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class GradeCalculatorTest {

    // 1. Тест с корректными оценками в допустимом диапазоне и допустимым количеством
    @Test
    public void testCalculateAverageWithValidGrades() {
        GradeCalculator calculator = new GradeCalculator();
        List<Double> grades = Arrays.asList(85.0, 90.5, 78.0, 92.5);
        double result = calculator.calculateAverage(grades);
        Assertions.assertEquals(86.5, result, 0.0001);
    }

    // 2. Тест с null вместо списка оценок
    @Test
    public void testCalculateAverageWithNullList() {
        GradeCalculator calculator = new GradeCalculator();
        IllegalArgumentException exception = assertThrowsExactly(IllegalArgumentException.class, () -> calculator.calculateAverage(null));
        Assertions.assertEquals("Список оценок равен null", exception.getMessage());
    }

    // 3. Тест с пустым списком оценок
    @Test
    public void testCalculateAverageWithEmptyList() {
        GradeCalculator calculator = new GradeCalculator();
        IllegalArgumentException exception = assertThrowsExactly(IllegalArgumentException.class, () -> calculator.calculateAverage(Collections.emptyList()));
        Assertions.assertEquals("Список оценок пуст", exception.getMessage());
    }

    // 4. Тест с количеством оценок, превышающим максимально допустимое
    @Test
    public void testCalculateAverageWithTooManyGrades() {
        GradeCalculator calculator = new GradeCalculator();
        List<Double> grades = new ArrayList<>(Collections.nCopies(100001, 75.0));
        IllegalArgumentException exception = assertThrowsExactly(IllegalArgumentException.class, () -> calculator.calculateAverage(grades));
        Assertions.assertEquals("Слишком большое количество оценок", exception.getMessage());
    }

    // 5. Тест с максимально допустимым количеством оценок
    @Test
    public void testCalculateAverageWithMaxAllowedGrades() {
        GradeCalculator calculator = new GradeCalculator();
        List<Double> grades = new ArrayList<>(Collections.nCopies(100000, 80.0));
        double result = calculator.calculateAverage(grades);
        Assertions.assertEquals(80.0, result, 0.0001);
    }

    // 6. Тест с оценками, содержащими null
    @Test
    public void testCalculateAverageWithNullGrade() {
        GradeCalculator calculator = new GradeCalculator();
        List<Double> grades = Arrays.asList(85.0, null, 78.0);
        IllegalArgumentException exception = assertThrowsExactly(IllegalArgumentException.class, () -> calculator.calculateAverage(grades));
        Assertions.assertEquals("Оценка в списке равна null", exception.getMessage());
    }

    // 7. Тест с положительной оценкой вне допустимого диапазона
    @Test
    public void testCalculateAverageWithInvalidPositiveGrade() {
        GradeCalculator calculator = new GradeCalculator();
        List<Double> grades = List.of(105.0);
        IllegalArgumentException exception = assertThrowsExactly(IllegalArgumentException.class, () -> calculator.calculateAverage(grades));
        Assertions.assertEquals("Оценка вне допустимого диапазона (0-100): 105.0", exception.getMessage());
    }

    // 8. Тест с отрицательной оценкой вне допустимого диапазона
    @Test
    public void testCalculateAverageWithInvalidNegativeGrade() {
        GradeCalculator calculator = new GradeCalculator();
        List<Double> grades = List.of(-1.0);
        IllegalArgumentException exception = assertThrowsExactly(IllegalArgumentException.class, () -> calculator.calculateAverage(grades));
        Assertions.assertEquals("Оценка вне допустимого диапазона (0-100): -1.0", exception.getMessage());
    }
}