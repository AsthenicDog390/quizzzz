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

import client.utils.MultiPlayerGame;
import client.utils.SinglePlayerGame;
import commons.questions.MoreExpensive;
import commons.questions.Question;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;


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

    private MultipleChoiceMultiCtrl multipleChoiceMultiCtrl;
    private Scene multipleChoiceMulti;

    private SinglePlayerGame singlePlayerGame;
    private MultiPlayerGame multiPlayerGame;

    public void initialize(Stage primaryStage, Pair<QuoteOverviewCtrl, Parent> overview,
                           Pair<AddQuoteCtrl, Parent> add, Pair<MainMenuCtrl, Parent> menu, Pair<HowToPlayCtrl, Parent> howToPlay,
                           Pair<MultipleChoiceSingleCtrl, Parent> multipleChoiceSingle, Pair<ServerLocationCtrl, Parent> serverLocation,
                           Pair<StartingScreenCtrl, Parent> startingScreen, Pair<WaitingRoomCtrl, Parent> waitingRoom, 
                           Pair<NameSelectionCtrl, Parent> nameSelection, Pair<NameSelectionMultiCtrl, Parent> nameSelectionMulti, 
                           Pair<MultipleChoiceMultiCtrl, Parent> multipleChoiceMulti, Pair<LeaderboardCtrl, Parent> leaderboard) {

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

        this.startingScreenCtrl = startingScreen.getKey();
        this.startingScreen = new Scene(startingScreen.getValue());

        this.multipleChoiceSingleCtrl = multipleChoiceSingle.getKey();
        this.multipleChoiceSingle = new Scene(multipleChoiceSingle.getValue());

        this.multipleChoiceMultiCtrl = multipleChoiceMulti.getKey();
        this.multipleChoiceMulti = new Scene(multipleChoiceMulti.getValue());

        this.nameSelectionCtrl = nameSelection.getKey();
        this.nameSelection = new Scene(nameSelection.getValue());

        this.startingScreenCtrl = startingScreen.getKey();
        this.startingScreen = new Scene(startingScreen.getValue());

        this.nameSelectionMultiCtrl = nameSelectionMulti.getKey();
        this.nameSelectionMulti = new Scene(nameSelectionMulti.getValue());

        showMainMenu();
//        showMultipleSingle();
        primaryStage.show();
    }

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
        multipleChoiceSingleCtrl.startTimer();
    }

    public void showNameSelect(){
        primaryStage.setTitle("Name Selection");
        primaryStage.setScene(nameSelection);
    }

    public void showStartingScreen(){
        primaryStage.setTitle("Starting Screen");
        primaryStage.setScene(startingScreen);
    }

    public void showNameSelectionMulti(){
        primaryStage.setTitle("Multiplayer Name Selection");
        primaryStage.setScene(nameSelectionMulti);
    }

    private void showMultipleMultiSingle() {
        primaryStage.setTitle("Question");
        primaryStage.setScene(multipleChoiceMulti);
    }

    public SinglePlayerGame getSinglePlayerGame() {
        return this.singlePlayerGame;
    }

    public MultiPlayerGame getMultiPlayerGame() { return this.multiPlayerGame; }

    public void startSinglePlayerGame() {
        this.singlePlayerGame = new SinglePlayerGame(this);
    }

    public void startMultiPlayerGame() {
        this.multiPlayerGame = new MultiPlayerGame(this);
    }

    public void setQuestionSinglePlayer(Question question) {
        if (question instanceof MoreExpensive) {
            this.showMultipleChoiceSingle();
            this.multipleChoiceSingleCtrl.setQuestion((MoreExpensive) question);
        }
    }

    public void gameEnded() {
        this.showMainMenu();
        this.singlePlayerGame = null;
        this.multiPlayerGame = null;
    }

    public void setQuestionMultiPlayer(Question question) {
        if (question instanceof MoreExpensive) {
            this.showMultipleMultiSingle();
            this.multipleChoiceMultiCtrl.setQuestion((MoreExpensive) question);
        }
    }


    public void showLeaderboard(){
        this.showLeaderboard();
    }
}

