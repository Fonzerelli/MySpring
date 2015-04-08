package com.myspring.ivashyn.db.daoImpl;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.myspring.ivashyn.db.dao.RoleDao;
import com.myspring.ivashyn.db.dao.UserDao;
import com.myspring.ivashyn.db.entity.User;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/tests.xml")
@DbUnitConfiguration(databaseConnection = "dbUnitDatabaseConnection")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
@TransactionConfiguration(transactionManager="transactionManager")
public class HibernateUserDaoTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateUserDaoTest.class);
    public static final String USER_TABLE_NAME = "USER";

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    public HibernateUserDaoTest() {
    }

    @Test
    @DatabaseSetup("classpath:dataset.xml")
    public void testFindByLogin() throws Exception {
        String userName = "john_doe";
        User user = userDao.findByLogin(userName);
        assertNotNull(user);
        assertEquals(user.getLogin(), userName);
    }

    @Test
    @DatabaseSetup("classpath:dataset.xml")
    public void testFindByLoginNegative() throws Exception {
        User user = userDao.findByLogin("wrongUser");
        assertTrue(user == null);
    }

    @Test
    @DatabaseSetup("classpath:dataset.xml")
    public void testFindByEmail() throws Exception {
        String email = "jane_d@gmail.com";
        User user = userDao.findByEmail(email);
        assertNotNull(user);
        assertEquals(user.getEmail(), email);
    }

    @Test
    @DatabaseSetup("classpath:dataset.xml")
    public void testFindById() throws Exception {
        Long id = 1L;
        User user = userDao.findById(id);
        assertNotNull(user);
        assertEquals(user.getRole().getName(), "Admin");
    }

    @Test
    @DatabaseSetup("classpath:dataset.xml")
    public void testFindByEmailNegative() throws Exception {
        User res = userDao.findByEmail("no_such@ddress");
        assertTrue(res == null);
    }

    @Test
    @DatabaseSetup("classpath:dataset.xml")
    public void testFindAll() throws Exception {
        List<User> user = userDao.findAll();
        assertTrue(user != null);
        assertEquals(2, user.size());
    }


    @Test
    @DatabaseSetup("classpath:dataset.xml")
    @ExpectedDatabase(value = "classpath:expected_update_user.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testUpdate() throws Exception {
        String testEmail = "test@email.com";
        User user = new User();
        user.setId(2L);
        user.setLogin("jane_doe");
        user.setRole(roleDao.findByName("User"));
        user.setFirstName("Jane");
        user.setPassword("password1");
        user.setLastName("Doe");
        user.setEmail(testEmail);
        user.setBirthday(new Date(77, 10, 11));
        userDao.update(user);
    }


    @Test
    @DatabaseSetup("classpath:dataset.xml")
    @ExpectedDatabase(value = "classpath:expected_delete_user.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testRemove() throws Exception {
        User user = new User();
        user.setId(2l);
        userDao.remove(user);
    }

    @Test
    @DatabaseSetup("classpath:dataset.xml")
    @ExpectedDatabase(value = "classpath:expected_create_user.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testCreate() throws Exception {
        User user = new User();
        String testerName = "tester";
        user.setLogin(testerName);
        user.setRole(roleDao.findByName("Admin"));
        user.setFirstName("first_name");
        user.setLastName("last_name");
        user.setPassword("pass");
        user.setEmail("email@email.com");
        user.setBirthday(new Date(98, 11, 12));
        userDao.create(user);
    }

}
