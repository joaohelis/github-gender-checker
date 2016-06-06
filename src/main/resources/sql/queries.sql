
-- http://ghtorrent.org/dblite/
-- Export data format - CSV

SELECT u.id, u.login, u.location, count(*) AS followers 
FROM users u, followers f
WHERE u.id = f.user_id AND u.country_code = 'br' AND u.type = 'USR' AND u.deleted = FALSE AND
(EXISTS (SELECT 1 FROM commits WHERE author_id = u.id AND 
         created_at BETWEEN (CURRENT_DATE() - INTERVAL 3 MONTH) AND CURRENT_DATE()) OR
 EXISTS (SELECT 1 FROM issue_events WHERE actor_id = u.id AND 
         created_at BETWEEN (CURRENT_DATE() - INTERVAL 3 MONTH) AND CURRENT_DATE()))
GROUP BY u.id
ORDER BY followers DESC;