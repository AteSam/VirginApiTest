package com.example.virginapitest.view

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.virginapitest.R
import com.example.virginapitest.adapter.PeopleListAdapter
import com.example.virginapitest.databinding.FragmentPeopleListBinding
import com.example.virginapitest.domain.entity.People
import com.example.virginapitest.viewmodel.PeopleEvent
import com.example.virginapitest.viewmodel.PeopleItemAction
import com.example.virginapitest.viewmodel.PeopleViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class PeopleListFragment : Fragment() {

    private lateinit var binding: FragmentPeopleListBinding

    private val peopleViewModel: PeopleViewModel by viewModels()

    private lateinit var adapter: PeopleListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true);
        binding = FragmentPeopleListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PeopleListAdapter {
            peopleViewModel.submitAction(PeopleItemAction.PersonItemClicked(it))
        }
        peopleViewModel.peopleEvent.onEach { processEvent(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
        peopleViewModel.submitAction(PeopleItemAction.FetchPerson)

        binding.roomsButton.setOnClickListener() {
            loadFragment()
        }
    }

    private fun loadFragment(){
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(android.R.id.content, RoomsListFragment())
        transaction.disallowAddToBackStack()
        transaction.commit()
    }

    private fun processEvent(event: PeopleEvent) {
        when (event) {
            PeopleEvent.PersonLoading -> {
                binding.apply {
                    progress.isVisible = true
                    error.isGone = true
                }
            }

            PeopleEvent.PersonError -> {
                binding.apply {
                    progress.isGone = true
                    error.isVisible = true
                }
            }

            is PeopleEvent.PersonLoaded -> {
                binding.apply {
                    progress.isGone = true
                    error.isGone = true
                }
                binding.peopleRecyclerView.adapter = adapter
                adapter.submitList(event.data)
            }

            is PeopleEvent.PersonItemClickEvent -> {
                showDialog(event.person)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.refreshmenu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.refresh -> {
                peopleViewModel.submitAction(PeopleItemAction.FetchPerson)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDialog(person: People) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(person.firstName)
        builder.setMessage(person.email)

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->

        }

        builder.show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.

         * @return A new instance of fragment PeopleListFragment.
         */
        // TODO: Rename and change types and number of parameters
        val TAG = PeopleListFragment::class.java.simpleName
        @JvmStatic
        fun newInstance() =
            PeopleListFragment()
    }
}