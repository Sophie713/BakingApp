package com.sophie.miller.bakingapp.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.sophie.miller.bakingapp.DetailActivity;
import com.sophie.miller.bakingapp.R;
import com.sophie.miller.bakingapp.databinding.FragmentStepDetailsBinding;
import com.sophie.miller.bakingapp.objects.IngredientObject;
import com.sophie.miller.bakingapp.objects.StepsObject;
import com.sophie.miller.bakingapp.utils.GeneralUtils;
import com.sophie.miller.bakingapp.viewModel.MainViewModel;

import java.util.ArrayList;


public class StepDetailsFragment extends Fragment {

    //classes
    private FragmentStepDetailsBinding binding;
    private StepsObject step = new StepsObject(0, "", "", "");
    private ArrayList<IngredientObject> ingredients = new ArrayList<>();
    private MainViewModel viewModel;
    private DetailActivity activity;
    private int currentStepPosition = 0;

    //video
    private SimpleExoPlayer player;
    private String videoUrl = "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4";

    public StepDetailsFragment() {
        // Required empty public constructor
    }

    public static StepDetailsFragment newInstance() {
        return new StepDetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get viemodel of my activity and retrieve data
        viewModel = ViewModelProviders.of(activity).get(MainViewModel.class);
        viewModel.getChosenStep().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                currentStepPosition = integer;
                if (currentStepPosition == 0) {
                    ingredients.addAll(viewModel.getChosenRecipe().getIngredients());
                } else {
                    step = viewModel.getChosenRecipe().getSteps().get(currentStepPosition);
                    GeneralUtils.log(step.getStepTitle());
                }
                fillUI();
            }
        });

    }

    private void fillUI() {
        int orientation = activity.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT||activity.getResources().getConfiguration().smallestScreenWidthDp >599) {
            binding.fragmentStepDetailsDescriptionTV.setText(step.getStepDescription());
            if (activity.getResources().getConfiguration().smallestScreenWidthDp < 600) {
                binding.fragmentStepDetailsBackBTN.setOnClickListener(click);
                binding.fragmentStepDetailsNextBTN.setOnClickListener(click);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_step_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentStepDetailsBinding.bind(view);


       /* try {
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveVideoTrackSelection.Factory(new DefaultBandwidthMeter()));
            player = ExoPlayerFactory.newSimpleInstance(this.getActivity(), trackSelector, new DefaultLoadControl());
            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(videoUrl), new DefaultHttpDataSourceFactory("factory"), new DefaultExtractorsFactory(), null, null);

            binding.fragmentStepDetailsPlayer.setPlayer(player);
            player.prepare(mediaSource);
            player.setPlayWhenReady(true);
        } catch (Exception e) {
            Log.e("xyz", e.toString() + " " + e.getMessage());
        }*/
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fragment_step_details_back_BTN: {
                    if (currentStepPosition > 0)
                        viewModel.setChosenStep(currentStepPosition - 1);
                    break;
                }
                case R.id.fragment_step_details_next_BTN: {
                    if (currentStepPosition < viewModel.getChosenRecipe().getSteps().size())
                        viewModel.setChosenStep(currentStepPosition + 1);
                    break;
                }
            }
        }
    };

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
