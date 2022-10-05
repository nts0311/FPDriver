package com.sonnt.fpdriver.features._base

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.sonnt.fpdriver.generated.callback.OnClickListener

open class BaseActivity: AppCompatActivity() {
    fun createDialog(
        title: String = "Thông báo",
        content: String,
        positiveButtonTitle: String = "OK",
        negativeButtonTitle: String? = null,
        positiveClicked: (() -> Unit)? = null,
        negativeClicked: (() -> Unit)? = null,
    ) {
        val alert = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(content)
            .setPositiveButton(positiveButtonTitle) { _, _ ->
                positiveClicked?.let { it() }
            }
        negativeButtonTitle?.let { negativeButtonTitle ->
            alert.setNegativeButton(negativeButtonTitle) { _, _ ->
                negativeClicked?.let { it() }
            }
        }

        alert.create().show()
    }
}