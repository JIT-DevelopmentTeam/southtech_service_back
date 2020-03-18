package org.jeecg.modules.management;

import java.sql.Timestamp;

public class BaseEntity {

    private Timestamp modifytime;

    public Timestamp getModifytime() {
        return modifytime;
    }

    public void setModifytime(Timestamp modifytime) {
        this.modifytime = modifytime;
    }
}
