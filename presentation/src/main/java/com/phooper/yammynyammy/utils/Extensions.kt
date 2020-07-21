package com.phooper.yammynyammy.utils

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.ph00.domain.models.*
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.entities.*
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Replace
import java.text.DateFormat
import java.util.*
import java.util.regex.Pattern

fun TextInputEditText.setHideLayoutErrorOnTextChangedListener(textInputLayout: TextInputLayout) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            textInputLayout.error = null
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}

fun Context.hideKeyboardKtx(view: View) {
    (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow(view.windowToken, 0)
}

fun Fragment.hideKeyboardKtx() {
    view?.let { activity?.hideKeyboardKtx(it) }
}

fun Fragment.setAppBarTitle(titleResId: Int) {
    (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(titleResId)
}

fun Fragment.hideAppBar() {
    (requireActivity() as AppCompatActivity).supportActionBar?.hide()
}


fun Fragment.showAppBar() {
    (requireActivity() as AppCompatActivity).supportActionBar?.show()
}

fun Activity.showMessageKtx(msg: String) =
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
//    Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT).show()

//fun Activity.showMessageKtx(msg: String) =
//    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

fun Fragment.showMessageKtx(msg: String) =
    requireActivity().showMessageKtx(msg)

fun Fragment.showMessageAboveBottomNav(msgRes: Int) =
    Snackbar.make(
        requireActivity().findViewById(R.id.coordinator_layout),
        msgRes,
        Snackbar.LENGTH_SHORT
    ).show()

fun Date?.formatToString(): String = if (this == null) "" else
    DateFormat.getDateTimeInstance(
        DateFormat.MEDIUM,
        DateFormat.SHORT,
        Locale("en")
    ).format(this)

fun String.isEmailValid() =
    Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    ).matcher(this).matches()

fun AddressModel.toPresentation() =
    Address(id = uid, street = street, houseNum = houseNum, apartNum = apartNum)

fun ProductModel.toPresentation(): Product =
    Product(id = id, imageURL = imageURL, title = title, desc = desc, price = price)

fun CartProductModel.toPresentation(): CartProduct =
    CartProduct(totalPrice = totalPrice, count = count, product = product.toPresentation())

fun UserModel.toPresentation(): User = User(phoneNum = phoneNum, name = name)

fun OrderAddressAndStatusModel.toPresentation(): OrderAddressAndStatus =
    OrderAddressAndStatus(address = address, status = status)

fun OrderModel.toPresentation(): Order =
    Order(
        id = uid,
        timestamp = timestamp,
        addressAndStatus = OrderAddressAndStatus(
            address = addressAndStatus.address,
            status = addressAndStatus.status
        ),
        products = products.map { it.toPresentation() },
        deliveryPrice = deliveryPrice,
        totalPrice = totalPrice
    )

fun formatAddress(houseNum: String, apartNum: String, street: String) =
    "Apt. $apartNum $houseNum $street"

fun Navigator.setLaunchScreen(screen: SupportAppScreen) {
    applyCommands(
        arrayOf(
            BackTo(null),
            Replace(screen)
        )
    )
}

fun Disposable.addTo(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}