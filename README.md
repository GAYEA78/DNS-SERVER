# DNS-SERVER

This project is a DNS Server implementation written in Java. It is designed to handle DNS queries and provide responses based on data stored in a zone file. The server supports DNS functionality, including resolving queries from its zone, caching DNS records, and forwarding unresolved queries to an upstream DNS server.

## Features

The DNS server provides the following core features:
- **Zone File Parsing**: The server reads and parses a DNS zone file containing records such as `A`, `CNAME`, and `SOA`. It stores these records in memory for efficient query resolution.
- **Caching**: A caching mechanism is implemented to store resolved DNS records temporarily, reducing the need for repeated queries to the upstream server.
- **Query Forwarding**: If a requested record is not found in the server's zone or cache, the query is forwarded to an upstream DNS resolver (default: `127.0.0.53`).
- **UDP Communication**: The server uses UDP to send and receive DNS packets on port `53`, adhering to DNS protocol specifications.

## Components

1. **DNSZone**: Parses the zone file and provides a list of DNS records for the server to use.
2. **DNSRecord**: Represents individual DNS records with attributes such as name, type, class, TTL, and data.
3. **DNSCache**: Handles temporary storage of resolved DNS records, improving efficiency by avoiding redundant queries.
4. **DNSMessage**: Manages the creation and parsing of DNS messages, including queries and responses.
5. **DNSServer**: The main server class that handles incoming DNS requests, manages zone and cache records, and interacts with the upstream server.

## Requirements

- **Java Development Kit (JDK)**: Version 11 or higher.
- A DNS zone file with records in the format:  
  
  NAME TTL CLASS TYPE RDATA
  
  Example:
  example.com 3600 IN A 192.0.2.1
  "www.example.com" 3600 IN CNAME "example.com"
  

## Usage

1. **Compile the Project**:
   Compile all Java files in the `dns` package:
   javac dns/*.java

2. **Run the Server**:
   Launch the server by providing a valid zone file:
 
   sudo java dns.DNSServer path_to_zone_file
   
   Note: Root privileges (`sudo`) are required to bind to port `53`.

4. **Test the Server**:
   Use tools like `dig` or `nslookup` to query the server:

   dig @127.0.0.1 -p 53 "example.com"
 

## Notes

- By default, the server forwards unresolved queries to `127.0.0.53`, the stub resolver used by recent Ubuntu systems. This can be modified in the `DNSServer` class to point to a public DNS server like `8.8.8.8` (Google).
- The server currently supports a limited set of DNS record types (`A` and `CNAME`) and assumes all records belong to the `IN` class.

## Limitations

- The server is single-threaded and may not handle high traffic efficiently.
- Error handling for zone file parsing and network operations is basic and may cause the server to terminate on errors.
- Additional DNS features like DNSSEC and EDNS are not supported.

## Future Improvements

- Add support for more DNS record types (e.g., `MX`, `AAAA`).
- Implement multithreading for better performance.
- Provide a configurable upstream server address and port.
- Enhance error handling and logging for production use.
