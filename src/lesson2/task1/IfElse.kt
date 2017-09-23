@file:Suppress("UNUSED_PARAMETER")
package lesson2.task1

import lesson1.task1.discriminant
import lesson1.task1.sqr
import lesson4.task1.abs
import lesson5.task1.mostExpensive
import java.lang.Math.*

/**
 * Пример
 *
 * Найти наименьший корень биквадратного уравнения ax^4 + bx^2 + c = 0
 */
fun minBiRoot(a: Double, b: Double, c: Double): Double {
    // 1: в главной ветке if выполняется НЕСКОЛЬКО операторов
    if (a == 0.0) {
        if (b == 0.0) return Double.NaN // ... и ничего больше не делать
        val bc = -c / b
        if (bc < 0.0) return Double.NaN // ... и ничего больше не делать
        return -Math.sqrt(bc)
        // Дальше функция при a == 0.0 не идёт
    }
    val d = discriminant(a, b, c)   // 2
    if (d < 0.0) return Double.NaN  // 3
    // 4
    val y1 = (-b + Math.sqrt(d)) / (2 * a)
    val y2 = (-b - Math.sqrt(d)) / (2 * a)
    val y3 = Math.max(y1, y2)       // 5
    if (y3 < 0.0) return Double.NaN // 6
    return -Math.sqrt(y3)           // 7
}

/**
 * Простая
 *
 * Мой возраст. Для заданного 0 < n < 200, рассматриваемого как возраст человека,
 * вернуть строку вида: «21 год», «32 года», «12 лет».
 */
fun ageDescription(age: Int): String {
    return if (age%10==1&&age!=11&&age!=111)"$age год" else
    if (age%10 in 2..4&&age !in 5..20&&age !in 105..120)"$age года" else "$age лет"
}

    /**
     * Проста
     *
     *
     * Путник двигался t1 часов со скоростью v1 км/час, затем t2 часов — со скоростью v2 км/час
     * и t3 часов — со скоростью v3 км/час.
     * Определить, за какое время он одолел первую половину пути?
     */



fun timeForHalfWay(t1: Double, v1: Double,
                   t2: Double, v2: Double,
                   t3: Double, v3: Double): Double {
    val s=(t1*v1+t2*v2+t3*v3)/2
    val s1=t1*v1
    val s2=t2*v2
    val s3=t3*v3

    return if (s==s1)t1 else
        if (s<s1)(s/v1) else
            if (s==(s1+s2))(t1+t2) else
                if (s in s1..(s1+s2))(t1+(s-s1)/v2) else
                    if (s==(s1+s2+s3))(t1+t2+t3) else (t1+t2+(s-s1-s2)/v3)
                   }


/**
 * Простая
 *
 * Нa шахматной доске стоят черный король и две белые ладьи (ладья бьет по горизонтали и вертикали).
 * Определить, не находится ли король под боем, а если есть угроза, то от кого именно.
 * Вернуть 0, если угрозы нет, 1, если угроза только от первой ладьи, 2, если только от второй ладьи,
 * и 3, если угроза от обеих ладей.
 * Считать, что ладьи не могут загораживать друг друга
 */
fun whichRookThreatens(kingX: Int, kingY: Int,
                       rookX1: Int, rookY1: Int,
                       rookX2: Int, rookY2: Int): Int {
    return if ((kingX==rookX1&&kingX!=rookX2&&kingY!=rookY2)||(kingY==rookY1&&kingY!=rookY2&&kingX!=rookX2))1 else
        if ((kingX==rookX2&&kingX!=rookX1&&kingY!=rookY1)||(kingY==rookY2&&kingY!=rookY1&&kingX!=rookX1))2 else
            if ((kingY==rookY1&&kingY!=rookY2&&kingX==rookX2)||(kingY==rookY2&&kingY!=rookY1&&kingX==rookX1)||(kingX==rookX1&&kingX==rookX2)||(kingY==rookY1&&kingY==rookY2))3 else 0
}


    /**
     * Простая
     *
     * На шахматной доске стоят черный король и белые ладья и слон
     * (ладья бьет по горизонтали и вертикали, слон — по диагоналям).
     * Проверить, есть ли угроза королю и если есть, то от кого именно.
     * Вернуть 0, если угрозы нет, 1, если угроза только от ладьи, 2, если только от слона,
     * и 3, если угроза есть и от ладьи и от слона.
     * Считать, что ладья и слон не могут загораживать друг друга.
     */



fun rookOrBishopThreatens(kingX: Int, kingY: Int,
                          rookX: Int, rookY: Int,
                          bishopX: Int, bishopY: Int): Int {
        return if ((abs(kingX-bishopX))==(abs(kingY-bishopY))&&kingX!=rookX&&kingY!=rookY)2 else
            if ((abs(kingX-bishopX))==(abs(kingY-bishopY))&&(kingX==rookX||kingY==rookY))3 else
                if ((abs(kingX-bishopX))!=(abs(kingY-bishopY))&&(kingX==rookX||kingY==rookY))1 else 0
    }
        /**
         * Простая
         *
         * Треугольник задан длинами своих сторон a, b, c.
         * Проверить, является ли данный треугольник остроугольным (вернуть 0),
         * прямоугольным (вернуть 1) или тупоугольным (вернуть 2).
         * Если такой треугольник не существует, вернуть -1.
         */






fun triangleKind(a: Double, b: Double, c: Double): Int {
            return if (a+b<c||a+c<b||c+b<a)-1 else
                if ((a>b&&a>c&&sqr(a)==sqr(b)+sqr(c))||(c>b&&c>a&&sqr(c)==sqr(b)+sqr(a))||(b>a&&b>c&&sqr(b)==sqr(a)+sqr(c)))1 else
                    if ((a>b&&a>c&&sqr(a)>sqr(b)+sqr(c))||(c>b&&c>a&&sqr(c)>sqr(b)+sqr(a))||(b>a&&b>c&&sqr(b)>sqr(a)+sqr(c)))2 else 0

        }



            /**
             * Средняя
             *
             * Даны четыре точки на одной прямой: A, B, C и D.
             * Координаты точек a, b, c, d соответственно, b >= a, d >= c.
             * Найти длину пересечения отрезков AB и CD.
             * Если пересечения нет, вернуть -1.
             */



fun segmentLength(a: Int, b: Int, c: Int, d: Int): Int {
             return if (b<c||d<a)-1 else
                 if (b>=d&&a<c)(d-c) else
                     if (b<d&&a>c)(b-a) else
                         if(b>c&&b<d&&c>a)(b-c) else
                             if (c==b||d==a||a==b&&c==d&&c==b||a==b&&b==c||a==d&&d==b||a==d&&a==c||b==d&&b==c)0 else
                                 if  (a==c&&b==d)(b-a) else
                                     if (a==c&&b<d)(b-c) else (d-a)

}