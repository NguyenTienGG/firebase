package com.example.b231

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
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

        //code delete
        binding.btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("empID").toString()
            )
        }
        //code update
        binding.btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("empID").toString(),
                intent.getStringExtra("empName").toString(),
            )
        }


    }

    private fun openUpdateDialog(empId: String, empName: String) {

        // Display dialog
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)
        mDialog.setView(mDialogView)

        //update info
        val etEmpName = mDialogView.findViewById<EditText>(R.id.etEmpName)
        val etEmpAge = mDialogView.findViewById<EditText>(R.id.etEmpAge)
        val etEmpSalary = mDialogView.findViewById<EditText>(R.id.etEmpSalary)
        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etEmpName.setText(intent.getStringExtra("empName"))
        etEmpAge.setText(intent.getStringExtra("empAge"))
        etEmpSalary.setText(intent.getStringExtra("empSalary"))

        mDialog.setTitle("Update $empName Info")
        val alertDialog = mDialog.create()
        alertDialog.show()

        // setonclicklistener btnupdate data
        btnUpdateData.setOnClickListener {
            UpdateEmployeeData(
                empId,
                etEmpName.text.toString(),
                etEmpAge.text.toString(),
                etEmpSalary.text.toString()
            )

            Toast.makeText(applicationContext, "Record Updated Successfully", Toast.LENGTH_SHORT).show()
                binding.tvEmpName.setText(etEmpName.text.toString())
                binding.tvEmpAge.setText(etEmpAge.text.toString())
                binding.tvEmpSalary.setText(etEmpSalary.text.toString())
            alertDialog.dismiss()
        }
    }

    private fun UpdateEmployeeData(id: String, name: String, age: String, salary: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Employees").child(id)
        val empInfo = EmployeeModel(id, name, age, salary)
        dbRef.setValue(empInfo)
    }

    private fun deleteRecord(id: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Employees").child(id)
        val mTask = dbRef.removeValue()
        mTask.addOnSuccessListener {
            Toast.makeText(this, "Record Deleted Successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener { err ->
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