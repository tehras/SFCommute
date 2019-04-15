package com.github.tehras.sfcommute.caltrain

import com.github.tehras.sfcommute.service.caltrain.models.Route
import com.github.tehras.sfcommute.service.caltrain.models.RoutesResponse
import com.github.tehras.sfcommute.service.caltrain.models.StopsServedByRoute
import com.squareup.workflow.*
import okio.BufferedSource
import okio.ByteString
import java.nio.charset.Charset
import kotlin.reflect.jvm.jvmName

sealed class CaltrainState {
    data class Home(val routes: RoutesResponse) : CaltrainState()
    object LoadScheduleData : CaltrainState()

    fun toSnapshot(): Snapshot {
        return Snapshot.write { sink ->
            sink.writeUtf8WithLength(this::class.jvmName)

            when (this) {
                is Home -> {
                    sink.writeByteStringWithLength(this.routes.toSnapshot().bytes)
                }
                LoadScheduleData -> {
                    sink.writeUtf8WithLength(this::class.jvmName)
                }
            }
        }
    }

    companion object {
        fun fromSnapshot(byteString: ByteString): CaltrainState {
            byteString.parse { source ->
                val className = source.readUtf8WithLength()

                return when (className) {
                    Home::class.jvmName -> Home(routesResponseFromSnapshot(source))
                    else -> throw IllegalArgumentException("Unknown type $className")
                }
            }
        }
    }
}

fun routesResponseFromSnapshot(source: BufferedSource): RoutesResponse {
    return RoutesResponse(
        routes = source.readList {
            Route(
                name = source.readString(Charset.defaultCharset()),
                stopsServedByRoute = source.readList {
                    StopsServedByRoute(
                        stopName = source.readString(Charset.defaultCharset()),
                        stopId = source.readString(Charset.defaultCharset())
                    )
                }
            )
        }
    )
}

fun RoutesResponse.toSnapshot(): Snapshot {
    return Snapshot.write { sink ->
        sink.writeList(this.routes) { route ->
            sink.writeByteStringWithLength(route.toSnapshot().bytes)
        }
    }
}

fun Route.toSnapshot(): Snapshot {
    return Snapshot.write { sink ->
        sink.writeUtf8WithLength(this.name)
        sink.writeList(this.stopsServedByRoute) { stop ->
            sink.writeByteStringWithLength(stop.toSnapshot().bytes)
        }
    }
}

fun StopsServedByRoute.toSnapshot(): Snapshot {
    return Snapshot.write { sink ->
        sink.writeUtf8WithLength(this.stopId)
        sink.writeUtf8WithLength(this.stopName)

    }
}