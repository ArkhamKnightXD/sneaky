package knight.arkham.helpers;

import com.badlogic.gdx.physics.box2d.*;
import knight.arkham.objects.Enemy;
import knight.arkham.objects.Player;
import knight.arkham.objects.structures.Checkpoint;

import static knight.arkham.helpers.Constants.*;

public class GameContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {

        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        int collisionBits = fixtureA.getFilterData().categoryBits | fixtureB.getFilterData().categoryBits;

        switch (collisionBits) {

            case PLAYER_BIT | CHECKPOINT_BIT:

                if (fixtureA.getFilterData().categoryBits == CHECKPOINT_BIT)
                    ((Checkpoint) fixtureA.getUserData()).createCheckpoint();

                else
                    ((Checkpoint) fixtureB.getUserData()).createCheckpoint();
                break;

            case PLAYER_BIT | DOOR_BIT:

                TileMapHelper.canChangePlayer = true;
                break;

            case PLAYER_BIT | ENEMY_HEAD_BIT:

                if (fixtureA.getFilterData().categoryBits == ENEMY_HEAD_BIT)
                    ((Enemy) fixtureA.getUserData()).hitByPlayer();

                else
                    ((Enemy) fixtureB.getUserData()).hitByPlayer();
                break;

            case PLAYER_BIT | ENEMY_BIT:

                if (fixtureA.getFilterData().categoryBits == PLAYER_BIT) {

                    ((Player) fixtureA.getUserData()).hitByEnemy();
                    ((Enemy) fixtureB.getUserData()).changeDirection();
                }

                else {

                    ((Player) fixtureB.getUserData()).hitByEnemy();
                    ((Enemy) fixtureA.getUserData()).changeDirection();
                }
                break;

            case ENEMY_BIT | STOP_ENEMY_BIT:

                if (fixtureA.getFilterData().categoryBits == ENEMY_BIT)
                    ((Enemy) fixtureA.getUserData()).changeDirection();

                else
                    ((Enemy) fixtureB.getUserData()).changeDirection();
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {

        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        int collisionBits = fixtureA.getFilterData().categoryBits | fixtureB.getFilterData().categoryBits;

        short collisionPlayerWithDoor = PLAYER_BIT | DOOR_BIT;

        if (collisionBits == collisionPlayerWithDoor)
            TileMapHelper.canChangePlayer = false;
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
}
