package com.example.aniket.demo1_1.view;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aniket.demo1_1.R;
import com.example.aniket.demo1_1.repository.localStorage.entities.Category;
import com.example.aniket.demo1_1.viewmodel.CategoryViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubCategryFragment extends Fragment {

    private Category category;
    private View rootView;
    private CategoryViewModel productViewModel;
    private RecyclerView recyclerView;

    public SubCategryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_sub_categry, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        productViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        category = getArguments().getParcelable("category");
        if (category != null) {

            productViewModel.getSubCategories(category.getSubCategoryNames()).observe(this, new Observer<ArrayList<Category>>() {
                @Override
                public void onChanged(@Nullable ArrayList<Category> categories) {

                    if (categories != null && categories.size() > 0) {

                        recyclerView.setAdapter(new CustomAdapter(categories));
                    }
                }
            });
        }
        init();
    }

    private void init() {

        initViews();
    }

    private void initViews() {

        initRecyclerView();
    }

    private void initRecyclerView() {

        recyclerView = rootView.findViewById(R.id.recyc);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

        ArrayList<Category> categories;

        public CustomAdapter(ArrayList<Category> categories) {

            this.categories = categories;
        }

        @NonNull
        @Override
        public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.subcategory_item_layout, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder myViewHolder, int i) {

            Category category = categories.get(i);
            myViewHolder.nameTxt.setText(category.getName());
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getActivity(), ProductDisplayActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("subCategory", category);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return categories.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView nameTxt;

            MyViewHolder(@NonNull View itemView) {

                super(itemView);
                nameTxt = itemView.findViewById(R.id.name);
            }
        }
    }
}
