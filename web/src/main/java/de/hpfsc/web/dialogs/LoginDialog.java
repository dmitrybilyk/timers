package de.hpfsc.web.dialogs;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.WidgetComponent;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout.VBoxLayoutAlign;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayoutData;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import de.hpfsc.web.GreetingService;
import de.hpfsc.web.GreetingServiceAsync;
import de.hpfsc.web.panels.BorderLayoutExample;


/**
 * User login dialog
 */
public class LoginDialog extends Dialog {

  private final TextField<String> userNameTextField = new TextField();
  private final TextField<String> passwordTextField = new TextField();
  private final LabelField loginFailedLabel;

  private Button loginButton = new Button("Login");
  private Button forgotPasswordButton;
  private Button cleanButton;
  private Button cancelButton;

//  private final LoginClient client;

  private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);


//  private final boolean usernameEditable;


  /**
   * Creates a LoginDialog with login service from {@link LoginService.App#getInstance()}
   * and UserUtils from {@link UserUtils#getLoggedUser()}
   * @param client
   */
//  public LoginDialog(LoginClient client) {
//      this(client, LoginService.App.getInstance(), null);
//  }
//
//  public LoginDialog(LoginClient client, UserBO loggedUser) {
//      this(client, LoginService.App.getInstance(), loggedUser);
//  }

  public LoginDialog() {

//    this.loginService = loginService;
//    this.setId(ObjectIds.LOGINDIALOG_ID);

//    usernameEditable = createButtonsByUser(loggedUser);

    setClosable(false);

    final FormLayout layout = new FormLayout();
    layout.setLabelWidth(100);
    layout.setDefaultWidth(200);
    setLayout(layout);

    setHeadingText("Logining...");
    getHeader().addStyleName("text-align-center");
    setBodyStyle("padding: 8px");
    setWidth(350);
    setResizable(false);
    setModal(true);

    boolean showObsoleteBrowserWarning = false;
//    if (loggedUser == null) {
//      if (GXT.isIE6 || GXT.isIE7) {
//        showObsoleteBrowserWarning = true;
//      }
//    }

    if (showObsoleteBrowserWarning) {
      LayoutContainer warningContainer = new LayoutContainer();
      VBoxLayout vLayout = new VBoxLayout();
      vLayout.setVBoxLayoutAlign(VBoxLayoutAlign.CENTER);
      warningContainer.setLayout(vLayout);

      LabelField obsoleteTitle = new LabelField("title is obsolete");
      obsoleteTitle.addStyleName("custom-label");
      obsoleteTitle.addStyleName("text-bold");

      Image icon = new Image("resources/images/warning.png");
      WidgetComponent widgetComponent = new WidgetComponent(icon);
      widgetComponent.setSize(61, 55);

      LabelField obsoleteMessage = new LabelField("obsolete message");
      obsoleteMessage.addStyleName("custom-label");
      obsoleteMessage.addStyleName("text-align-justify");

      warningContainer.add(widgetComponent, new VBoxLayoutData(0, 0, 0, 0));
      warningContainer.add(obsoleteTitle, new VBoxLayoutData(0, 0, 0, 0));
      warningContainer.add(obsoleteMessage, new VBoxLayoutData(0, 0, 10, 0));
      warningContainer.setWidth(320);
      warningContainer.setHeight(200);
      add(warningContainer);
    }

    loginFailedLabel = new LabelField();
    loginFailedLabel.setUseHtml(false);
    loginFailedLabel.addStyleName("error-text");
    loginFailedLabel.setVisible(false);

    add(loginFailedLabel);
    userNameTextField.setFieldLabel("User name");

    add(userNameTextField);

    passwordTextField.setPassword(true);
    passwordTextField.setAllowBlank(false);
    passwordTextField.setFieldLabel("Password");
    passwordTextField.addKeyListener(new KeyListener() {
      @Override
      public void componentKeyPress(ComponentEvent event) {
        if (event.getKeyCode() == KeyCodes.KEY_ENTER) {
          clickLoginButton();
        }
      }
    });
    add(passwordTextField);
    setFocusWidget(userNameTextField);

    fillButtons();

    loginButton.addSelectionListener(new SelectionListener<ButtonEvent>() {
      @Override
      public void componentSelected(ButtonEvent ce) {
        BorderLayoutExample borderLayoutExample = new BorderLayoutExample();
        borderLayoutExample.show();
        RootPanel.get().add(borderLayoutExample);
      }
    });

    createLoginFieldValidation();
    createPasswordFieldValidation();
  }

  private void createLoginFieldValidation() {
    /* because of the setFocusWidget, normal setAllowBlank(false)
       cannot be used. Because of it, the field is always
       automatically marked as invalid. Do with blur instead
     */
    userNameTextField.addListener(Events.OnBlur, new Listener<BaseEvent>() {
      @Override
      public void handleEvent(BaseEvent be) {
        if (getUsername().isEmpty()) {
          userNameTextField.markInvalid("user name is invalid");
        } else {
          userNameTextField.clearInvalid();
        }
      }
    });
  }

  private void createPasswordFieldValidation() {
    passwordTextField.addListener(Events.OnBlur, new Listener<BaseEvent>() {
      @Override
      public void handleEvent(BaseEvent be) {
        if (getPassword().isEmpty()) {
          passwordTextField.markInvalid("pass is wrong");
        } else {
          passwordTextField.clearInvalid();
        }
      }
    });
  }

  protected void displayErrorMessage(String message) {
    loginFailedLabel.setValue(message);
    loginFailedLabel.setVisible(true);
  }

    protected void setEnabledAllButtons(boolean enabled) {
        if( loginButton != null ) {
            loginButton.setEnabled( enabled );
        }
        if( forgotPasswordButton != null ) {
            forgotPasswordButton.setEnabled( enabled );
        }
        if( cleanButton != null ) {
            cleanButton.setEnabled( enabled );
        }
        if( cancelButton != null ) {
            cancelButton.setEnabled( enabled );
        }
    }

    protected void fillButtons() {
        if( loginButton != null ) {
            addButton( loginButton );
        }
        if( forgotPasswordButton != null ) {
            addButton( forgotPasswordButton );
        }
        if( cleanButton != null ) {
            addButton( cleanButton );
        }
        if( cancelButton != null ) {
            addButton( cancelButton );
        }
    }

