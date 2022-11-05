package cn.nagico.week7.service.impl;

import cn.nagico.week7.model.supplier.Supplier;
import cn.nagico.week7.dao.SupplierMapper;
import cn.nagico.week7.service.ISupplierService;
import cn.nagico.week7.util.exception.ApiException
import cn.nagico.week7.util.response.ApiResponseType
import com.baomidou.mybatisplus.core.metadata.OrderItem
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author nagico
 * @since 2022-10-27
 */
@Service
open class SupplierServiceImpl : ServiceImpl<SupplierMapper, Supplier>(), ISupplierService {
    override fun querySupplierList(condition: Map<String, Any>, page: Long, pageSize: Long): IPage<Supplier> {
        val pageWrapper = Page<Supplier>(page, pageSize).addOrder(OrderItem("id", false))

        val queryWrapper = KtQueryWrapper(Supplier::class.java)
        if (condition.containsKey("name")) {
            queryWrapper.like(Supplier::name, condition["name"])
        }
        if (condition.containsKey("description")) {
            queryWrapper.like(Supplier::description, condition["description"])
        }

        return baseMapper.selectPage(pageWrapper, queryWrapper)
    }

    override fun getSupplier(id: Long): Supplier {
        return baseMapper.selectById(id) ?: throw ApiException(ApiResponseType.ID_NOT_EXIST, "id不存在")
    }

    @Transactional
    override fun addSupplier(supplier: Supplier): Supplier {
        supplier.createTime = LocalDateTime.now()
        supplier.updateTime = LocalDateTime.now()

        baseMapper.insert(supplier)

        return getSupplier(supplier.id!!)
    }

    override fun removeSupplier(id: Long) {
        baseMapper.deleteById(id)
    }

    override fun updateSupplier(id: Long, supplier: Supplier): Supplier {
        if (id != supplier.id)
            throw ApiException(ApiResponseType.PARAMETER_ERROR, "id不匹配")
        if (baseMapper.selectById(id) == null)
            throw ApiException(ApiResponseType.ID_NOT_EXIST, "id不存在")

        supplier.updateTime = LocalDateTime.now()
        baseMapper.updateById(supplier)
        return getSupplier(id)
    }

}
