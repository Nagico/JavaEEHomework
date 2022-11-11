package cn.nagico.week8.model.supplier

import io.swagger.v3.oas.annotations.media.Schema


@Schema(description = "供应商简要信息")
data class SupplierInfo (
    @Schema(description = "供应商ID", accessMode = Schema.AccessMode.READ_ONLY)
    var id: Long? = null,

    @Schema(description = "供应商名称")
    var name: String,
) {
    constructor(supplier: Supplier) : this(supplier.id, supplier.name)
}