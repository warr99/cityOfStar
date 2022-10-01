package pojo.po;

import dao.DbField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author WARRIOR
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wish {
    @DbField("wish_id")
    private Integer wishId;
    @DbField("wish_context")
    private String wishContext;
}
