package zz.kidog.Diamond.property;

import zz.kidog.Diamond.utils.Messages;

public enum ServerProperty {

    ONLINE, MAXIMUM, STATUS, MOTD, TPS, LASTUPDATED, GROUP;

    public String getJedisId() {
        return "server-data-" + Messages.getServerName() + ":" + this.name().toLowerCase();
    }
}