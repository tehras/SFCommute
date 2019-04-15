package com.github.tehras.sfcommute.mainflow

import com.squareup.workflow.Snapshot
import com.squareup.workflow.parse
import com.squareup.workflow.readUtf8WithLength
import com.squareup.workflow.writeUtf8WithLength
import okio.ByteString
import kotlin.reflect.jvm.jvmName

sealed class MainState {
    internal object CalTrainHome : MainState()

    fun toSnapshot(): Snapshot {
        return Snapshot.write { sink -> sink.writeUtf8WithLength(this::class.jvmName) }
    }

    companion object {
        fun fromSnapshot(byteString: ByteString): MainState = byteString.parse {
            val mainStateName = it.readUtf8WithLength()

            return when (mainStateName) {
                CalTrainHome::class.jvmName -> CalTrainHome
                else -> throw IllegalArgumentException("Unrecognized state: $mainStateName")
            }
        }
    }
}