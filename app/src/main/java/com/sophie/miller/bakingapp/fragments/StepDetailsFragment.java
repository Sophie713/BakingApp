package com.sophie.miller.bakingapp.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.sophie.miller.bakingapp.DetailActivity;
import com.sophie.miller.bakingapp.R;
import com.sophie.miller.bakingapp.adapters.IngredientsAdapter;
import com.sophie.miller.bakingapp.databinding.FragmentStepDetailsBinding;
import com.sophie.miller.bakingapp.objects.IngredientObject;
import com.sophie.miller.bakingapp.objects.StepsObject;
import com.sophie.miller.bakingapp.utils.Const;
import com.sophie.miller.bakingapp.utils.GeneralUtils;
import com.sophie.miller.bakingapp.viewModel.MainViewModel;

import java.util.ArrayList;

import static com.google.android.exoplayer2.Player.STATE_BUFFERING;
import static com.google.android.exoplayer2.Player.STATE_ENDED;
import static com.google.android.exoplayer2.Player.STATE_IDLE;
import static com.google.android.exoplayer2.Player.STATE_READY;


public class StepDetailsFragment extends Fragment implements ExoPlayer.EventListener {

    //adapter of ingredients and ingredients
    private IngredientsAdapter ingredientsAdapter = new IngredientsAdapter();
    private ArrayList<IngredientObject> ingredients = new ArrayList<>();

    //parent activity and its viewmodel
    private DetailActivity activity;
    private MainViewModel viewModel;
    //binding
    private FragmentStepDetailsBinding binding;

    //default empty step, step I am on, 0 = ingredients = no video
    private StepsObject step = new StepsObject(0, "", "", "");
    private int currentStepPosition = 0;

    //exoplayer
    private SimpleExoPlayer simpleExoPlayer;
    //position where the video paused
    private long videoPosition = 0;
    private String stepVideoUrl = "";

    public StepDetailsFragment() {
        // Required empty public constructor
    }

