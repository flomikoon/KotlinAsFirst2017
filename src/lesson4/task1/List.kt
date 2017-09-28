@file:Suppress("UNUSED_PARAMETER")
package lesson4.task1

import lesson1.task1.discriminant
import java.lang.Math.pow
import java.lang.Math.sqrt

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
        when {
            y < 0 -> listOf()
            y == 0.0 -> listOf(0.0)
            else -> {
                val root = Math.sqrt(y)
                // Результат!
                listOf(-root, root)
            }
        }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + Math.sqrt(d)) / (2 * a)
    val y2 = (-b - Math.sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double{
    var r=0.0
    for (element in v){
        r+=element*element
    }
    return sqrt(r)
}

/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double{
    var r=0.0
    val d=list.size
    if (d==0) return 0.0
    for (element in list){
        r+=element
    }
    return r/d
}

/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double>{
    var r=0.0
    val d=list.size
    for (i in 0 until d){
        val element=list[i]
        r+=element
    }
    r/=d
    for (j in 0 until d){
        val element=list[j]
        list[j]=element-r
    }
    return list
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.0.
 */
fun times(a: List<Double>, b: List<Double>): Double {
    var r=0.0
    for (i in 0 until a.size){
        val element=a[i]
        val element1=b[i]
        r+=element*element1
    }
    return r
}


/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0.0 при любом x.
 */
fun polynom(p: List<Double>, x: Double): Double{
    var r=0.0
    for (i in 0 until p.size){
        val element=p[i]
        r+=element*pow(x, i.toDouble())
    }
    return r
}

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Double>): MutableList<Double> {
    var r=1.0
    for (i in 1 until list.size) {
        val element=list[i]
        list[i]=element+r
        r+=element
    }
    return list
}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int>{
    val resoult=mutableListOf<Int>()
    var n1=n
    var i=2
    while (n1>1){
        if (n1%i==0) {
            resoult.add(i)
            n1/=i
            i=1
        }
        i+=1
    }
    return resoult.sorted()
}

/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 */
fun factorizeToString(n: Int): String{
    val resoult=factorize(n)
    return resoult.sorted().joinToString(
            separator = "*"
    )
}

/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int>{
    val resoult=mutableListOf<Int>()
    var n1=n
    if (n==0) resoult.add(0)
    while (n1>0){
       val d=n1%base
            resoult.add(d)
            n1/=base
        }
    return resoult.reversed()
}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 */
fun convertToString(n: Int, base: Int): String= TODO()

/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int{
    var r=0.0
    val spisok=digits.reversed()
        for (i in 0 until spisok.size) {
                val element=spisok[i]
                r+=element*pow(base.toDouble(), i.toDouble())
        }
    return r.toInt()
}

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 */
fun decimalFromString(str: String, base: Int): Int = TODO()

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String{
    var n1=n
    val resoult=mutableListOf<String>()
    while (n1-1000>=0){
        resoult.add("M")
        n1-=1000
    }
    while (n1-900>=0){
        resoult.add("CM")
        n1-=900
    }
    while (n1-500>=0){
        resoult.add("D")
        n1-=500
    }
    while(n1-400>=0){
        resoult.add("CD")
        n1-=400
    }
    while (n1-100>=0){
        resoult.add("C")
        n1-=100
    }
    while (n1-90>=0){
        resoult.add("XC")
        n1-=90
    }
    while (n1-50>=0){
        resoult.add("L")
        n1-=50
    }
    while (n1-40>=0){
        resoult.add("XL")
        n1-=40
    }
    while (n1-10>=0){
        resoult.add("X")
        n1-=10
    }
    while (n1-9>=0){
        resoult.add("IX")
        n1-=9
    }
    while (n1-5>=0){
        resoult.add("V")
        n1-=5
    }
    while (n1-4>=0){
        resoult.add("IV")
        n1-=4
    }
    while (n1-1>=0){
        resoult.add("I")
        n1-=1
    }
    return resoult.joinToString (
            separator = ""
    )
}

/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun russian(n: Int): String{
    var n1=n
    val resoult=mutableListOf<String>()
    var d=100000
    val n3=n/1000
    if(0<n3) {
        n1 /= d
        if (n1 == 1) {
            resoult.add("сто")
        }
        if (n1 == 2) {
            resoult.add("двести")
        }
        if (n1 == 3) {
            resoult.add("триста")
        }
        if (n1 == 4) {
            resoult.add("четыреста")
        }
        if (n1 == 5) {
            resoult.add("пятьсот")
        }
        if (n1 == 6) {
            resoult.add("шестьсот")
        }
        if (n1 == 7) {
            resoult.add("семьсот")
        }
        if (n1 == 8) {
            resoult.add("восемьсот")
        }
        if (n1 == 9) {
            resoult.add("девятьсот")
        }
        n1 = n % d
        d /= 100
        val n2 = n1
        n1 /= d
        if (10 < n1 && n1 < 20) {
            if (n1 == 11) {
                resoult.add("одинадцать тысяч")
            }
            if (n1 == 12) {
                resoult.add("двенадцать тысяч")
            }
            if (n1 == 13) {
                resoult.add("тринадцать тысяч")
            }
            if (n1 == 14) {
                resoult.add("четырнадцать тысяч")
            }
            if (n1 == 15) {
                resoult.add("пятнадцать тысяч")
            }
            if (n1 == 16) {
                resoult.add("шестнадцать тысяч")
            }
            if (n1 == 17) {
                resoult.add("семнадцать тысяч ")
            }
            if (n1 == 18) {
                resoult.add("восемнадцать тысяч")
            }
            if (n1 == 19) {
                resoult.add("девятнадцать тысяч")
            }
        } else {
            d *= 10
            n1 = n2
            n1 /= d
            if (n1 == 1) {
                resoult.add("десять")
            }
            if (n1 == 2) {
                resoult.add("двадцать")
            }
            if (n1 == 3) {
                resoult.add("тридцать")
            }
            if (n1 == 4) {
                resoult.add("сорок")
            }
            if (n1 == 5) {
                resoult.add("пятьдесят")
            }
            if (n1 == 6) {
                resoult.add("шестьдесят")
            }
            if (n1 == 7) {
                resoult.add("семьдесят")
            }
            if (n1 == 8) {
                resoult.add("восемьдесят")
            }
            if (n1 == 9) {
                resoult.add("девяносто")
            }
            n1 = n % d
            d /= 10
            n1 /= d
            if (n1 == 0) {
                resoult.add("тысяч")
            }
            if (n1 == 1) {
                resoult.add("одна тысяча")
            }
            if (n1 == 2) {
                resoult.add("две тысячи")
            }
            if (n1 == 3) {
                resoult.add("три тысячи")
            }
            if (n1 == 4) {
                resoult.add("четыре тысячи")
            }
            if (n1 == 5) {
                resoult.add("пять тысяч")
            }
            if (n1 == 6) {
                resoult.add("шесть тысяч")
            }
            if (n1 == 7) {
                resoult.add("семь тысяч")
            }
            if (n1 == 8) {
                resoult.add("восемь тысяч")
            }
            if (n1 == 9) {
                resoult.add("девять тысяч")
            }
        }
    } else d/=100
        n1=n%d
        d/=10
        n1/=d
        if(n1==1){
            resoult.add("сто")
        }
        if(n1==2){
            resoult.add("двести")
        }
        if(n1==3){
            resoult.add("триста")
        }
        if(n1==4){
            resoult.add("четыреста")
        }
        if(n1==5){
            resoult.add("пятьсот")
        }
        if(n1==6){
            resoult.add("шестьсот")
        }
        if(n1==7){
            resoult.add("семьсот")
        }
        if(n1==8){
            resoult.add("восемьсот")
        }
        if(n1==9){
            resoult.add("девятьсот")
        }
    n1 = n % d
    d /= 100
    val n2 = n1
    n1 /= d
    if (10 < n1 && n1 < 20) {
        if (n1 == 11) {
            resoult.add("одинадцать")
        }
        if (n1 == 12) {
            resoult.add("двенадцать")
        }
        if (n1 == 13) {
            resoult.add("тринадцать")
        }
        if (n1 == 14) {
            resoult.add("четырнадцать")
        }
        if (n1 == 15) {
            resoult.add("пятнадцать")
        }
        if (n1 == 16) {
            resoult.add("шестнадцать")
        }
        if (n1 == 17) {
            resoult.add("семнадцать")
        }
        if (n1 == 18) {
            resoult.add("восемнадцать")
        }
        if (n1 == 19) {
            resoult.add("девятнадцать")
        }
    } else {
        d *= 10
        n1 = n2
        n1 /= d
        if (n1 == 1) {
            resoult.add("десять")
        }
        if (n1 == 2) {
            resoult.add("двадцать")
        }
        if (n1 == 3) {
            resoult.add("тридцать")
        }
        if (n1 == 4) {
            resoult.add("сорок")
        }
        if (n1 == 5) {
            resoult.add("пятьдесят")
        }
        if (n1 == 6) {
            resoult.add("шестьдесят")
        }
        if (n1 == 7) {
            resoult.add("семьдесят")
        }
        if (n1 == 8) {
            resoult.add("восемьдесят")
        }
        if (n1 == 9) {
            resoult.add("девяносто")
        }
        n1 = n % d
        d /= 10
        n1 /= d
        if (n1 == 1) {
            resoult.add("один")
        }
        if (n1 == 2) {
            resoult.add("два")
        }
        if (n1 == 3) {
            resoult.add("три")
        }
        if (n1 == 4) {
            resoult.add("четыре")
        }
        if (n1 == 5) {
            resoult.add("пять")
        }
        if (n1 == 6) {
            resoult.add("шесть")
        }
        if (n1 == 7) {
            resoult.add("семь")
        }
        if (n1 == 8) {
            resoult.add("восемь")
        }
        if (n1 == 9) {
            resoult.add("девять")
        }
    }
        return resoult.joinToString(
                separator =" "
        )
}