package cn.nagico.week7.service;

import cn.nagico.week7.model.product.Product;
import cn.nagico.week7.model.product.ProductDto
import cn.nagico.week7.model.product.ProductVo
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author nagico
 * @since 2022-10-27
 */
interface IProductService : IService<Product> {
    fun queryProductList(condition: Map<String, Any>, page: Long, pageSize: Long): IPage<Product>

    fun getProductWithSupplier(id: Long): ProductDto

    fun addProduct(product: ProductVo): ProductDto

    fun removeProduct(id: Long)

    fun updateProduct(id: Long, product: ProductVo): ProductDto
}
