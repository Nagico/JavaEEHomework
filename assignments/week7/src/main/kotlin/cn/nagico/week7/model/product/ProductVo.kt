package cn.nagico.week7.model.product

class ProductVo : Product() {
    var suppliers: MutableList<Long> = mutableListOf()
}