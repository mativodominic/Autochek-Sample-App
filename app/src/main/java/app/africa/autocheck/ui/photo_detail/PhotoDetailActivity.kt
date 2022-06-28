package app.africa.autocheck.ui.photo_detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import androidx.viewpager.widget.PagerAdapter
import app.africa.autocheck.R
import app.africa.autocheck.core.framework.ui.BaseMvpActivity
import app.africa.autocheck.databinding.ActivityPhotoDetailBinding
import app.africa.autocheck.databinding.PhotoDetailItemBinding
import coil.load
import com.igreenwood.loupe.Loupe
import com.igreenwood.loupe.extensions.setOnViewTranslateListener

class PhotoDetailActivity : BaseMvpActivity(R.layout.activity_photo_detail) {

    companion object {
        const val EXTRA_MEDIA_URLS = "extra.media"
        const val EXTRA_POSITION = "extra.position"

        fun createIntent(context: Context, urls: ArrayList<String>, initialPos: Int): Intent {
            return Intent(context, PhotoDetailActivity::class.java).apply {
                putExtra(EXTRA_POSITION, initialPos)
                putExtra(EXTRA_MEDIA_URLS, urls)
            }
        }
    }

    private lateinit var adapter: ImageAdapter
    private lateinit var binding: ActivityPhotoDetailBinding
    private val urls: ArrayList<String> by lazy { intent.getSerializableExtra(EXTRA_MEDIA_URLS) as ArrayList<String> }
    private val initialPos: Int by lazy { intent.getIntExtra(EXTRA_POSITION, 0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolbar()
        initAdapter()
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            title = ""
        }

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun showToolbar() {
        binding.toolbar.animate()
            .setInterpolator(AccelerateDecelerateInterpolator())
            .translationY(0f)
    }

    private fun hideToolbar() {
        binding.toolbar.animate()
            .setInterpolator(AccelerateDecelerateInterpolator())
            .translationY(-binding.toolbar.height.toFloat())
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, R.anim.fade_out_fast)
    }

    override fun onBackPressed() {
        adapter.clear()
        super.onBackPressed()
    }

    private fun initAdapter() {
        adapter = ImageAdapter(this, urls)
        binding.viewpager.adapter = adapter
        binding.viewpager.currentItem = initialPos
        binding.viewpager.offscreenPageLimit = 5
    }

    inner class ImageAdapter(var context: Context, var urls: List<String>) : PagerAdapter() {

        private var views = hashMapOf<Int, ImageView>()
        private var currentPos = 0
        private var loupeMap = hashMapOf<Int, Loupe>()

        override fun getCount(): Int = urls.size

        override fun isViewFromObject(view: View, obj: Any) = view == obj

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val binding = PhotoDetailItemBinding.inflate(LayoutInflater.from(context))
            container.addView(binding.root)
            loadImage(binding.imageItem, container, position)
            views[position] = binding.imageItem
            return binding.root
        }

        private fun loadImage(imageItem: ImageView, container: ViewGroup, position: Int) {
            imageItem.load(urls[position]) {
                crossfade(true)
                allowHardware(true)

                listener(
                    onError = { _, result ->
                        result.throwable.printStackTrace()
                        startPostponedEnterTransition()
                    },
                    onSuccess = { _, result ->
                        Palette.Builder(result.drawable.toBitmap()).generate { palette ->
                            val context = binding.root.context
                            val bgColor = palette?.getDarkVibrantColor(
                                ContextCompat.getColor(
                                    context, R.color.featured_car_bg
                                )
                            )
                            if (bgColor != null) {
                                imageItem.setBackgroundColor(bgColor)
                            }
                        }

                        try {
                            val loupe = Loupe.create(imageItem, container) {
                                setOnViewTranslateListener(
                                    onStart = { hideToolbar() },
                                    onRestore = { showToolbar() },
                                    onDismiss = { finish() }
                                )
                            }

                            loupeMap[position] = loupe
                            if (position == initialPos) {
                                startPostponedEnterTransition()
                            }
                        } catch (ex: Exception) {
                            ex.printStackTrace()
                        }

                    }
                )
            }
        }

        fun clear() {
            loupeMap.forEach {
                val loupe = it.value
                loupe.cleanup()
            }
            loupeMap.clear()
        }

        override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
            container.removeView(obj as View)
        }

        override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
            super.setPrimaryItem(container, position, `object`)
            this.currentPos = position
        }

    }

}
