package com.bogsnebes.tinkoffcurs.ui.people

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.bogsnebes.tinkoffcurs.R
import com.bogsnebes.tinkoffcurs.data.dto.ProfileDto

class PeopleAdapter(
    private val context: Context,
    private val profileList: List<ProfileDto>,
    private val callback: (profile: ProfileDto) -> Unit
) : RecyclerView.Adapter<PeopleAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PeopleAdapter.ViewHolder {
        val view =
            LayoutInflater.from(context)
                .inflate(R.layout.item_people_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return profileList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.avatar.load(R.drawable.ic_people)
        profileList[position].avatar?.let {
            holder.avatar.load(profileList[position].avatar) {
                transformations(
                    RoundedCornersTransformation(CORNERS_RADIUS)
                )
            }
        }
        holder.name.text = profileList[position].name
        holder.email.text = profileList[position].email
        holder.itemView.setOnClickListener {
            callback.invoke(profileList[position])
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val avatar: ImageView = view.findViewById(R.id.avatarPeopleIv)
        val name: TextView = view.findViewById(R.id.namePeopleTv)
        val email: TextView = view.findViewById(R.id.emailPeopleTv)
    }

    companion object {
        private const val CORNERS_RADIUS = 42f
    }
}