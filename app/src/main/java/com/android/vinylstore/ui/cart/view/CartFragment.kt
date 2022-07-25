package com.android.vinylstore.ui.cart.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.vinylstore.R
import com.android.vinylstore.Root
import com.android.vinylstore.databinding.FragmentCartBinding
import com.android.vinylstore.ui.cart.adapter.CartItemsAdapter
import com.android.vinylstore.ui.cart.viewmodel.CartViewModel

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding

    lateinit var viewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appComponent = Root.getAppComponent(requireContext())
        viewModel = appComponent.getActivityMainComponent().getCartModelFactory()
            .create(CartViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.cartItems.observe(viewLifecycleOwner) {
            val adapter = CartItemsAdapter(it)
            binding.cartRecyclerView.adapter = adapter
        }

        val menu = binding.cartToolbar.menu
        binding.cartToolbar.setOnMenuItemClickListener {
            when (it) {
                menu.findItem(R.id.cart_menu_add) -> viewModel.addTestItem()
                menu.findItem(R.id.cart_menu_clear) -> viewModel.clearCart()
            }
            true
        }
    }

}