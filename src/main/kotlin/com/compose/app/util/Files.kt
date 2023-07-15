package com.compose.app.util

import java.io.File
import java.util.*


enum class FileOrder {
    ASC, DESC
}

fun Array<File>.sortByName(order: FileOrder = FileOrder.ASC): Array<File> {
    Arrays.sort(this) { o1, o2 ->
        if (o1.isDirectory() && o2.isFile()) return@sort -1
        if (o1.isFile() && o2.isDirectory()) return@sort 1
        o1.getName().compareTo(o2.getName()) * when (order) {
            FileOrder.ASC -> 1
            FileOrder.DESC -> -1
        }
    }
    return this
}
