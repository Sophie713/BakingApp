package com.sophie.miller.bakingapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.sophie.miller.bakingapp.databinding.ActivityDetailBinding;
import com.sophie.miller.bakingapp.fragments.StepDetailsFragment;
import com.sophie.miller.bakingapp.fragments.StepsFragment;
import com.sophie.miller.bakingapp.utils.Const;
import com.sophie.miller.bakingapp.utils.GeneralUtils;
import com.sophie.miller.bakingapp.viewModel.MainViewModel;

public class DetailActivity extends AppCompatActivity {


    private ActivityDetailBinding binding;
    private StepDetailsFragment stepDetailsFragment;
    private StepsFragment stepsFragment;

    private FragmentManager manager = getSupportFragmentManager();
    MainViewModel viewModel;
    int chosenRecipePosition = 0;
    private int selectedFragment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        //get the chosen recipe
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            chosenRecipePosition = intent.getIntExtra(Const.intentKeyChosenRecipe, 0);
            GeneralUtils.log("" + chosenRecipePosition);
            viewModel.setChosenRecipePosition(chosenRecipePosition);
        } else {
            chosenRecipePosition = savedInstanceState.getInt(Const.intentKeyChosenRecipe);
            GeneralUtils.log("" + chosenRecipePosition);
            viewModel.setChosenRecipePosition(chosenRecipePosition);
        }
        //initialize fragments
        stepDetailsFragment = StepDetailsFragment.newInstance();
        stepsFragment = StepsFragment.newInstance();
        //if I haven't had it opened, I open the list of steps
        //if it there are both fragments, I am not choosing so I also do this
        if (savedInstanceState == null||binding.activityDetailContainer2 != null) {
            //set my fragment visible
            manager.beginTransaction().
                    replace(R.id.activity_detail_container, stepsFragment).
                    commitNow();

            if (binding.activityDetailContainer2 != null) {
                manager.beginTransaction().
                        replace(R.id.activity_detail_container2, stepDetailsFragment).
                        commitNow();
            }
        } else {
            //I have switched fragments, return my state
            selectedFragment = savedInstanceState.getInt(Const.selectedFragment);
            switchFragments(selectedFragment);
        }

    }

    /**
     * function to switch fragments
     * 0 = stepsFragment = default, all steps listed
     * 1 =  stepDetailsFragment = single chosen step displayed
     * only used on small screens
     * @param fragmentId 1 or 0
     */
    public void switchFragments(int fragmentId) {
        selectedFragment = fragmentId;
        if (binding.activityDetailContainer2 == null) {
            Fragment fragment = fragmentId == 0 ? stepsFragment : stepDetailsFragment;
            manager.beginTransaction().replace(R.id.activity_detail_container, fragment).commit();
        }
    }

    /**
     * save stated on config changes
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(Const.intentKeyChosenRecipe, chosenRecipePosition);
        outState.putInt(Const.selectedFragment, selectedFragment);
        super.onSaveInstanceState(outState);
    }
}
