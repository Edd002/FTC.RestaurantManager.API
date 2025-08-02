package com.fiap.tech.challenge.config;

import com.fiap.tech.challenge.domain.city.CityServiceGateway;
import com.fiap.tech.challenge.domain.city.entity.City;
import com.fiap.tech.challenge.domain.loadtable.LoadTableServiceGateway;
import com.fiap.tech.challenge.domain.state.StateServiceGateway;
import com.fiap.tech.challenge.domain.state.entity.State;
import com.fiap.tech.challenge.domain.user.UserServiceGateway;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.domain.usertype.UserTypeServiceGateway;
import com.fiap.tech.challenge.domain.usertype.entity.UserType;
import com.fiap.tech.challenge.global.util.JsonUtil;
import com.google.gson.reflect.TypeToken;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.List;

@Log
@Configuration
public class RunOnReady {

    @Value("${crypto.key}")
    private String cryptoKey;

    private static final String PATH_RESOURCE_STATE = "/runready/state.json";
    private static final String PATH_RESOURCE_CITY = "/runready/city.json";
    private static final String PATH_RESOURCE_USER_TYPE = "/runready/user_type.json";
    private static final String PATH_RESOURCE_USER = "/runready/user.json";

    private final LoadTableServiceGateway loadTableServiceGateway;
    private final StateServiceGateway stateServiceGateway;
    private final CityServiceGateway cityServiceGateway;
    private final UserTypeServiceGateway userTypeServiceGateway;
    private final UserServiceGateway userServiceGateway;

    @Autowired
    public RunOnReady(LoadTableServiceGateway loadTableServiceGateway, StateServiceGateway stateServiceGateway, CityServiceGateway cityServiceGateway, UserTypeServiceGateway userTypeServiceGateway, UserServiceGateway userServiceGateway) {
        this.loadTableServiceGateway = loadTableServiceGateway;
        this.stateServiceGateway = stateServiceGateway;
        this.cityServiceGateway = cityServiceGateway;
        this.userTypeServiceGateway = userTypeServiceGateway;
        this.userServiceGateway = userServiceGateway;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onReady() {
        List<State> stateList = JsonUtil.objectListFromJson("state", PATH_RESOURCE_STATE, new TypeToken<ArrayList<State>>() {
        }.getType());
        List<City> cityList = JsonUtil.objectListFromJson("city", PATH_RESOURCE_CITY, new TypeToken<ArrayList<City>>() {
        }.getType());
        List<UserType> userTypeList = JsonUtil.objectListFromJson("userType", PATH_RESOURCE_USER_TYPE, new TypeToken<ArrayList<UserType>>() {
        }.getType());
        List<User> userList = JsonUtil.objectListFromJson("user", PATH_RESOURCE_USER, new TypeToken<ArrayList<User>>() {
        }.getType());
        if ((loadTableServiceGateway.isEntityLoadEnabled(State.class.getSimpleName()))) {
            stateList.forEach(this::createState);
            loadTableServiceGateway.create(State.class.getSimpleName());
        }
        if ((loadTableServiceGateway.isEntityLoadEnabled(City.class.getSimpleName()))) {
            cityList.forEach(this::createCity);
            loadTableServiceGateway.create(City.class.getSimpleName());
        }
        if ((loadTableServiceGateway.isEntityLoadEnabled(UserType.class.getSimpleName()))) {
            userTypeList.forEach(this::createUserType);
            loadTableServiceGateway.create(UserType.class.getSimpleName());
        }
        if ((loadTableServiceGateway.isEntityLoadEnabled(User.class.getSimpleName()))) {
            userList.forEach(this::createUser);
            loadTableServiceGateway.create(User.class.getSimpleName());
        }
    }

    private void createState(State state) {
        try {
            stateServiceGateway.save(state);
        } catch (Exception exception) {
            log.severe(String.format("O estado de nome %s não pode ser cadastrado. Erro: %s", state.getName(), exception.getMessage()));
        }
    }

    private void createCity(City city) {
        try {
            cityServiceGateway.save(city);
        } catch (Exception exception) {
            log.severe(String.format("A cidade de nome %s não pode ser cadastrada. Erro: %s", city.getName(), exception.getMessage()));
        }
    }

    private void createUserType(UserType userType) {
        try {
            userTypeServiceGateway.save(userType);
        } catch (Exception exception) {
            log.severe(String.format("O tipo de usuário de nome %s não pode ser cadastrado. Erro: %s", userType.getName(), exception.getMessage()));
        }
    }

    private void createUser(User user) {
        try {
            user.setEncryptedPassword(cryptoKey, user.getPassword());
            userServiceGateway.save(user);
        } catch (Exception exception) {
            log.severe(String.format("O usuário de nome %s não pode ser cadastrado. Erro: %s", user.getName(), exception.getMessage()));
        }
    }
}