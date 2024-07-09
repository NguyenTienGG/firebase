package com.example.b231

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.b231.databinding.ActivityEmployeeDetailsBinding
import com.google.firebase.database.FirebaseDatabase

class EmployeeDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmployeeDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_employee_details)
        binding = ActivityEmployeeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setValueToView()
        binding.btnDelete.setOnClickListener{
            deleteRecord(intent.getStringExtra("empID").toString()
            )
        }
    }

    private fun deleteRecord(id: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Employees").child(id)
        val mTask =dbRef.removeValue()
        mTask.addOnSuccessListener {
            Toast.makeText(this, "Record Deleted Successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{err->
            Toast.makeText(this, " delete Error: ${err.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setValueToView() {
        binding.tvEmpId.text = intent.getStringExtra("empID")
        binding.tvEmpName.text = intent.getStringExtra("empName")
        binding.tvEmpAge.text = intent.getStringExtra("empAge")
        binding.tvEmpSalary.text = intent.getStringExtra("empSalary")
    }
}