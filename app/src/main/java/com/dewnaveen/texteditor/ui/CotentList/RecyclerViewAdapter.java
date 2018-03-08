package com.dewnaveen.texteditor.ui.CotentList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.widget.LinearLayoutManager;

import com.dewnaveen.texteditor.R;
import com.dewnaveen.texteditor.data.db.model.ContentListResponse;
import com.dewnaveen.texteditor.data.db.model.ContentListResponse;
import com.dewnaveen.texteditor.data.db.model.Data;
import com.dewnaveen.texteditor.utils.CommonUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Data> modelList;

    private OnItemClickListener mItemClickListener;


    public RecyclerViewAdapter(Context context, List<Data> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(List<Data> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycler_list, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ViewHolder) {
            final Data model = getItem(position);
            ViewHolder genericViewHolder = (ViewHolder) holder;

            String html_hdr = model.getHeader();
            Document doc_hdr = Jsoup.parse(html_hdr);
            genericViewHolder.itemTxtTitle.setText(doc_hdr.body().text());

            String html_content = model.getContent();
            Document doc_content = Jsoup.parse(html_content);
            genericViewHolder.itemTxtMessage.setText(doc_content.body().text());

            String date_ = CommonUtils.parseDateToddMMyyyy(model.getUpdated_at());
            genericViewHolder.itemTxtTime.setText(date_);

        }
    }


    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public void addItems(List<Data> data) {
        modelList.addAll(data);
        notifyDataSetChanged();
    }


    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private Data getItem(int position) {
        return modelList.get(position);
    }

    public void resetAdapter() {
        modelList.clear();
        notifyDataSetChanged();
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position, Data model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


         @BindView(R.id.img_user)
         ImageView imgUser;

         @BindView(R.id.item_txt_title)
         TextView itemTxtTitle;

         @BindView(R.id.item_txt_message)
         TextView itemTxtMessage;

         @BindView(R.id.item_txt_time)
         TextView itemTxtTime;

        public ViewHolder(final View itemView) {
            super(itemView);

             ButterKnife.bind(this, itemView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));


                }
            });

        }
    }

}

