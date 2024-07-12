package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    // TODO #3 COMPLETE: create new messages
    public Message createMessage(Message message) {
        return messageDAO.createMessage(message);
    }

    // TODO #4 COMPLETE: get all messages
    public List<Message> getAllMessages() {
        List<Message> allMessages = messageDAO.getAllMessages();

        return allMessages;
    }

    // TODO #5 COMPLETE: get message by ID
    public Message getMessageByID(int message_id) {
        return messageDAO.getMessageByID(message_id);
    }

    // TODO #6 COMPLETE: delete message by ID
    public Message deleteMessageByID(int message_id) {
        return messageDAO.deleteMessageByID(message_id);
    }

    // TODO #7 COMPLETE: update message by ID
    public Message updateMessageByID(int message_id, Message message) {
        Message existingMessage = messageDAO.getMessageByID(message_id);

        if (existingMessage == null) {
            return null;
        }

        String messageText = message.getMessage_text();

        if (messageText.length() > 0 && messageText.length() <= 255) {
            Message updatedMessage = new Message(
                    message_id,
                    message.getPosted_by(),
                    messageText,
                    message.getTime_posted_epoch());

            return messageDAO.updateMessageByID(message_id, updatedMessage);
        }

        return null;
    }

    // TODO #8 COMPLETE: get all messages by user
    public List<Message> getMessagesByAccountID(int account_id) {
        List<Message> messagesByAccountID = messageDAO.getMessagesByAccountID(account_id);

        return messagesByAccountID;
    }
}
