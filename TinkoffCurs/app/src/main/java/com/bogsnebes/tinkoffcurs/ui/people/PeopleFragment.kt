package com.bogsnebes.tinkoffcurs.ui.people

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bogsnebes.tinkoffcurs.R
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
        val progressBar: ProgressBar = view.findViewById(R.id.peopleProgressBar)
        val search: EditText = view.findViewById(R.id.searchPeopleEt)
        search.doAfterTextChanged {
            viewModel.searchProfile(it.toString())
        }
        val peopleAdapter = PeopleAdapter(view.context, mutableListOf()) {
            parentFragmentManager.setFragmentResult(
                PROFILE_OPEN_KEY,
                bundleOf(PROFILE_OPEN_KEY to it)
            )
        }
        recyclerPeople.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = peopleAdapter
        }

        viewModel.peopleScreenState.observe(viewLifecycleOwner) {
            when (it) {
                is PeopleScreenState.Result -> {
                    peopleAdapter.setItems(it.items)
                    recyclerPeople.scrollToPosition(0)
                    progressBar.isVisible = false
                }
                is PeopleScreenState.Loading -> {
                    progressBar.isVisible = true
                }
                else -> {
                    Toast.makeText(view.context, "Error", Toast.LENGTH_SHORT).show()
                    progressBar.isVisible = false
                }
            }
        }
    }

    companion object {
        const val PROFILE_OPEN_KEY: String = "PROFILE_OPEN_KEY"
        const val TAG: String = "PeopleFragment"
        fun newInstance() = PeopleFragment()
    }
}