//  protected boolean createButtonsByUser(UserBO loggedUser) {
//
//      loginButton = createLoginButton();
//      if( loggedUser == null) {
//          userNameTextField.setValue("");
//          userNameTextField.setEnabled(true);
//          cleanButton = createCleanButton();
//          forgotPasswordButton = createForgotPasswordButton();
//          cancelButton = null;
//          return true;
//      } else {
//          userNameTextField.setValue(loggedUser.getLogin());
//          userNameTextField.setEnabled(false);
//          cleanButton = null;
//          forgotPasswordButton = null;
//          cancelButton = createCancelButton();
//          passwordTextField.focus();
//          return false;
//      }
//  }
//
//
//  protected Button createLoginButton() {
//      Button button = new Button(i18Constants.loginDialog_loginButton_value());
//      button.setId(ObjectIds.LOGINDIALOG_LOGIN_BUTTON_ID);
//      button.addListener(Events.OnClick, new Listener<BaseEvent>() {
//        @Override
//        public void handleEvent(BaseEvent be) {
//          final String username = getUsername();
//          final String password = passwordTextField.getValue() == null ? "" : passwordTextField.getValue();
//
//          // if there is no username or password, don't do anything
//          if (username.isEmpty() || password.isEmpty()) {
//            return;
//          }
//
//          setEnabledAllButtons(false);
//          loginService.loginUser(username, password.toCharArray(), new ScorecardAsyncCallback<UserBO>() {
//            @Override
//            public void handleFailure(Throwable caught) {
//                if( caught instanceof ThirdPartyLoginException) {
//                    displayErrorMessage(i18Constants.loginDialog_message_loginFailedThirdParty());
//                } else
//              if (caught instanceof LoginFailedException) {
//                switch (((LoginFailedException) caught).getLockStatus()){
//                    case PASSWORD_EXPIRED:
//                        openPasswordRenewalDialog();
//                        break;
//                    case ABOUT_TO_BE_LOCKED:
//                        displayErrorMessage(i18Constants.loginDialog_message_loginFailedAboutToLock());
//                        break;
//                    case LOCKED:
//                        displayErrorMessage(i18Constants.loginDialog_message_loginFailedLocked());
//                        break;
//                    case PASSWORD_INCORRECT:
//                    case USERNAME_NOT_FOUND:
//                    case NOT_LOCKED:
//                    default:
//                        displayErrorMessage(i18Constants.loginDialog_message_loginFailedLabel());
//                }
//
//              } else { //RequestFailedException
//                ScorecardMessageBox.alert(i18Messages.loginDialog_massages_onFailure_otherException_title(), i18Messages.loginDialog_massages_onFailure_otherException_text(), null);
//              }
//              setEnabledAllButtons(true);
//              if( client != null ) {
//                  client.onFailure();
//              }
//            }
//
//            @Override
//            public void handleSuccess(UserBO result) {
//              hide();
//              PlayerCallback.sendEvent2Player(Js2Applet.UNMASK, "");
//              if (client != null) {
//                client.onSuccess(result);
//              }
//            }
//          });
//        }
//      });
//      return button;
//  }

  @Override
  protected void onShow() {
    super.onShow();
//    PlayerCallback.sendEvent2Player(Js2Applet.MASK, "");
  }

  protected Button createCleanButton() {
      Button cleanButton = new Button("clean button");
      cleanButton.addListener(Events.OnClick, new Listener<BaseEvent>() {
        @Override
        public void handleEvent(BaseEvent be) {
          userNameTextField.setValue("");
          passwordTextField.setValue("");
          userNameTextField.focus();
        }
      });
      return cleanButton;
  }

