package app.africa.autocheck

import android.app.Application
import app.africa.autocheck.core.framework.koin.localModule
import app.africa.autocheck.core.framework.koin.remoteModule
import app.africa.autocheck.core.framework.koin.repositoryModule
import app.africa.autocheck.core.framework.koin.rootModule
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.SvgDecoder
import coil.decode.VideoFrameDecoder
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.core.context.startKoin
import timber.log.Timber
import java.io.ByteArrayInputStream
import java.security.MessageDigest
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate

class App : Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()

        startKoin {

            // use the Android context given there
            androidContext(this@App)

            // load properties from assets/koin.properties file
            androidFileProperties()

            // load modules
            modules(listOf(rootModule, localModule, remoteModule, repositoryModule))
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .crossfade(true)
            .components {
                add(SvgDecoder.Factory())
                add(VideoFrameDecoder.Factory())
            }
            .build()
    }

    /**
     * Get fingerprint from a certificate in android.content.pm.Signature
     * @return fingerprint that contains the SHA-256 digest
     */
    private fun getFingerprint(ce: android.content.pm.Signature): String {
        var fingerprint = ""
        val input = ByteArrayInputStream(ce.toByteArray())
        var xc: X509Certificate? = null

        try {
            val cf = CertificateFactory.getInstance("X509")

            cf?.let {
                xc = cf.generateCertificate(input) as X509Certificate
            }

            val md: MessageDigest = MessageDigest.getInstance("SHA-256")
            xc?.let {
                val publicKey = md.digest(it.encoded)

                val hexString = StringBuilder()
                for (aPublicKeybyte in publicKey){
                    val appendString = Integer.toHexString(0xFF and aPublicKeybyte.toInt())
                    if (appendString.length == 1) hexString.append("0");
                    hexString.append(appendString)
                }

                fingerprint = hexString.toString()
            }

        } catch (ex: CertificateException) {
            ex.printStackTrace()
        }

        return fingerprint
    }

}