package org.jeecg.modules.wechat.constant;

/**
 * 微信公众号常量
 * @author KicoChan
 */
public class WechatConstant {

    // AppID
    public static final String APP_ID = "wx35360545b97a547b";

    // Secret
    public static final String SECRET = "ae325b78c21d83a49295ae2749e09d46";

    public static final String ACCESS_TOKEN_KEY = "wechatToken";

    public static final long EXPIRE_ID = 7200;

    // 获取公众号用户Token Url
    public static final String GET_USER_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

    // 校验Token url
    public static final String VERIFY_TOKEN_URL = "https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID";

    // 刷新Token
    public static final String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";


}
