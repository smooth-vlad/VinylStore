package com.android.vinylstore

object Formatter {
    /**
     * Example: 2612351 -> 2 612 351
     */
    fun formatNumber(src: String): String {
        val result = StringBuilder()
        src.reversed().forEachIndexed { index, c ->
            if (index > 0 && index % 3 == 0)
                result.append(' ')
            result.append(c)
        }
        return result.reversed().toString()
    }
}