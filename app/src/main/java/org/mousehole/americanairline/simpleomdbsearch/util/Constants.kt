package org.mousehole.americanairline.simpleomdbsearch.util

object Constants {
    // retrofit constants
    const val BASE_URL = "https://www.omdbapi.com/"
    const val APIKEY = "apikey=bebd9632"
    const val SERIES_QUERY_PATH = "/?Type=series&$APIKEY"
    const val MOVIE_QUERY_PATH = "/?Type=movie&$APIKEY"
    const val SEARCH_PARAM = "t"
    const val SEASON_PARAM = "Season"
    const val EPISODE_PARAM = "Episode"

    // broadcast flags
    const val SEASON_BROADCAST = "season_broadcast"
    const val EPISODE_BROADCAST = "episode_broadcast"

    const val SEASON_DATA = "season_data"
    const val EPISODE_DATA = "episode_data"
}