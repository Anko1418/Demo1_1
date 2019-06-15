package com.example.aniket.demo1_1.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aniket.demo1_1.ActivityCommunicator;
import com.example.aniket.demo1_1.R;
import com.example.aniket.demo1_1.repository.localStorage.entities.Category;
import com.example.aniket.demo1_1.repository.localStorage.entities.Products;
import com.example.aniket.demo1_1.repository.localStorage.entities.Variants;
import com.example.aniket.demo1_1.viewmodel.ProductViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class ProductDisplayActivity extends AppCompatActivity implements ActivityCommunicator {

    private ArrayList<Products> originalProducts = new ArrayList<>();
    private ArrayList<Products> tempProducts = new ArrayList<>();
    private RecyclerView recyclerView;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_display);
        init();
    }

    private void init() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {

            fab.hide();
            FilterFragment filterFragment = new FilterFragment();
            Bundle bundle = new Bundle();
            tempProducts.clear();
            for (Products produc : originalProducts) {

                tempProducts.add(produc.copy(produc));
            }
            bundle.putParcelableArrayList("products", tempProducts);
            filterFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.parent_layout, filterFragment, "FilterFragment")
                    .addToBackStack(null)
                    .commit();
        });
        initRecyclerView();
        initData();
    }

    private void initData() {

        Category category = Objects.requireNonNull(getIntent().getExtras())
                .getParcelable("subCategory");
        ProductViewModel productViewModel = ViewModelProviders.of(this)
                .get(ProductViewModel.class);
        if (category != null) {

            productViewModel.getCategoryBasedProducts(category.getName())
                    .observe(this, products -> {

                        if (products != null && products.size() > 0) {

                            for (Products produc : products) {

                                originalProducts.add(produc.copy(produc));
                            }
                            tempProducts.addAll(products);
                            recyclerView.setAdapter(new CustomAdapter(tempProducts));
                        }
                    });
        } else {

            String rank = getIntent().getExtras().getString("rank");
            productViewModel.getRankBasedProducts(rank)
                    .observe(this, products -> {

                        if (products != null && products.size() > 0) {

                            for (Products produc : products) {

                                originalProducts.add(produc.copy(produc));
                            }
                            tempProducts.addAll(products);
                            recyclerView.setAdapter(new CustomAdapter(tempProducts));
                        }
                    });
        }
    }

    private void initRecyclerView() {

        recyclerView = findViewById(R.id.recyc);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void sortProducts(ArrayList<Products> products) {

        Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
        if (products.size() == 0) {

            Toast.makeText(this, "No products available as per your filter criteria", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void resetProducts() {

        tempProducts.clear();
        for (Products produc : originalProducts) {

            tempProducts.add(produc.copy(produc));
        }
        Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
    }

    class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

        private ArrayList<Products> products;

        CustomAdapter(ArrayList<Products> products) {

            this.products = products;
        }

        @NonNull
        @Override
        public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            return new MyViewHolder(LayoutInflater.from(ProductDisplayActivity.this).inflate(R.layout.product_item_layout, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

            Products product = products.get(i);
            myViewHolder.nameTxt.setText(product.getName());
            StringBuilder sizes = new StringBuilder("Sizes: ");
            StringBuilder colors = new StringBuilder("Colors: ");
            StringBuilder prices = new StringBuilder("Prices: ");
            StringBuilder ranks = new StringBuilder("Ranks: ");
            for (int j = 0; j < product.getVariants().size(); j++) {

                Variants variants = product.getVariants().get(j);
                sizes.append(variants.getSize());
                colors.append(variants.getColor());
                prices.append(variants.getPrice());
                if (j != product.getVariants().size() - 1) {

                    sizes.append(", ");
                    colors.append(", ");
                    prices.append(", ");
                }
            }
            for (int j = 0; j < product.getRanks().size(); j++) {

                ranks.append(product.getRanks().get(j));
                if (j != product.getRanks().size() - 1) {

                    ranks.append(", ");
                }
            }
            myViewHolder.sizesTxt.setText(sizes);
            myViewHolder.colorsTxt.setText(colors);
            myViewHolder.pricesTxt.setText(prices);
            myViewHolder.ranksTxt.setText(ranks);
        }

        @Override
        public int getItemCount() {
            return products.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView nameTxt, sizesTxt, colorsTxt, pricesTxt, ranksTxt;

            MyViewHolder(@NonNull View itemView) {

                super(itemView);
                nameTxt = itemView.findViewById(R.id.name);
                pricesTxt = itemView.findViewById(R.id.prices);
                sizesTxt = itemView.findViewById(R.id.sizes);
                colorsTxt = itemView.findViewById(R.id.colors);
                ranksTxt = itemView.findViewById(R.id.ranks);
            }
        }
    }

    public void showFab() {

        fab.show();
    }
}
