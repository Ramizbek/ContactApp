package ramizbek.aliyev.contactapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ramizbek.aliyev.contactapp.databinding.ItemRvBinding
import ramizbek.aliyev.contactapp.models.User

class RvAdapter(var list: ArrayList<User>, var rvAction: RvAction) : RecyclerView.Adapter<RvAdapter.VH>() {

    inner class VH(var itemRV: ItemRvBinding) : RecyclerView.ViewHolder(itemRV.root) {
        fun onBind(user: User) {
            itemRV.itemName.text = user.name
            itemRV.itemNumber.text = user.number

            itemRV.btnMore.setOnClickListener {
                rvAction.showPopupMenu(it, user)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size


}

interface RvAction{
    fun showPopupMenu(view:View, user:User)
}
