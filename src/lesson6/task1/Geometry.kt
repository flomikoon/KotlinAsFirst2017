@file:Suppress("UNUSED_PARAMETER")
package lesson6.task1

import lesson1.task1.sqr
import java.lang.Math.*

/**
 * Точка на плоскости
 */
data class Point(val x: Double, val y: Double) {
    /**
     * Пример
     *
     * Рассчитать (по известной формуле) расстояние между двумя точками
     */
    fun distance(other: Point): Double = Math.sqrt(sqr(x - other.x) + sqr(y - other.y))
}

/**
 * Треугольник, заданный тремя точками (a, b, c, см. constructor ниже).
 * Эти три точки хранятся в множестве points, их порядок не имеет значения.
 */
class Triangle private constructor(private val points: Set<Point>) {

    private val pointList = points.toList()

    val a: Point get() = pointList[0]

    val b: Point get() = pointList[1]

    val c: Point get() = pointList[2]

    constructor(a: Point, b: Point, c: Point): this(linkedSetOf(a, b, c))
    /**
     * Пример: полупериметр
     */
    fun halfPerimeter() = (a.distance(b) + b.distance(c) + c.distance(a)) / 2.0

    /**
     * Пример: площадь
     */
    fun area(): Double {
        val p = halfPerimeter()
        return Math.sqrt(p * (p - a.distance(b)) * (p - b.distance(c)) * (p - c.distance(a)))
    }

    /**
     * Пример: треугольник содержит точку
     */
    fun contains(p: Point): Boolean {
        val abp = Triangle(a, b, p)
        val bcp = Triangle(b, c, p)
        val cap = Triangle(c, a, p)
        return abp.area() + bcp.area() + cap.area() <= area()
    }

    override fun equals(other: Any?) = other is Triangle && points == other.points

    override fun hashCode() = points.hashCode()

    override fun toString() = "Triangle(a = $a, b = $b, c = $c)"
}

/**
 * Окружность с заданным центром и радиусом
 */
data class Circle(val center: Point, val radius: Double) {
    /**
     * Простая
     *
     * Рассчитать расстояние между двумя окружностями.
     * Расстояние между непересекающимися окружностями рассчитывается как
     * расстояние между их центрами минус сумма их радиусов.
     * Расстояние между пересекающимися окружностями считать равным 0.0.
     */
    fun distance(other: Circle): Double {
        val rX = sqr(center.x - other.center.x)
        val rY = sqr(center.y - other.center.y)
        val rO = Math.sqrt(rX + rY)
        return if (rO > radius + other.radius) rO - radius - other.radius
        else 0.0
    }

    /**
     * Тривиальная
     *
     * Вернуть true, если и только если окружность содержит данную точку НА себе или ВНУТРИ себя
     */
    fun contains(p: Point): Boolean {
        val dX = sqr(p.x - center.x)
        val dY = sqr(p.y - center.y)
        val dO = Math.sqrt(dX + dY)
        return dO <= radius
    }
}

/**
 * Отрезок между двумя точками
 */
data class Segment(val begin: Point, val end: Point) {
    override fun equals(other: Any?) =
            other is Segment && (begin == other.begin && end == other.end || end == other.begin && begin == other.end)

    override fun hashCode() =
            begin.hashCode() + end.hashCode()
}

/**
 * Средняя
 *
 * Дано множество точек. Вернуть отрезок, соединяющий две наиболее удалённые из них.
 * Если в множестве менее двух точек, бросить IllegalArgumentException
 */
fun diameter(vararg points: Point): Segment {
    require(points.size >= 2)
    var max = 0.0
    var p1 = points[0]
    var p2 = points[1]
    for (i in 0 until points.size) {
        for (j in 1 until points.size) {
            val dx = points[i].x - points[j].x
            val dy = points[i].y - points[j].y
            if (sqrt(sqr(dx) + sqr(dy)) > max) {
                max = sqrt(sqr(dx) + sqr(dy))
                p1 = points[i]
                p2 = points[j]
            }
        }
    }
    return Segment(p1, p2)
}

/**
 * Простая
 *
 * Построить окружность по её диаметру, заданному двумя точками
 * Центр её должен находиться посередине между точками, а радиус составлять половину расстояния между ними
 */
fun circleByDiameter(diameter: Segment): Circle {
    val cy = abs(diameter.begin.y - diameter.end.y) / 2 + min(diameter.begin.y, diameter.end.y)
    val cx = abs(diameter.begin.x - diameter.end.x) / 2 + min(diameter.begin.x, diameter.end.x)
    val radius = Point(cx, cy).distance(Point(diameter.begin.x, diameter.begin.y))
    return Circle(Point(cx, cy), radius)
}

/**
 * Прямая, заданная точкой point и углом наклона angle (в радианах) по отношению к оси X.
 * Уравнение прямой: (y - point.y) * cos(angle) = (x - point.x) * sin(angle)
 * или: y * cos(angle) = x * sin(angle) + b, где b = point.y * cos(angle) - point.x * sin(angle).
 * Угол наклона обязан находиться в диапазоне от 0 (включительно) до PI (исключительно).
 */
