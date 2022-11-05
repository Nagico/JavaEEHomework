package cn.nagico.week7.model.log

class ExceptionLogDto: RequestLog() {
    var classType: String? = null
    var exceptionCode: String? = null
    var exceptionType: String? = null
    var exceptionMsg: String? = null
    var exceptionStack: String? = null
}