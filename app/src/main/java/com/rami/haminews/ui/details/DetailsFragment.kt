package com.rami.haminews.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.URLUtil
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.rami.haminews.R
import com.rami.haminews.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment(R.layout.fragment_details) {
    private val binding: FragmentDetailsBinding by viewBinding()
    private val bundleArgs: DetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getArgs()
    }

    private fun getArgs() {
        val articleArgs = bundleArgs.article
        articleArgs.let { article ->
            article.urlToImage?.let {
                Glide.with(this).load(article.urlToImage).into(binding.headerImage)
            }
            binding.headerImage.clipToOutline = true
            binding.articleDetailsTitle.text = article.title
            binding.articleDetailsDescriptionTv.text = article.description

            binding.articleDetailsButton.setOnClickListener {
                try {
                    Intent()
                        .setAction(Intent.ACTION_VIEW)
                        .addCategory(Intent.CATEGORY_BROWSABLE)
                        .setData(
                            Uri.parse(
                                takeIf { URLUtil.isValidUrl(article.url) }
                                    ?.let {
                                        article.url
                                    } ?: "https://google.com",
                            ),
                        )
                        .let {
                            ContextCompat.startActivity(requireContext(), it, null)
                        }
                } catch (e: Exception) {
                    Toast.makeText(
                        context,
                        "The device doesn't have any browser to view the document!",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
        }
    }
}
