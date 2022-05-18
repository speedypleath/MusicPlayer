package com.example.androidmusicplayer.camera

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.io.File


@ExperimentalCoilApi
@ExperimentalCoroutinesApi
@ExperimentalPermissionsApi
@Composable
fun MainContent(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf(EMPTY_IMAGE_URI) }
    var painter = rememberAsyncImagePainter(imageUri)
    if (imageUri != EMPTY_IMAGE_URI) {
        Box(modifier = modifier) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = rememberAsyncImagePainter(imageUri),
                contentDescription = "Captured image"
            )
            Row {
                Button(
                    onClick = {
                        imageUri = EMPTY_IMAGE_URI
                    },
                    modifier = Modifier
                        .padding(20.dp),
                ) {
                    Text("Remove image")
                }
                Button(
                    onClick = {
                        val file = imageUri.encodedPath?.let { File(it) }
                        val photoURI: Uri = FileProvider.getUriForFile(
                            context,
                            context.applicationContext.packageName + ".provider",
                            file!!
                        )
                        val share = Intent(Intent.ACTION_SEND)
                        share.type = "image/jpg"
                        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        share.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
                        share.putExtra(Intent.EXTRA_STREAM, photoURI)
                        val intent = Intent.createChooser(share, "Share Image")
                        startActivity(context, intent, null)
                    },
                    modifier = Modifier
                        .padding(20.dp),
                ) {
                    Text("Share image")
                }
            }
        }
    } else {
        var showGallerySelect by remember { mutableStateOf(false) }
        if (showGallerySelect) {
            GallerySelect(
                modifier = modifier,
                onImageUri = { uri ->
                    showGallerySelect = false
                    imageUri = uri
                }
            )
        } else {
            Box(modifier = modifier) {
                CameraCapture(
                    modifier = modifier,
                    onImageFile = { file ->
                        imageUri = file.toUri()
                    }
                )
                Button(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(4.dp),
                    onClick = {
                        showGallerySelect = true
                    }
                ) {
                    Text("Select from Gallery")
                }
            }
        }
    }
}