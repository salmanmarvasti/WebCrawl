import java.io.InputStream
import java.math.BigInteger
import java.security.MessageDigest

sealed class Hasher {


    companion object {
        fun sha256(input: String): String {
            val md = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(input.toByteArray())
            return BigInteger(1, digest).toString(16)
        }

        fun getMD5Checksum(filename: InputStream): String {
            val md = MessageDigest.getInstance("MD5")
            return Utils.checksum(md, filename)
        }

        fun md5(input: String): String {
            val md = MessageDigest.getInstance("MD5")
            return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
        }
    }
}

