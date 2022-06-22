package ramizbek.aliyev.contactapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import ramizbek.aliyev.contactapp.adapter.RvAction
import ramizbek.aliyev.contactapp.adapter.RvAdapter
import ramizbek.aliyev.contactapp.databinding.ActivityMainBinding
import ramizbek.aliyev.contactapp.databinding.ItemDialogBinding
import ramizbek.aliyev.contactapp.db.MyDBHelper
import ramizbek.aliyev.contactapp.models.User

class MainActivity : AppCompatActivity(), RvAction {
    //Binding
    private lateinit var binding: ActivityMainBinding
    private lateinit var myDBHelper: MyDBHelper
    private lateinit var list: ArrayList<User>
    private lateinit var rvAdapter: RvAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myDBHelper = MyDBHelper(this)

        list = myDBHelper.readUser() as ArrayList<User>

        //Equaling to RecyclerView Adapter
        rvAdapter = RvAdapter(list, this)
        binding.rv.adapter = rvAdapter

        //AlertDialog
        binding.btnAdd.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this).create()
            val itemDialog = ItemDialogBinding.inflate(layoutInflater)
            itemDialog.btnSave.setOnClickListener {
                val user = User(
                    itemDialog.edtName.text.toString(),
                    itemDialog.edtPhoneNumber.text.toString()
                )

                list.add(user)
                rvAdapter.notifyDataSetChanged()
                myDBHelper.createUser(user)
                Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show()
                alertDialog.cancel()
            }
            alertDialog.setView(itemDialog.root)
            alertDialog.show()
        }
    }

    override fun showPopupMenu(view: View, user: User) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.inflate(R.menu.my_popup_menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete -> {

                    myDBHelper.deleteUser(user)
                    val index = list.indexOf(user)
                    list.remove(user)
                    rvAdapter.notifyItemRemoved(index)

                    Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
                }
                R.id.edit -> {
                    val alertDialog = AlertDialog.Builder(this).create()
                    val itemDialog = ItemDialogBinding.inflate(layoutInflater)
                    itemDialog.edtName.setText(user.name)
                    itemDialog.edtPhoneNumber.setText(user.number)

                    itemDialog.btnSave.setOnClickListener {
                        user.name = itemDialog.edtName.text.toString()
                        user.number = itemDialog.edtPhoneNumber.text.toString()

                        var index = list.indexOf(user)

                        myDBHelper.updateUser(user)
                        list[index] = user
                        rvAdapter.notifyItemChanged(index)

                        Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show()
                        alertDialog.cancel()
                    }

                    alertDialog.setView(itemDialog.root)
                    alertDialog.show()
                }
            }
            true
        }
        popupMenu.show()

    }
}