package org.example.task_4;

import java.util.List;

public class GradeCalculator {

    public static final int MAX_ALLOWED_GRADES_COUNT = 100000;

    public double calculateAverage(List<Double> grades) {
        if (grades == null) {
            throw new IllegalArgumentException("Список оценок равен null");
        }
        if (grades.isEmpty()) {
            throw new IllegalArgumentException("Список оценок пуст");
        }
        if (grades.size() > MAX_ALLOWED_GRADES_COUNT) {
            throw new IllegalArgumentException("Слишком большое количество оценок");
        }

        double sum = 0;
        int count = 0;
        for (Double grade : grades) {
            if (grade == null) {
                throw new IllegalArgumentException("Оценка в списке равна null");
            }
            if (grade < 0 || grade > 100) {
                throw new IllegalArgumentException("Оценка вне допустимого диапазона (0-100): " + grade);
            }
            double newSum = sum + grade;
            if (Double.isInfinite(newSum)) {
                throw new ArithmeticException("Переполнение при суммировании оценок");
            }
            sum = newSum;
            count++;
        }
        return sum / count;
    }
}