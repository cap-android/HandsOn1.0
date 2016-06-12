package com.xmz.handson10.data;

/**
 * Created by xmz on 2016/5/27.
 *
 * 用于调取不同的数据库，恢复玩家上次的存储数据，单例
 */
public class DocumentedDatabaseNameFactory {

    private static DocumentedDatabaseNameFactory INSTANCE;

    private final String mDatabaseName_prefix = "handson";

    private final String default_index = "00";

    private String mDatabaseName;

    private DocumentedDatabaseNameFactory(String documentedIndex) {

        mDatabaseName = this.mDatabaseName_prefix + documentedIndex;
    }

    private DocumentedDatabaseNameFactory() {
        mDatabaseName = mDatabaseName_prefix + default_index;
    }

/*
*  documentedIndex为用户选择的存档编号
*  生成的数据库名将是 handson+编号
*/
    public static String getDatabaseName(String documentedIndex) {
        if (INSTANCE == null) {
            INSTANCE = new DocumentedDatabaseNameFactory(documentedIndex);
        }
        return INSTANCE.mDatabaseName;
    }

    /*
    * 默认的数据库编号为00，对应的数据库名为 handson00
    * */
    public static String getDatabaseName() {
        if (INSTANCE == null) {
            INSTANCE = new DocumentedDatabaseNameFactory();
        }
        return INSTANCE.mDatabaseName;
    }

}
