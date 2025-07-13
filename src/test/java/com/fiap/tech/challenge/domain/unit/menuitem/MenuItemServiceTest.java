package com.fiap.tech.challenge.domain.unit.menuitem;

import com.fiap.tech.challenge.domain.address.entity.Address;
import com.fiap.tech.challenge.domain.menu.entity.Menu;
import com.fiap.tech.challenge.domain.menuitem.IMenuItemRepository;
import com.fiap.tech.challenge.domain.menuitem.MenuItemService;
import com.fiap.tech.challenge.domain.menuitem.dto.MenuItemPostRequestDTO;
import com.fiap.tech.challenge.domain.menuitem.dto.MenuItemResponseDTO;
import com.fiap.tech.challenge.domain.menuitem.entity.MenuItem;
import com.fiap.tech.challenge.domain.restaurant.RestaurantService;
import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import com.fiap.tech.challenge.domain.restaurantuser.RestaurantUserService;
import com.fiap.tech.challenge.domain.restaurantuser.entity.RestaurantUser;
import com.fiap.tech.challenge.domain.user.authuser.AuthUserContextHolder;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.global.exception.EntityNotFoundException;
import com.fiap.tech.challenge.global.search.builder.PageableBuilder;
import com.fiap.tech.challenge.domain.restaurant.enumerated.RestaurantTypeEnum;
import com.fiap.tech.challenge.global.util.JsonUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// TODO Remover classe de teste a aplicar testes unitários com Mockito apenas nas classes isoladas de Framework (UseCase.java)

@ExtendWith(MockitoExtension.class)
class MenuItemServiceTest {

    private static final String PATH_RESOURCE_MENU_ITEM = "/mock/menuitem/menuitem.json";

    @InjectMocks
    private MenuItemService menuItemService;

    @Mock
    private IMenuItemRepository menuItemRepository;

    @Mock
    private RestaurantService restaurantService;

    @Mock
    private RestaurantUserService restaurantUserService;

    @Mock
    private PageableBuilder pageableBuilder;

    @Mock
    private ModelMapper modelMapper;

    private MockedStatic<AuthUserContextHolder> mockedAuthUserContext;

    private User mockUser;

    @BeforeEach
    void setUp() {
        mockUser = new User(1L);
        modelMapper = new ModelMapper();

        mockedAuthUserContext = Mockito.mockStatic(AuthUserContextHolder.class);
        mockedAuthUserContext.when(AuthUserContextHolder::getAuthUser).thenReturn(mockUser);

        menuItemService = new MenuItemService(menuItemRepository, restaurantService, restaurantUserService, pageableBuilder, modelMapper);
    }

    @AfterEach
    void tearDown() {
        mockedAuthUserContext.close();
    }

    @Nested
    class MenuItemService__Create {

        @DisplayName(value = "Teste de sucesso - Deve criar item no menu com sucesso")
        @Test
        void shouldCreateMenuItemSuccessfully() {
            Restaurant restaurant = new Restaurant(
                    1L,
                    "Restaurante Teste",
                    new Date(),
                    new Date(),
                    new Date(),
                    new Date(),
                    new Date(),
                    new Date(),
                    RestaurantTypeEnum.CAFETERIA,
                    mock(Menu.class),
                    mock(Address.class)
            );

            RestaurantUser mockRestaurantUser = new RestaurantUser(restaurant, mockUser);

            MenuItemPostRequestDTO menuItemPostRequestDTO = JsonUtil.objectFromJson(
                    "menuItemPostRequestDTO",
                    PATH_RESOURCE_MENU_ITEM,
                    MenuItemPostRequestDTO.class
            );

            MenuItem menuItem = new MenuItem(
                    1L,
                    "Espaguete",
                    "Espaguete à bolonhesa.",
                    new BigDecimal("19.99"),
                    true,
                    "https://ftc-restaurant-manager-api.s3.amazonaws.com/1-admin/305-image_(9).png-20250614014808",
                    mock(Menu.class)
            );

            when(restaurantUserService.findByHashIdAndUser("c57469e0cf8245d0b9a9b3e39b303dc0", mockUser)).thenReturn(mockRestaurantUser);
            MenuItemService spyService = Mockito.spy(menuItemService);
            Mockito.doReturn(menuItem).when(spyService).save(any(MenuItem.class));

            MenuItemResponseDTO menuItemResponseDTO = spyService.create(menuItemPostRequestDTO);

            assertThat(menuItemResponseDTO).isNotNull();
            assertThat(menuItemResponseDTO.getName()).isEqualTo("Espaguete");
            assertThat(menuItemResponseDTO.getDescription()).isEqualTo("Espaguete à bolonhesa.");
            assertThat(menuItemResponseDTO.getPrice()).isEqualTo(new BigDecimal("19.99"));
            assertThat(menuItemResponseDTO.getAvailability()).isEqualTo(true);
            assertThat(menuItemResponseDTO.getPhotoUrl()).isEqualTo("https://ftc-restaurant-manager-api.s3.amazonaws.com/1-admin/305-image_(9).png-20250614014808");
        }

        @DisplayName(value = "Teste de falha - Deve lançar exceção quando usuário não tem permissão no restaurante")
        @Test
        void shouldThrowExceptionWhenUserHasNoAssociationWithTheRestaurant() {
            MenuItemPostRequestDTO menuItemPostRequestDTO = JsonUtil.objectFromJson(
                    "menuItemPostRequestDTO",
                    PATH_RESOURCE_MENU_ITEM,
                    MenuItemPostRequestDTO.class
            );

            when(restaurantUserService.findByHashIdAndUser("c57469e0cf8245d0b9a9b3e39b303dc0", mockUser))
                    .thenThrow(new EntityNotFoundException("Nenhuma associação para o usuário com o restaurante com hash id c57469e0cf8245d0b9a9b3e39b303dc0 foi encontrada."));

            assertThrows(EntityNotFoundException.class, () -> menuItemService.create(menuItemPostRequestDTO));

            verify(menuItemRepository, never()).save(any(MenuItem.class));
        }
    }
}
