package com.example.galeria
import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var pagerAdapter: GalleryPagerAdapter

    private val images = mutableListOf<Image>()

    companion object {
        private const val REQUEST_CODE_CAMERA = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)

        pagerAdapter = GalleryPagerAdapter(this, images)
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getString(pagerAdapter.getTitleResId(position))
        }.attach()

        // Dodaj przykładowe zdjęcia do galerii
        images.add(Image(R.drawable.photo1, "Opis zdjęcia 1", 0))
        images.add(Image(R.drawable.photo2, "Opis zdjęcia 2", 0))
        images.add(Image(R.drawable.photo3, "Opis zdjęcia 3", 0))
        images.add(Image(R.drawable.photo4, "Opis zdjęcia 3", 0))
        checkCameraPermission()
    }

    private fun checkCameraPermission() {
        val permission = Manifest.permission.CAMERA
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), REQUEST_CODE_CAMERA)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_CAMERA && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Uprawnienie do kamery zostało przyznane
            // Możesz tutaj uruchomić funkcję do robienia zdjęć
        }
    }

    fun takePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_CODE_CAMERA)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            val imageBitmap = data.extras?.get("data") as Bitmap?
            imageBitmap?.let {
                val imageUri = saveImageToGallery(it)
                imageUri?.let { uri ->
                    val image = Image(uri, "", 0)
                    images.add(image)
                    pagerAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun saveImageToGallery(bitmap: Bitmap): Uri? {
        val savedImageUri: Uri?
        val imageFileName = "IMG_${System.currentTimeMillis()}.jpg"
        val contentResolver = contentResolver
        val imageCollection = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, imageFileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }
        savedImageUri = contentResolver.insert(imageCollection, contentValues)
        savedImageUri?.let { uri ->
            contentResolver.openOutputStream(uri)?.use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            }
        }

        return savedImageUri
    }
}