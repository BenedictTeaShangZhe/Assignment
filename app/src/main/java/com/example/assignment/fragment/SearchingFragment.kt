package com.example.assignment.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.assignment.R
import com.example.assignment.SearchingFoodActivity.listStation
import com.example.assignment.SearchingFoodActivity.listUserOrdered

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_searching, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnOrder = view.findViewById<Button>(R.id.btnOrder)
        val btnOrdered = view.findViewById<Button>(R.id.btnOrdered)

        btnOrder.setOnClickListener(){
            val intent = Intent(this.requireActivity(), listStation::class.java)
            startActivity(intent)}

        btnOrdered.setOnClickListener(){
            val intent = Intent(this.requireActivity(), listUserOrdered::class.java)
            startActivity(intent)}
    }
}