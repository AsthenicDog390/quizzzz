/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package client.scenes;

import client.utils.Config;
import client.utils.MultiPlayerGame;
import client.utils.SinglePlayerGame;
import com.google.inject.Inject;
import commons.exceptions.NameAlreadyPickedException;
import commons.Player;
import commons.questions.MoreExpensive;
import commons.questions.Question;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;
import java.util.List;

public class MainCtrl {

    private Stage primaryStage;

    private QuoteOverviewCtrl overviewCtrl;

    private Scene overview;

    private AddQuoteCtrl addCtrl;

    private Scene add;

    private ServerLocationCtrl serverLocationCtrl;

    private Scene serverLocation;

    private MainMenuCtrl menuCtrl;

    private Scene menu;

    private HowToPlayCtrl howToPlayCtrl;

    private Scene howToPlay;

    private WaitingRoomCtrl waitingRoomCtrl;

    private Scene waitingRoom;

    private NameSelectionCtrl nameSelectionCtrl;

    private Scene nameSelection;

    private NameSelectionMultiCtrl nameSelectionMultiCtrl;

    private Scene nameSelectionMulti;

    private StartingScreenCtrl startingScreenCtrl;

    private Scene startingScreen;

    private MultipleChoiceSingleCtrl multipleChoiceSingleCtrl;

    private Scene multipleChoiceSingle;

    private EstimateSingleCtrl estimateSingleCtrl;

    private Scene estimateSingle;

    private EstimateMultiCtrl estimateMultiCtrl;

    private Scene estimateMulti;

    private MultipleChoiceMultiCtrl multipleChoiceMultiCtrl;

    private Scene multipleChoiceMulti;

    private LeaderboardCtrl leaderboardCtrl;

    private Scene leaderboard;

    private SinglePlayerGame singlePlayerGame;

    private MultiPlayerGame multiPlayerGame;

    private AddActivitiesCtrl addActivitiesCtrl;

    private Scene addActivities;

    FileChooser chooser;

    private final Config config;

    @Inject
    public MainCtrl(Config config) {
        this.config = config;
    }

    /**
     * Initializer for all the Controllers and Instances that are going to be used.
     *
     * @params - all the parameters being the Controllers for creating the scenes and using them.
     */
    public void initialize(Stage primaryStage, Pair<QuoteOverviewCtrl, Parent> overview,
                           Pair<AddQuoteCtrl, Parent> add, Pair<MainMenuCtrl, Parent> menu, Pair<HowToPlayCtrl, Parent> howToPlay,
                           Pair<MultipleChoiceSingleCtrl, Parent> multipleChoiceSingle, Pair<ServerLocationCtrl, Parent> serverLocation,
                           Pair<StartingScreenCtrl, Parent> startingScreen, Pair<WaitingRoomCtrl, Parent> waitingRoom, Pair<NameSelectionCtrl, Parent> nameSelection, Pair<NameSelectionMultiCtrl, Parent> nameSelectionMulti,
                           Pair<MultipleChoiceMultiCtrl, Parent> multipleChoiceMulti, Pair<LeaderboardCtrl, Parent> leaderboard, Pair<EstimateSingleCtrl, Parent> estimateSingle, Pair<EstimateMultiCtrl, Parent> estimateMulti,
                           Pair<AddActivitiesCtrl, Parent> addActivities) {
        chooser = new FileChooser();

        this.primaryStage = primaryStage;
        this.overviewCtrl = overview.getKey();
        this.overview = new Scene(overview.getValue());

        this.addCtrl = add.getKey();
        this.add = new Scene(add.getValue());

        this.serverLocationCtrl = serverLocation.getKey();
        this.serverLocation = new Scene(serverLocation.getValue());

        this.menuCtrl = menu.getKey();
        this.menu = new Scene(menu.getValue());

        this.howToPlayCtrl = howToPlay.getKey();
        this.howToPlay = new Scene(howToPlay.getValue());

        this.waitingRoomCtrl = waitingRoom.getKey();
        this.waitingRoom = new Scene(waitingRoom.getValue());

        this.nameSelectionCtrl = nameSelection.getKey();
        this.nameSelection = new Scene(nameSelection.getValue());

        this.multipleChoiceSingleCtrl = multipleChoiceSingle.getKey();
        this.multipleChoiceSingle = new Scene(multipleChoiceSingle.getValue());

        this.addActivitiesCtrl = addActivities.getKey();
        this.addActivities = new Scene(addActivities.getValue());

        this.estimateSingleCtrl = estimateSingle.getKey();
        this.estimateSingle = new Scene(estimateSingle.getValue());

        this.estimateMultiCtrl = estimateMulti.getKey();
        this.estimateMulti = new Scene(estimateMulti.getValue());

        this.multipleChoiceMultiCtrl = multipleChoiceMulti.getKey();
        this.multipleChoiceMulti = new Scene(multipleChoiceMulti.getValue());

        this.nameSelectionMultiCtrl = nameSelectionMulti.getKey();
        this.nameSelectionMulti = new Scene(nameSelectionMulti.getValue());

        this.leaderboardCtrl = leaderboard.getKey();
        this.leaderboard = new Scene(leaderboard.getValue());

        this.startingScreenCtrl = startingScreen.getKey();
        this.startingScreen = new Scene(startingScreen.getValue());

        showServerLocation();
        primaryStage.show();
    }

