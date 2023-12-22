package org.bertural.deepforge;

/**
 * DataModelConstants
 */
public class DMC {

    public class TYPE {
        public static final String DATETIME = "org.jadira.usertype.dateandtime.joda.PersistentDateTime";
        public static final String LOCAL_DATETIME = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime";
        public static final String LOCAL_TIME = "org.jadira.usertype.dateandtime.joda.PersistentLocalTime";
        public static final String LOCAL_DATE = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate";
        public static final String DURATION = "org.jadira.usertype.dateandtime.joda.PersistentDurationAsMillisLong"; // Maps a Duration to and from Long for Hibernate.
    }
}
