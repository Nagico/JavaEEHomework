package cn.nagico.week6.dao;

import cn.nagico.week6.model.product.Product;
import cn.nagico.week6.model.product.ProductDto
import cn.nagico.week6.model.supplier.Supplier
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author nagico
 * @since 2022-10-27
 */
@Mapper
interface ProductMapper : BaseMapper<Product> {
    @Select("SELECT s.* FROM supplier s, supplier_products sp WHERE s.id = sp.suppliers_id AND sp.products_id = #{id}")
    fun getSuppliersByProductId(id: Long): List<Supplier>

    @Select("INSERT INTO supplier_products (suppliers_id, products_id) VALUES (#{supplierId}, #{productId})")
    fun addSupplierToProduct(productId: Long, supplierId: Long)

    @Select("DELETE FROM supplier_products WHERE suppliers_id = #{supplierId} AND products_id = #{productId}")
    fun removeSupplierFromProduct(productId: Long, supplierId: Long)

    @Select("DELETE FROM supplier_products WHERE products_id = #{productId}")
    fun removeAllSuppliersFromProduct(productId: Long)
}
