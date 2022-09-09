package com.cubes.komentarapp.ui.subcategory;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cubes.komentarapp.data.model.domain.Category;
import com.cubes.komentarapp.data.source.datarepository.DataRepository;
import com.cubes.komentarapp.databinding.ActivitySubcategoryBinding;
import com.cubes.komentarapp.di.AppContainer;
import com.cubes.komentarapp.di.MyApplication;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class SubcategoryActivity extends AppCompatActivity {

    private ActivitySubcategoryBinding binding;
    private Category category = new Category();
    private DataRepository dataRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubcategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        dataRepository = appContainer.dataRepository;

        int categoryId = getIntent().getIntExtra("categoryId", -1);
        int subcategoryId = getIntent().getIntExtra("subcategoryId", -1);

        binding.imageViewBack.setOnClickListener(view -> finish());

        loadData(categoryId, subcategoryId);
    }


    private void loadData(int categoryId, int subcategoryId) {

        dataRepository.loadCategoryData(new DataRepository.CategoryResponseListener() {
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

                binding.viewPager.setCurrentItem(subcategoryPosition, false);

                binding.textViewCategoryTitle.setText(category.name);
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(SubcategoryActivity.this, "Došlo je do greške.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
