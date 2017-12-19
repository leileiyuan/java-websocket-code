package jwsp.chapter8.data;

import java.util.*;

public class UserListUpdateMessage extends StructuredMessage {

    public UserListUpdateMessage(List usernames) {
        super(ChatMessage.USERLIST_UPDATE, usernames);
    }

    public List getUserList() {
        return super.dataList;
    }

}
