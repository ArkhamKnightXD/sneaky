package knight.arkham.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import knight.arkham.Dark;
import knight.arkham.helpers.TileMapHelper;

public class GameScreen extends ScreenAdapter {
    private final Dark game;
    private final OrthographicCamera camera;
    private final TileMapHelper mapHelper;

    public GameScreen() {

        game = Dark.INSTANCE;

        camera = game.camera;

        mapHelper = new TileMapHelper("maps/level3.tmx", "images/test.atlas");
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height);
    }

    @Override
    public void render(float deltaTime) {

        ScreenUtils.clear(0, 0, 0, 0);

        mapHelper.update(deltaTime, camera);

        mapHelper.draw(camera);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        mapHelper.dispose();
    }
}
