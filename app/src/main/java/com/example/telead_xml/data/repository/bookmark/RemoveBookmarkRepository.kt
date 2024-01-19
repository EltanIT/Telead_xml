
import android.util.Log
import com.example.telead_xml.config.URLs
import com.example.telead_xml.domen.objects.ResponseData
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException
import java.net.URL

class RemoveBookmarkRepository {

    private val urlString = URLs().removeBookmark

    fun request(id: String, token: String): ResponseData?{
        val client = OkHttpClient()
        val url = URL(urlString+id)

        val bodyMap = mapOf(
            "courseId" to id)
        val gson = Gson()

        val body = RequestBody.create("application/json".toMediaTypeOrNull(),gson.toJson(bodyMap))
        val request = Request.Builder()
            .url(url)
            .delete()
            .addHeader("accept", "application/json")
            .addHeader("Authorization", token)
            .build()
        try {
            client.newCall(request).execute().use { response ->
                Log.i("swagger", response.code.toString())
                return ResponseData(response, response.body?.string())
            }
        }catch (e: IOException){
            return null
        }
    }
}