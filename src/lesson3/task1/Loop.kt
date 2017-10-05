@file:Suppress("UNUSED_PARAMETER")
package lesson3.task1

import lesson1.task1.sqr
import java.lang.Math.*

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    for (m in 2..Math.sqrt(n.toDouble()).toInt()) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n/2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
        when{
            n == m -> 1
            n < 10 -> 0
            else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
        }

/**
 * Тривиальная
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 */
fun digitNumber(n: Int): Int {
    var k=0
    var maxi=n
    if (n==0) return 1
    if (n==Int.MAX_VALUE) return 10 else
     while (maxi!=0) {
         maxi/=10
         k+=1
    }
    return k
}

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    var k=0
    var d=0
    var d1=1
    var m=n
    if (n==1) k=1 else{
        while(m>1){
            k=d+d1
            val d2=d
            d=d1
            d1=d2+d
            m-=1
        }
    }
    return k
}

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int{
    var k=Math.max(n,m)
    while (k in 1..n*m){
        if (k%n!=0||k%m!=0)
            k+=1 else return k
    }
    return k
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int{
    var k=2
    while(n%k!=0){
        k+=1
    }
    return k
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 * */
fun maxDivisor(n: Int): Int {
    var k=n-1
    while(n%k!=0){
        k-=1
    }
    return k
}

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean{
    val d=Math.min(n,m)
    for (i in 2..d){
        if (n%i==0&&m%i==0)return false
    }
    return true
}

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean{
    var k=0
        while(k<=sqrt(n.toDouble())){
            if(k*k>=m)return true else k+=1
        }
    return false
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 /
 * 5! - x^7 / 7! + ...
 s>0* Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun sin(x: Double, eps: Double): Double
{
    var d=x
    if (d>2*PI) {
        while (d>2*PI) d-=2*PI
    }
    if (d<-2*PI) {
        while (d<2*PI) d+=2*PI
    }
    var k=0.0
    var s=d
    var i=1
    while (abs(s)>=abs(eps)){
        s=pow(d,i.toDouble())/factorial(i)
        if (i%4==1){
            k+=s
        }
        if (i%4==3){
            k-=s
        }
        i+=2
    }
    return k
}



/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun cos(x: Double, eps: Double): Double
{
    var d=x
    if (d>2*PI) {
        while (d>2*PI) d-=2*PI
    }
    if (d<-2*PI) {
        while (d<2*PI) d+=2*PI
    }
    var k=1.0
    var s=d
    var i=2
    while (abs(s)>=abs(eps)){
        s=pow(d,i.toDouble())/factorial(i)
        if (i%4==0){
            k+=s
        }
        if (i%4==2){
            k-=s
        }
        i+=2
    }
    return k
}

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 * Не использовать строки при решении задачи.
 */
fun revert(n: Int): Int {
    var k=0
    var s=0
    var n1=n
    var n2=n
    if (n in 0..9) return n else
    while (n1!=0) {
        n1/= 10
        k+=1
    }
    var d=pow(10.0,(k-1).toDouble()).toInt()
    var d2=1
    val n3=n2/d
    for (i in 1..k){
        n1/=d
        n1*=d2
        s+=n1
        n1=n2%d
        n2%=d
        d/=10
        d2*=10
    }
    return s+n3
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 */
fun isPalindrome(n: Int): Boolean{
    var k=0
    var n1=n
    var co=0
    if (n in 1..9) return true
    while (n1!=0) {
        n1/=10
        k+=1
    }
    n1=n
    var d=pow(10.0,k-1.toDouble()).toInt()
    co = if (k%2==0) k else k-1
    for (i in 1..co/2){
        if (n1%10==n1/d){
            n1/=10
            d/=10
            n1%=d
            d/=10
        } else return false
    }
    return true
}

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 */
fun hasDifferentDigits(n: Int): Boolean {
    var k=0
    var n1=n
   while (n1>9){
       if (n1%10!=n1%100/10){
           k+=1
       }
       n1/=10
   }
    return k!=0
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 */
fun squareSequenceDigit(n: Int): Int{
    var k=0
    var count=0
    var i=1
    while (n>count){
        k=i*i
        while (k>0){
            k/=10
            count+=1
        }
        k=i*i
        i+=1
    }
    val c=count-n
    if (count>n) {
        for (j in 1..c) {
            k/=10
        }
    }
    return k%10
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 01123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 */
fun fibSequenceDigit(n: Int): Int{n==1
    var k=0
    var count=1
    var d=0
    var d1=1
    var d2=0
    if (n==1) k=1
    while (n>count){
        k=d+d1
        while (k>0){
            k/=10
            count+=1
        }
        k=d+d1
        d2=d
        d=d1
        d1=d2+d
    }
    val c=count-n
    if (count>n) {
        for (j in 1..c) {
            k/=10
        }
    }
    return k%10
}


