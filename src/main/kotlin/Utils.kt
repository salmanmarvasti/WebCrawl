
import java.io.IOException
import java.io.InputStream
import java.security.MessageDigest

object Utils {
    @Throws(IOException::class, IOException::class)
    fun checksum(
        digest: MessageDigest,
        fis: InputStream
    ): String {
        // Get file input stream for reading the file
        // content
//        val fis = FileInputStream(file)

        // Create byte array to read data in chunks
        val byteArray = ByteArray(1024)
        var bytesCount = 0

        // read the data from file and update that data in
        // the message digest
        while (fis.read(byteArray).also { bytesCount = it } != -1) {
            digest.update(byteArray, 0, bytesCount)
        }

        // close the input stream
        fis.close()

        // store the bytes returned by the digest() method
        val bytes = digest.digest()

        // this array of bytes has bytes in decimal format
        // , so we need to convert it into hexadecimal format

        // for this we create an object of StringBuilder
        // since it allows us to update the string i.e. its
        // mutable
        val sb = StringBuilder()

        // loop through the bytes array
        for (i in bytes.indices) {

            // the following line converts the decimal into
            // hexadecimal format and appends that to the
            // StringBuilder object
            sb.append(
                Integer
                    .toString((bytes[i].toInt() and 0xff) + 0x100, 16)
                    .substring(1)
            )
        }

        // finally we return the complete hash
        return sb.toString()
    }
}