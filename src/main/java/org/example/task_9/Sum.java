public class Sum {
    public static void main(String[] args) {
        int[] integers = new Random().ints(SIZE, 0, 100).toArray();
        int sum = Arrays.stream(integers).parallel().reduce(0, Integer::sum);
        System.out.println("Sum of all elements: " + sum);
    }
}
