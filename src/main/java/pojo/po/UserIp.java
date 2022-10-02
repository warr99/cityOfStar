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
public class UserIp {
    @DbField("ip_id")
    private Integer userIpId;
    @DbField("ip_head")
    private Integer ipHead;
    @DbField("ip_up_hue")
    private Integer ipUpHue;
    @DbField("ip_up_saturation")
    private Integer ipUpSaturation;
    @DbField("ip_up_lightness")
    private Integer ipUpLightness;
    @DbField("ip_low_hue")
    private Integer ipLowHue;
    @DbField("ip_low_saturation")
    private Integer ipLowSaturation;
    @DbField("ip_low_lightness")
    private Integer ipLowLightness;

    public UserIp(Integer userIpId) {
        this.userIpId = userIpId;
    }
}
