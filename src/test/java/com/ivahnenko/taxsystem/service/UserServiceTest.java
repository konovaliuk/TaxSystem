package com.ivahnenko.taxsystem.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.ivahnenko.taxsystem.dao.connection.ConnectionFactory;
import com.ivahnenko.taxsystem.dao.connection.DAOConnection;
import com.ivahnenko.taxsystem.dao.DAOFactory;
import com.ivahnenko.taxsystem.dao.UserDAO;
import com.ivahnenko.taxsystem.dao.UtilDAO;
import com.ivahnenko.taxsystem.model.User;
import com.ivahnenko.taxsystem.service.impl.UserServiceImpl;

/**
 * Test class for {@code UserService}
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(UtilDAO.class)
public class UserServiceTest {
    private static final int DB_POSITIVE_RETURN_MOCK = 1;

    @Mock
    private DAOFactory daoFactory;
    @Mock
    private ConnectionFactory connectionFactory;
    @Mock
    private DAOConnection connection;
    @Mock
    private UserDAO userDAO;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void saveShouldReturnTrue() {
        User user = new User();
        when(connectionFactory.getConnection()).thenReturn(connection);
        when(daoFactory.getUserDAO(connection)).thenReturn(userDAO);
        when(userDAO.save(user)).thenReturn(DB_POSITIVE_RETURN_MOCK);

        boolean actionPerformed = userService.save(user);

        verify(connectionFactory).getConnection();
        verify(daoFactory).getUserDAO(connection);
        verify(userDAO).save(user);

        assertTrue(actionPerformed);
    }

    @Test
    public void getByIdShouldReturnOptionalUser() {
        User user = new User();
        Optional<User> optionalUserExpected = Optional.of(user);

        when(connectionFactory.getConnection()).thenReturn(connection);
        when(daoFactory.getUserDAO(connection)).thenReturn(userDAO);
        when(userDAO.get(1)).thenReturn(optionalUserExpected);

        Optional<User> optionalUserActual = userService.getById(1);


        verify(connectionFactory).getConnection();
        verify(daoFactory).getUserDAO(connection);
        verify(userDAO).get(1);
        assertEquals(optionalUserExpected, optionalUserActual);
    }

    @Test
    public void getByUsernameShouldReturnOptionalUser() {
        User user = new User();
        String someUsername = "username";
        Optional<User> optionalUserExpected = Optional.of(user);

        when(connectionFactory.getConnection()).thenReturn(connection);
        when(daoFactory.getUserDAO(connection)).thenReturn(userDAO);
        when(userDAO.getByUsername(someUsername)).thenReturn(optionalUserExpected);

        Optional<User> optionalUserActual = userService.getByUsername(someUsername);

        verify(connectionFactory).getConnection();
        verify(daoFactory).getUserDAO(connection);
        verify(userDAO).getByUsername(someUsername);
        assertEquals(optionalUserExpected, optionalUserActual);
    }

    @Test
    public void getAllShouldReturnUserList() {
        List<User> userListExcpected = new ArrayList<>();

        when(connectionFactory.getConnection()).thenReturn(connection);
        when(daoFactory.getUserDAO(connection)).thenReturn(userDAO);
        when(userDAO.getAll()).thenReturn(userListExcpected);

        List<User> userListActual = userService.getAll();

        verify(connectionFactory).getConnection();
        verify(daoFactory).getUserDAO(connection);
        verify(userDAO).getAll();
        assertEquals(userListExcpected, userListActual);
    }

    @Test
    public void getAllByTypeShouldReturnCorrectUserList() {
        String someType = "someType";
        List<User> userListExcpected = new ArrayList<>();

        when(connectionFactory.getConnection()).thenReturn(connection);
        when(daoFactory.getUserDAO(connection)).thenReturn(userDAO);
        when(userDAO.getAllByType(someType)).thenReturn(userListExcpected);

        List<User> userListActual = userService.getAllByType(someType);

        verify(connectionFactory).getConnection();
        verify(daoFactory).getUserDAO(connection);
        verify(userDAO).getAllByType(someType);
        assertEquals(userListExcpected, userListActual);
    }

    @Test
    public void loginShouldReturnOptionalUser() {
        User user = new User();
        String someUsername = "username";
        String somePassword = "password";
        Optional<User> optionalUserExpected = Optional.of(user);
        Optional<String> optionalPassword = Optional.of(somePassword);
        PowerMockito.mockStatic(UtilDAO.class);

        when(connectionFactory.getConnection()).thenReturn(connection);
        when(daoFactory.getUserDAO(connection)).thenReturn(userDAO);
        when(userDAO.getByUsername(someUsername)).thenReturn(optionalUserExpected);
        when(userDAO.getPassword(someUsername)).thenReturn(optionalPassword);
        when(UtilDAO.validatePassword(somePassword, somePassword)).thenReturn(true);


        Optional<User> optionalUserActual = userService.login(someUsername, somePassword);
        PowerMockito.verifyStatic(UtilDAO.class);
        UtilDAO.validatePassword(somePassword, somePassword);

        verify(connectionFactory).getConnection();
        verify(daoFactory).getUserDAO(connection);
        verify(userDAO).getByUsername(someUsername);
        assertEquals(optionalUserExpected, optionalUserActual);
    }

    @Test
    public void isEmailExistsShouldReturnTrue() {
        String email = "vladimir@.com";
        when(connectionFactory.getConnection()).thenReturn(connection);
        when(daoFactory.getUserDAO(connection)).thenReturn(userDAO);
        when(userDAO.isEmailExists(email)).thenReturn(true);

        boolean emailExistsActual = userService.isEmailExists(email);

        verify(connectionFactory).getConnection();
        verify(daoFactory).getUserDAO(connection);
        verify(userDAO).isEmailExists(email);
        assertTrue(emailExistsActual);
    }

    @Test
    public void isUsernameExistsShouldReturnTrue() {
        String someUsername = "username";
        when(connectionFactory.getConnection()).thenReturn(connection);
        when(daoFactory.getUserDAO(connection)).thenReturn(userDAO);
        when(userDAO.isUsernameExists(someUsername)).thenReturn(true);

        boolean usernameExists = userService.isUsernameExists(someUsername);

        verify(connectionFactory).getConnection();
        verify(daoFactory).getUserDAO(connection);
        verify(userDAO).isUsernameExists(someUsername);
        assertTrue(usernameExists);
    }

    @Test
    public void isUserHasTaxpayerShouldReturnTrue() {
        User user = new User();
        when(connectionFactory.getConnection()).thenReturn(connection);
        when(daoFactory.getUserDAO(connection)).thenReturn(userDAO);
        when(userDAO.isUserHasTaxpayer(user)).thenReturn(true);

        boolean userHasTaxpayer = userService.isUserHasTaxpayer(user);

        verify(connectionFactory).getConnection();
        verify(daoFactory).getUserDAO(connection);
        verify(userDAO).isUserHasTaxpayer(user);
        assertTrue(userHasTaxpayer);
    }

    @Test
    public void changeTypeShouldReturnTrue() {
        User user = new User();
        when(connectionFactory.getConnection()).thenReturn(connection);
        when(daoFactory.getUserDAO(connection)).thenReturn(userDAO);
        when(userDAO.changeType(user)).thenReturn(DB_POSITIVE_RETURN_MOCK);

        boolean actionPerformed = userService.changeType(user);

        verify(connectionFactory).getConnection();
        verify(daoFactory).getUserDAO(connection);
        verify(userDAO).changeType(any(User.class));
        assertTrue(actionPerformed);
    }

    @Test
    public void changeDeletionStateShouldReturnTrue() {
        User user = new User();
        when(connectionFactory.getConnection()).thenReturn(connection);
        when(daoFactory.getUserDAO(connection)).thenReturn(userDAO);
        when(userDAO.changeDeleted(user)).thenReturn(DB_POSITIVE_RETURN_MOCK);

        boolean actionPerformed = userService.changeDeletionStatus(user);

        verify(connectionFactory).getConnection();
        verify(daoFactory).getUserDAO(connection);
        verify(userDAO).changeDeleted(user);

        assertTrue(actionPerformed);
    }

    @Test
    public void getAmountShouldReturnCorrectAmount() {
        int amountExpected = 50;
        when(connectionFactory.getConnection()).thenReturn(connection);
        when(daoFactory.getUserDAO(connection)).thenReturn(userDAO);
        when(userDAO.getAmount()).thenReturn(amountExpected);

        int amountActual = userService.getAmount();

        verify(connectionFactory).getConnection();
        verify(daoFactory).getUserDAO(connection);
        verify(userDAO).getAmount();
        assertEquals(50, amountActual);
    }
}
