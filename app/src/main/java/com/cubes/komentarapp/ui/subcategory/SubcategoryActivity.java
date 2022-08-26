package com.cubes.komentarapp.ui.subcategory;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cubes.komentarapp.data.model.domain.Category;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.ActivitySubcategoryBinding;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class SubcategoryActivity extends AppCompatActivity {

    private ActivitySubcategoryBinding binding;
    private int categoryId;
    private int subcategoryId;
    private Category category = new Category();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubcategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        categoryId = getIntent().getIntExtra("categoryId", -1);
        subcategoryId = getIntent().getIntExtra("subcategoryId", -1);

        binding.imageViewBack.setOnClickListener(view -> finish());

        binding.refresh.setOnClickListener(view -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            loadData(categoryId, subcategoryId);
        });

        loadData(categoryId, subcategoryId);
    }


    private void loadData(int categoryId, int subcategoryId) {

        DataRepository.getInstance().loadCategoryData(new DataRepository.CategoryResponseListener() {
            @Override
            public void onResponse(ArrayList<Category> response) {
                for (int i = 0; i < response.size(); i++) {
                    if (response.get(i).id == categoryId) {
                        category = response.get(i);
                        break;
                    }
                }

                int subcategoryPosition = -1;


                for (int i = 0; i < category.subcategories.size(); i++) {
                    if (subcategoryId == category.subcategories.get(i).id) {
                        subcategoryPosition = i;
                        break;
                    }
                }

                int[] subcategoryIdList = new int[category.subcategories.size()];


                for (int i = 0; i < category.subcategories.size(); i++) {
                    subcategoryIdList[i] = category.subcategories.get(i).id;
                }

                SubcategoryPagerAdapter adapter = new SubcategoryPagerAdapter(SubcategoryActivity.this, subcategoryIdList, category.name);
                binding.viewPager.setAdapter(adapter);

                new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> tab.setText(category.subcategories.get(position).name)).attach();

                binding.viewPager.setCurrentItem(subcategoryPosition);

                binding.textViewCategoryTitle.setText(category.name);

                binding.refresh.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Throwable t) {
                binding.refresh.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);

                Toast.makeText(SubcategoryActivity.this, "Došlo je do greške.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
