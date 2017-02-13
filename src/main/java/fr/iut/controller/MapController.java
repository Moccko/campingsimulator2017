package fr.iut.controller;

import fr.iut.App;
import fr.iut.State;
import fr.iut.model.Location;
import fr.iut.view.MapCreatorView;
import javafx.scene.Scene;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by shellcode on 2/13/17.
 */
public class MapController implements ControllerInterface {

    private App app;
    private MapCreatorView mapCreatorView = new MapCreatorView(this);

    public MapController(App app) {
        this.app = app;
    }

    @Override
    public Scene getView() {
        return mapCreatorView;
    }

    public void store(File mapFile, ArrayList<Location> locations) {
        //TODO : persist map and locations in the bdd
    }

    @Override
    public void finish() {
        app.switchState(State.HOME);
    }
}
