package com.reactnativemlkitocr

import android.graphics.Bitmap
import android.graphics.BitmapFactory 
import android.net.Uri
import com.facebook.react.bridge.*
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.io.File
import java.io.InputStream

fun getBitmapFromPathOrUri(imagePath: String, context: ReactApplicationContext): Bitmap? {
    return try {
        val uri = if (imagePath.startsWith("file://") || imagePath.startsWith("content://")) {
            Uri.parse(imagePath)
        } else {
            Uri.fromFile(File(imagePath))
        }

        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        inputStream?.use {
            BitmapFactory.decodeStream(it)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

class TextRecognitionModule(reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {

  override fun getName(): String = "TextRecognitionModule"

  @ReactMethod
  fun recognizeText(imagePath: String, promise: Promise) {
    try {
        val bitmap = getBitmapFromPathOrUri(imagePath, reactApplicationContext)

        if (bitmap == null) {
            promise.reject("INVALID_IMAGE", "Failed to decode image from path or URI")
            return
        }

        val image = InputImage.fromBitmap(bitmap, 0)
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                promise.resolve(visionText.text)
            }
            .addOnFailureListener { e ->
                promise.reject("TEXT_RECOGNITION_FAILED", e)
            }

    } catch (e: Exception) {
        promise.reject("TEXT_RECOGNITION_INIT_FAILED", e)
    }
  }
}