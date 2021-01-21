package org.mousehole.americanairline.simpleomdbsearch.model.season

data class SeasonResponse(
    val Episodes: List<Episode>,
    val Response: String,
    val Season: String,
    val Title: String,
    val totalSeasons: String
)