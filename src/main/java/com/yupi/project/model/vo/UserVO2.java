package com.yupi.project.model.vo;

import com.hetu.common.model.entity.User;

/**
 * @BelongsProject: OpenAPI-backend
 * @BelongsPackage: com.yupi.project.model.vo
 * @ClassName: UserVo2
 * @Description: TODO
 * @Author: FengZH
 * @Date: 2023/08/28 16:06:05
 * @Version: V1.0
 **/
public class UserVO2 extends User implements java.io.Serializable {
    /**
     * id
     */
    private Long id;

    private String accessKey;

    private String secretKey;

    private static final long serialVersionUID = 1L;

}
