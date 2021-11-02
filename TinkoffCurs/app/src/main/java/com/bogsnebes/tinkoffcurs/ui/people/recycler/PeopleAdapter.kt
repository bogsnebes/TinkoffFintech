package com.bogsnebes.tinkoffcurs.ui.people.recycler

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
    private val profileList: MutableList<ProfileDto>,
    private val callbackProfile: (profile: ProfileDto) -> Unit
) : RecyclerView.Adapter<PeopleAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(context)
                .inflate(R.layout.item_people_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return profileList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        profileList[position].avatar?.let {
            holder.avatar.load(profileList[position].avatar) {
                transformations(
                    RoundedCornersTransformation(CORNERS_RADIUS)
                )
            }
        }
        if (profileList[position].online) {
            holder.online.load(R.drawable.ic_online)
        } else {
            holder.online.load(R.drawable.ic_offline)
        }
        holder.name.text = profileList[position].name
        holder.email.text = profileList[position].email
        holder.itemView.setOnClickListener {
            callbackProfile.invoke(profileList[position])
        }
    }

    fun setItems(newList: List<ProfileDto>) {
        profileList.clear()
        profileList.addAll(newList)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val avatar: ImageView = view.findViewById(R.id.avatarPeopleIv)
        val name: TextView = view.findViewById(R.id.namePeopleTv)
        val email: TextView = view.findViewById(R.id.emailPeopleTv)
        val online: ImageView = view.findViewById(R.id.onlinePeopleIv)
    }

    companion object {
        private const val CORNERS_RADIUS = 42f
    }
}