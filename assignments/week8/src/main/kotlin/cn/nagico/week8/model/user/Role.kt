package cn.nagico.week8.model.user

import javax.persistence.*

@Entity
open class Role (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    var name: String? = null,
    var remark: String? = null,

    @Convert(converter = StringListConverter::class)
    var permissions: MutableList<String>? = null

)