package com.example.tugaspresentasi.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tugaspresentasi.data.repositories.UserRepository;
import com.example.tugaspresentasi.utils.SingleLiveEvent;

public class RegistrationViewModel extends ViewModel {
    private final MutableLiveData<Boolean> registrationSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final SingleLiveEvent<Void> navigateToHomeEvent = new SingleLiveEvent<>();
    private final UserRepository userRepository;

    public RegistrationViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(String email, String password, String username) {
        try {
            boolean success = userRepository.registerUser(email, password, username);

            if (success) {
                registrationSuccess.postValue(true);
                navigateToHomeEvent.call();
            }else {
                errorLiveData.postValue("Registration failed");
            }
        } catch (IllegalArgumentException e) {
            errorLiveData.postValue(e.getMessage());
        }
    }

    public LiveData<Boolean> getRegistrationSuccess() {
        return registrationSuccess;
    }
    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }
}
