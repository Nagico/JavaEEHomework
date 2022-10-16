package cn.nagico.week4.util.pagenation

import java.lang.Integer.min
import java.net.URI

class PagedList<T>(page: Int, pageSize: Int, data: List<T>, uri: URI? = null) {
    val count: Int
    val previous: String?
    val next: String?
    val results: List<T>

    init {
        count = data.size
        val start = (page - 1) * pageSize
        val end = start + pageSize

        val baseUrl = if (uri == null) {
            null
        } else if (uri.port == -1 ) {
            uri.scheme + "://" + uri.host + uri.path
        } else {
            uri.scheme + "://" + uri.host + ":" + uri.port + uri.path
        }

        previous = if (baseUrl != null && page > 1) {
            "$baseUrl?page=${page - 1}&pageSize=$pageSize"
        } else {
            null
        }
        next = if (baseUrl != null && end < count) {
            "$baseUrl?page=${page + 1}&pageSize=$pageSize"
        } else {
            null
        }

        results = if (start >= count || end <= 0) {
            listOf()
        } else {
            data.subList(start, min(end, count))
        }
    }
}