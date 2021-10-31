package com.bogsnebes.tinkoffcurs.ui.people

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bogsnebes.tinkoffcurs.R
import com.bogsnebes.tinkoffcurs.data.dto.TestData
import com.bogsnebes.tinkoffcurs.ui.people.recycler.PeopleAdapter

class PeopleFragment : Fragment() {
    private lateinit var viewModel: PeopleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.people_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PeopleViewModel::class.java)
        val recyclerPeople: RecyclerView = view.findViewById(R.id.peopleRv)
        val peopleAdapter = PeopleAdapter(view.context, TestData.testPeopleList) {
            parentFragmentManager.setFragmentResult(
                PROFILE_OPEN_KEY,
                bundleOf(PROFILE_OPEN_KEY to it)
            )
        }
        recyclerPeople.layoutManager = LinearLayoutManager(view.context)
        recyclerPeople.adapter = peopleAdapter
    }

    companion object {
        const val PROFILE_OPEN_KEY: String = "PROFILE_OPEN_KEY"
        const val TAG: String = "PeopleFragment"
        fun newInstance() = PeopleFragment()
    }
}