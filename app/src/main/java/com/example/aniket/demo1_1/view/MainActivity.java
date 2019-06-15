package com.example.aniket.demo1_1.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aniket.demo1_1.Constants;
import com.example.aniket.demo1_1.R;
import com.example.aniket.demo1_1.repository.localStorage.entities.Category;
import com.example.aniket.demo1_1.viewmodel.CategoryViewModel;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Category> mainCatgeroy = new ArrayList<>();
    private RecyclerView categoryRecyclerView;
    private RecyclerView ranksRecyclerView;
    private CategoryViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {

        initRecyclerView();
        initData();
    }

    private void initData() {

        productViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        productViewModel.getMainCategory().observe(this, categories -> {

            if (categories != null && categories.size() > 0) {

                mainCatgeroy = categories;
                categoryRecyclerView.setAdapter(new CustomAdapter(mainCatgeroy));
                String[] ranks = new String[3];
                ranks[0] = (Constants.MOST_ORDERED_PRODUCTS);
                ranks[1] = (Constants.MOST_SHARED_PRODUCTS);
                ranks[2] = (Constants.MOST_VIEWED_PRODUCTS);
                ranksRecyclerView.setAdapter(new CustomAdapter(ranks));
            }
        });
    }

    private void initRecyclerView() {

        categoryRecyclerView = findViewById(R.id.recyc);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));

        ranksRecyclerView = findViewById(R.id.rankingrecyc);
        ranksRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
    }

    class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

        private String[] ranks;
        private ArrayList<Category> categories;
        private boolean isCategory;

        private CustomAdapter(ArrayList<Category> categories) {

            this.categories = categories;
            isCategory = true;
        }

        private CustomAdapter(String[] ranks) {

            this.ranks = ranks;
            isCategory = false;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new MyViewHolder(LayoutInflater.from(MainActivity.this)
                    .inflate(R.layout.recyclerview_item_layout, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

            if (isCategory) {

                handleCategory(myViewHolder, i);
            } else {

                handleRanks(myViewHolder, i);
            }
        }

        private void handleRanks(MyViewHolder myViewHolder, int i) {

            String name = ranks[i];
            myViewHolder.name.setText(name);
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(MainActivity.this, ProductDisplayActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("rank", name);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }

        private void handleCategory(MyViewHolder myViewHolder, int i) {

            Category category = categories.get(i);
            myViewHolder.name.setText(category.getName());
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Fragment fragment = new SubCategryFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("category", category);
                    fragment.setArguments(bundle);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.parentLayout, fragment, "SubCategryFragment")
                            .addToBackStack(null)
                            .commit();
                }
            });
        }

        @Override
        public int getItemCount() {

            if (isCategory) {

                return categories.size();
            } else {

                return ranks.length;
            }
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView name;
            View itemView;

            MyViewHolder(@NonNull View itemView) {

                super(itemView);
                this.itemView = itemView;
                name = itemView.findViewById(R.id.name);
            }
        }
    }
}
