package ecl.ho.keysocalbum.ui.bookmark

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ecl.ho.keysocalbum.BuildConfig
import ecl.ho.keysocalbum.R
import timber.log.Timber


class ImageCardAdapter(
    private val items: List<CardItem>,
    private val mWidth: Int,
    private val mHeight: Int
) : RecyclerView.Adapter<ImageCardAdapter.ViewHolder>(), View.OnClickListener {

    private var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?): ImageCardAdapter {
        mOnItemClickListener = onItemClickListener
        return this
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (BuildConfig.DEBUG) {
            Timber.e("onCreateViewHolder: type:$viewType")
        }
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.vh_item_gallery, parent, false)
        v.setOnClickListener(this)
        v.layoutParams = RecyclerView.LayoutParams(mWidth, mHeight)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (BuildConfig.DEBUG) {
            Timber.d("onBindViewHolder: position:$position")
        }
        val item = items[position]
        val option = RequestOptions()
            .fitCenter()
            .placeholder(R.drawable.ic_baseline_cloud_download_24)

        Glide
            .with(holder.itemView)
            .load(item.url)
            .apply(option)
            .into(holder.image)
        holder.title.text = item.mName
        holder.itemView.tag = position
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onClick(v: View) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener!!.onItemClick(v, v.getTag() as Int)
        }
    }

    /**
     * @author chensuilun
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.imageView) as ImageView
        var title: TextView = itemView.findViewById(R.id.imgtitle) as TextView

    }

    /**
     * @author chensuilun
     */
    interface OnItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

    /**
     *
     */
    data class CardItem(var url: String, var mName: String)

    companion object {
        private const val TAG = "ImageCardAdapter"
    }
}