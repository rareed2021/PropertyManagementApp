package com.test.propertymanagementapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.test.propertymanagementapp.app.CustomApplication
import com.test.propertymanagementapp.data.models.enums.AccountType
import com.test.propertymanagementapp.databinding.FragmentRegisterBinding
import javax.inject.Inject


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val PARAM_TYPE = AccountType.KEY
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {

    private lateinit var viewModel: AuthViewModel
    private lateinit var binding : FragmentRegisterBinding
    @Inject
    lateinit var viewmodel : AuthViewModel
    var accountType: AccountType? = null
        private set
//    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            accountType = it.getSerializable(PARAM_TYPE) as? AccountType
        }
        (activity?.application as? CustomApplication)?.component
            ?.activityComponent?.create(this.activity as AppCompatActivity)
            ?.inject(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container,false)
        binding.viewmodel = viewModel
        binding.showLandlordEmail = accountType == AccountType.tenant
        binding.lifecycleOwner=activity
        init()
        //return inflater.inflate(R.layout.fragment_register, container, false)
        return binding.root
    }

    private fun init() {
        viewModel.currentAction.observe(viewLifecycleOwner){
            activity?.also { act->
                when(it){
                    AuthAction.REGISTER->Toast.makeText(activity, "Registered successfully", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.accountType.value = accountType
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LandlordRegisterFragment.
         */
        @JvmStatic
        fun newInstance(type: AccountType) =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(PARAM_TYPE,type)
                }
            }
    }
}