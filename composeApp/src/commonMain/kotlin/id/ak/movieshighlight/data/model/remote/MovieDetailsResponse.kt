package id.ak.movieshighlight.data.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsResponse(
    @SerialName("original_language")
    val originalLanguage: String? = null,

    @SerialName("imdb_id")
    val imdbId: String? = null,

    @SerialName("video")
    val video: Boolean? = null,

    @SerialName("title")
    val title: String? = null,

    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    @SerialName("revenue")
    val revenue: Int? = null,

    @SerialName("genres")
    val genres: List<GenreModel>? = null,

    @SerialName("popularity")
    val popularity: Double? = null,

    @SerialName("production_countries")
    val productionCountries: List<ProductionCountryModel>? = null,

    @SerialName("id")
    val id: Int? = null,

    @SerialName("vote_count")
    val voteCount: Int? = null,

    @SerialName("budget")
    val budget: Int? = null,

    @SerialName("overview")
    val overview: String? = null,

    @SerialName("original_title")
    val originalTitle: String? = null,

    @SerialName("runtime")
    val runtime: Int? = null,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("origin_country")
    val originCountry: List<String>? = null,

    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguageModel>? = null,

    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompanyModel>? = null,

    @SerialName("release_date")
    val releaseDate: String? = null,

    @SerialName("vote_average")
    val voteAverage: Double? = null,

    @SerialName("belongs_to_collection")
    val belongsToCollection: BelongsToCollectionModel? = null,

    @SerialName("tagline")
    val tagline: String? = null,

    @SerialName("adult")
    val adult: Boolean? = null,

    @SerialName("homepage")
    val homepage: String? = null,

    @SerialName("status")
    val status: String? = null
) {
    val fullPosterPath: String
        get() = buildString {
            append("https://image.tmdb.org/t/p/original")
            append(posterPath)
        }

    val genresDisplay: String?
        get() = genres?.mapNotNull { it.name }?.joinToString()

    val originCountriesDisplay: String?
        get() = originCountry?.joinToString()

    val productionCompaniesDisplay: String?
        get() = productionCompanies?.mapNotNull { it.name }?.joinToString()

    val productionCountriesDisplay: String?
        get() = productionCountries?.mapNotNull { it.name }?.joinToString()
}
