package com.reactnativemlkitocr

import android.graphics.BitmapFactory
import com.facebook.react.bridge.*
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.io.File

class TextRecognitionModule(reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {

  override fun getName(): String {
    return "TextRecognitionModule"
  }

  @ReactMethod
  fun recognizeText(imagePath: String, promise: Promise) {
    try {
      val bitmap = BitmapFactory.decodeFile(imagePath)
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
