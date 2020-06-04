package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }


    /**Unit test for the change of the neighbour status
     */
    @Test
    public void changeNeighbourStatus() {
        //Getting a neighbour and checking that he is not a favorite
        Neighbour neighbour = service.getNeighbours().get(1);
        assertFalse(neighbour.isFavorite());
        //Changing the neighbour status and checking if he changed as favorite
        service.changeStatusNeighbour(neighbour);
        assertTrue(neighbour.isFavorite());
        //Changing the neighbour status again and checking that he is no longer a favorite
        service.changeStatusNeighbour(neighbour);
        assertFalse(neighbour.isFavorite());
    }


    /**Unit test to see if the favorite list get only all the favorite neighbours
     */
    @Test
    public void getFavoriteNeighbour() {
        //Checking if the list is empty
        List<Neighbour> favoriteNeighbours = service.favoriteNeighbours();
        assertTrue(favoriteNeighbours.isEmpty());
        //Adding 3 neighbours to the list using "changeStatusNeighbour"
        service.changeStatusNeighbour(service.getNeighbours().get(0));
        service.changeStatusNeighbour(service.getNeighbours().get(2));
        service.changeStatusNeighbour(service.getNeighbours().get(5));
        //Refreshing the list and checking if it contains 3 elements in it
        favoriteNeighbours = service.favoriteNeighbours();
        assertEquals(3,favoriteNeighbours.size());
        //Checking if each neighbour is in the list
        assertTrue(favoriteNeighbours.contains(service.getNeighbours().get(0)));
        assertTrue(favoriteNeighbours.contains(service.getNeighbours().get(2)));
        assertTrue(favoriteNeighbours.contains(service.getNeighbours().get(5)));
    }
}
