package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.backends.lwjgl.LwjglBackend;
import sk.tuke.kpi.oop.game.scenarios.EscapeRoom;

public class Main {

    public static void main(String[] args){
        WindowSetup windowSetup = new WindowSetup("Project Ellen", 800, 600);

        Game game = new GameApplication(windowSetup, new LwjglBackend());
/*
        Scene scene = new World("world");
        //FirstSteps firstSteps = new FirstSteps();

        MissionImpossible mission = new MissionImpossible();

        game.addScene(scene);
        scene.addListener(mission);*/

/*
        Scene missionImpossible = new World("mission-impossible", "maps/mission-impossible.tmx", new MissionImpossible.Factory());
        MissionImpossible mission = new MissionImpossible();

        game.addScene(missionImpossible);
        missionImpossible.addListener(mission);
*/

        Scene escapeRoom = new World("EscapeRoom", "maps/escape-room.tmx", new EscapeRoom.Factory());
        EscapeRoom escape = new EscapeRoom();

        game.addScene(escapeRoom);
        escapeRoom.addListener(escape);

        game.getInput().onKeyPressed(Input.Key.ESCAPE, game::stop);
        game.start();
    }
}
