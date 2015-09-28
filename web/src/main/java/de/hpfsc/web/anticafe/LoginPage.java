package de.hpfsc.web.anticafe;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Created by dmitry on 15.07.15.
 */
public class LoginPage extends Composite {
  private VerticalPanel verticalPanel;
  private Label loginMessageLabel;
  private TextBox userNameInput;
  private TextBox passwordInput;
  private Button loginButton;

  public LoginPage() {
    verticalPanel = new VerticalPanel();
    initWidget(verticalPanel);

    loginMessageLabel = new Label("Введите имя и пароль:");
    verticalPanel.add(loginMessageLabel);

    userNameInput = new TextBox();
    verticalPanel.add(userNameInput);

    passwordInput = new TextBox();
    verticalPanel.add(passwordInput);

    loginButton = new Button("Войти");
    verticalPanel.add(loginButton);

//    loginButton.addClickHandler(new ClickHandler() {
//      public void onClick(ClickEvent event) {
//        LoginPage.this.setVisible(false);
//      }
//    });

  }

}
