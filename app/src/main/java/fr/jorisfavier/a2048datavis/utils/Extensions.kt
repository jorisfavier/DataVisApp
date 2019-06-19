package fr.jorisfavier.a2048datavis.utils

import java.text.SimpleDateFormat
import java.util.*

fun Date.year(): Int {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar.get(Calendar.YEAR)
}

fun Date.month(): Int {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar.get(Calendar.MONTH)
}

fun Date.dayOfMonth(): Int {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar.get(Calendar.DAY_OF_MONTH)
}

/**
 * Returns a String version of the date
 * @return a String based on the pattern MMM d, yyyy
 */
fun Date.formatStandard(): String {
    val formatter = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
    return formatter.format(this)
}

/**
 * Returns a String version of the date based on the AppAnnie server date pattern
 * @return a String following the yyyy-MM-dd pattern
 */
fun Date.formatForApi(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return formatter.format(this)
}