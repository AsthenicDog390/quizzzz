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

    private MultipleChoiceSingleCtrl multipleChoiceSingleCtrl;
    private Scene multipleChoiceSingle;

    private NameSelectionCtrl nameSelectionCtrl;
    private Scene nameScreen;

    private SinglePlayerGame singlePlayerGame;

    public void initialize(Stage primaryStage, Pair<QuoteOverviewCtrl, Parent> overview,
                           Pair<AddQuoteCtrl, Parent> add, Pair<MainMenuCtrl, Parent> menu, Pair<HowToPlayCtrl, Parent> howToPlay, Pair<MultipleChoiceSingleCtrl, Parent> multipleChoiceSingle,
                           Pair<ServerLocationCtrl, Parent> serverLocation, Pair<NameSelectionCtrl, Parent> nameScreen) {
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

        this.multipleChoiceSingleCtrl = multipleChoiceSingle.getKey();
        this.multipleChoiceSingle = new Scene(multipleChoiceSingle.getValue());

        this.nameSelectionCtrl = nameScreen.getKey();
        this.nameScreen = new Scene(nameScreen.getValue());

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

    public void showNameScreen(){
        primaryStage.setTitle("Name Selection");
        primaryStage.setScene(nameScreen);
    }

    public void showMultipleChoiceSingle() {
        primaryStage.setTitle("Question");
        primaryStage.setScene(multipleChoiceSingle);
        multipleChoiceSingleCtrl.startTimer();
        multipleChoiceSingleCtrl.setScore(0);
    }

    public SinglePlayerGame getGame() {
        return this.singlePlayerGame;
    }

    public void startSinglePlayerGame() {
        this.singlePlayerGame = new SinglePlayerGame(this);
    }

    public void setQuestionSinglePlayer(Question question) {
        if (question instanceof MoreExpensive) {
            this.showMultipleChoiceSingle();
            this.multipleChoiceSingleCtrl.setQuestion((MoreExpensive) question);
        }
    }

    public void gameEnded() {
        this.showMainMenu();
    }
}