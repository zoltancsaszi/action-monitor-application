package net.zoltancsaszi.actionmonitor.repository;

import lombok.extern.slf4j.Slf4j;
import org.h2.api.Trigger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * A H2 database stored procedure implementation called by a trigger.
 *
 * @author Zoltan Csaszi
 */
@Slf4j
public class MonitoringTrigger implements Trigger {

    @Override
    public void init(Connection conn, String schemaName,
                     String triggerName, String tableName,
                     boolean before, int type) throws SQLException {
        if (log.isDebugEnabled()) {
            log.debug("Trigger init: " + schemaName + ", " + triggerName + ", "
                    + tableName + ", " + before + ", " + type);
        }
    }

    @Override
    public void fire(Connection conn, Object[] oldRow, Object[] newRow)
            throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("Trigger fired on Wallet. :old{" + Arrays.toString(oldRow)
                    + "} :new{" + Arrays.toString(newRow) + "}");
        }

        String eventType = "updated";

        if (oldRow == null) {
            eventType = "inserted";
        }

        long id = (long) newRow[0];

        PreparedStatement prep = conn.prepareStatement(
                "INSERT INTO AuditLog" +
                        "(TABLE_NAME, PRIM_KEY, TIMESTAMP_, EVENT_TYPE) " +
                        "VALUES('Wallet', ?, ?, ?)");

        prep.setBigDecimal(1, BigDecimal.valueOf(id));
        prep.setBigDecimal(2, BigDecimal.valueOf(System.currentTimeMillis()));
        prep.setString(3, eventType);
        prep.execute();
    }

    @Override
    public void close() throws SQLException {
        if (log.isDebugEnabled()) {
            log.debug("Trigger closed.");
        }
    }

    @Override
    public void remove() throws SQLException {
        if (log.isDebugEnabled()) {
            log.debug("Trigger removed.");
        }
    }
}
