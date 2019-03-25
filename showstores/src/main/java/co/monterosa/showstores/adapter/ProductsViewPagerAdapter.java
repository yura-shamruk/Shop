package co.monterosa.showstores.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import co.monterosa.showstores.R;
import co.monterosa.showstores.model.ShopDetails;
import co.monterosa.showstores.model.ShopProduct;

public class ProductsViewPagerAdapter extends RecyclerView.Adapter<ProductsViewPagerAdapter.ViewHolder> {

    private LayoutInflater mInflater;

    @NonNull
    private Context context;

    @NonNull
    private List<ShopProduct> shopProducts;

    @NonNull
    private String currencySymbol;

    @Nullable
    private ItemClickListener mClickListener;

    public ProductsViewPagerAdapter(@NonNull Context context,
                                    @NonNull List<ShopProduct> shopProducts,
                                    @NonNull String currencySymbol) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.shopProducts = shopProducts;
        this.currencySymbol = currencySymbol;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.product_grid_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShopProduct shopProduct = shopProducts.get(position);
        String title = shopProduct.getTitle();
        String image = shopProduct.getImage();
        String price = "";
        if (shopProduct.getPrice() != null) {
            price = currencySymbol + shopProduct.getPrice().toString();
        }
        holder.priceTextView.setText(price);
        holder.productNameTextView.setText(title);
        holder.iconImageView.setImageDrawable(null);
        holder.productType.setText(shopProduct.getProductType());
        Picasso.with(context)
                .load(image)
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.iconImageView);
    }

    @Override
    public int getItemCount() {
        return shopProducts.size();
    }

    public ShopProduct getItem(int position) {
        return shopProducts.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView productNameTextView;
        TextView priceTextView;
        ImageView iconImageView;
        TextView productType;

        ViewHolder(View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            iconImageView = itemView.findViewById(R.id.iconImageView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            productType = itemView.findViewById(R.id.productType);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                int adapterPosition = getAdapterPosition();
                mClickListener.onItemClick(getItem(adapterPosition));
            }
        }
    }


    public void setClickListener(@Nullable ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(ShopProduct shopProduct);
    }
}
