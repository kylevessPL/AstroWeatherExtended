package pl.piasta.astroweatherextended.ui.favourites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pl.piasta.astroweatherextended.R;

public class FavouritesRecyclerViewAdapter extends RecyclerView.Adapter<FavouritesRecyclerViewAdapter.ViewHolder> {

    private final List<FavouriteItem> mItems;
    private final ClickListener mClickListener;

    public FavouritesRecyclerViewAdapter(List<FavouriteItem> items, ClickListener clickListener) {
        mItems = items;
        mClickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favourite_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        FavouriteItem item = mItems.get(position);
        if (item.mSet) {
            holder.mFavouriteSet.setVisibility(View.GONE);
        } else {
            holder.mFavouriteSet.setVisibility(View.VISIBLE);
        }
        holder.mContent.setText(item.mValue);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final View mView;
        public final TextView mContent;
        public final Button mFavouriteSet;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContent = view.findViewById(R.id.content);
            mFavouriteSet = view.findViewById(R.id.favourite_set);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mContent.getText() + "'";
        }

        @Override
        public void onClick(final View view) {
            int id = view.getId();
            long itemId = getItemId();
            int position = getBindingAdapterPosition();
            if (id == R.id.favourite_set) {
                mItems.forEach(item -> item.mSet = false);
                mItems.get(position).mSet = true;
                mClickListener.onButtonClicked(itemId, position);
                notifyDataSetChanged();
            } else if (id == R.id.favourite_delete) {
                mItems.remove(position);
                mClickListener.onButtonClicked(itemId, position);
                notifyDataSetChanged();
            }
        }
    }
}