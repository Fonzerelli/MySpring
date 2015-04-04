package com.nixsolutions.ivashyn.db.daoImpl;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.nixsolutions.ivashyn.db.dao.RoleDao;
import com.nixsolutions.ivashyn.db.entity.Role;
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

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/tests.xml")
@DbUnitConfiguration(databaseConnection = "dbUnitDatabaseConnection")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
@TransactionConfiguration(transactionManager="transactionManager")
public class HibernateRoleDaoTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateRoleDaoTest.class);

    public static final String ROLE_TABLE_NAME = "ROLE";

    @Autowired
    private RoleDao roleDao;

    public HibernateRoleDaoTest() {
    }

    @Test
    @DatabaseSetup("classpath:dataset.xml")
    public void testFindByName() throws Exception {
        String adminName = "Admin";
        Role role = roleDao.findByName(adminName);
        assertNotNull(role);
        assertEquals(role.getName(), adminName);
    }

    @Test
    @DatabaseSetup("classpath:dataset.xml")
    public void testFindById() throws Exception {
        String adminName = "Admin";
        Role role = roleDao.findById(1l);
        assertNotNull(role);
        assertEquals(role.getName(), adminName);
    }

    @Test
    @DatabaseSetup("classpath:dataset.xml")
    public void testFindAllRoleNames() throws Exception {
        List<String> roleList = roleDao.findAllRoleNames();
        assertNotNull(roleList);
        for (String s : roleList) {
            LOGGER.info(s);
        }
        assertEquals(roleList.size(), 3);
    }

    @Test
    @DatabaseSetup("classpath:dataset.xml")
    public void testFindAll() throws Exception {
        List<Role> roleList = roleDao.findAll();
        assertNotNull(roleList);
        assertEquals(roleList.size(), 3);
    }

    @Test
    @DatabaseSetup("classpath:dataset.xml")
    public void testFindByNameNegative() throws Exception {
        Role res = roleDao.findByName("wrongTestName");
        assertTrue(res == null);
    }

    @Test
    @DatabaseSetup("classpath:dataset.xml")
    @ExpectedDatabase(value = "classpath:expected_create_role.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testCreate() throws Exception {
        Role role = new Role();
        String testerName = "Test_created";
        role.setName(testerName);
        roleDao.create(role);
    }

    @Test
    @DatabaseSetup("classpath:dataset.xml")
    @ExpectedDatabase(value = "classpath:expected_update_role.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testUpdate() throws Exception {
        Role role = new Role();
        role.setId((long) 1);
        role.setName("updatedName");
        roleDao.update(role);
    }

    @Test
    @DatabaseSetup("classpath:dataset.xml")
    @ExpectedDatabase(value = "classpath:expected_delete_role.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testRemove() throws Exception {
        Role role = roleDao.findById(3l);
        roleDao.remove(role);
    }
}
