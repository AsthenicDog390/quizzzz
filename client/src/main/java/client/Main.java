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

package client;

import static com.google.inject.Guice.createInjector;

import java.io.IOException;
import java.net.URISyntaxException;

import client.scenes.ServerLocationCtrl;
import client.scenes.*;
import com.google.inject.Injector;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static final Injector INJECTOR = createInjector(new MyModule());

    private static final MyFXML FXML = new MyFXML(INJECTOR);

    /**
     * Main function that launches the app.
     * @param args - Usual string arguments passed to main.
     * @throws URISyntaxException - If a wrong URI is tried to be accessed.
     * @throws IOException - If a wrong in/out argument is used.
     */
    public static void main(String[] args) throws URISyntaxException, IOException {
        launch();
    }

    /**
     * Preparing the fxml files for further injection.
     * @param primaryStage - the stage that will be implemented first.
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        var overview = FXML.load(QuoteOverviewCtrl.class, "client", "scenes", "QuoteOverview.fxml");
        var add = FXML.load(AddQuoteCtrl.class, "client", "scenes", "AddQuote.fxml");
        var menu = FXML.load(MainMenuCtrl.class, "client", "scenes", "MainMenu.fxml");
        var howToPlay = FXML.load(HowToPlayCtrl.class, "client", "scenes", "HowToPlay.fxml");
        var leaderboard = FXML.load(LeaderboardCtrl.class, "client", "scenes", "Leaderboard.fxml");
        var serverLocation = FXML.load(ServerLocationCtrl.class, "client", "scenes", "ServerLocation.fxml");
        var startingScreen = FXML.load(StartingScreenCtrl.class, "client", "scenes", "StartingScreen.fxml");
        var multipleChoiceSingle = FXML.load(MultipleChoiceSingleCtrl.class, "client", "scenes", "MultipleChoiceSingle.fxml");
        var multipleChoiceMulti = FXML.load(MultipleChoiceMultiCtrl.class, "client", "scenes", "MultipleChoiceMulti.fxml");
        var waitingRoom = FXML.load(WaitingRoomCtrl.class, "client", "scenes", "WaitingRoom.fxml");
        var nameSelectionMulti = FXML.load(NameSelectionMultiCtrl.class, "client", "scenes", "NameSelectionMulti.fxml");
        var nameSelection = FXML.load(NameSelectionCtrl.class, "client", "scenes", "NameSelection.fxml");
        var estimateSingle = FXML.load(EstimateSingleCtrl.class, "client", "scenes", "EstimateSingle.fxml");
        var estimateMulti = FXML.load(EstimateMultiCtrl.class, "client", "scenes", "EstimateMulti.fxml");
        var addActivities = FXML.load(AddActivitiesCtrl.class, "client", "scenes", "AddActivities.fxml");

        var mainCtrl = INJECTOR.getInstance(MainCtrl.class);


        /**
         * Initialisation of the main controller for starting the main scene
         */
        mainCtrl.initialize(primaryStage, overview, add, menu, howToPlay, multipleChoiceSingle, serverLocation, startingScreen, waitingRoom, nameSelection, nameSelectionMulti, multipleChoiceMulti, leaderboard, estimateSingle, estimateMulti, addActivities);
    }
}