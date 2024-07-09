package com.example.b231

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.b231.adapter.EmpAdapter
import com.example.b231.databinding.ActivityFetchingBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FetchingActivity : AppCompatActivity() {
    private lateinit var ds: ArrayList<EmployeeModel>
    private lateinit var dbRef: DatabaseReference
    private lateinit var binding: ActivityFetchingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fetching)
        binding = ActivityFetchingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.rvEmp.layoutManager = LinearLayoutManager(this)
        binding.rvEmp.setHasFixedSize(true)
        ds = arrayListOf<EmployeeModel>()
        getThongTinNV()
    }

    private fun getThongTinNV() {
        binding.rvEmp.visibility=View.GONE
        binding.txtLoadingData.visibility=View.VISIBLE
        dbRef = FirebaseDatabase.getInstance().getReference("Employees")
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                ds.clear()
                if (snapshot.exists()){
                    for (empSnapshot in snapshot.children){
                        val emp = empSnapshot.getValue(EmployeeModel::class.java)
                        ds.add(emp!!)
                    }
                    val mAdapter = EmpAdapter(ds)
                    binding.rvEmp.adapter = mAdapter

                    mAdapter.setOnClickListener(object : EmpAdapter.OnItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@FetchingActivity, EmployeeDetailsActivity::class.java)
                            intent.putExtra("empID", ds[position].empID)
                            intent.putExtra(("empName"), ds[position].empName)
                            intent.putExtra(("empAge"), ds[position].empAge)
                            intent.putExtra(("empSalary"), ds[position].empSalary)

                            startActivity(intent)
                        }
                    })


                    binding.rvEmp.visibility=View.VISIBLE
                    binding.txtLoadingData.visibility=View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        } )
    }
}