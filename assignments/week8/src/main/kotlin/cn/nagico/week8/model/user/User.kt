package cn.nagico.week8.model.user

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
open class User (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    var username: String? = null,

    var password: String? = null,

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.REFRESH])
    var roles: MutableSet<Role>? = null
)