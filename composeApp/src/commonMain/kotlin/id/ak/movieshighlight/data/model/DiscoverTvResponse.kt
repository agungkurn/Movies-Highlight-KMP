package id.ak.movieshighlight.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiscoverTvResponse(
    @SerialName("results")
    val results: List<DiscoverTvModel>? = null
) : ListResponse()
