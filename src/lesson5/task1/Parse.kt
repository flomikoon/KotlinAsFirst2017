@file:Suppress("UNUSED_PARAMETER")
package lesson5.task1

import lesson8.task2.parseExpr

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
fun dateStrToDigit(str: String): String {
    var itog = ""
    val mess = listOf<String>("января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря")
    val mesc = listOf<String>("01.", "02.", "03.", "04.", "05.", "06.", "07.", "08.", "09.", "10.", "11.", "12.")
    val red = mutableListOf<String>()
    var k = 0
    if (str.matches(Regex("""^\d{1,2} [а-я]{3,} \d+$"""))) {
        val parks = str.split(" ")
        for (park in parks) {
            red.add(park)
        }
        for (i in 0 until red.size) {
            val el = red[i]
            if (i == 0 && el.toInt() < 10) {
                itog = "0" + el.toInt().toString() + "."
            } else if (i == 1) {
                for (j in 0 until mess.size) {
                    val eld = mess[j]
                    if (eld == el) {
                        itog += mesc[j]
                        k += 1
                    }
                }
            } else if (i != 2) {
                itog = itog + el + "."
            } else {
                itog += el
            }
        }
    }
    return if (k == 0) "" else itog
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 */
fun dateDigitToStr(digital: String): String {
    var itog = ""
    val mess = listOf<String>("января ", "февраля ", "марта ", "апреля ", "мая ", "июня ", "июля ", "августа ", "сентября ", "октября ", "ноября ", "декабря ")
    val mesc = listOf<String>("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12")
    val red = mutableListOf<String>()
    var k = 0
    if (digital.matches(Regex("""^\d{2}.\d{2}.\d+$"""))) {
        val parks = digital.split(".")
        for (park in parks) {
            red.add(park)
        }
        for (i in 0 until red.size) {
            val el = red[i]
            if (i == 0 && el.toInt() < 10) {
                itog = itog + el.toInt().toString() + " "
            } else if (i == 1) {
                for (j in 0 until mesc.size) {
                    val eld = mesc[j]
                    if (eld == el) {
                        itog += mess[j]
                        k += 1
                    }
                }
            } else if (i != 2) {
                itog = itog + el + " "
            } else {
                itog += el
            }
        }
    }
    return if (k == 0) "" else itog
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
    var parts = phone.split("")
    val res = mutableListOf<String>()
    val resd = mutableListOf<Int>()
    try {
        parts = if (s == "+") {
            val p = phone.substring(1, phone.length).toString()
            p.split(")", "(", "-")
        } else {
            phone.split(")", "(", "-", " ")
        }
        for (part in parts) {
            res.add(part)
        }
        val r = res.joinToString(separator = "")
        parts = r.split(" ")
        for (part in parts) {
            val el = part.toInt()
            resd.add(el)
        }
        return if (s == "+") {
            resd.joinToString(prefix = "+", separator = "")
        } else resd.joinToString(separator = "")
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
    val parts = jumps.split(" ", "%", "-")
    var k = 0
    if (parts.joinToString(separator = "").matches(Regex("""\d+"""))) {
        for (part in parts) {
            if (part != "" && part.toInt() > k) {
                k = part.toInt()
            }
        }
    } else k -= 1
    return k
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
    val s = jumps.split(" ", "%", "-", "+")
    var k = 0
    val res = mutableListOf<String>()
    val red = mutableListOf<String>()
    if (s.joinToString(separator = "").matches(Regex("""\d+"""))) {
        val parts = jumps.split(" ")
        for (part in parts) {
            res.add(part)
        }
        for (i in 0 until res.size) {
            val el = res[i]
            if ("+" in el) {
                red.add(res[i - 1])
                for (j in 0 until red.size) {
                    val eld = red[j]
                    if (eld.toInt() > k) {
                        k = eld.toInt()
                    }
                }
            }

        }
        if (k == 0 && "+" !in jumps) {
            k -= 1
        }
    } else k -= 1
    return k
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
    var k = 0
    val res = mutableListOf<String>()
    val parts = expression.split(" ")
    require(parts.joinToString("").matches(Regex("""\d+([+-]\d+)*""")))
    for (part in parts) {
        res.add(part)
    }
    for (i in 0 until res.size) {
        if (i == 0) k = res[0].toInt()
        val el = res[i]
        if ("+" in el) k += res[i + 1].toInt()
        if ("-" in el) k -= res[i + 1].toInt()
    }
    return k
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
    val resnazv = mutableListOf<String>()
    val reschena = mutableListOf<String>()
    val reskon = mutableListOf<String>()
    var k = 0.0
    var l = 0
    if (description == "") return ""
    try {
        for (i in 0 until s.size step 2) {
            resnazv.add(s[i])
            reschena.add(s[i + 1])
        }
        for (j in 0 until reschena.size) {
            val eld = reschena[j]
            if (eld.toDouble() > k) {
                k = eld.toDouble()
                l = j
            }
        }
        val d = resnazv[l]
        reskon.add(d)
        return reskon.joinToString(
                separator = ""
        )
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
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> = TODO()
