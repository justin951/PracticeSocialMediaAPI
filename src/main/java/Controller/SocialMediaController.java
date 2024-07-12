package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import io.javalin.Javalin;
import io.javalin.http.Context;

public class SocialMediaController {
  AccountService accountService;
  MessageService messageService;

  public SocialMediaController() {
    this.accountService = new AccountService();
    this.messageService = new MessageService();
  }

  /**
   * In order for the test cases to work, you will need to write the endpoints in
   * the startAPI() method, as the test
   * suite must receive a Javalin object from this method.
   * 
   * @return a Javalin app object which defines the behavior of the Javalin
   *         controller.
   */
  public Javalin startAPI() {
    Javalin app = Javalin.create();
    // app.get("example-endpoint", this::exampleHandler);
    app.post("/register", this::postAccountHandler);
    app.post("/login", this::postLoginHandler);
    app.post("/messages", this::postMessageHandler);
    app.get("/messages", this::getAllMessages);
    app.get("/messages/{message_id}", this::getMessageByID);
    app.delete("/messages/{message_id}", this::deleteMessageByID);
    app.patch("/messages/{message_id}", this::updateMessageByID);
    app.get("/accounts/{account_id}/messages", this::getAllMessagesByAccountID);

    return app;
  }

  // TODO #1 COMPLETE: account registration
  private void postAccountHandler(Context ctx) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    Account account = mapper.readValue(ctx.body(), Account.class);

    if (account.getUsername().isEmpty() || account.getPassword().length() < 4) {
      ctx.status(400);
      return;
    }

    Account createdAccount = accountService.createAccount(account);
    if (createdAccount != null) {
      ctx.json(mapper.writeValueAsString(createdAccount));
    } else {
      ctx.status(400);
    }
  }

  // TODO #2 COMPLETE: account login
  private void postLoginHandler(Context ctx) throws JsonMappingException, JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();

    Account account = mapper.readValue(ctx.body(), Account.class);

    Account accountLogin = accountService.loginAccount(account);

    if (accountLogin != null) {
      Account accountWithID = new Account();

      accountWithID.setAccount_id(accountLogin.getAccount_id());
      accountWithID.setUsername(accountLogin.getUsername());
      accountWithID.setPassword(accountLogin.getPassword());

      ctx.json(mapper.writeValueAsString(accountWithID));
    } else {
      ctx.status(401);
    }
  }

  // TODO #3 COMPLETE: create message
  private void postMessageHandler(Context ctx) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    Message message = mapper.readValue(ctx.body(), Message.class);

    // tripple or statement
    if (message.getMessage_text() == null || message.getMessage_text().isEmpty()
        || message.getMessage_text().length() > 255) {
      ctx.status(400);
      return;
    }

    Message createdMessage = messageService.createMessage(message);
    if (createdMessage != null) {
      ctx.json(mapper.writeValueAsString(createdMessage));
    } else {
      ctx.status(400);
    }
  }

  // TODO #4 COMPLETE: get all messages
  private void getAllMessages(Context ctx) {
    List<Message> messages = messageService.getAllMessages();
    if (messages != null) {
      ctx.json(messages);
    } else {
      ctx.status(400);
    }
  }

  // TODO #5 COMPLETE: get message by ID
  private void getMessageByID(Context ctx) {
    int messageID = Integer.parseInt(ctx.pathParam("message_id"));
    Message message = messageService.getMessageByID(messageID);
    if (message != null) {
      ctx.json(message);
    } else {
      ctx.status(200);
    }
  }

  // TODO #6 COMPLETED: delete message by ID
  private void deleteMessageByID(Context ctx) {
    int messageID = Integer.parseInt(ctx.pathParam("message_id"));
    Message deletedMessage = messageService.deleteMessageByID(messageID);
    if (deletedMessage != null) {
      ctx.status(200).json(deletedMessage);
    } else {
      ctx.status(200);
    }
  }

  // TODO #7 COMPLETE: update message by ID
  private void updateMessageByID(Context ctx) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();

    Message message = mapper.readValue(ctx.body(), Message.class);

    int message_id = Integer.parseInt(ctx.pathParam("message_id"));

    Message updatedMessage = messageService.updateMessageByID(message_id, message);

    if (updatedMessage == null) {
      ctx.status(400);
    } else {
      ctx.json(mapper.writeValueAsString(updatedMessage));
    }
  }

  // TODO #8 COMPLETE: get all messages by user
  private void getAllMessagesByAccountID(Context ctx) {
    int account_id = Integer.parseInt(ctx.pathParam("account_id"));

    List<Message> messages = messageService.getMessagesByAccountID(account_id);

    if (messages.size() > 0) {
      ctx.json(messages);
      System.out.println("messages found");
    } else {
      ctx.json(messages);
      System.out.println("no messages found");
    }
  }

}