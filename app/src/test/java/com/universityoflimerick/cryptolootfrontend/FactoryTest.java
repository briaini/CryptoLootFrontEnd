package com.universityoflimerick.cryptolootfrontend;

import com.universityoflimerick.cryptolootfrontend.Model.User.User;
import com.universityoflimerick.cryptolootfrontend.Utils.factory.UserFactory;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertNotEquals;

public class FactoryTest {

    UserFactory userFactory;
    User rUser, pUser;

    /**
     * Setup method to create User Factory for user instantiation.
     * Method then intantiates two users, one regular, one premium.
     */
    @Before
    public void setup(){
        userFactory = new UserFactory();
        rUser = userFactory.getUser("regular");
        pUser = userFactory.getUser("premium");
    }

    /**
     * Tests that regular users have a regular type,
     * premium users have a premium type and
     * premium users don't have a regular type.
     */
    @Test
    public void factory_user_creation_test(){
        assertEquals("regular", rUser.getType());
        assertEquals("premium", pUser.getType());
        assertNotEquals("regular", pUser.getType());
    }


}
