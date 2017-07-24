CREATE INDEX IF NOT EXISTS app_books_idx ON BOOKS(app) WHERE app = TRUE;
CREATE INDEX IF NOT EXISTS user_books_idx ON BOOKS(author, app) WHERE app = FALSE;