package cn.nagico.week7.model.product

import cn.nagico.week7.model.supplier.Supplier

class ProductDto(product: Product) : Product() {
    var suppliers: MutableList<Supplier> = mutableListOf()

    init {
        this.id = product.id
        this.name = product.name
        this.description = product.description
        this.image = product.image
        this.price = product.price
        this.createTime = product.createTime
        this.updateTime = product.updateTime
    }
}