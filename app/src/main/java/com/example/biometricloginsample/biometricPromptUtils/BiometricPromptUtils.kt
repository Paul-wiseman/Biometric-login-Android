package com.example.biometricloginsample.biometricPromptUtils

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.biometricloginsample.R

object BiometricPromptUtils {
    private const val TAG = "BiometricPromptUtils"

    fun createBiometricPrompt(
        activity:AppCompatActivity,
        processSuccess: (BiometricPrompt.AuthenticationResult) -> Unit
    ):BiometricPrompt{

        val execute = ContextCompat.getMainExecutor(activity)

        val callback =  object : BiometricPrompt.AuthenticationCallback(){
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Log.d(TAG, "errCode is $errorCode and errString is: $errString")
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Log.d(TAG, "Authentication was successful")
                processSuccess(result)
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Log.d(TAG, "User biometric rejected")
            }
        }

        return BiometricPrompt(activity, execute, callback)
    }

    fun createPromptInfo(activity: AppCompatActivity):BiometricPrompt.PromptInfo =
        BiometricPrompt.PromptInfo.Builder().apply {
            setTitle(activity.getString(R.string.prompt_info_title))
            setSubtitle(activity.getString(R.string.prompt_info_subtitle))
            setDescription(activity.getString(R.string.prompt_info_description))
            setConfirmationRequired(false)
            setNegativeButtonText(activity.getString(R.string.prompt_info_use_app_password))
        }.build()

}