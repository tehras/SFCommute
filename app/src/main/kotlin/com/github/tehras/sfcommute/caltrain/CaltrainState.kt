package com.github.tehras.sfcommute.caltrain

import com.squareup.workflow.*
import okio.ByteString
import kotlin.reflect.jvm.jvmName

sealed class CaltrainState {
    object Home : CaltrainState()
    object LoadScheduleData : CaltrainState()

    fun toSnapshot(): Snapshot {
        return Snapshot.write { sink ->
            sink.writeUtf8WithLength(this::class.jvmName)
        }
    }

    companion object {
        fun fromSnapshot(byteString: ByteString): CaltrainState {
            byteString.parse { source ->
                val className = source.readUtf8WithLength()

                return when (className) {
                    Home::class.jvmName -> Home
                    else -> throw IllegalArgumentException("Unknown type $className")
                }
            }
        }
    }
}