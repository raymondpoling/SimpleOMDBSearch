package org.mousehole.americanairline.simpleomdbsearch.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import org.mousehole.americanairline.simpleomdbsearch.R
import org.mousehole.americanairline.simpleomdbsearch.model.movie.MovieResponse

class MovieFragment : Fragment() {

    private lateinit var posterImageView : ImageView
    private lateinit var movieTitleTextView : TextView
    private lateinit var synopsisTextView: TextView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.movie_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.let { v ->
            posterImageView = v.findViewById(R.id.movie_poster_imageview)
            movieTitleTextView = v.findViewById(R.id.movie_title_textview)
            synopsisTextView = v.findViewById(R.id.movie_synopsis_textview)

        }

    }

    fun changeMovie(movieResponse: MovieResponse) {
            movieResponse.let {
                movieTitleTextView.text = it.Title
                synopsisTextView.text = it.Plot

                Glide.with(this).load(movieResponse.Poster).into(posterImageView)

            }
    }

}