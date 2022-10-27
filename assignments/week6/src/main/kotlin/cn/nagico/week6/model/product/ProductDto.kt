package cn.nagico.week6.model.product

import cn.nagico.week6.model.supplier.Supplier

class ProductDto(
    product: Product,
    var suppliers: MutableList<Supplier> = mutableListOf()
) : Product(product)