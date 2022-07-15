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
import com.example.virginapitest.adapter.RoomsListAdapter
import com.example.virginapitest.databinding.FragmentRoomsListBinding
import com.example.virginapitest.domain.entity.Rooms
import com.example.virginapitest.viewmodel.RoomsEvent
import com.example.virginapitest.viewmodel.RoomsItemAction
import com.example.virginapitest.viewmodel.RoomsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class RoomsListFragment : Fragment() {

    private lateinit var binding: FragmentRoomsListBinding

    private val roomsViewModel: RoomsViewModel by viewModels()

    private lateinit var adapter: RoomsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true);
        binding = FragmentRoomsListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = RoomsListAdapter {
            roomsViewModel.submitAction(RoomsItemAction.RoomItemClicked(it))
        }
        roomsViewModel.roomsEvent.onEach { processEvent(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
        roomsViewModel.submitAction(RoomsItemAction.FetchRoom)
    }


    private fun processEvent(event: RoomsEvent) {
        when (event) {
            RoomsEvent.RoomLoading -> {
                binding.apply {
                    progress.isVisible = true
                    error.isGone = true
                }
            }

            RoomsEvent.RoomError -> {
                binding.apply {
                    progress.isGone = true
                    error.isVisible = true
                }
            }

            is RoomsEvent.RoomLoaded -> {
                binding.apply {
                    progress.isGone = true
                    error.isGone = true
                }
                binding.roomsRecyclerView.adapter = adapter
                adapter.submitList(event.data)
            }

            is RoomsEvent.RoomItemClickEvent -> showDialog(event.room)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.refreshmenu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.refresh -> {
                roomsViewModel.submitAction(RoomsItemAction.FetchRoom)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDialog(room: Rooms) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(room.name)
        builder.setMessage(room.max_occupancy.toString())

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
        val TAG = RoomsListFragment::class.java.simpleName
        @JvmStatic
        fun newInstance() =
            RoomsListFragment()
    }
}