package com.example.tugaspresentasi.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.tugaspresentasi.data.repositories.UserRepository;
import com.example.tugaspresentasi.ui.viewmodels.RegistrationViewModel;

public class RegistrationViewModelFactory implements ViewModelProvider.Factory {
    private final UserRepository userRepository;

    public RegistrationViewModelFactory(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RegistrationViewModel.class)) {
            return (T) new RegistrationViewModel(userRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel Class");
    }
}
