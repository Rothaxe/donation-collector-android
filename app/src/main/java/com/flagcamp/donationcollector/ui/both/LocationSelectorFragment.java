package com.flagcamp.donationcollector.ui.both;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.flagcamp.donationcollector.R;
import com.flagcamp.donationcollector.databinding.FragmentLocationSelectorBinding;

public class LocationSelectorFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private FragmentLocationSelectorBinding binding;
    EditText streetInput;
    EditText aptNumberInput;
    EditText cityInput;
    Spinner stateSpinner;
    EditText zipcodeInput;
    TextView testText;
    String state;
    Button doneButton;
    Button cancelButton;
    String fullAddress;
    String imagePath;
    String[] schedulesArray;

    public LocationSelectorFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentLocationSelectorBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
        String fromLocation = LocationSelectorFragmentArgs.fromBundle(getArguments()).getFromLocation();
        streetInput = binding.streetInput;
        aptNumberInput = binding.aptNumberInput;
        cityInput = binding.cityInput;
        stateSpinner = (Spinner) binding.stateInput;
        zipcodeInput = binding.zipcodeInput;
        testText = binding.testText;
        doneButton = binding.doneButton;
        cancelButton = binding.cancelButton;

        imagePath = LocationSelectorFragmentArgs.fromBundle(getArguments()).getImagePath();
        schedulesArray = LocationSelectorFragmentArgs.fromBundle(getArguments()).getSchedules();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.states_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(adapter);
        stateSpinner.setOnItemSelectedListener(this);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Done Button", aptNumberInput.getText().toString());
                String aptNumber = aptNumberInput.getText().toString().equals("(Optional)") ? "" : aptNumberInput.getText().toString();
                fullAddress = (aptNumber.equals("") || aptNumber.equals("") ? "" : (aptNumber + ", ")) + streetInput.getText().toString() + ", " + cityInput.getText().toString() + ", "
                        + state + " " + zipcodeInput.getText().toString();
                testText.setText(fullAddress);

                LocationSelectorFragmentDirections.ActionTitleLocationToPostsPreview actionTitleLocationToPostsPreview =
                        LocationSelectorFragmentDirections.actionTitleLocationToPostsPreview();
                actionTitleLocationToPostsPreview.setLocation(fullAddress);
                actionTitleLocationToPostsPreview.setImagePath(imagePath);
                actionTitleLocationToPostsPreview.setSchedules(schedulesArray);
                NavHostFragment.findNavController(LocationSelectorFragment.this).navigate(actionTitleLocationToPostsPreview);
            }
        });



        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationSelectorFragmentDirections.ActionTitleLocationToPostsPreview actionTitleLocationSelectorToPostsPreview =
                        LocationSelectorFragmentDirections.actionTitleLocationToPostsPreview();
                actionTitleLocationSelectorToPostsPreview.setLocation(null);
                actionTitleLocationSelectorToPostsPreview.setImagePath(imagePath);
                actionTitleLocationSelectorToPostsPreview.setSchedules(schedulesArray);
                NavHostFragment.findNavController(LocationSelectorFragment.this).navigate(actionTitleLocationSelectorToPostsPreview);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        state = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        state = parent.getItemAtPosition(0).toString();
    }
}
