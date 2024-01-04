package com.example.tugaspresentasi.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tugaspresentasi.data.models.User;
import com.example.tugaspresentasi.data.repositories.UserRepository;
import com.example.tugaspresentasi.utils.SingleLiveEvent;

public class LoginViewModel extends ViewModel {
    private final MutableLiveData<LoginState> loginState = new MutableLiveData<>();
    private final SingleLiveEvent<Void> navigateToRegister = new SingleLiveEvent<>();
    private final SingleLiveEvent<Void> navigateToHomeEvent = new SingleLiveEvent<>();
    private final UserRepository userRepository;

    public LoginViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void login(String username, String password) {
        new Thread(() -> {
            User user = userRepository.userLogin(username, password);

            if (user != null) {
                loginState.postValue(new LoginState.Success(user));
                navigateToHomeEvent.call();
            } else {
                loginState.postValue(new LoginState.Error("Invalid username or password"));
            }
        }).start();
    }

    public abstract static class LoginState {
        public static final class Success extends LoginState {
            private final User user;

            public Success(User user) {
                this.user = user;
            }

            public User getUser() {
                return user;
            }

            public int getUserId() { // Add this method
                return user.getId(); // Replace getId() with the actual method you're using to get the userId from the User object
            }
        }
        public static final class Error extends LoginState {
            private final String errorMessage;

            public Error(String errorMessage) {
                this.errorMessage = errorMessage;
            }

            public String getErrorMessage() {
                return errorMessage;
            }
        }
    }

    public LiveData<LoginState> getLoginState() {
        return loginState;
    }

    public LiveData<Void> getNavigateToHomeEvent() {
        return navigateToHomeEvent;
    }

    public LiveData<Void> getNavigateToRegisterEvent() {
        return navigateToRegister;
    }
    public void navigateToRegister() {
        navigateToRegister.call();
    }
}