class Line private constructor(val b: Double, val angle: Double) {
    init {
        assert(angle >= 0 && angle < Math.PI) { "Incorrect line angle: $angle" }
    }

    constructor(point: Point, angle: Double) : this(point.y * Math.cos(angle) - point.x * Math.sin(angle), angle)

    /**
     * Средняя
     *
     * Найти точку пересечения с другой линией.
     * Для этого необходимо составить и решить систему из двух уравнений (каждое для своей прямой)
     */
    fun crossPoint(other: Line): Point {
        val x = ((other.b / cos(other.angle)) - (b / cos(angle))) / (tan(angle) - tan(other.angle))
        val y = (x * sin(other.angle) + other.b) / cos(other.angle)
        return Point(x, y)
    }

    override fun equals(other: Any?) = other is Line && angle == other.angle && b == other.b

    override fun hashCode(): Int {
        var result = b.hashCode()
        result = 31 * result + angle.hashCode()
        return result
    }

    override fun toString() = "Line(${Math.cos(angle)} * y = ${Math.sin(angle)} * x + $b)"
}

/**
 * Средняя
 *
 * Построить прямую по отрезку
 */
fun lineBySegment(s: Segment): Line {
    val tiltAngel = atan((s.end.y - s.begin.y) / (s.end.x - s.begin.x))
    return Line(s.begin, reduce(tiltAngel))
}

fun reduce(tiltAngle: Double):Double{
    var tiltAngle1=tiltAngle
    if (tiltAngle1 < 0) tiltAngle1 += PI
    if (tiltAngle1 >= PI) tiltAngle1  -= PI
    return tiltAngle1
}
/**
 * Средняя
 *
 * Построить прямую по двум точкам
 */
fun lineByPoints(a: Point, b: Point): Line = lineBySegment(Segment(a,b))

/**
 * Сложная
 *
 * Построить серединный перпендикуляр по отрезку или по двум точкам
 */
fun bisectorByPoints(a: Point, b: Point): Line {
    val c = Point((a.x + b.x) / 2, (a.y + b.y) / 2)
    val tiltAngel = PI / 2 + atan((a.y - b.y) / (a.x - b.x))
    return Line(c, reduce(tiltAngel))
}

/**
 * Средняя
 *
 * Задан список из n окружностей на плоскости. Найти пару наименее удалённых из них.
 * Если в списке менее двух окружностей, бросить IllegalArgumentException
 */
fun findNearestCirclePair(vararg circles: Circle): Pair<Circle, Circle> {
    require(circles.size >= 2)
    var min = Double.MAX_VALUE
    var o1 = circles[0]
    var o2 = circles[1]
    for (i in 0 until circles.size) {
        for (j in 1 until circles.size) {
            if (circles[i] != circles[j]) {
                val rx = sqr(circles[i].center.x - circles[j].center.x)
                val ry = sqr(circles[i].center.y - circles[j].center.y)
                var ro = Math.sqrt(rx + ry)
                ro = if (ro > circles[i].radius + circles[j].radius) ro - circles[i].radius - circles[j].radius
                else 0.0
                if (ro < min) {
                    min = ro
                    o1 = circles[i]
                    o2 = circles[j]
                }
            }
        }
    }
    return Pair(o1, o2)
}

/**
 * Сложная
 *
 * Дано три различные точки. Построить окружность, проходящую через них
 * (все три точки должны лежать НА, а не ВНУТРИ, окружности).
 * Описание алгоритмов см. в Интернете
 * (построить окружность по трём точкам, или
 * построить окружность, описанную вокруг треугольника - эквивалентная задача).
 */
fun circleByThreePoints(a: Point, b: Point, c: Point): Circle= TODO()
/**{
    val x =((((b.y-a.y)/(b.x-a.x))/((c.y-b.y)/(c.x-b.x)))*(a.y-c.y)+((c.y-b.y)/(c.x-b.x))*(a.x+b.x)-((b.y-a.y)/(b.x-a.x))*(b.x+c.x))/(2*(((c.y-b.y)/(c.x-b.x))-((b.y-a.y)/(b.x-a.x))))
    val y =(-1/((b.y-a.y)/(b.x-a.x)))*(x-(a.x+b.x)/2)+(a.y+b.y)/2
    val center=Point(x,y)
    val radius=Math.sqrt(sqr(a.x - center.x) + sqr(a.y - center.y))
    return Circle(center,radius)
}**/

/**
 * Очень сложная
 *
 * Дано множество точек на плоскости. Найти круг минимального радиуса,
 * содержащий все эти точки. Если множество пустое, бросить IllegalArgumentException.
 * Если множество содержит одну точку, вернуть круг нулевого радиуса с центром в данной точке.
 *
 * Примечание: в зависимости от ситуации, такая окружность может либо проходить через какие-либо
 * три точки данного множества, либо иметь своим диаметром отрезок,
 * соединяющий две самые удалённые точки в данном множестве.
 */
fun minContainingCircle(vararg points: Point): Circle = TODO()

