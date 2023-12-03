package de.kaiserdragon.anncharts

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class InfoDialogFragment : DialogFragment() {

    companion object {
        const val TAG = "InfoDialogFragment"
        private const val ARG_INFO_TEXT = "info_text"

        fun newInstance(infoText: String): InfoDialogFragment {
            val fragment = InfoDialogFragment()
            val args = Bundle()
            args.putString(ARG_INFO_TEXT, infoText)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Information")
        builder.setMessage(requireArguments().getString(ARG_INFO_TEXT, ""))
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        return builder.create()
    }
}
