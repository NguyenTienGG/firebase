package com.example.b231

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.b231.databinding.ActivityInsertionBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {
    private lateinit var dbRef: DatabaseReference
    private lateinit var binding: ActivityInsertionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_insertion)
        binding = ActivityInsertionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbRef = FirebaseDatabase.getInstance().getReference("Employees")

        binding.btnSave.setOnClickListener {
            saveEmployeeData()
        }

    }

    private fun saveEmployeeData() {
        val empName = binding.edtEmpName.text.toString()
        val empAge = binding.edtEmpAge.text.toString()
        val empSalary = binding.edtEmpSalary.text.toString()
        val empID = dbRef.push().key!!
// kiểm tra các ô nhập liệu đã có dữ liệu hay chưa
        if(empName.isEmpty()){
            binding.edtEmpName.error = "Please Enter Employee Name"
            return
        }
        if(empAge.isEmpty()){
            binding.edtEmpAge.error = "Please Enter Employee Age"
            return
        }
        if(empSalary.isEmpty()){
            binding.edtEmpSalary.error = "Please Enter Employee Salary"
            return
        }


        val employee = EmployeeModel(empID, empName, empAge, empSalary)
        dbRef.child(empID).setValue(employee).addOnCompleteListener {
            binding.edtEmpName.text.clear()
            binding.edtEmpAge.text.clear()
            binding.edtEmpSalary.text.clear()
            binding.edtEmpName.requestFocus()
            Toast.makeText(this, "Employee Data Saved Successfully", Toast.LENGTH_SHORT).show()
        }

            .addOnFailureListener {
                Toast.makeText(this, "Failed to save Employee Data", Toast.LENGTH_SHORT).show()
            }
    }
}