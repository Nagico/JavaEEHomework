package cn.nagico.week8.repository

import cn.nagico.week8.model.supplier.Supplier
import org.springframework.data.domain.AbstractPageRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SupplierRepository : JpaRepository<Supplier, Long> {
    fun findByNameContains(name: String, pageable: Pageable) : Page<Supplier>
}