package com.fiap.tech.challenge.config;

import com.fiap.tech.challenge.domain.city.CityService;
import com.fiap.tech.challenge.domain.city.entity.City;
import com.fiap.tech.challenge.domain.loadtable.LoadTableService;
import com.fiap.tech.challenge.domain.state.StateService;
import com.fiap.tech.challenge.domain.state.entity.State;
import com.fiap.tech.challenge.domain.user.UserService;
import com.fiap.tech.challenge.domain.user.entity.User;
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
    private static final String PATH_RESOURCE_USER = "/runready/user.json";

    private final LoadTableService loadTableService;
    private final StateService stateService;
    private final CityService cityService;
    private final UserService userService;

    @Autowired
    public RunOnReady(LoadTableService loadTableService, StateService stateService, CityService cityService, UserService userService) {
        this.loadTableService = loadTableService;
        this.stateService = stateService;
        this.cityService = cityService;
        this.userService = userService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onReady() {
        List<State> stateList = JsonUtil.objectListFromJson("state", PATH_RESOURCE_STATE, new TypeToken<ArrayList<State>>() {
        }.getType());
        List<City> cityList = JsonUtil.objectListFromJson("city", PATH_RESOURCE_CITY, new TypeToken<ArrayList<City>>() {
        }.getType());
        List<User> userList = JsonUtil.objectListFromJson("user", PATH_RESOURCE_USER, new TypeToken<ArrayList<User>>() {
        }.getType());
        if ((loadTableService.isEntityLoadEnabled(State.class.getSimpleName()))) {
            stateList.forEach(this::createState);
            loadTableService.create(State.class.getSimpleName());
        }
        if ((loadTableService.isEntityLoadEnabled(City.class.getSimpleName()))) {
            cityList.forEach(this::createCity);
            loadTableService.create(City.class.getSimpleName());
        }
        if ((loadTableService.isEntityLoadEnabled(User.class.getSimpleName()))) {
            userList.forEach(this::createUser);
            loadTableService.create(User.class.getSimpleName());
        }
    }

    private void createState(State state) {
        try {
            stateService.save(state);
        } catch (Exception exception) {
            log.severe(String.format("O estado de nome %s não pode ser cadastrado. Erro: %s", state.getName(), exception.getMessage()));
        }
    }

    private void createCity(City city) {
        try {
            cityService.save(city);
        } catch (Exception exception) {
            log.severe(String.format("A cidade de nome %s não pode ser cadastrada. Erro: %s", city.getName(), exception.getMessage()));
        }
    }

    private void createUser(User user) {
        try {
            user.setEncryptedPassword(cryptoKey, user.getPassword());
            userService.save(user);
        } catch (Exception exception) {
            log.severe(String.format("O usuário de nome %s não pode ser cadastrado. Erro: %s", user.getName(), exception.getMessage()));
        }
    }
}