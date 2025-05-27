package com.reactnativemlkitocr

import android.net.Uri                      
import com.facebook.react.bridge.*
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class TextRecognitionModule(reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {

  override fun getName(): String = "TextRecognitionModule"

  /**
   * imageUriString can be:
   *   • file://…  (camera)
   *   • content://… (gallery or newer CameraX)
   */
  @ReactMethod
  fun recognizeText(imageUriString: String, promise: Promise) {
    try {
      val uri = Uri.parse(imageUriString)

      val image = InputImage.fromFilePath(reactApplicationContext, uri)

      val recognizer = TextRecognition.getClient(
        TextRecognizerOptions.DEFAULT_OPTIONS
      )

      recognizer
        .process(image)
        .addOnSuccessListener { visionText ->
          promise.resolve(visionText.text)   // send text back to JS
        }
        .addOnFailureListener { e ->
          promise.reject("TEXT_RECOGNITION_FAILED", e)
        }

    } catch (e: Exception) {
      promise.reject("TEXT_RECOGNITION_INIT_FAILED", e)
    }
  }
}
