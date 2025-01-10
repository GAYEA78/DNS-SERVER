//Arona Gaye

package dns;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class representing a cache of stored DNS records.
 *
 * @version 1.0
 */
public class DNSCache {

    // List to store DNS records in the cache
    private ArrayList<DNSRecord> cacheRecords;

    // Constructor to initialize the cache as an empty list
    public DNSCache() {
        cacheRecords = new ArrayList<DNSRecord>();
    }

    /**
     * Adds a list of DNS records to the cache.
     *
     * @param newRecords The list of DNS records to add to the cache
     */
    public void addRecords(ArrayList<DNSRecord> newRecords) {
        cacheRecords.addAll(newRecords); // Appends all records to the cache
    }

    /**
     * Retrieves DNS records from the cache that match the given name, type, and class.
     *
     * @param name   The domain name to match
     * @param type   The record type to match (e.g., A, AAAA, CNAME)
     * @param rclass The record class to match (e.g., IN)
     * @return A list of matching DNS records
     */
    public ArrayList<DNSRecord> getRecords(String name, String type, String rclass) {
        var matches = new ArrayList<DNSRecord>(); // List to store matching records
        for (var record : cacheRecords) {
            // Check if the record matches the specified name, type, and class
            if (record.getName().equals(name) &&
                record.getClassStr().equals(rclass) &&
                record.getTypeStr().equals(type)) {
                matches.add(record); // Add matching record to the results
            }
        }
        return matches; // Return the list of matching records
    }

    /**
     * Removes expired DNS records from the cache.
     * A record is considered expired if its time-to-live (TTL) has elapsed.
     */
    public void removeExpiredRecords() {
        Iterator<DNSRecord> iterator = cacheRecords.iterator(); // Iterator for traversing the cache
        while (iterator.hasNext()) {
            DNSRecord record = iterator.next();
            // Calculate elapsed time since the record was added
            long elapsed = Duration.between(record.getTimestamp(), Instant.now()).getSeconds();
            // Remove the record if its TTL has expired
            if (elapsed >= record.getTTL()) {
                iterator.remove();
            }
        }
    }
}
