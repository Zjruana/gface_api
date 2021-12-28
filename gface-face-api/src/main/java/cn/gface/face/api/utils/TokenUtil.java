package cn.gface.face.api.utils;

import cn.gface.face.api.data.TBUserInfo;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @desc 使用token验证用户是否登录
 **/
public class TokenUtil {
    //设置过期时间
    private static final long EXPIRE_DATE = 7 * 24 * 60 * 60 * 1000; // 7 * 24小时
    //    private static final long EXPIRE_DATE = 1 * 60 * 1000; // 1分钟
    //token秘钥
    private static final String TOKEN_SECRET = "*&0909!@G_FACE1234@FACECOM00";

    public static String setToken(TBUserInfo tbUserInfo) {
        String token = "";
        try {
            //过期时间
            Date date = new Date(System.currentTimeMillis() + EXPIRE_DATE);
            //秘钥及加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            //设置头部信息
            Map<String, Object> header = new HashMap<>();
            header.put("typ", "JWT");
            header.put("alg", "HS256");
            //携带username，password信息，生成签名
            token = JWT.create()
                    .withHeader(header)
                    .withClaim("username", tbUserInfo.getUserName())
                    .withClaim("role", tbUserInfo.getRole())
//                    .withClaim("name", tbUserInfo.getName())
//                    .withClaim("phoneNumber", tbUserInfo.getPhoneNumber())
//                    .withClaim("email", tbUserInfo.getEmail())
//                    .withClaim("sex", tbUserInfo.getSex())
//                    .withClaim("profile", tbUserInfo.getProfile())
//                    .withClaim("createTime", tbUserInfo.getCreateTime())
//                    .withClaim("avatar", tbUserInfo.getAvatar())
                    .withClaim("pid", tbUserInfo.getPid())
                    .withClaim("id", tbUserInfo.getId())
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return token;
    }

    /**
     * 验证token，通过返回true
     *
     * @param token
     * @return
     */
    public static boolean verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e) {
            System.out.println("token verify ==============> NO NO NO NO");
            return false;
        }
    }

    /**
     * 解码 token
     *
     * @param token
     * @return
     */
    public static Map<String, Object> getToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return new HashMap<String, Object>() {{
                put("username", jwt.getClaim("username").asString());
//                put("name", jwt.getClaim("name").asString());
//                put("phoneNumber", jwt.getClaim("phoneNumber").asString());
//                put("email", jwt.getClaim("email").asString());
//                put("profile", jwt.getClaim("profile").asString());
//                put("createTime", jwt.getClaim("createTime").asString());
//                put("avatar", jwt.getClaim("avatar").asString());
//                put("sex", jwt.getClaim("sex").asInt());
                put("pid", jwt.getClaim("pid").asInt());
                put("id", jwt.getClaim("id").asInt());
                put("role", jwt.getClaim("role").asString());
            }};
        } catch (Exception e) {
            return null;
        }
    }
}
