package jrails;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ModelTest {

    private Model model;

    @Before
    public void setUp() throws Exception {
        model = new Model(){};
    }

    @Test
    public void id() {
        assertThat(model.id(), notNullValue());
    }

    @Test
    public void testSave() {
        model.save();
        assertThat(model.id(), is(1)); // First saved model should have ID 1
    }

    @After
    public void tearDown() throws Exception {
        Model.reset();
    }
/*
    @Test
    public void testFind() {
        model.save();
        Model found = Model.find(Model.class, 1);
        assertThat(found, notNullValue());
        assertThat(found.id(), is(1));
    }

    @Test
    public void testAll() {
        model.save();
        Model model2 = new Model() {};
        model2.save();
        List<Model> models = Model.all(Model.class);
        assertThat(models.size(), is(2));
    }

    @Test
    public void testDestroy() {
        model.save();
        Model found = Model.find(Model.class, 1);
        assertThat(found, notNullValue());
        found.destroy();
        Model foundAgain = Model.find(Model.class, 1);
        assertThat(foundAgain, is((Model) null));
    }
*/
    @Test
    public void testReset() {
        model.save();
        Model model2 = new Model() {};
        model2.save();
        Model.reset();
        List<Model> models = Model.all(Model.class);
        assertThat(models.size(), is(0));
    }
}