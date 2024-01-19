
import android.util.Log
import com.example.telead_xml.config.URLs
import com.example.telead_xml.domen.objects.ResponseData
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException

class GetBookmarkRepository {

    private val url = URLs().getBookmark

    fun request(token: String): ResponseData?{
        val client = OkHttpClient()

        val request = Request.Builder()
            .url(url)
            .get()
            .addHeader("accept", "application/json")
            .addHeader("Content-Type", "application/json")
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