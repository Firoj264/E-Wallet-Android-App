package com.example.myecard.ewallet

import android.content.Intent

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_add_money.*


class AddMoneyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_money)

        // Action for add amount button
        addAmountBtn.setOnClickListener {

            // Checking if the amount textField is empty
            if (!amountTextField.text.isEmpty()) {

                // Initializing variables
                val database = FirebaseDatabase.getInstance()
                var amount = amountTextField.text.toString().toInt()

                // Setting firebase database reference
                var myRef = database.reference

                // fetching previous amount value from database.
                myRef.child("Users").child("UserID").child(FirebaseAuth.getInstance().currentUser!!.uid)
                    .addListenerForSingleValueEvent(object :ValueEventListener{
                        override fun onCancelled(error: DatabaseError) {}
                        override fun onDataChange(snapshot: DataSnapshot) {

                            // Setting amount value to amount variable
                            amount += snapshot!!.child("amount").value.toString().toInt()

                            // Setting amount to database.
                            myRef = database.reference.child("Users").child("UserID")
                                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                                .child("amount")

                            // Updating the amount in firebase database
                            myRef.setValue(amount).addOnSuccessListener {
                                Toast.makeText(this@AddMoneyActivity,"Money has been transferred to your wallet successfully.", Toast.LENGTH_LONG).show()
                                val homeActivityIntent = Intent(this@AddMoneyActivity, HomeActivity::class.java)
                                startActivity(homeActivityIntent)
                                finish()
                            }
                        }
                    })
            }else
            // Informing user if the amount textField is empty
                Toast.makeText(this@AddMoneyActivity,"Please enter the amount to be added", Toast.LENGTH_LONG).show()
        }
    }
}