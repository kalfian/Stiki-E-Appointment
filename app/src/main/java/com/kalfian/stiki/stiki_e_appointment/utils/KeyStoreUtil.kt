package com.kalfian.stiki.stiki_e_appointment.utils

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

class KeystoreUtils(private val context: Context) {

    private val keyStore: KeyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }

    fun storeKey(alias: String, data: ByteArray) {
        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        val keyGenSpec = KeyGenParameterSpec.Builder(alias, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
            .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
            .build()

        keyGenerator.init(keyGenSpec)
        val secretKey = keyGenerator.generateKey()

        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val encryptedData = cipher.doFinal(data)

        val encryptedDataString = Base64.encodeToString(encryptedData, Base64.DEFAULT)
        val preferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        preferences.edit().putString(alias, encryptedDataString).apply()
    }

    fun retrieveKey(alias: String): ByteArray? {
        val preferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val encryptedDataString = preferences.getString(alias, null) ?: return null
        val encryptedData = Base64.decode(encryptedDataString, Base64.DEFAULT)

        val secretKey = keyStore.getKey(alias, null) as? SecretKey ?: return null
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        cipher.init(Cipher.DECRYPT_MODE, secretKey)
        return cipher.doFinal(encryptedData)
    }
}