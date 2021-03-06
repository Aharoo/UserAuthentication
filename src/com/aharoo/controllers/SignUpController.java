package com.aharoo.controllers;

import com.aharoo.ChangeSceneHandler;
import com.aharoo.CheckAndRegisterUserHandler;
import com.aharoo.ErrorsHandler;
import com.aharoo.PasswordGenerator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.aharoo.animations.Shake;

public class SignUpController {

	private CheckAndRegisterUserHandler registerUserHandler = new CheckAndRegisterUserHandler();
	private ChangeSceneHandler changeSceneHandler = new ChangeSceneHandler();
	private ErrorsHandler errorsHandler = new ErrorsHandler();
	private PasswordGenerator passwordGenerator = new PasswordGenerator();

	@FXML
	private TextField signUpLoginField;

	@FXML
	private PasswordField signUpPasswordField;

	@FXML
	private Button signUpButton;

	@FXML
	private Button passwordGeneratorButton;

	@FXML
	private TextField passwordGeneratorField;

	@FXML
	private Button signUpLoginButton;

	@FXML
	private Button PasswordHelpButton;


	@FXML
	void initialize() {

		signUpButton.setOnAction(event -> {
			String login = signUpLoginField.getText().trim();
			String password = signUpPasswordField.getText().trim();
			if(!login.equals("") && !password.equals("")) {
				if(!registerUserHandler.checkExistingUser(login)){
					if (password.length() < 6) {
						errorsHandler.shortPasswordErrorMessage();
					}
					else if (password.length() > 25) {
						errorsHandler.longPasswordErrorMessage();
					}
					else if(login.length() < 4){
						errorsHandler.shortLoginErrorMessage();
					}
					else {
						registerUserHandler.registerNewUser(login, password);
						successRegistrationMessage();
						String path = "/com/aharoo/view/singIn.fxml";
						changeSceneHandler.changeScene(signUpButton, path, "AuthenticationWindow");
					}
				} else {
					new Shake(signUpLoginField).playAnimation();
					new Shake(signUpPasswordField).playAnimation();
					errorsHandler.userAlreadyExistsErrorMessage();
				}
			} else {
				errorsHandler.emptyFieldsErrorMessage();
			}

		});

		signUpLoginButton.setOnAction(event -> {
			String path = "/com/aharoo/view/singIn.fxml";
			changeSceneHandler.changeScene(signUpLoginButton,path,"AuthenticationWindow");
		});

		passwordGeneratorButton.setOnAction(event -> {
			String password = passwordGenerator.generateRandomPassword();
			passwordGeneratorField.setText(password);
		});

		PasswordHelpButton.setOnAction(event -> {
			passwordHelpMessage();
		});


	}

	public void successRegistrationMessage(){
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Success Registration");
		alert.setHeaderText("??????????????!");
		alert.setContentText("???? ???????? ?????????????? ?????????????????????????? ?? ??????????????!");

		alert.showAndWait();
	}

	public void passwordHelpMessage(){
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("PasswordHelpMessage");
		alert.setHeaderText("???????????????????????? ???? ?????????????????? ????????????");
		alert.setContentText("???????????? ?????????????? ????????: ?????? 6 ???? 25 ???????????????? (?????????????????????????? 12 ????????????????),?????????????? ?????????? ?????????????????? ???? ???????????????? ?????????????????? ?????????????????????? ????????????????,?????????? ???? ???????????????????? ??????????????");

		alert.showAndWait();
	}
}