package com.example.myecard.ewallet
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_add_money.*
import kotlinx.android.synthetic.main.activity_payy_bill.*
import kotlinx.android.synthetic.main.activity_send_money.*

class PayyBillActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payy_bill)

        sendButtonpay.setOnClickListener {

    // Checking if the amount textField is empty
    if (!payTextField.text.isEmpty()) {

        // Initializing variables
        val database = FirebaseDatabase.getInstance()
        var amount = payTextField.text.toString().toInt()

        // Setting firebase database reference
        var myRef = database.reference

        // fetching previous amount value from database.
        myRef.child("Users").child("UserID").child(FirebaseAuth.getInstance().currentUser!!.uid)
            .addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onCancelled(error: DatabaseError) {}
                override fun onDataChange(snapshot: DataSnapshot) {

                    // Setting amount value to amount variable
                    amount = snapshot!!.child("amount").value.toString().toInt()-amount

                    // Setting amount to database.
                    myRef = database.reference.child("Users").child("UserID")
                        .child(FirebaseAuth.getInstance().currentUser!!.uid)
                        .child("amount")

                    // Updating the amount in firebase database
                    myRef.setValue(amount).addOnSuccessListener {
                        Toast.makeText(this@PayyBillActivity,"Money has been cut off to your wallet successfully.", Toast.LENGTH_LONG).show()
                        val homeActivityIntent = Intent(this@PayyBillActivity, HomeActivity::class.java)
                        startActivity(homeActivityIntent)
                        finish()
                    }
                }
            })
    }else
    // Informing user if the amount textField is empty
        Toast.makeText(this@PayyBillActivity,"Please enter the amount to be added", Toast.LENGTH_LONG).show()
}
}
}