package ecl.ho.keysocalbum.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.widget.AppCompatImageView
import timber.log.Timber
import java.io.BufferedInputStream
import java.lang.Exception

class ImageUtil {
    companion object {
        fun setBitmapFromImgPath(addImgPreview: AppCompatImageView, path: String) {
            try {
                val bitmap = getBitmap(addImgPreview.context, path)
                bitmap?.let {
                    addImgPreview.setImageBitmap(bitmap)
                }
            } catch (except: Exception) {
                Timber.e(except.message.toString())
            }
        }

        fun getBitmap(context: Context, path: String): Bitmap? {
            context.openFileInput(path).also { fileInputStream ->
                val bufferedInputStream = BufferedInputStream(fileInputStream)
                bufferedInputStream.use {
                    return try {
                        BitmapFactory.decodeStream(it)
                    } catch (ex: Exception) {
                        Timber.e(ex.message.toString())
                        null
                    }
                }
            }
        }
    }
}