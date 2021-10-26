package com.bogsnebes.tinkoffcurs.ui.profile

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import coil.transform.RoundedCornersTransformation
import com.bogsnebes.tinkoffcurs.R
import com.bogsnebes.tinkoffcurs.data.dto.ProfileDto

class ProfileFragment : Fragment() {
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        val avatar: ImageView = view.findViewById(R.id.avatarProfileIv)
        val name: TextView = view.findViewById(R.id.nameProfileTv)
        val email: TextView = view.findViewById(R.id.emailProfileTv)
        val online: TextView = view.findViewById(R.id.onlineProfileTv)
        (arguments?.getSerializable(PROFILE) as? ProfileDto)?.let { profileDto ->
            profileDto.avatar?.let {
                avatar.load(profileDto.avatar) {
                    transformations(
                        RoundedCornersTransformation(CORNERS_RADIUS)
                    )
                }
            }
            name.text = profileDto.name
            email.text = profileDto.email
            if (profileDto.online) {
                online.text = view.context.getString(R.string.online)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    online.setTextColor(view.context.getColor(R.color.green))
                }
            } else {
                online.text = view.context.getString(R.string.online)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    online.setTextColor(view.context.getColor(R.color.red))
                }
            }
        }
    }

    companion object {
        private const val PROFILE: String = "PROFILE"
        private const val CORNERS_RADIUS = 16f
        fun newInstance(profileDto: ProfileDto) = ProfileFragment().apply {
            arguments = bundleOf(PROFILE to profileDto)
        }
    }
}