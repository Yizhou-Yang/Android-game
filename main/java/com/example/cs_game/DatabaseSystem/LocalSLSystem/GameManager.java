/*
package com.example.cs_game.DatabaseSystem.LocalSLSystem;

public class GameManager {
        private static GameManager gameManager;
        private static GameState gameState;
        private Context context;

        public GameManager(Context context) {
            this.context = context;
        }

        void newGame(){
            gameManager = GameManagerBuilder.getManager();
        }

        void loadGame(){

            String filename = gameState.getuser().get;
            try {
                InputStream inputStream = context.openFileInput(filename);
                if (inputStream != null) {
                    ObjectInputStream input = new ObjectInputStream(inputStream);
                    gameState = (GameState) input.readObject();
                    inputStream.close();
                }
            } catch (FileNotFoundException e) {
                Log.e("GameState", "FileNotFoundException");
                gameState = new GameState();

            } catch (IOException e) {
                Log.e("GameState", "IOException");
                gameState = new GameState();

            } catch (ClassNotFoundException e) {
                Log.e("GameState", "ClassNotFoundException");
                gameState = new GameState();
            }
        }

        void saveGame(){
            String filename = user.getUsername() + SUFFIX;
            try {
                ObjectOutputStream outputStream = new ObjectOutputStream(
                        context.openFileOutput(filename, context.MODE_PRIVATE));
                outputStream.writeObject(gameState);
                outputStream.close();
            } catch (IOException e) {
                Log.e("Exception", "File write failed: " + e.toString());
            }
        }

        static GameState getGameState(){
            return gameState;
        }

    }

}

*/