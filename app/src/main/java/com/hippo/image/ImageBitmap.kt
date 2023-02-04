/*
 * Copyright 2022 Tarsin Norbin
 *
 * This file is part of EhViewer
 *
 * EhViewer is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * EhViewer is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with EhViewer.
 * If not, see <https://www.gnu.org/licenses/>.
 */
package com.hippo.image

import android.graphics.ImageDecoder
import android.graphics.drawable.Drawable
import java.io.FileInputStream
import kotlin.jvm.Throws

class ImageBitmap private constructor(image: Image) {
    private var mImage: Image? = image
    private var mReferences = 0

    @Synchronized
    fun obtain(): Boolean {
        return if (mImage?.isRecycled == true) {
            false
        } else {
            ++mReferences
            true
        }
    }

    @Synchronized
    fun release() {
        --mReferences
        if (mReferences <= 0 && !mImage!!.isRecycled) {
            mImage!!.recycle()
            mImage = null
        }
    }

    fun getDrawable(): Drawable {
        check(obtain()) { "Recycled!" }
        return mImage!!.mObtainedDrawable as Drawable
    }

    val width: Int
        get() = mImage!!.width

    val height: Int
        get() = mImage!!.height

    companion object {
        @Throws(ImageDecoder.DecodeException::class)
        @JvmStatic
        fun decode(stream: FileInputStream, hardware: Boolean): ImageBitmap {
            val image = Image.decode(stream, hardware)
            return ImageBitmap(image)
        }
    }
}