//  protected Button createForgotPasswordButton() {
//      Button button = new Button(i18Constants.loginDialog_forgotPassword_value());
//      button.setId(ObjectIds.LOGINDIALOG_FORGOT_PASSWORD_BUTTON_ID);
//      button.addListener(Events.OnClick, new Listener<BaseEvent>() {
//        @Override
//        public void handleEvent(BaseEvent be) {
//          final MessageBox mb = ScorecardMessageBox.prompt(i18Messages.loginDialog_forgotPassword_confirmTitle(), i18Messages.loginDialog_forgotPassword_promptMessage(), new Listener<MessageBoxEvent>() {
//            @Override
//            public void handleEvent(MessageBoxEvent be) {
//              if (be.getButtonClicked().getItemId().equals(Dialog.OK)) {
//                final String login = be.getValue();
//                loginService.generateTemporaryPassword(login, new ScorecardAsyncCallback<Void>() {
//                  @Override
//                  public void handleSuccess(Void ignored) {
//                    ScorecardMessageBox.alert(i18Messages.loginDialog_forgotPassword_confirmTitle(), i18Messages.loginDialog_forgotPassword_passwordSentSuccess(login), null);
//                  }
//
//                  @Override
//                  public void handleFailure(Throwable th) {
//                      /* See SC-2758 */
//                    if (th instanceof NoSuchEntityException) {
//
//                      ScorecardMessageBox.alert(i18Messages.loginDialog_forgotPassword_confirmTitle(), i18Messages.loginDialog_forgotPassword_userNotFound(), null);
//
//                    } else if (th instanceof NotAllowedException) {
//
//                      ScorecardMessageBox.alert(i18Messages.loginDialog_forgotPassword_confirmTitle(), i18Messages.loginDialog_forgotPassword_userIsExternal(), null);
//
//                    } else {
//
//                      ScorecardMessageBox.alert(i18Messages.loginDialog_forgotPassword_confirmTitle(), i18Messages.general_message_rpcfailed( "LD265" ), null);
//
//                    }
//                  }
//                });
//              }
//            }
//          });
//
//          final Button okButton = mb.getDialog().getButtonById(Dialog.OK);
//
//          // listener on the text field. enables/disables the OK button
//          mb.getTextBox().addListener(Events.KeyUp, new Listener<FieldEvent>() {
//            @Override
//            public void handleEvent(FieldEvent be) {
//              String text = ((String) be.getField().getValue()).trim();
//              if (text != null && !text.isEmpty()) {
//                okButton.enable();
//              } else {
//                okButton.disable();
//              }
//            }
//          });
//
//          /* the textbox may contain the user login from the login window.
//            in that case, it must not be disabled
//          */
//          if (userNameTextField.getValue() != null && !userNameTextField.getValue().trim().isEmpty()) {
//            mb.getTextBox().setValue(userNameTextField.getValue());
//            okButton.enable();
//          } else {
//            okButton.disable();
//          }
//
//        }
//      });
//      return button;
//  }
//
//  protected Button createCancelButton() {
//      Button cancelButton = new Button(i18Constants.loginDialog_cancelButton_value());
//      cancelButton.setId(ObjectIds.LOGINDIALOG_CANCEL_BUTTON_ID);
//      cancelButton.addListener(Events.OnClick, new Listener<BaseEvent>() {
//        @Override
//        public void handleEvent(BaseEvent be) {
//            Scorecard.getInstance().restart();
//            hide();
//        }
//      });
//      return cancelButton;
//  }

  public String getUsername() {
      return userNameTextField.getValue() == null ? "" : userNameTextField.getValue();
  }

  public void setUsername(String username) {
      userNameTextField.setValue( username );
  }

  public String getPassword() {
      return passwordTextField.getValue() == null ? "" : passwordTextField.getValue();
  }

  public void setPassword(String password) {
     passwordTextField.setValue( password );
  }

  protected boolean clickLoginButton() {
      if( loginButton != null) {
          loginButton.fireEvent( Events.OnClick );
          return true;
      }
      return false;
  }

  protected boolean clickCleanButton() {
      if( cleanButton != null ) {
          cleanButton.fireEvent( Events.OnClick );
          return true;
      }
      return false;
  }

  protected boolean clickForgotPasswordButton() {
      if( forgotPasswordButton != null ) {
          forgotPasswordButton.fireEvent( Events.OnClick );
          return true;
      }
      return false;
  }

  protected boolean clickCancelButton() {
      if( cancelButton != null ) {
          cancelButton.fireEvent( Events.OnClick );
          return true;
      }
      return false;
  }

  @Override
  protected final void createButtons() {
      /* we don't want to be buttons set here
       * because this method is called from parent constructor */
  }

//  public void openPasswordRenewalDialog() {
//
//      PasswordChangeDialogLoginServiceCallback loginServiceCallback = new PasswordChangeDialogLoginServiceCallback( this, getUsername() );
//      PasswordChangeDialog dialog = new PasswordChangeDialog( loginServiceCallback, getPassword() );
//      dialog.displayHeaderLabel( i18Messages.password_change_dialog_password_has_expired() );
//      dialog.show();
//  }

//    public interface LoginClient {
//
//        void onSuccess(UserBO user);
//
//        void onFailure();
//    }
}
