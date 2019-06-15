package com.example.aniket.demo1_1.view;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.aniket.demo1_1.R;
import com.example.aniket.demo1_1.models.UserFilterHistory;
import com.example.aniket.demo1_1.repository.localStorage.entities.Products;
import com.example.aniket.demo1_1.repository.localStorage.entities.Variants;

import java.util.ArrayList;
import java.util.Objects;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterFragment extends Fragment {

    private UserFilterHistory userFilterHistory = UserFilterHistory.getInstance();
    private ArrayList<String> userSelectedColor = new ArrayList<>();
    private ArrayList<Products> products = new ArrayList<>();
    private ArrayList<String> colors = new ArrayList<>();
    private ArrayList<Integer> sizes = new ArrayList<>();
    private Spinner sizeSpinner;
    private int originalMin = 0, originalMax = 0;
    private EditText minEdt, maxEdt;
    private View rootView;
    private RecyclerView recyclerView;

    public FilterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_filter, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {

        initViews();
        initData();
    }

    private void initData() {

        products = Objects.requireNonNull(getArguments()).getParcelableArrayList("products");
        getFilterData();
    }

    private void getFilterData() {

        Completable.fromAction(this::parseData)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> {

                    minEdt.setText(String.valueOf(originalMin));
                    maxEdt.setText(String.valueOf(originalMax));
                    recyclerView.getAdapter().notifyDataSetChanged();
                    if (userFilterHistory.getUserMin() > 0 && userFilterHistory.getUserMin() != originalMin) {

                        minEdt.setText(String.valueOf(userFilterHistory.getUserMin()));
                    }
                    if (userFilterHistory.getUserMax() > 0 && userFilterHistory.getUserMax() != originalMax) {

                        maxEdt.setText(String.valueOf(userFilterHistory.getUserMax()));
                    }
                    if (userFilterHistory.getSize() > 0) {

                        sizeSpinner.setSelection(sizes.indexOf(userFilterHistory.getSize()));
                    }
                }).subscribe();
    }

    private void parseData() {

        originalMin = products.get(0).getVariants().get(0).getPrice();
        originalMax = originalMin;
        for (Products produc : products) {

            for (Variants variants : produc.getVariants()) {

                int price = variants.getPrice();
                if (originalMin > price) {

                    originalMin = price;
                }
                if (originalMax < price) {

                    originalMax = price;
                }
                if (!colors.contains(variants.getColor())) {

                    colors.add(variants.getColor());
                }
                if (!sizes.contains(variants.getSize())) {

                    sizes.add(variants.getSize());
                }
            }
        }
        sizes.add(0, 0);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, sizes);
        sizeSpinner.setAdapter(adapter);
    }

    private void initViews() {

        sizeSpinner = rootView.findViewById(R.id.sizespinner);
        minEdt = rootView.findViewById(R.id.minEdt);
        maxEdt = rootView.findViewById(R.id.maxEdt);
        rootView.findViewById(R.id.applyTxt).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                setFilters();
                ((ProductDisplayActivity) getActivity()).sortProducts(products);
                getActivity().onBackPressed();
            }
        });
        rootView.findViewById(R.id.resetTxt).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                userFilterHistory.clearHistory();
                userSelectedColor.clear();
                ((ProductDisplayActivity) getActivity()).resetProducts();
                getActivity().onBackPressed();
            }
        });
        recyclerView = rootView.findViewById(R.id.recyc);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new ColorAdapter());
    }


    private void setFilters() {

        String minStr = minEdt.getText().toString().trim();
        String maxStr = maxEdt.getText().toString().trim();
        if (!TextUtils.isEmpty(minStr)) {

            if (!TextUtils.isEmpty(maxStr)) {

                int userMin = Integer.parseInt(minStr);
                int userMax = Integer.parseInt(maxStr);
                if (userMax != originalMax || userMin != originalMin) {

                    if (userMin <= userMax) {

                        if (userMin >= originalMin) {

                            if (userMax <= originalMax) {

                                userFilterHistory.setUserMin(userMin);
                                userFilterHistory.setUserMax(userMax);
                                for (int i = 0; i < products.size(); i++) {

                                    ArrayList<Variants> variantsArrayList = products.get(i).getVariants();
                                    for (int j = 0; j < variantsArrayList.size(); j++) {

                                        Variants variants = variantsArrayList.get(j);
                                        if (variants.getPrice() < userMin) {

                                            variantsArrayList.remove(j);
                                            j--;
                                        } else if (variants.getPrice() > userMax) {

                                            variantsArrayList.remove(j);
                                            j--;
                                        }
                                    }
                                    if (variantsArrayList.size() == 0) {

                                        products.remove(i);
                                        i--;
                                    }
                                }
                            } else {

                                Toast.makeText(getActivity(), "Maximum cant be greater than " + originalMax, Toast.LENGTH_SHORT).show();
                            }
                        } else {

                            Toast.makeText(getActivity(), "Minimum cant be less than " + originalMin, Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        Toast.makeText(getActivity(), "Min cant be grater than Max" + originalMin, Toast.LENGTH_SHORT).show();
                    }
                }
            } else {

                Toast.makeText(getActivity(), "Enter maximum price", Toast.LENGTH_SHORT).show();
            }
        } else {

            Toast.makeText(getActivity(), "Enter minimum price", Toast.LENGTH_SHORT).show();
        }

        int size = (int) sizeSpinner.getSelectedItem();
        if (size != 0) {

            userFilterHistory.setSize(size);
            for (int i = 0; i < products.size(); i++) {

                ArrayList<Variants> variantsArrayList = products.get(i).getVariants();
                for (int j = 0; j < variantsArrayList.size(); j++) {

                    Variants variants = variantsArrayList.get(j);
                    if (size != variants.getSize()) {

                        variantsArrayList.remove(j);
                        j--;
                    }
                }
                if (variantsArrayList.size() == 0) {

                    products.remove(i);
                    i--;
                }
            }
        }
        if (userSelectedColor.size() > 0) {

            userFilterHistory.getColors().clear();
            userFilterHistory.setColors(userSelectedColor);
            for (int i = 0; i < products.size(); i++) {

                ArrayList<Variants> variantsArrayList = products.get(i).getVariants();
                for (int j = 0; j < variantsArrayList.size(); j++) {

                    Variants variants = variantsArrayList.get(j);
                    if (!userSelectedColor.contains(variants.getColor())) {

                        variantsArrayList.remove(j);
                        j--;
                    }
                }
                if (variantsArrayList.size() == 0) {

                    products.remove(i);
                    i--;
                }
            }
        }
    }

    class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.MyViewHolder> {

        @NonNull
        @Override
        public ColorAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.color_item_layout, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ColorAdapter.MyViewHolder myViewHolder, int i) {

            myViewHolder.checkBox.setText(colors.get(i));
            myViewHolder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {

                if (isChecked) {

                    userSelectedColor.add(colors.get(i));
                } else {

                    userSelectedColor.remove(colors.get(i));
                }
            });
            if (userFilterHistory.getColors().contains(colors.get(i))) {

                myViewHolder.checkBox.setChecked(true);
            }
        }

        @Override
        public int getItemCount() {
            return colors.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            CheckBox checkBox;

            MyViewHolder(@NonNull View itemView) {
                super(itemView);
                checkBox = itemView.findViewById(R.id.colorchk);
            }
        }
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        ((ProductDisplayActivity)Objects.requireNonNull(getActivity())).showFab();
    }
}
