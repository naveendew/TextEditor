package com.dewnaveen.texteditor.ui.Portfolio;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dewnaveen.texteditor.R;
import com.dewnaveen.texteditor.data.network.model.PortfolioResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PortfolioAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private ArrayList<PortfolioResponse.Apps> modelList;

    private OnItemClickListener mItemClickListener;


    public PortfolioAdapter(Context context, ArrayList<PortfolioResponse.Apps> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<PortfolioResponse.Apps> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_portfolio, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final PortfolioResponse.Apps model = getItem(position);
            ViewHolder genericViewHolder = (ViewHolder) holder;

//            genericViewHolder.itemTxtTitle.setText(model.getTitle());
//            genericViewHolder.itemTxtMessage.setText(model.getDescription());
            final PortfolioResponse.Apps apps = modelList.get(position);

//            int width_ = ScreenUtils.getScreenWidth(genericViewHolder.itemView.getContext());
            if (apps.getCoverImgUrl() != null) {
                Glide.with(genericViewHolder.itemView.getContext())
                        .load(apps.getCoverImgUrl())
                        .asBitmap()
                        .centerCrop()
                        .into(genericViewHolder.coverImageView);
            }

//            genericViewHolder.coverImageView.setBackgroundColor(rainbow[position]);
//            coverImageView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(),R.color.navy_blue));


            if (apps.getTitle() != null) {
                genericViewHolder.titleTextView.setText(apps.getTitle());
            }

            if (apps.getSubtitle() != null) {
                genericViewHolder.authorTextView.setText(apps.getSubtitle());
            }

            if (apps.getStage() != null) {
                genericViewHolder.dateTextView.setText(apps.getStage());
            }

            if (apps.getDescription() != null) {
                genericViewHolder.contentTextView.setText(apps.getDescription());
            }


        }
    }


    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private PortfolioResponse.Apps getItem(int position) {
        return modelList.get(position);
    }


    public void addItems(List<PortfolioResponse.Apps> data) {
        modelList.addAll(data);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, PortfolioResponse.Apps model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cover_image_view)
        ImageView coverImageView;
        @BindView(R.id.title_text_view)
        TextView titleTextView;
        @BindView(R.id.author_text_view)
        TextView authorTextView;
        @BindView(R.id.date_text_view)
        TextView dateTextView;
        @BindView(R.id.content_text_view)
        TextView contentTextView;
        @BindView(R.id.cv_holder)
        CardView cvHolder;

        public ViewHolder(final View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);


            itemView.setOnClickListener(view -> mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition())));

        }
    }

}

