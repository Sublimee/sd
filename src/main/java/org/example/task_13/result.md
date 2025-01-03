```java
public static int findMax(int[] arr) {
    int res = arr[0];
    for (int i = 1; i < arr.length; i++) {
        if (arr[i] > res) {
            res = arr[i];
        }
    }
    return res;
}
```

Тройка Хоара:

{P: arr.length > 0} findMax(arr) {Q: res = max(arr)}

Запишем инвариант цикла для функции findMax(arr):

I: 1 <= i < arr.length и res = max(res, arr[i]), 

где **res** содержит максимальное значение из уже просмотренных элементов массива, а
**i** — индекс, указывающий на следующий элемент для проверки.

---

Доказательство инварианта

Инициализация:

    До начала первой итерации цикла: 𝑖 = 1 и res = arr[0]
    Проверка инварианта: 1 <= 1 < arr.length (если arr.length > 1, т.е. цикл предполагается) и res = max(arr[0]) = arr[0]
    Инвариант истинен в начале.

Сохранение инварианта:

    Предположим, что инвариант истинен до некоторой итерации цикла, где 1 <= i < arr.length и res = max(res, arr[i]).
    Во время выполнения цикла: res = max(res, arr[i]) и i = i + 1.
    После итерации: i увеличивается на 1, следовательно, i = i + 1, а res = max(max(prev_res, arr[i - 1]), arr[i]) = max(prev_res, arr[i - 1], arr[i]) = max(res, arr[i]), где prev_res = max(res, arr[i-2])
    Таким образом, инвариант по-прежнему истинен: 1 <= i < arr.length и res = max(res, arr[i])

Завершение:

    Цикл завершается, когда i становится равным arr.length
    На этом этапе res будет равен максимальному значению из представленных в массиве max(arr), так как инвариант после последней итерации: i = arr.length - 1 и res = max(res, arr[i])
    Следовательно, постусловие выполняется: max(arr)

Итак, мы доказали, что findMax(arr) корректно вычисляет максимальное значение массива, что доказано следующим образом:

    * Инвариант I истинен до начала цикла.
    * Инвариант сохраняется на каждой итерации.
    * Инвариант и условие завершения цикла гарантируют выполнение постусловия Q