package cn.daenx.myadmin.common.encryptField.core.encryptor;


import cn.daenx.myadmin.common.encryptField.core.EncryptContext;
import cn.daenx.myadmin.common.encryptField.core.IEncryptor;

/**
 * 所有加密执行者的基类
 *
 * @author 老马
 * @version 4.6.0
 */
public abstract class AbstractEncryptor implements IEncryptor {

    public AbstractEncryptor(EncryptContext context) {
        // 用户配置校验与配置注入
    }

}
