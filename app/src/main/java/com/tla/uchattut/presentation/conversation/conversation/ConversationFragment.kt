package com.tla.uchattut.presentation.conversation.conversation

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.tla.uchattut.R
import com.tla.uchattut.presentation.conversation.dialogues.view.DialoguesFragment
import com.tla.uchattut.presentation.conversation.search_user.view.SearchUserFragment

class ConversationFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_conversation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dialoguesFragment = DialoguesFragment()

        childFragmentManager.beginTransaction()
            .add(R.id.fragmentConversationContainer, dialoguesFragment, DIALOGUES_FRAGMENT_TAG)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.conversation, menu)

        val searchView: SearchView = menu.findItem(R.id.actionSearch).actionView as SearchView

        searchView.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewDetachedFromWindow(arg0: View?) {
                hideSearchUserFragment()
            }

            override fun onViewAttachedToWindow(arg0: View?) {
                showSearchUserFragment()
            }
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val searchUserFragment =
                    childFragmentManager.findFragmentByTag(SEARCH_USER_FRAGMENT_TAG) as? SearchUserFragment
                searchUserFragment?.executeSearchQuery(newText)
                return false
            }
        })
    }

    private fun showSearchUserFragment() {
        val searchUserFragment = SearchUserFragment()
        childFragmentManager.beginTransaction()
            .add(R.id.fragmentConversationContainer, searchUserFragment, SEARCH_USER_FRAGMENT_TAG)
            .commit()
    }

    private fun hideSearchUserFragment() {
        val searchUserFragment = childFragmentManager.findFragmentByTag(SEARCH_USER_FRAGMENT_TAG)!!
        childFragmentManager.beginTransaction()
            .remove(searchUserFragment)
            .commit()

    }

    companion object {
        private const val SEARCH_USER_FRAGMENT_TAG = "SearchUserFragment"
        private const val DIALOGUES_FRAGMENT_TAG = "DialoguesFragment"

        const val TAG = "ConversationFragment"
    }
}