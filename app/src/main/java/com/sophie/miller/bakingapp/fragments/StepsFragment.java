package com.sophie.miller.bakingapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.sophie.miller.bakingapp.DetailActivity;
import com.sophie.miller.bakingapp.R;
import com.sophie.miller.bakingapp.adapters.StepsAdapter;
import com.sophie.miller.bakingapp.databinding.FragmentStepsBinding;
import com.sophie.miller.bakingapp.objects.RecipeObject;
import com.sophie.miller.bakingapp.utils.BaseListener;
import com.sophie.miller.bakingapp.viewModel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StepsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepsFragment extends Fragment implements BaseListener {

    private FragmentStepsBinding binding;
    private StepsAdapter stepsAdapter = new StepsAdapter(this);
    private MainViewModel viewModel;
    private DetailActivity activity = null;

    private ArrayList<String> steps = new ArrayList<>();

    public StepsFragment() {
        // Required empty public constructor
    }

    public static StepsFragment newInstance() {
        return new StepsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get viemodel of my activity and retrieve data
        viewModel = ViewModelProviders.of(activity).get(MainViewModel.class);
        viewModel.getRecipes().observe(this, new Observer<List<RecipeObject>>() {
            @Override
            public void onChanged(List<RecipeObject> recipeObjects) {
                viewModel.setChosenRecipe(recipeObjects.get(viewModel.getChosenRecipePosition()));
                activity.setTitle(viewModel.getChosenRecipe().getRecipeName());
                steps.clear();
                steps.add(0, getString(R.string.ingredients));
                for (int i = 0; i < viewModel.getChosenRecipe().getSteps().size(); i++) {
                    steps.add(viewModel.getChosenRecipe().getSteps().get(i).getStepTitle());
                }
                stepsAdapter.setSteps(steps);
                stepsAdapter.notifyDataSetChanged();

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_steps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentStepsBinding.bind(view);

        binding.fragmentStepsRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        binding.fragmentStepsRecyclerView.hasFixedSize();
        binding.fragmentStepsRecyclerView.setAdapter(stepsAdapter);
    }

    @Override
    public void setSelectedStep(int step) {
        viewModel.setChosenStep(step);
        //todo check if tablet or not
        activity.switchFragments(1);
    }

    //I know the context since I use the fragment only here
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (DetailActivity) context;
    }

    //avoid memory leaks
    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }
}