    private void showServerLocation() {
        primaryStage.setTitle("Pick server location");
        primaryStage.setScene(serverLocation);
    }

    /**
     * Multiple show"XYZ" type of functions, setting the scene to the desired one, implementing different functionality depending on what is needed.
     */
    public void showOverview() {
        primaryStage.setTitle("Quotes: Overview");
        primaryStage.setScene(overview);
        overviewCtrl.refresh();
    }

    public void showAdd() {
        primaryStage.setTitle("Quotes: Adding Quote");
        primaryStage.setScene(add);
        add.setOnKeyPressed(e -> addCtrl.keyPressed(e));
    }

    public void showMainMenu() {
        primaryStage.setTitle("Quizzz");
        primaryStage.setScene(menu);
    }

    public void showHowToPlay() {
        primaryStage.setTitle("How To Play");
        primaryStage.setScene(howToPlay);
    }

    public void showWaitingRoom() {
        primaryStage.setTitle("Waiting Room");
        primaryStage.setScene(waitingRoom);
    }

    public void showNameSelection() {
        primaryStage.setTitle("Name Selection");
        primaryStage.setScene(nameSelection);
    }

    public void showMultipleChoiceSingle() {
        primaryStage.setTitle("Question");
        primaryStage.setScene(multipleChoiceSingle);
    }

    public void showNameSelect() {
        primaryStage.setTitle("Name Selection");
        primaryStage.setScene(nameSelection);
    }

    public void showStartingScreen() {
        primaryStage.setTitle("Starting Screen");
        primaryStage.setScene(startingScreen);
        startingScreenCtrl.start();
    }

    public void showLeaderboard(List<Player> scores) {
        leaderboardCtrl.setLeaderboard(scores);
        primaryStage.setTitle("LeaderBoard");
        primaryStage.setScene(leaderboard);
    }

    public void showNameSelectionMulti() {
        primaryStage.setTitle("Multiplayer Name Selection");
        primaryStage.setScene(nameSelectionMulti);
    }

    private void showMultipleMultiSingle() {
        multipleChoiceSingleCtrl.initializeScoreLabel();
        primaryStage.setTitle("Question");
        primaryStage.setScene(multipleChoiceMulti);
    }

    public void showEstimateSingle() {
        primaryStage.setTitle("Question");
        primaryStage.setScene(estimateSingle);
        estimateSingleCtrl.startTimer();
    }

    public void showEstimateMulti() {
        primaryStage.setTitle("Question");
        primaryStage.setScene(estimateMulti);
        estimateMultiCtrl.startTimer();
    }

    public void showAddActivities() {
        primaryStage.setTitle("Add Activities");
        primaryStage.setScene(addActivities);
    }

    /**
     * Getter for the current SinglePlayerGame instance used.
     *
     * @return - the single player game in use.
     */
    public SinglePlayerGame getSinglePlayerGame() {
        return this.singlePlayerGame;
    }

    /**
     * Getter for the current MultiPlayerGame instance used.
     *
     * @return - the multi-player game in use.
     */
    public MultiPlayerGame getMultiPlayerGame() {
        return this.multiPlayerGame;
    }

    /**
     * Starter for a single player game, using a given name for the player.
     *
     * @param name - a string representing the name of the player.
     */
    public void startSinglePlayerGame(String name) {
        this.singlePlayerGame = new SinglePlayerGame(config, this, name);
    }

    /**
     * Starter for a multi-player game.
     * @param name - The name of the new player
     */
    public void startMultiPlayerGame(String name) throws NameAlreadyPickedException {
        this.multiPlayerGame = new MultiPlayerGame(config, this, name);
    }

    /**
     * Setter for a question in a single player game, with the given question.
     *
     * @param question - the question that is wanted to be set in the active single player game.
     */
    public void setQuestionSinglePlayer(Question question) {
        if (question instanceof MoreExpensive) {
            this.showMultipleChoiceSingle();
            this.multipleChoiceSingleCtrl.setQuestion((MoreExpensive) question);
        }
    }

    /**
     * Function that ends the game.
     */
    public void gameEnded() {
        this.showMainMenu();
        this.singlePlayerGame.endGame();
        this.singlePlayerGame = null;
        this.multiPlayerGame = null;
    }

    public String pickFileLocation() {
        File file = chooser.showOpenDialog(primaryStage);
        if (file == null) {
            return null;
        }

        return file.getAbsolutePath();
    }

    /**
     * Setter for a question in a multi-player game, with the given question.
     *
     * @param question - the question that is wanted to be set in the active multi-player game.
     */
    public void setQuestionMultiPlayer(Question question) {
        if (question instanceof MoreExpensive) {
            this.showMultipleMultiSingle();
            this.multipleChoiceMultiCtrl.setQuestion((MoreExpensive) question);
        }
    }

    /**
     * Starter for the Timer on the single player game.
     */
    public void startSinglePlayerTimer() {
        multipleChoiceSingleCtrl.startTimer();
    }

    public void startMultiplayerGame() {
        if (multiPlayerGame != null) {
            multiPlayerGame.startGame();
        }
    }

    public void setPlayerList(List<Player> players) {
        this.waitingRoomCtrl.setPlayerList(players);
    }
}

