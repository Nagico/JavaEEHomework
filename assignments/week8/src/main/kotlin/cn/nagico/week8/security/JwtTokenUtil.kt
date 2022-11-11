package cn.nagico.week8.security

import cn.nagico.week8.model.user.UserDetail
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

/**
 * 解析和验证JWT令牌的工具类
 */
@Component
class JwtTokenUtil {
    /**
     * token的密钥
     */
    @Value("\${jwt.secret}")
    private val secret: String? = null
    @Value("\${jwt.expiration}")
    private val expiration: Long = 0

    /**
     * 从Token中获得Claims
     * @param token
     * @return
     */
    fun getClaimFromToken(token: String?): Claims {
        return Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token).body
    }

    /**
     * 生成Token
     * @param userDetails
     * @return
     */
    fun generateToken(userDetails: UserDetail): String {
        val claims: MutableMap<String, Any> = HashMap() //可以自由加入各种身份信息，如角色
        claims["id"] = userDetails.getId()
        claims["username"] = userDetails.username
        claims["roles"] = userDetails.getRolesList()

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(userDetails.getIdString())
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + expiration * 1000))
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact()
    }

    /**
     * 对Token进行验证
     * @param token
     * @param userDetails
     * @return
     */
    fun validateToken(token: String?, userDetails: UserDetail?): Boolean {
        return validateClaim(getClaimFromToken(token), userDetails)
    }

    /**
     * 对Claims进行验证
     * @param claim
     * @param userDetails
     * @return
     */
    fun validateClaim(claim: Claims, userDetails: UserDetail?): Boolean {
        //可以验证其他信息，如角色
        return (userDetails != null &&
                claim.subject.equals(userDetails.getIdString())
                && !claim.expiration.before(Date()))
    }

    fun canTokenBeRefreshed(token: String?): Boolean {
        return !isTokenExpired(token) || ignoreTokenExpiration(token)
    }

    fun isTokenExpired(token: String?): Boolean {
        val claim: Claims = getClaimFromToken(token)
        return claim.expiration.before(Date())
    }

    private fun ignoreTokenExpiration(token: String?): Boolean {
        // here you specify tokens, for that the expiration is ignored
        return false
    }
}