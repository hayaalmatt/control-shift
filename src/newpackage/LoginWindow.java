package newpackage;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginWindow {

    public static Stage window;

    public static void show(Stage owner, HomepageWindow hmp) {
        window = new Stage();
        window.initOwner(owner);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Login");

        window.setScene(loginScene(hmp));
        window.show();
    }

    private static Scene loginScene(HomepageWindow hmp) {
        TextField username = new TextField();
        username.setPromptText("Username");

        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        Label msg = new Label();

        Button loginBtn = new Button("Login");
        Button createBtn = new Button("Create Account");
        
        

        loginBtn.setOnAction(e -> {
            String gen = UserDataBase.validateLogin(username.getText(), password.getText());
            if (gen != null) {
                CurrentUser.login(username.getText(), gen);
                hmp.updateTopBar(window);
                window.close();
            } else {
                msg.setText("Wrong username or password");
                msg.setStyle("-fx-text-fill:red");
            }
        });

        createBtn.setOnAction(e -> window.setScene(signupScene(hmp)));

        VBox box = new VBox(12, username, password, loginBtn, createBtn, msg);
        box.setAlignment(Pos.CENTER);
        Scene newScene = new Scene(box, 350, 250);
        newScene.getStylesheets().add(
            LoginWindow.class.getResource("style.css").toExternalForm()
        );
        return newScene;
    }

    private static Scene signupScene(HomepageWindow hmp) {
        TextField username = new TextField();
        username.setPromptText("Choose username");

        PasswordField p1 = new PasswordField();
        p1.setPromptText("Password");

        PasswordField p2 = new PasswordField();
        p2.setPromptText("Confirm password");

        ToggleGroup genderGroup = new ToggleGroup();
        RadioButton male = new RadioButton("Male");
        RadioButton female = new RadioButton("Female");
        male.setToggleGroup(genderGroup);
        female.setToggleGroup(genderGroup);

        Label msg = new Label();
        Button createBtn = new Button("Create Account");
        
        Button back = new Button("Back");

        createBtn.setOnAction(e -> {
            if (!p1.getText().equals(p2.getText())) {
                msg.setText("Passwords do not match!");
                msg.setStyle("-fx-text-fill:red");
                return;
            }
            if (genderGroup.getSelectedToggle() == null) {
                msg.setText("Choose a gender!");
                msg.setStyle("-fx-text-fill:red");
                return;
            }
            String gender = male.isSelected() ? "male" : "female";

            if (!UserDataBase.addUser(username.getText(), p1.getText(), gender)) {
                msg.setText("Username already exists!");
                msg.setStyle("-fx-text-fill:red");
                return;
            }
            CurrentUser.login(username.getText(), gender);
            hmp.updateTopBar(window);
            window.close();
        });
        
        back.setOnAction(e->{
            window.setScene(loginScene(hmp));
        });

        VBox box = new VBox(15, username, p1, p2, male, female, createBtn, back, msg);
        box.setAlignment(Pos.CENTER);
        Scene newScene = new Scene(box, 350, 380);
        newScene.getStylesheets().add(
            LoginWindow.class.getResource("style.css").toExternalForm()
        );
        return newScene; 
    }
}