    public static StepDetailsFragment newInstance() {
        return new StepDetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get video position if it has been saved
        if (savedInstanceState != null) {
            videoPosition = savedInstanceState.getLong(Const.videoPosition);
        }
        //get viemodel of my activity and retrieve data , observe selected step
        viewModel = ViewModelProviders.of(activity).get(MainViewModel.class);
        viewModel.getChosenStep().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (simpleExoPlayer != null)
                    simpleExoPlayer.release();
                currentStepPosition = integer;
                if (currentStepPosition == 0) {
                    ingredients.addAll(viewModel.getChosenRecipe().getIngredients());
                    ingredientsAdapter.setIngredients(ingredients);
                    ingredientsAdapter.notifyDataSetChanged();
                } else {
                    step = viewModel.getChosenRecipe().getSteps().get(currentStepPosition - 1);
                    GeneralUtils.log(step.getVideoURL());
                }
                //fill ui
                fillUI();
            }
        });

    }

    private void fillUI() {
        int orientation = activity.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT && activity.getResources().getConfiguration().smallestScreenWidthDp < 600) {
            binding.fragmentStepDetailsBackBTN.setOnClickListener(click);
            binding.fragmentStepDetailsNextBTN.setOnClickListener(click);
        }
        //if ingredients are visible hide other elements
        if (currentStepPosition == 0) {
            //show recycler view
            binding.fragmentStepDetailsIngredientsRV.setVisibility(View.VISIBLE);
            if (binding.fragmentStepDetailsMainLayoutCL != null) {
                binding.fragmentStepDetailsMainLayoutCL.setVisibility(View.GONE);
            } else {
                binding.fragmentStepDetailsPlayer.setVisibility(View.GONE);
            }
        } else {
            //if steps are visible hide ingredients
            binding.fragmentStepDetailsIngredientsRV.setVisibility(View.GONE);
            //show steps
            if (binding.fragmentStepDetailsMainLayoutCL != null) {
                binding.fragmentStepDetailsMainLayoutCL.setVisibility(View.VISIBLE);
            }
            binding.fragmentStepDetailsPlayer.setVisibility(View.VISIBLE);
            if (orientation == Configuration.ORIENTATION_PORTRAIT || activity.getResources().getConfiguration().smallestScreenWidthDp > 599) {
                binding.fragmentStepDetailsDescriptionTV.setText(step.getStepDescription());
            }
            //video is there, start it
            startVideo();
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
        //setup recyclerView
        binding.fragmentStepDetailsIngredientsRV.setLayoutManager(new LinearLayoutManager(activity));
        binding.fragmentStepDetailsIngredientsRV.hasFixedSize();
        binding.fragmentStepDetailsIngredientsRV.setAdapter(ingredientsAdapter);
    }

    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //release plaeyr if needed
            if (simpleExoPlayer != null) {
                simpleExoPlayer.release();
            }
            switch (v.getId()) {
                case R.id.fragment_step_details_back_BTN: {
                    if (currentStepPosition > 0)
                        viewModel.setChosenStep(currentStepPosition - 1);
                    else
                        GeneralUtils.toast(activity, getString(R.string.no_prev_steps));
                    break;
                }
                case R.id.fragment_step_details_next_BTN: {
                    if (currentStepPosition < viewModel.getChosenRecipe().getSteps().size())
                        viewModel.setChosenStep(currentStepPosition + 1);
                    else
                        GeneralUtils.toast(activity, getString(R.string.last_step));
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

    /**
     * stop player on activity paused
     * save position
     */
    @Override
    public void onPause() {
        super.onPause();
        videoPosition = simpleExoPlayer.getCurrentPosition();
        simpleExoPlayer.release();
    }

    /**
     * start playing the video again
     */
    @Override
    public void onResume() {
        super.onResume();
        startVideo();
    }

    /**
     * start playing (from where we left of)
     */
    private void startVideo() {
        //we are not in ingredients tab
        if (currentStepPosition != 0) {
            stepVideoUrl = step.getVideoURL();
            //step has a video
            if (stepVideoUrl.length() > 1) {
                binding.fragmentStepDetailsPlayer.setVisibility(View.VISIBLE);
                //release player
                if (simpleExoPlayer != null)
                    simpleExoPlayer.release();
                //setup player
                simpleExoPlayer = new SimpleExoPlayer.Builder(activity).build();
                simpleExoPlayer.addListener(this);
                // Produces DataSource instances through which media data is loaded.
                DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(activity,
                        Util.getUserAgent(activity, "BakingApp"));
                // This is the MediaSource representing the media to be played.
                MediaSource videoSource =
                        new ProgressiveMediaSource.Factory(dataSourceFactory)
                                .createMediaSource(Uri.parse(stepVideoUrl));
                // Prepare the player with the source.
                binding.fragmentStepDetailsPlayer.setPlayer(simpleExoPlayer);
                simpleExoPlayer.prepare(videoSource);
                simpleExoPlayer.seekTo(videoPosition);
                simpleExoPlayer.setPlayWhenReady(true);
            } else {
                binding.fragmentStepDetailsPlayer.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(Const.videoPosition, videoPosition);
    }

    /**
     * observe player states
     */
    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if (playbackState == STATE_IDLE)
            Log.e("xyz", "state idle");
        if (playbackState == STATE_BUFFERING) {
            //todo make loading screen
            Log.e("xyz", "state buffering");
        }
        if (playbackState == STATE_READY) {
            if (playWhenReady)
                Log.e("xyz", "state playing");
            else {
                videoPosition = simpleExoPlayer.getCurrentPosition();
                Log.e("xyz", "state paused");
            }
        }
        if (playbackState == STATE_ENDED) {
            videoPosition = 0;
            Log.e("xyz", "state ended");
        }
    }
}

