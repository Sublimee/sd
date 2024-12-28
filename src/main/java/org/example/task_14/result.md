```java
static void quickSort(int[] arr, int low, int high) {
    if (low < 0) {
        throw new IllegalArgumentException("Нижняя граница не может быть меньше 0");
    }

    if (high < 0) {
        throw new IllegalArgumentException("Верхняя граница не может быть меньше 0");
    }

    if (high > arr.length - 1) {
        throw new IllegalArgumentException("Верхняя граница не может быть больше длины массива");
    }
    
    if (low < high) {
        int pivotIndex = partition(arr, low, high);
        quickSort(arr, low, pivotIndex - 1);
        quickSort(arr, pivotIndex + 1, high);
    }
}

static int partition(int[] arr, int low, int high) {
    int pivot = arr[high];
    int i = low - 1;
    for (int j = low; j < high; j++) {
        if (arr[j] <= pivot) {
            i++;
            swap(arr, i, j);
        }
    }
    swap(arr, i + 1, high);
    return i + 1;
}

static void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
}
```

Тройка Хоара для quickSort(arr, low, high):

{P: low >= 0, 0 <= high < arr.length, high >= low} quickSort(arr, low, high) {Q: диапазон arr[low:high] отсортирован по возрастанию}

Тройка Хоара для partition(arr, low, high):

{P: arr.length > 0, low >= 0, 0 <= high < arr.length, high > low} partition(arr, low, high) {Q: элементы диапазона arr[low:pivotIndex) <= arr[pivotIndex], а элементы диапазона arr[pivotIndex+1:high+1) > arr[pivotIndex]}


---

Доказательство корректности partition(arr, low, high)

    Запишем инвариант цикла для функции partition(arr, low, high):
    I: arr[low:i] <= pivot, если i >= low, и arr[i+1:j) > pivot

    Инициализация:
    j = low, используется для итерации по подмассиву, цикл начинается с первого элемента диапазона
    i = low − 1, начальная граница для элементов, меньших или равных опорному (pivot), т.е. изначально нет элементов, которые <= pivot
    arr[i+1:j) == arr[low − 1 + 1, low) == arr[low, low) -> пустой диапазон, поэтому условие инварианта выполняется

    Сохранения инварианта:

    На каждой итерации проверяется arr[j]:
    * если arr[j] <= pivot, то элемент добавляется в диапазон arr[low:i], путем его расширения вправо за счет инкремента i и обмена arr[i] с arr[j]. Инвариант сохраняется, так как arr[low:i] <= pivot (i стал больше или равен low) и arr[i+1:j) > pivot.
    * если arr[j] > pivot, то переходим к обработке слудующего элемента за счет увеличения j. Диапазон arr[low:i] остается неизменным, а arr[i+1:j) пополняется очередныv элементом, который больше pivot: arr[i+1:j+1). Соответственно инвариант сохраняется.

    Завершение:

    Цикл завершается, когда j становится равным high
    На этом этапе все элементы до i включительно меньше или равны pivot, все элементы после i больше pivot. После этого опорный элемент pivot ставится на место i+1,что разделяет массив на две части и гарантирует arr[low:pivotIndex] <= pivot и arr[pivotIndex:high+1) > pivot.

    Итак, мы доказали, что partition(arr, low, high) корректно разделяет массив на части, где элементы упорядочены относительно pivot:

    * Инвариант I истинен до начала цикла.
    * Инвариант сохраняется на каждой итерации.
    * Инвариант и условие завершения цикла гарантируют выполнение постусловия Q

---

Доказательство корректности quickSort(arr, low, high)

    Инициализация
    * Если low >= high
    Либо массив пуст, либо состоит из одного элемента -> массив уже отсортирован, инвариант выполняется.
    * Если low < high
    На каждом уровне рекурсии массив с помощью partition(arr, low, high)делится на части, которые сортируются независимо: arr[low:pivotIndex) и arr[pivotIndex+1:high], причём arr[pivotIndex] находится на своей окончательной позиции. По ранее доказанному инварианту: arr[low:pivotIndex) <= arr[pivotIndex] < arr[pivotIndex+1:high]

    Процесс:
    Рекурсивный вызов quickSort(arr, low, pivotIndex-1) сортирует левую часть массива arr[low:pivotIndex). По рекурсивному инварианту, после выполнения arr[low:pivotIndex) отсортирован. Рекурсивный вызов quickSort(arr, pivotIndex+1, high) сортирует правую часть массива [pivotIndex+1, high]. По рекурсивному инварианту, после выполнения arr[pivotIndex+1, high) отсортирован.
    
    Завершение:
    После выполнения двух рекурсивных вызовов и с учётом того, что pivot находится на правильной позиции, массив arr[low:high] становится полностью отсортированным.