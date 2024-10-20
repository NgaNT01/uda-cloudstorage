-----------------------------------------------------------------
-- Users
-----------------------------------------------------------------
INSERT INTO
    USERS (userid, username, salt, password, firstname, lastname)
VALUES
    (null, 'test', 'hY3HOQwKt4m8KxM36yNf9w==', 'EtvSh5FQFS/97Dc3MaOyT1Yu2dQt6rBpWbYGGt079+raylMZvvPKVCTPvj/A53lmN6Nryfb1n8GLjhygbp6bcx51JJQOU0ILQ++IeQ5TT2JrKb9zBzu2h5VuyMOCEa0g7HexWn6No3UYhrsAzgAlSkLJYb1dMfLAjs297HTpQm9ZDEQe3/cGutCAcLIjwxK0rGap0VK2sdBF9cu9yrtpDtWkEyh5sGJyJ4eYRpVx7NAfZwKWth0oRPuuPikvv93Unxx1Xo+OsXhAiDBtvbV6xX9ENUjSHVP+tBKetIBMmlC+8xHwjjUb/sGpxh8XLz/OAIUT+M5wpPonZERcBBP5NQ==', 'test', 'test');

-----------------------------------------------------------------
-- Notes
-----------------------------------------------------------------
INSERT INTO
    NOTES (id, title, description, userid)
VALUES
    (null, 'Meeting Notes', 'Discussion points from the team meeting.', 1),
    (null, 'Grocery List', 'Items to buy this weekend.', 1),
    (null, 'Project Ideas', 'Brainstorming new features for the app.', 1),
    (null, 'Travel Plans', 'Itinerary for the upcoming vacation.', 1);

-----------------------------------------------------------------
-- Credentials
-----------------------------------------------------------------
INSERT INTO
    CREDENTIALS (id, key, url, password, username, userid)
VALUES
    (null, 'N3bJk8k5TezF3A8kGfB1kA==', 'https://twitter.com', 'X9hZt2K6tF0gM8Z4P9cE4A==', 'test', 1),
    (null, 'Kq2uH1aT5Lp0N7C5Tj8V3w==', 'https://linkedin.com', 'Y7fD3zU5pK1tJ6Z8L4sH5C==', 'test', 1),
    (null, 'Qx2eP4bR8Bf3W6L9Kz7A1c==', 'https://github.com', 'Z8vL5wR3fH4jQ9B2M1hF2B==', 'test', 1);
