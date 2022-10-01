package pojo.po;

import dao.DbField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author WARRIOR
 * @version 1.0
 * 单条聊天记录
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRecord {
    @DbField("chat_id")
    private Integer chatRecordId;
    @DbField("send_user_id")
    private User sendUser;
    @DbField("receive_user_id")
    private User receiveUser;
    @DbField("time")
    private Date sendTime;
}
