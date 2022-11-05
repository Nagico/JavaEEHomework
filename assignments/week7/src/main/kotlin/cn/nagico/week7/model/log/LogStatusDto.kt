package cn.nagico.week7.model.log

class LogStatusDto(
    var controllerMethod: String? = null,
    var count: Int? = null,
    var failedCount: Int? = null,
    var maxTime: Double? = null,
    var minTime: Double? = null,
    var avgTime: Double? = null,
)