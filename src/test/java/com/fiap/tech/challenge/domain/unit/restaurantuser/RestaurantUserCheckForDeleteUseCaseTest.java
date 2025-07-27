package com.fiap.tech.challenge.domain.unit.restaurantuser;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.fiap.tech.challenge.domain.factory.UserTypeFactory;
import com.fiap.tech.challenge.domain.restaurantuser.entity.RestaurantUser;
import com.fiap.tech.challenge.domain.restaurantuser.usecase.RestaurantUserCheckForDeleteUseCase;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.global.exception.EntityCannotBeDeletedException;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RestaurantUserCheckForDeleteUseCaseTest {
    private static final String CANNOT_DELETE_ADMIN_USER_EXCEPTION_MESSAGE = "Usuários administradores não podem ser excluídos.";
    private static final String CANNOT_DELETE_ONLY_OWNER_USER_EXCEPTION_MESSAGE = "Não foi possível realizar a exclusão pois deve existir pelo menos uma associação de usuário dono de restaurante com o restaurante.";

    AutoCloseable openMocks;

    @Mock
    private User loggedUser;
    @Mock
    private List<RestaurantUser> restaurantUserList;
    @Mock
    private RestaurantUser restaurantUser;
    @Mock
    private User ownerUser;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void teardown() throws Exception{
        openMocks.close();
    }

    @Test
    @DisplayName("Teste de sucesso - o usuário deve estar disponível para exclusão.")
    void restaurantUserShouldBeAvailableForDeletion(){
        when(loggedUser.getType()).thenReturn(UserTypeFactory.loadEntityUserTypeClient());

        assertTrue(new RestaurantUserCheckForDeleteUseCase(loggedUser, restaurantUserList).isAllowedToDelete());
    }

    @Test
    @DisplayName("Teste de falha - deve lançar exceção ao tentar excluir usuário Admin.")
    void adminRestaurantUserShouldNotBeAvailableForDeletion(){
        when(loggedUser.getType()).thenReturn(UserTypeFactory.loadEntityUserTypeAdmin());

        EntityCannotBeDeletedException exception = assertThrows(EntityCannotBeDeletedException.class, () -> new RestaurantUserCheckForDeleteUseCase(loggedUser, restaurantUserList));
        assertEquals(CANNOT_DELETE_ADMIN_USER_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    @DisplayName("Teste de falha - deve lançar exceção ao tentar excluir um usuário dono de restaurante com apenas um usuário dono associado")
    void onlyRestaurantUserOwnerShouldNotBeAvailableForDeletion(){
        restaurantUserList = Collections.singletonList(restaurantUser);
        when(loggedUser.getType()).thenReturn(UserTypeFactory.loadEntityUserTypeOwner());
        when(restaurantUser.getUser()).thenReturn(ownerUser);
        when(ownerUser.getType()).thenReturn(UserTypeFactory.loadEntityUserTypeOwner());

        EntityCannotBeDeletedException exception = assertThrows(EntityCannotBeDeletedException.class, () -> new RestaurantUserCheckForDeleteUseCase(loggedUser, restaurantUserList));
        assertEquals(CANNOT_DELETE_ONLY_OWNER_USER_EXCEPTION_MESSAGE, exception.getMessage());
    }
}