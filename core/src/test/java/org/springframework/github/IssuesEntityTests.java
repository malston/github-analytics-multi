package org.springframework.github;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Issues.class)
@DataJpaTest
public class IssuesEntityTests {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void saveShouldPersistData() throws Exception {
        Issues user = this.entityManager.persistFlushFind(new Issues("donald", "github-webhook"));
        assertThat(user.getUsername()).isEqualTo("donald");
    }

    @Test
    public void createWhenUsernameIsNullShouldThrowException() throws Exception {
        expectedException.expect(ConstraintViolationException.class);
        this.entityManager.persistAndFlush(new Issues(null, "github-webhook"));
    }

    @Test
    public void createWhenRepositorytIsNullShouldThrowException() throws Exception {
        expectedException.expect(ConstraintViolationException.class);
        this.entityManager.persistAndFlush(new Issues("donald", null));
    }
}