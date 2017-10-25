@file:Suppress("UNUSED_PARAMETER")
package lesson5.task1

import lesson8.task2.parseExpr
import kotlin.coroutines.experimental.EmptyCoroutineContext.plus

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main(args: Array<String>) {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        }
        else {
            println("Прошло секунд с начала суток: $seconds")
        }
    }
    else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку
 */
val mon = listOf<String>("января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря")
val monnumber = listOf<String>("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12")

fun dateStrToDigit(str: String): String {
    var result = ""
    var k = 0
    if (str matches Regex("""^\d{1,2} [а-я]{3,} \d+$""")) {
        val parks = str.split(" ")
        result = if (parks[0].toInt() < 10) {
            "0" + parks[0].toInt().toString() + "."
        } else parks[0] + "."
        for (j in 0 until mon.size) {
            if (mon[j] == parks[1]) {
                result += monnumber[j] + "."
                k += 1
            }
        }
        result += parks[2]
    }
    return if (k == 0) "" else result
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 */
fun dateDigitToStr(digital: String): String {
    var result = ""
    var k = 0
    if (digital matches Regex("""^\d{2}.\d{2}.\d+$""")) {
        val parks = digital.split(".")
        result = parks[0].toInt().toString() + " "
        for (j in 0 until monnumber.size) {
            if (monnumber[j] == parks[1]) {
                result += mon[j] + " "
                k += 1
            }
        }
        result += parks[2]
    }
    return if (k == 0) "" else result
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -98 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку
 */

fun flattenPhoneNumber(phone: String): String {
    if (phone == "") return ""
    val s = phone.substring(0, 1)
    val result = mutableListOf<Int>()
    try {
        val parts = if (s == "+") {
            val p = phone.substring(1, phone.length)
            p.split(")", "(", "-")
        } else {
            phone.split(")", "(", "-", " ")
        }
        val r = parts.joinToString(separator = "").split(" ")
        for (part in r) {
            result.add(part.toInt())
        }
        return if (s == "+") {
            result.joinToString(prefix = "+", separator = "")
        } else result.joinToString(separator = "")
    } catch (e: NumberFormatException) {
        return ""
    }
}


/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    var max = -1
    if (Regex("""[^ %\-\d]""").findAll(jumps).count() > 0) return max
    Regex("""\d+""").findAll(jumps).asIterable().map { it.value.toInt() }.forEach { max = Math.max(max, it) }
    return max
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    var max = 0
    if (jumps.split(" ", "%", "-", "+").joinToString(separator = "").matches(Regex("""\d+"""))) {
        val parts = jumps.split(" ")
        for (i in 0 until parts.size) {
            if ("+" in parts[i] && parts[i - 1].toInt() > max) {
                max = parts[i - 1].toInt()
            }
        }
        if (max == 0 && "+" !in jumps) {
            max -= 1
        }
    } else max -= 1
    return max
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    var result = 0
    require(expression matches Regex("""\d+( [+-] \d+)*"""))
    val parts=expression.split(" ")
    for (i in 0 until parts.size) {
        if (i == 0) result = parts[0].toInt()
        if ("+" == parts[i]) result += parts[i + 1].toInt()
        if ("-" == parts[i]) result -= parts[i + 1].toInt()
    }
    return result
}




/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int = TODO()

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62.5; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть положительными
 */
fun mostExpensive(description: String): String {
    val s = description.split(" ", "; ")
    var result = ""
    var max = -1.0
    if (description == "") return ""
    try {
        for (i in 0 until s.size step 2) {
            if (s[i + 1].toDouble() > max) {
                max = s[i+1].toDouble()
                result=s[i]
            }
        }
        return result
    } catch (e: NumberFormatException) {
        return ""
    }
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int = TODO()

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    var number=cells/2
    val com=commands.toMutableList()
    val list= mutableListOf<Int>()
    var k=0
    var i=0
    require(commands.replace(" ", "") matches Regex("""[><+-\[\]]*"""))
    val a = Regex("""\[""").findAll(commands, 0).count()
    val b = Regex("""\]""").findAll(commands, 0).count()
    require(a == b)
    for (l in 0 until cells){
        list.add(0)
    }
    //for (i in 0 until com.size) {
    while (i < com.size){
        k+=1
        val s=com[i]
        if (s == '>') number += 1
        if (s == '<') number -= 1
        if (s == '+') list[number] += 1
        if (s == '-') list[number] -= 1
        if (s=='[' && list[number]==0){
            for (j in i until com.size){
                if (com[j]==']'){
                    i=j
                    break
                }
            }
        }
        if (s==']'&&list[number]!=0){
            for (j in i downTo 0){
                if (com[j]=='['){
                    i=j
                    break
                }
            }
        }
        if (k==limit) break
        i+=1
    }
    return list
}